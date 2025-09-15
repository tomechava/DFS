# dfs_client.py (versión parcheada para evitar desorden de bloques y con checks)
import os
import math
import grpc
import shutil
from google.protobuf import empty_pb2
from google.protobuf import wrappers_pb2
from google.protobuf import message
from google.protobuf import timestamp_pb2
from google.protobuf import duration_pb2
from google.protobuf import any_pb2
from google.protobuf import struct_pb2
from google.protobuf import json_format

import dfs_pb2
import dfs_pb2_grpc

BLOCK_SIZE = 64 * 1024 * 1024  # 64 MB


class DFSClient:
    def __init__(self, namenode_addr="127.0.0.1:50052"):
        self.namenode_addr = namenode_addr
        channel = grpc.insecure_channel(namenode_addr)
        self.namenode_stub = dfs_pb2_grpc.NameNodeServiceStub(channel)

    # --- Funciones hacia el NameNode (NameNodeService) ---
    def putFile(self, username, password, filename):
        filesize = os.path.getsize(filename)
        req = dfs_pb2.PutFileRequest(
            username=username,
            password=password,
            filename=os.path.basename(filename),
            filesize=filesize,
        )
        return self.namenode_stub.PutFile(req)

    def getFile(self, username, password, filename):
        req = dfs_pb2.GetFileRequest(
            username=username, password=password, filename=filename
        )
        return self.namenode_stub.GetFile(req)

    def list_files(self, username, password):
        req = dfs_pb2.ListFilesRequest(username=username, password=password)
        return self.namenode_stub.ListFiles(req)

    def remove_file(self, username, password, filename):
        req = dfs_pb2.RemoveFileRequest(
            username=username, password=password, filename=filename
        )
        return self.namenode_stub.RemoveFile(req)

    # --- Funciones hacia los DataNodes (DataNodeService) ---
    def _get_datanode_stub(self, datanode_address):
        options = [
            ('grpc.max_send_message_length', 200 * 1024 * 1024),
            ('grpc.max_receive_message_length', 200 * 1024 * 1024),
        ]
        channel = grpc.insecure_channel(datanode_address, options=options)
        return dfs_pb2_grpc.DataNodeServiceStub(channel)

    def uploadBlock(self, datanode_address, block_id, filename, data: bytes):
        """Envía un bloque binario al DataNode (con debug ligero)."""
        stub = self._get_datanode_stub(datanode_address)
        req = dfs_pb2.BlockUploadRequest(
            block_id=block_id,
            filename=filename,
            data=data,
        )
        resp = stub.UploadBlock(req)
        # opcional: print para debug si lo deseas
        print(f"[CLIENT] uploadBlock -> addr={datanode_address} block_id={block_id} bytes={len(data)} ok={resp.success}")
        return resp

    def downloadBlock(self, datanode_address, block_id, filename):
        """Descarga un bloque binario del DataNode (con debug ligero)."""
        stub = self._get_datanode_stub(datanode_address)
        req = dfs_pb2.BlockDownloadRequest(block_id=block_id, filename=filename)
        resp = stub.DownloadBlock(req)
        print(f"[CLIENT] downloadBlock <- addr={datanode_address} block_id={block_id} bytes={len(resp.data)}")
        return resp

    # --- Operaciones de alto nivel (usan ambos) ---
    def putFile_and_upload(self, username, password, local_path):
        """
        Orquesta: pide asignación al NameNode y sube los bloques a DataNodes.
        IMPORTANTE: ordena put_resp.blocks por block_index antes de leer y subir.
        """
        put_resp = self.putFile(username, password, local_path)

        filesize = os.path.getsize(local_path)
        expected_parts = math.ceil(filesize / BLOCK_SIZE) if filesize > 0 else 1
        if len(put_resp.blocks) != expected_parts:
            print(f"[WARNING] NameNode devolvió {len(put_resp.blocks)} bloques pero se esperan {expected_parts} a partir de filesize={filesize}")

        # Ordenar por block_index para asegurar que el primer chunk leído
        # se asigne al bloque con block_index == 0, etc.
        ordered_blocks = sorted(put_resp.blocks, key=lambda b: b.block_index)

        with open(local_path, "rb") as f:
            for i, block in enumerate(ordered_blocks):
                data = f.read(BLOCK_SIZE)
                if data is None:
                    data = b''
                if len(data) == 0 and filesize != 0:
                    # Esto no debería pasar: significa que el archivo terminó antes de lo esperado
                    raise RuntimeError(f"[ERROR cliente] Leyendo archivo local: bloque {i} está vacío (offset incorrecto). filesize={filesize}")
                resp = self.uploadBlock(
                    datanode_address=block.primary_address,
                    block_id=block.block_id,
                    filename=os.path.basename(local_path),
                    data=data,
                )
                print(f"[PUT] Bloque {i} (id={block.block_id}) -> {block.primary_address}, ok={resp.success} bytes_sent={len(data)}")

        return "[DONE] Archivo subido."

    def getFile_and_download(self, username, password, filename, output_path):
        """
        Orquesta: pide mapa al NameNode y reconstruye el archivo desde DataNodes.
        Asegura orden correcto por block_index y valida que cada bloque tenga bytes.
        """
        get_resp = self.getFile(username, password, filename)

        if not get_resp.blocks:
            raise RuntimeError(f"[ERROR cliente] NameNode no devolvió bloques para {filename}")

        # Ordenamos los bloques por block_index (orden lógico)
        ordered_blocks = sorted(get_resp.blocks, key=lambda b: b.block_index)
        print(f"[CLIENT] getFile -> {len(ordered_blocks)} bloques (ordenados por block_index)")

        with open(output_path, "wb") as out_f:
            for i, block in enumerate(ordered_blocks):
                resp = self.downloadBlock(
                    datanode_address=block.primary_address,
                    block_id=block.block_id,
                    filename=filename,
                )
                received = len(resp.data)
                if received == 0:
                    # Falla duro aquí para que lo detectes inmediatamente
                    raise RuntimeError(f"[ERROR cliente] Bloque {block.block_id} recibido VACÍO desde {block.primary_address} (index={block.block_index})")
                out_f.write(resp.data)
                print(f"[GET] Bloque {i} (id={block.block_id}, index={block.block_index}) <- {block.primary_address} bytes_written={received}")

        return f"[DONE] Archivo reconstruido en {output_path}"

    def mkdir(self, username, password, path):
        req = dfs_pb2.MkdirRequest(username=username, path=path)
        resp = self.namenode_stub.Mkdir(req)
        if resp.success:
            try:
                os.makedirs(path, exist_ok=True)
                print(f"Directorio local '{path}' creado")
            except Exception as e:
                print(f"❌ Error al crear directorio local: {e}")
        return resp

    def rmdir(self, username, password, path):
        req = dfs_pb2.RmdirRequest(username=username, path=path)
        resp = self.namenode_stub.Rmdir(req)
        if resp.success:
            try:
                shutil.rmtree(path)
                print(f"Directorio local '{path}' eliminado")
            except FileNotFoundError:
                print(f"Directorio local '{path}' no existe")
            except Exception as e:
                print(f"Error al eliminar directorio local: {e}")
        return resp
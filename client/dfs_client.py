# dfs_client.py
import os
import grpc
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
            filesize=filesize
        )
        return self.namenode_stub.PutFile(req)

    def getFile(self, username, password, filename):
        req = dfs_pb2.GetFileRequest(
            username=username,
            password=password,
            filename=filename
        )
        return self.namenode_stub.GetFile(req)

    def list_files(self, username, password):
        req = dfs_pb2.ListFilesRequest(username=username, password=password)
        return self.namenode_stub.ListFiles(req)

    def remove_file(self, username, password, filename):
        req = dfs_pb2.RemoveFileRequest(
            username=username,
            password=password,
            filename=filename
        )
        return self.namenode_stub.RemoveFile(req)


    # --- Funciones hacia los DataNodes (DataNodeService) ---
    def _get_datanode_stub(self, datanode_address):
        channel = grpc.insecure_channel(datanode_address)
        return dfs_pb2_grpc.DataNodeServiceStub(channel)

    def uploadBlock(self, datanode_address, block_id, filename, data):
        stub = self._get_datanode_stub(datanode_address)
        req = dfs_pb2.BlockUploadRequest(
            block_id=block_id,
            filename=filename,
            data=data
        )
        return stub.UploadBlock(req)

    def downloadBlock(self, datanode_address, block_id, filename):
        stub = self._get_datanode_stub(datanode_address)
        req = dfs_pb2.BlockDownloadRequest(
            block_id=block_id,
            filename=filename
        )
        return stub.DownloadBlock(req)


    # --- Operaciones de alto nivel (usan ambos) ---

    def putFile_and_upload(self, username, password, local_path):
        """Orquesta: pide asignación al NameNode y sube los bloques a DataNodes."""
        # 1. pedir asignación al NN
        put_resp = self.putFile(username, password, local_path)

        # 2. leer archivo y enviar bloques según asignación
        filesize = os.path.getsize(local_path)
        with open(local_path, "rb") as f:
            for block in put_resp.blocks:
                # posición de bloque según id
                offset = block.block_id * BLOCK_SIZE
                f.seek(offset)
                data = f.read(BLOCK_SIZE)

                resp = self.uploadBlock(
                    datanode_address=block.datanode_address,
                    block_id=block.block_id,
                    filename=os.path.basename(local_path),
                    data=data
                )
                print(f"[PUT] Bloque {block.block_id} -> {block.datanode_address}, ok={resp.success}")

        return "[DONE] Archivo subido."

    def getFile_and_download(self, username, password, filename, output_path):
        """Orquesta: pide mapa al NameNode y reconstruye el archivo desde DataNodes."""
        get_resp = self.getFile(username, password, filename)

        with open(output_path, "wb") as out_f:
            for block in sorted(get_resp.blocks, key=lambda b: b.block_id):
                resp = self.downloadBlock(
                    datanode_address=block.datanode_address,
                    block_id=block.block_id,
                    filename=filename
                )
                out_f.write(resp.data)
                print(f"[GET] Bloque {block.block_id} <- {block.datanode_address}")

        return f"[DONE] Archivo reconstruido en {output_path}"

# cli.py
import sys
import cmd
from dfs_client import DFSClient
import os
import shutil

class DFSCLI(cmd.Cmd):
    intro = "Bienvenido al cliente DFS. Escribe 'help' para ver comandos.\n"
    prompt = "dfs> "

    def __init__(self, namenode_addr="127.0.0.1:50052"):
        super().__init__()
        self.client = DFSClient(namenode_addr)
        self.username = "test_user"
        self.password = "1234"
        self.cwd = "/"   # directorio actual en DFS

    # -------------------------
    # Comandos NameNodeService
    # -------------------------
    def do_putFile(self, arg):
        "Solicitar al NameNode la asignación de bloques para un archivo: putFile <path_local>"
        if not arg:
            print("Uso: putFile <ruta_local>")
            return
        resp = self.client.putFile(self.username, self.password, arg)
        for b in resp.blocks:
            print(f"Bloque {b.block_id} -> {b.primary_address}")
            if b.replica_addresses:
                print(f"   réplicas: {list(b.replica_addresses)}")


    def do_getFile(self, arg):
        "Obtener información de bloques de un archivo: getFile <nombre_archivo>"
        if not arg:
            print("Uso: getFile <archivo>")
            return
        resp = self.client.getFile(self.username, self.password, arg)
        for b in resp.blocks:
            print(f"Bloque {b.block_id} en {b.datanode_address}")

    def do_listFiles(self, arg):
        "Listar archivos registrados: listFiles"
        resp = self.client.list_files(self.username, self.password)
        print("Archivos:", list(resp.filenames))

    def do_removeFile(self, arg):
        "Eliminar un archivo del sistema: removeFile <archivo>"
        if not arg:
            print("Uso: removeFile <archivo>")
            return

        dfs_path, local_path = self.resolve_path(arg)
        resp = self.client.remove_file(self.username, self.password, dfs_path)

        if resp.success:
            try:
                if os.path.exists(local_path):
                    os.remove(local_path)
                    print(f"🗑️ Archivo local '{local_path}' eliminado")
                else:
                    print(f"⚠️ Archivo local '{local_path}' no existe")
            except Exception as e:
                print(f"❌ Error al eliminar archivo local: {e}")

        print(f"{resp.success} - {resp.message} (DFS={dfs_path}, Local={local_path})")

    # -------------------------
    # Comandos DataNodeService
    # -------------------------
    def do_uploadBlock(self, arg):
        "Subir un bloque a un DataNode: uploadBlock <addr> <block_id> <archivo> <offset>"
        try:
            addr, block_id, filename, offset = arg.split()
            block_id, offset = int(block_id), int(offset)
            with open(filename, "rb") as f:
                f.seek(offset)
                data = f.read(64 * 1024 * 1024)
            resp = self.client.uploadBlock(addr, block_id, filename, data)
            print(f"Upload -> {resp.success}")
        except Exception as e:
            print("Uso: uploadBlock <addr> <block_id> <archivo> <offset>")
            print(e)

    def do_downloadBlock(self, arg):
        "Descargar un bloque desde un DataNode: downloadBlock <addr> <block_id> <archivo> <salida>"
        try:
            addr, block_id, filename, output = arg.split()
            block_id = int(block_id)
            resp = self.client.downloadBlock(addr, block_id, filename)
            with open(output, "wb") as f:
                f.write(resp.data)
            print(f"Bloque {block_id} descargado en {output}")
        except Exception as e:
            print("Uso: downloadBlock <addr> <block_id> <archivo> <salida>")
            print(e)

    # -------------------------
    # Comandos Alto Nivel
    # -------------------------
    def do_putFile_and_upload(self, arg):
        "Subir archivo completo (NameNode + DataNodes): putFile_and_upload <ruta_local>"
        if not arg:
            print("Uso: putFile_and_upload <archivo>")
            return
        print(self.client.putFile_and_upload(self.username, self.password, arg))

    def do_getFile_and_download(self, arg):
        "Descargar archivo completo (NameNode + DataNodes): getFile_and_download <archivo> <salida>"
        try:
            filename, output = arg.split()

            # Usamos resolve_path para que el archivo de salida caiga en el cwd correcto
            dfs_path, local_output = self.resolve_path(output)

            # Crear carpetas intermedias si no existen
            os.makedirs(os.path.dirname(local_output), exist_ok=True)
            print(self.client.getFile_and_download(self.username, self.password, filename, local_output))
        except:
            print("Uso: getFile_and_download <archivo> <salida>")

    def do_cd(self, arg):
        "Cambiar directorio actual en el DFS: cd <ruta>"
        if not arg:
            print("Uso: cd <ruta>")
            return

        dfs_path, _ = self.resolve_path(arg)
        self.cwd = dfs_path
        print(f"Directorio actual: {self.cwd}")
    
    def do_mkdir(self, arg):
        "Crear directorio en el DFS: mkdir <nombre>"
        if not arg:
            print("Uso: mkdir <nombre>")
            return

        dfs_path, local_path = self.resolve_path(arg)
        resp = self.client.mkdir(self.username, self.password, dfs_path)
        if resp.success:
            try:
                os.makedirs(local_path, exist_ok=True)
            except Exception as e:
                print(f"❌ Error al crear directorio local: {e}")
        print(f"{resp.success} - {resp.message} (DFS={dfs_path}, Local={local_path})")

    def do_rmdir(self, arg):
        "Eliminar directorio en el DFS: rmdir <nombre>"
        if not arg:
            print("Uso: rmdir <nombre>")
            return

        dfs_path, local_path = self.resolve_path(arg)
        resp = self.client.rmdir(self.username, self.password, dfs_path)
        if resp.success:
            try:
                if os.path.exists(local_path):
                    shutil.rmtree(local_path)
                    print(f"🗑️ Directorio local '{local_path}' eliminado")
                else:
                    print(f"⚠️ Directorio local '{local_path}' no existe")
            except Exception as e:
                print(f"❌ Error al eliminar directorio local: {e}")
        print(f"{resp.success} - {resp.message} (DFS={dfs_path}, Local={local_path})")



    def resolve_path(self, arg):
        """Construye ruta DFS (para el servidor) y ruta local (para el cliente)"""
        # --- DFS PATH ---
        if arg.startswith("/"):
            dfs_path = os.path.normpath(arg).replace("\\", "/")
        else:
            dfs_path = os.path.normpath(os.path.join(self.cwd, arg)).replace("\\", "/")

        if not dfs_path.startswith("/"):
            dfs_path = "/" + dfs_path  # DFS siempre arranca con /

        # --- LOCAL PATH ---
        # quitar solo el primer slash inicial
        local_path = dfs_path.lstrip("/")  

        return dfs_path, local_path
    
    # -------------------------
    # Utilidades
    # -------------------------
    def do_exit(self, arg):
        "Salir del cliente DFS"
        print("Saliendo...")
        return True


if __name__ == "__main__":
    namenode_addr = "127.0.0.1:50052"
    if len(sys.argv) > 1:
        namenode_addr = sys.argv[1]
    DFSCLI(namenode_addr).cmdloop()

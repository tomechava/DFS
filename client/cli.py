# cli.py
import sys
import cmd
from dfs_client import DFSClient

class DFSCLI(cmd.Cmd):
    intro = "Bienvenido al cliente DFS. Escribe 'help' para ver comandos.\n"
    prompt = "dfs> "

    def __init__(self, namenode_addr="127.0.0.1:50052"):
        super().__init__()
        self.client = DFSClient(namenode_addr)
        self.username = "test_user"
        self.password = "1234"

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
            print(f"Bloque {b.block_id} -> {b.datanode_address}")

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
        resp = self.client.remove_file(self.username, self.password, arg)
        print(f"{resp.success} - {resp.message}")

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
            print(self.client.getFile_and_download(self.username, self.password, filename, output))
        except:
            print("Uso: getFile_and_download <archivo> <salida>")

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

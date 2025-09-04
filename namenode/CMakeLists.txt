cmake_minimum_required(VERSION 3.15)
project(NameNode LANGUAGES CXX)

# Necesitamos C++17
set(CMAKE_CXX_STANDARD 17)

# Buscar gRPC y Protobuf instalados
find_package(Protobuf CONFIG REQUIRED)
find_package(gRPC CONFIG REQUIRED)

# Archivos generados por protoc
set(PROTO_SRCS
    src/dfs.pb.cc
    src/dfs.grpc.pb.cc
)

set(PROTO_HDRS
    src/dfs.pb.h
    src/dfs.grpc.pb.h
)

# Archivos de tu implementación
set(SERVER_SRCS
    src/main.cpp
    src/NameNodeService.cpp
    ${PROTO_SRCS}
)

add_executable(namenode_server ${SERVER_SRCS})

# Vincular librerías necesarias
target_link_libraries(namenode_server
    gRPC::grpc++
    protobuf::libprotobuf
)

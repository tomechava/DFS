cmake_minimum_required(VERSION 3.15)
project(NameNodeDFS CXX)

set(CMAKE_CXX_STANDARD 17)

# vcpkg integration
find_package(gRPC CONFIG REQUIRED)
find_package(Protobuf CONFIG REQUIRED)

# Archivos fuente
set(SOURCES
    ${CMAKE_CURRENT_SOURCE_DIR}/src/dfs.pb.cc
    ${CMAKE_CURRENT_SOURCE_DIR}/src/dfs.grpc.pb.cc
    ${CMAKE_CURRENT_SOURCE_DIR}/src/main.cpp
)


add_executable(namenode ${SOURCES})

target_include_directories(namenode PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/src
    ${CMAKE_CURRENT_SOURCE_DIR}/include
)

target_link_libraries(namenode
    PRIVATE gRPC::grpc++
    PRIVATE protobuf::libprotobuf
)

cmake_minimum_required(VERSION 3.7)
project(memory_management)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES test.c bottom.c page.c call.c process.c)
set(HEADER_FILES bottom.h page.h call.h type.h process.h prop.h)


add_executable(memory_management ${SOURCE_FILES} ${HEADER_FILES} )
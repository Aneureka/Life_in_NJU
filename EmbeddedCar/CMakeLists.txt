cmake_minimum_required(VERSION 3.8)
project(EmbeddedCar)

set(CMAKE_CXX_STANDARD 11)
add_executable(main main.cpp GPIOlib.h)
add_library(./libGPIO.a)
target_link_libraries(main ${CMAKE_SOURCE_DIR}/libGPIO.a)
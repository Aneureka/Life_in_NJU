# coding=utf-8

# 过滤拆分过的文件

import os

def remove_zero_volume(stock_file, result_file):

    # 打开股票文件
    sf = open(stock_file, 'r+')
    # 创建另一个文件
    rf = open(result_file, 'w+')
    # 读取表头并保存到新文件
    line = sf.readline()
    rf.write(line)
    # 读取每一行并过滤
    line = sf.readline()
    while line:
        if is_valid(line):
            rf.write(line)
        line = sf.readline()
    rf.close()
    sf.close()
    print " Successfully filtered."


def is_valid(line):
    infos = line.split("\t")
    if infos[6] == "0":
        return False
    else:
        return True


if __name__ == '__main__':
    stock_path = "C:\\Users\\Hiki\\Desktop\\ALL.csv"
    result_path = "C:\\Users\\Hiki\\Desktop\\ALL2.csv"
    remove_zero_volume(stock_path, result_path)
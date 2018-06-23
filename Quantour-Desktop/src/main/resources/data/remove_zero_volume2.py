# coding=utf-8

# 过滤拆分过的文件

import os

def remove_zero_volume(stocks_dir, result_dir):
    if not os.path.isdir(stocks_dir):
        raise Exception("传入的文件不是文件夹")
    stock_list = os.listdir(stocks_dir)
    # 对每个股票文件进行过滤
    for stock in stock_list:
        stock_file_name = stocks_dir + stock
        result_stock_file_name = result_dir + stock

        # 打开股票文件
        sf = open(stock_file_name, 'r+')
        # 创建另一个文件
        rf = open(result_stock_file_name, 'w+')

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
        print stock + " is successfully filtered."


def is_valid(line):
    infos = line.split("\t")
    if infos[6] == "0":
        return False
    else:
        return True


if __name__ == '__main__':
    stocks_dir = "C:\\Users\\Hiki\\Desktop\\stocks\\"
    results_dir = "C:\\Users\\Hiki\\Desktop\\new_stocks\\"
    if not os.path.exists(results_dir):
        os.makedirs(results_dir)
    remove_zero_volume(stocks_dir, results_dir)
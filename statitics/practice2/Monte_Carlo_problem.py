#-*- coding:utf-8 -*-
"""
下面为利用蒙特卡罗方法计算π的python实现代码，请仔细阅读，理解代码含义，并试着写出你自己版本的计算π的蒙特卡罗方法
"""

import random
'''
You can delete the MontyCarlo and write your Monty Carlo
'''

#this function may spend a little time to execute
def MontyCarlo():
    n = 1000000
    k = 0
    for i in range(n):
        x = random.uniform(-1, 1)
        y = random.uniform(-1, 1)
        if x**2 + y**2 < 1:
            k = k + 1
    return 4 * float(k) / float(n)

if __name__ == '__main__':
    pi = MontyCarlo()
    #print out the result
    log(pi)
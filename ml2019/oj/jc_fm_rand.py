import numpy as np
import math

def cal_abcd(y_true, y_pred):
    n = y_true.shape[0]
    a, b, c, d = 0, 0, 0, 0
    for i in range(n-1):
        for j in range(i+1, n):
            c1 = y_pred[i] == y_pred[j]
            c2 = y_true[i] == y_true[j]
            if c1 and c2:
                a += 1
            elif c1 and not c2:
                b += 1
            elif not c1 and c2:
                c += 1
            else:
                d += 1
    return a, b, c, d

def calc_JC(y_true, y_pred):
    '''
    计算并返回JC系数
    :param y_true: 参考模型给出的簇，类型为ndarray
    :param y_pred: 聚类模型给出的簇，类型为ndarray
    :return: JC系数
    '''

    #******** Begin *******#
    a, b, c, d = cal_abcd(y_true, y_pred)
    return a / (a + b + c)
    #******** End *******#


def calc_FM(y_true, y_pred):
    '''
    计算并返回FM指数
    :param y_true: 参考模型给出的簇，类型为ndarray
    :param y_pred: 聚类模型给出的簇，类型为ndarray
    :return: FM指数
    '''

    #******** Begin *******#
    a, b, c, d = cal_abcd(y_true, y_pred)
    return math.sqrt((a / (a + b)) * (a / (a + c)))
    #******** End *******#

def calc_Rand(y_true, y_pred):
    '''
    计算并返回Rand指数
    :param y_true: 参考模型给出的簇，类型为ndarray
    :param y_pred: 聚类模型给出的簇，类型为ndarray
    :return: Rand指数
    '''

    #******** Begin *******#
    m = y_true.shape[0]
    a, b, c, d = cal_abcd(y_true, y_pred)
    return 2 * (a + d) / (m * (m - 1))
    #******** End *******#


if __name__ == '__main__':
    y_true = np.array([0, 0, 0, 1, 1, 1])
    y_pred = np.array([0, 0, 1, 1, 2, 2])
    print(calc_JC(y_true, y_pred))
    print(calc_FM(y_true, y_pred))
    print(calc_Rand(y_true, y_pred))
"""
测试输入：
{'y_true':[0, 0, 0, 1, 1, 1], 'y_pred':[0, 0, 1, 1, 2, 2]}

预期输出：
0.285714 0.471405 0.666667
"""


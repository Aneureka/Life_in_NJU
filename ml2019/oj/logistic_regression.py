# -*- coding: utf-8 -*-

import numpy as np
import warnings

warnings.filterwarnings("ignore")


def sigmoid(x):
    '''
    sigmoid函数
    :param x: 转换前的输入
    :return: 转换后的概率
    '''
    return 1 / (1 + np.exp(-x))


def fit(x, y, eta=1e-3, n_iters=10000):
    '''
    训练逻辑回归模型
    :param x: 训练集特征数据，类型为ndarray
    :param y: 训练集标签，类型为ndarray
    :param eta: 学习率，类型为float
    :param n_iters: 训练轮数，类型为int
    :return: 模型参数，类型为ndarray
    '''
    #   请在此添加实现代码   #
    # ********** Begin *********#
    theta = np.zeros(x.shape[1])
    for i in range(n_iters):
        y_pred = sigmoid(np.dot(x, theta.T))
        theta -= eta * np.mean(np.dot((y_pred - y).reshape(1, x.shape[0]), x), axis=0)

    return theta
    # ********** End **********#

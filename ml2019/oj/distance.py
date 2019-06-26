# encoding=utf8
import numpy as np


def distance(x, y, p=2):
    """
    input:x(ndarray):第一个样本的坐标
          y(ndarray):第二个样本的坐标
          p(int):等于1时为曼哈顿距离，等于2时为欧氏距离
    output:distance(float):x到y的距离
    """
    # ********* Begin *********#
    if p == 1:
        return np.sum(np.abs(x-y))
    else:
        return np.sum((x-y)**p)**(1/p)
    # ********* End *********#

if __name__ == '__main__':
    x = np.arange(6)
    y = np.arange(3, 9)
    print(distance(x, y, p=2))

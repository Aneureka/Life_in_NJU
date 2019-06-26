# encoding=utf8
import numpy as np


# 计算样本间距离
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



# 计算质心
def cal_Cmass(data):
    '''
    input:data(ndarray):数据样本
    output:mass(ndarray):数据样本质心
    '''
    # ********* Begin *********#
    Cmass = np.mean(data, axis=0)
    # ********* End *********#
    return Cmass


# 计算每个样本到质心的距离，并按照从小到大的顺序排列
def sorted_list(data, Cmass):
    '''
    input:data(ndarray):数据样本
          Cmass(ndarray):数据样本质心
    output:dis_list(list):排好序的样本到质心距离
    '''
    # ********* Begin *********#
    m = data.shape[0]
    dis_list = np.zeros(m)
    for i in range(m):
        dis_list[i] = distance(data[i], Cmass)
    dis_list = np.sort(dis_list).tolist()
    # ********* End *********#
    return dis_list


if __name__ == '__main__':
    data = np.array([[3, 4], [6, 9], [2, 3], [3, 4], [7, 10], [8, 11]])
    Cmass = cal_Cmass(data)
    sorted_list(data, Cmass)








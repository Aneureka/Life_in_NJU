# -*- coding: utf-8 -*-
import numpy as np
from scipy.sparse.csgraph import shortest_path


def isomap(data, d, k, Max=10000):
    '''
    input:data(ndarray):待降维数据
          d(int):降维后数据维数
          k(int):最近的k个样本
          Max(int):表示无穷大
    output:Z(ndarray):降维后的数据
    '''
    # ********* Begin *********#
    # 计算dist2,dist2i,dist2j,dist2ij
    m = data.shape[0]
    dist2ij = np.zeros((m, m))
    for i in range(m):
        disti = np.sum(np.square(data[i] - data), axis=1)
        indices = np.argsort(disti)
        disti[indices[k:]] = Max
        dist2ij[i] = disti
    dist2i = np.zeros(m)
    dist2j = np.zeros(m)
    for i in range(m):
        dist2i[i] = np.mean(dist2ij[i, :])
        dist2j[i] = np.mean(dist2ij[:, i])
    dist2 = np.mean(dist2ij)
    # 计算B
    B = np.zeros((m, m))
    for i in range(m):
        for j in range(m):
            B[i, j] = - 0.5 * (dist2ij[i, j] - dist2i[i] - dist2j[j] + dist2)
    # 矩阵分解得到特征值与特征向量
    value, vector = np.linalg.eigh(B)
    indices = np.argsort(value)[::-1]
    selected_indices = indices[:d]
    # 计算Z
    Z = vector[:, selected_indices].dot(np.diag(np.sqrt(value[selected_indices])))
    # ********* End *********#
    return Z

"""
这道题让人不知道从哪里吐槽起了…
1）Max 表示的无穷大居然不是指 距离 的无穷大 而是 指 距离^2
2）“最近的k个样本” 居然包含了其本身
3） Isomap 居然不用最短路径算法
"""

if __name__ == '__main__':
    data = np.array([[1, 1], [1.5, 1.6], [2, 2], [2.4, 2.4], [1.95, 1.9], [3, 3], [3.3, 3.1]])
    d = 2
    k = 2
    print(isomap(data, d, k))

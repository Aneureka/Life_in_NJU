# -*- coding: utf-8 -*-
import numpy as np

from sklearn.manifold import MDS


def mds(data, d):
    '''
    input:data(ndarray):待降维数据
          d(int):降维后数据维数
    output:Z(ndarray):降维后的数据
    '''
    # ********* Begin *********#
    # 计算dist2,dist2i,dist2j,dist2ij
    m = data.shape[0]
    dist2ij = np.zeros((m, m))
    for i in range(m):
        for j in range(m):
            dist2ij[i, j] = np.sum(np.square(data[i] - data[j]))
    dist2i = np.mean(dist2ij, axis=1).reshape((1, m))
    dist2j = np.mean(dist2ij, axis=0).reshape((m, 1))
    dist2 = np.mean(dist2ij)
    # 计算B
    B = - 0.5 * (dist2ij - dist2i - dist2j + dist2)
    # 矩阵分解得到特征值与特征向量
    value, vector = np.linalg.eigh(B)
    indices = np.argsort(value)[::-1]
    selected_indices = indices[:d]
    # 计算Z
    Z = vector[:, selected_indices].dot(np.diag(np.sqrt(value[selected_indices])))
    # ********* End *********#
    return Z


if __name__ == '__main__':
    data = np.array([[1, 1], [1.5, 1.6], [2, 2], [2.4, 2.4], [1.95, 1.9], [3, 3], [3.3, 3.1]])
    d = 2
    print(mds(data, d))




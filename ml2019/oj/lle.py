#encoding=utf8
import numpy as np
from sklearn.utils import check_random_state


def lle(data, d, k):
    '''
    input:data(ndarray):待降维数据,行数为样本个数，列数为特征数
          d(int):降维后数据维数
          k(int):最近的k个样本
    output:Z(ndarray):降维后的数据
    '''
    #********* Begin *********#
    #确定样本i的邻域
    m = data.shape[0]
    neighbors = np.zeros((m, k))
    for i in range(m):
        dist_i = np.sqrt(np.sum(np.square(data[i] - data), axis=1))
        indices = np.argsort(dist_i)[1:k+1]
        neighbors[i] = indices
    neighbors = neighbors.astype(int)

    #求矩阵c及其逆
    c = np.zeros((m, k, k))
    for i in range(m):
        xi_xj = data[i] - data[neighbors[i]]
        c[i] = np.dot(xi_xj, xi_xj.T)
        c[i] = np.linalg.pinv(c[i])

    #求w
    w = np.zeros((m, k))
    for i in range(m):
        for j in range(k):
            w[i, j] = np.sum(c[i, j]) / np.sum(c[i])

    #求得M并矩阵分解
    M = np.dot(np.ones_like(w) - w, (np.ones_like(w) - w).T)
    eig_value, eig_vector = np.linalg.eigh(M)
    selected_indices = np.argsort(eig_value)[:d]
    #求Z
    Z = eig_vector[:, selected_indices]
    #********* End *********#
    return Z


if __name__ == '__main__':
    n_samples = 500
    random_state = check_random_state(0)
    p = random_state.rand(n_samples) * (2 * np.pi - 0.55)
    t = random_state.rand(n_samples) * np.pi
    indices = ((t < (np.pi - (np.pi / 8))) & (t > ((np.pi / 8))))
    x, y, z = np.sin(t[indices]) * np.cos(p[indices]), np.sin(t[indices]) * np.sin(p[indices]), np.cos(t[indices])
    data = np.array([x, y, z]).T
    lle(data, 2, 30)

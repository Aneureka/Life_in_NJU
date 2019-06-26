import numpy as np

def pca(data, k):
    '''
    对data进行PCA，并将结果返回
    :param data:数据集，类型为ndarray
    :param k:想要降成几维，类型为int
    :return: 降维后的数据，类型为ndarray
    '''

    #********* Begin *********#
    data -= np.mean(data, axis=0)
    cov = np.cov(data, rowvar=0)
    value, vector = np.linalg.eig(cov)
    indices = np.argsort(value)[::-1]
    selected_indices = indices[:k]
    return data.dot(vector.T[selected_indices].T)
    #********* End *********#

if __name__ == '__main__':
    data = np.array([[1, 2.2, 3.1, 4.3, 0.1, -9.8, 10], [1.8, -2.2, 13.1, 41.3, 10.1, -89.8, 100]])
    k = 3
    print(pca(data, k))
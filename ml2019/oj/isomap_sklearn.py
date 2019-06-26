# -*- coding: utf-8 -*-
from sklearn.manifold import Isomap


def isomap(data, d, k):
    '''
    input:data(ndarray):待降维数据
          d(int):降维后数据维度
          k(int):最近的k个样本
    output:Z(ndarray):降维后数据
    '''
    # ********* Begin *********#
    isomap = Isomap(n_neighbors=k, n_components=d, )
    Z = isomap.fit_transform(data)


    # ********* End *********#
    return Z


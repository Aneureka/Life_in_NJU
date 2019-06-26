# encoding=utf8
from sklearn.cluster import AgglomerativeClustering
import numpy as np

def Agglomerative_cluster(data):
    '''
    对红酒数据进行聚类
    :param data: 数据集，类型为ndarray
    :return: 聚类结果，类型为ndarray
    '''

    # ********* Begin *********#
    data = (data - np.mean(data, axis=0)) / np.std(data, axis=0)
    agnes = AgglomerativeClustering(n_clusters=3)
    result = agnes.fit_predict(data)
    return result
    # ********* End *********#

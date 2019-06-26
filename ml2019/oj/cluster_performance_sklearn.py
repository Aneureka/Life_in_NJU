from sklearn.metrics.cluster import fowlkes_mallows_score, adjusted_rand_score

def cluster_performance(y_true, y_pred):
    '''
    返回FM指数和Rand指数
    :param y_true:参考模型的簇划分，类型为ndarray
    :param y_pred:聚类模型给出的簇划分，类型为ndarray
    :return: FM指数，Rand指数
    '''

    #********* Begin *********#
    return fowlkes_mallows_score(y_true, y_pred), adjusted_rand_score(y_true, y_pred)
    #********* End *********#



import numpy as np


def calc_dist(a, b, axis=None):
    return np.sqrt(np.sum(np.square(a - b), axis=axis))


def calc_DBI(feature, pred):
    """
    计算并返回DB指数
    :param feature: 待聚类数据的特征，类型为`ndarray`
    :param pred: 聚类后数据所对应的簇，类型为`ndarray`
    :return: DB指数
    """

    # ********* Begin *********#
    clusters, cluster_sizes = np.unique(pred, return_counts=True)
    k = clusters.shape[0]
    avg = np.zeros(k)
    mu = np.zeros((k, feature.shape[1]))
    for i in range(k):
        c = clusters[i]
        indices = np.where(pred == c)[0]
        # TODO 考虑簇中只有一个值的情况
        mu[i] = np.mean(feature[indices], axis=0)
        avg[i] = np.mean(calc_dist(feature[indices], mu[i], axis=1))
    dbi = 0.0
    for i in range(k):
        temp = (avg[i] + np.delete(avg, i, 0)) / calc_dist(mu[i], np.delete(mu, i, 0), axis=1)
        dbi += np.max(temp)
    dbi /= k
    return dbi
    # ********* End *********#


def calc_dmin(feature, pred, c_i, c_j):
    indices_i = np.where(pred == c_i)[0]
    indices_j = np.where(pred == c_j)[0]
    feature_i = feature[indices_i]
    feature_j = feature[indices_j]
    dmin = np.inf
    for i in range(feature_i.shape[0]):
        dist_i = calc_dist(feature_i[i], feature_j, axis=1)
        min_dist_i = np.min(dist_i)
        if min_dist_i < dmin:
            dmin = min_dist_i
    return dmin

def calc_DI(feature, pred):
    """
    计算并返回Dunn指数
    :param feature: 待聚类数据的特征，类型为`ndarray`
    :param pred: 聚类后数据所对应的簇，类型为`ndarray`
    :return: Dunn指数
    """

    # ********* Begin *********#
    clusters, cluster_sizes = np.unique(pred, return_counts=True)
    k = clusters.shape[0]
    # 计算 max diam
    max_diam = 0.0
    for i in range(k):
        diam_i = 0.0
        c = clusters[i]
        c_size = cluster_sizes[i]
        indices = np.where(pred == c)[0]
        for p in range(c_size-1):
            for q in range(p+1, c_size):
                dist_pq = calc_dist(feature[indices[p]], feature[indices[q]])
                if dist_pq > diam_i:
                    diam_i = dist_pq
        if diam_i > max_diam:
            max_diam = diam_i
    min_dmin = np.inf
    for i in range(k-1):
        for j in range(i+1, k):
            c_i = clusters[i]
            c_j = clusters[j]
            dmin_ij = calc_dmin(feature, pred, c_i, c_j)
            if dmin_ij < min_dmin:
                min_dmin = dmin_ij
    di = min_dmin / max_diam
    return di
    # ********* End *********#


if __name__ == '__main__':
    feature = np.array([[3, 4], [6, 9], [2, 3], [3, 4], [7, 10], [8, 11]])
    pred = np.array([1, 2, 1, 1, 2, 2])
    # print(calc_DBI(feature, pred))
    print(calc_DI(feature, pred))


"""
测试输入：
{'feature':[[3,4],[6,9],[2,3],[3,4],[7,10],[8,11]], 'pred':[1, 2, 1, 1, 2, 2]}

预期输出：
0.204765 2.061553
"""

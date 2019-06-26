import numpy as np


def calc_dist(a, b):
    return np.sqrt(np.sum(np.square(a - b)))


def calc_min_dist(cluster1, cluster2):
    '''
    计算簇间最小距离
    :param cluster1:簇1中的样本数据，类型为ndarray
    :param cluster2:簇2中的样本数据，类型为ndarray
    :return:簇1与簇2之间的最小距离
    '''

    # ********* Begin *********#
    min_dist = np.inf
    for i in range(cluster1.shape[0]):
        for j in range(cluster2.shape[0]):
            dist = calc_dist(cluster1[i], cluster2[j])
            if dist < min_dist:
                min_dist = dist
    return min_dist
    # ********* End *********#


def calc_max_dist(cluster1, cluster2):
    '''
    计算簇间最大距离
    :param cluster1:簇1中的样本数据，类型为ndarray
    :param cluster2:簇2中的样本数据，类型为ndarray
    :return:簇1与簇2之间的最大距离
    '''

    # ********* Begin *********#
    max_dist = 0.0
    for i in range(cluster1.shape[0]):
        for j in range(cluster2.shape[0]):
            dist = calc_dist(cluster1[i], cluster2[j])
            if dist > max_dist:
                max_dist = dist
    return max_dist
    # ********* End *********#


def calc_avg_dist(cluster1, cluster2):
    '''
    计算簇间平均距离
    :param cluster1:簇1中的样本数据，类型为ndarray
    :param cluster2:簇2中的样本数据，类型为ndarray
    :return:簇1与簇2之间的平均距离
    '''

    # ********* Begin *********#
    m, n = cluster1.shape[0], cluster2.shape[0]
    avg_dist = 0.0
    for i in range(m):
        for j in range(n):
            dist = calc_dist(cluster1[i], cluster2[j])
            avg_dist += dist
    avg_dist /= (m * n)
    return avg_dist
    # ********* End *********#

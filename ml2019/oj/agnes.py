import numpy as np


def calc_dist(a, b):
    return np.sqrt(np.sum(np.square(a - b)))


def calc_max_dist(feature, cluster1, cluster2):
    max_dist = 0.0
    for x1 in cluster1:
        for x2 in cluster2:
            dist = calc_dist(feature[x1], feature[x2])
            if dist > max_dist:
                max_dist = dist
    return max_dist


def find_min_dist(M):
    m = M.shape[0]
    min_dist = np.inf
    x, y = 0, 0
    for i in range(m-1):
        for j in range(i+1, m):
            if M[i, j] < min_dist:
                min_dist = M[i, j]
                x = i
                y = j
    return x, y


def AGNES(feature, k):
    '''
    AGNES聚类并返回聚类结果，量化距离时请使用簇间最大欧氏距离
    假设数据集为`[1, 2], [10, 11], [1, 3]]，那么聚类结果可能为`[[1, 2], [1, 3]], [[10, 11]]]
    :param feature:数据集，类型为ndarray
    :param k:表示想要将数据聚成`k`类，类型为`int`
    :return:聚类结果，类型为list
    '''

    #********* Begin *********#
    m = feature.shape[0]
    clusters = []
    for i in range(m):
        c_i = set()
        c_i.add(i)
        clusters.append(c_i)
    M = np.zeros((m, m))
    for i in range(m-1):
        for j in range(i+1, m):
            M[i, j] = calc_max_dist(feature, clusters[i], clusters[j])
            M[j, i] = M[i, j]
    q = m
    while q > k:
        i_star, j_star = find_min_dist(M)
        clusters[i_star] = clusters[i_star].union(clusters[j_star])
        for j in range(j_star+1, q):
            clusters[j-1] = clusters[j]
        M = np.delete(M, j_star, 0)
        M = np.delete(M, j_star, 1)
        for j in range(q-1):
            M[i_star, j] = calc_max_dist(feature, clusters[i_star], clusters[j])
            M[j, i_star] = M[i_star, j]
        q -= 1
    res = [feature[list(c)].tolist() for c in clusters[:k]]
    return res
    #********* End *********#


if __name__ == '__main__':
    data = np.array([[1, 2], [10, 11], [1, 3]])
    k = 2
    print(AGNES(data, k))

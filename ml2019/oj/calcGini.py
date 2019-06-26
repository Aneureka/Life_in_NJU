import numpy as np


def calcGini(feature, label, index):
    '''
    计算基尼系数
    :param feature:测试用例中字典里的feature，类型为ndarray
    :param label:测试用例中字典里的label，类型为ndarray
    :param index:测试用例中字典里的index，即feature部分特征列的索引。该索引指的是feature中第几个特征，如index:0表示使用第一个特征来计算信息增益。
    :return:基尼系数，类型float
    '''

    # ********* Begin *********#
    feature_i = feature[:, index]
    feature_i_values = np.unique(feature_i)
    gini_index = 0.0
    for value in feature_i_values:
        prop = np.sum(feature_i == value) / feature_i.shape[0]
        gini_index += prop * getGini(label[feature_i == value])
    return gini_index
    # ********* End *********#

def getGini(label):
    label_values = np.unique(label)
    gini = 1.0
    for value in label_values:
        p = np.sum(label == value) / label.shape[0]
        gini -= p**2
    return gini


if __name__ == '__main__':
    feature = np.array([[0, 1], [1, 0], [1, 2], [0, 0], [1, 1]])
    label = np.array([0, 1, 0, 0, 1])
    index = 0
    print(calcGiniIndex(feature, label, index))
    # 0.41
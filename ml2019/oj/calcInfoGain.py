import numpy as np


def calcInfoGain(feature, label, index):
    '''
    计算信息增益
    :param feature:测试用例中字典里的feature，类型为ndarray
    :param label:测试用例中字典里的label，类型为ndarray
    :param index:测试用例中字典里的index，即feature部分特征列的索引。该索引指的是feature中第几个特征，如index:0表示使用第一个特征来计算信息增益。
    :return:信息增益，类型float
    '''

    #*********** Begin ***********#
    total_entropy = calEntropy(label)
    feature_i = feature[:, index]
    unique_feature_i = np.unique(feature_i)
    new_entropy = 0.0
    for value in unique_feature_i:
        label_i = label[feature_i == value]
        prop = np.sum(feature_i == value) / feature_i.shape[0]
        new_entropy += prop * calEntropy(label_i)
    return total_entropy - new_entropy
    #*********** End *************#


def calEntropy(label):
    p_0 = np.sum(label == 0) / label.shape[0]
    p_1 = np.sum(label == 1) / label.shape[0]
    entropy = 0.0
    if p_0 != 0:
        entropy += - p_0 * np.log2(p_0)
    if p_1 != 0:
        entropy += - p_1 * np.log2(p_1)
    return entropy


if __name__ == '__main__':
    feature = np.array([[0, 1], [1, 0], [1, 2], [0, 0], [1, 1]])
    label = np.array([0, 1, 0, 0, 1])
    index = 0
    print(calcInfoGain(feature, label, index))
    # 0.41
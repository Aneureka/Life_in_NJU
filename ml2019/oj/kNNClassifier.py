#encoding=utf8
import numpy as np

class kNNClassifier(object):
    def __init__(self, k):
        '''
        初始化函数
        :param k:kNN算法中的k
        '''
        self.k = k
        # 用来存放训练数据，类型为ndarray
        self.train_feature = None
        # 用来存放训练标签，类型为ndarray
        self.train_label = None


    def fit(self, feature, label):
        '''
        kNN算法的训练过程
        :param feature: 训练集数据，类型为ndarray
        :param label: 训练集标签，类型为ndarray
        :return: 无返回
        '''

        #********* Begin *********#
        self.train_feature = feature
        self.train_label = label
        #********* End *********#


    def predict(self, feature):
        '''
        kNN算法的预测过程
        :param feature: 测试集数据，类型为ndarray
        :return: 预测结果，类型为ndarray或list
        '''

        #********* Begin *********#
        pred_label = np.zeros(feature.shape[0])
        for i in range(feature.shape[0]):
            dist = np.sqrt(np.sum((self.train_feature - feature[i, :])**2, axis=1))
            min_dist_indices = np.argsort(dist)[:self.k]
            min_dist_labels = self.train_label[min_dist_indices]
            counts = np.bincount(min_dist_labels)
            pred_label[i] = np.argmax(counts)
        #********* End *********#
        return pred_label
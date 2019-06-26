import numpy as np
import warnings


class NaiveBayesClassifier(object):
    def __init__(self):
        '''
        self.label_prob表示每种类别在数据中出现的概率
        例如，{0:0.333, 1:0.667}表示数据中类别0出现的概率为0.333，类别1的概率为0.667
        '''
        self.label_prob = {}
        '''
        self.condition_prob表示每种类别确定的条件下各个特征出现的概率
        例如训练数据集中的特征为 [[2, 1, 1],
                              [1, 2, 2],
                              [2, 2, 2],
                              [2, 1, 2],
                              [1, 2, 3]]
        标签为[1, 0, 1, 0, 1]
        那么当标签为0时第0列的值为1的概率为0.5，值为2的概率为0.5;
        当标签为0时第1列的值为1的概率为0.5，值为2的概率为0.5;
        当标签为0时第2列的值为1的概率为0，值为2的概率为1，值为3的概率为0;
        当标签为1时第0列的值为1的概率为0.333，值为2的概率为0.666;
        当标签为1时第1列的值为1的概率为0.333，值为2的概率为0.666;
        当标签为1时第2列的值为1的概率为0.333，值为2的概率为0.333,值为3的概率为0.333;
        因此self.label_prob的值如下：     
        {
            0:{
                0:{
                    1:0.5
                    2:0.5
                }
                1:{
                    1:0.5
                    2:0.5
                }
                2:{
                    1:0
                    2:1
                    3:0
                }
            }
            1:
            {
                0:{
                    1:0.333
                    2:0.666
                }
                1:{
                    1:0.333
                    2:0.666
                }
                2:{
                    1:0.333
                    2:0.333
                    3:0.333
                }
            }
        }
        '''
        self.condition_prob = {}

    def fit(self, feature, label):
        '''
        对模型进行训练，需要将各种概率分别保存在self.label_prob和self.condition_prob中
        :param feature: 训练数据集所有特征组成的ndarray
        :param label:训练数据集中所有标签组成的ndarray
        :return: 无返回
        '''

        # ********* Begin *********#
        N = label.shape[0]
        self.label_prob[0] = np.sum(label == 0) / (N+2)
        self.label_prob[1] = np.sum(label == 1) / (N+2)
        self.condition_prob[0] = {}
        self.condition_prob[1] = {}
        self.condition_prob[0][0] = {}
        self.condition_prob[0][1] = {}
        self.condition_prob[0][2] = {}
        self.condition_prob[1][0] = {}
        self.condition_prob[1][1] = {}
        self.condition_prob[1][2] = {}
        feature_0, feature_1 = feature[label == 0], feature[label == 1]
        with warnings.catch_warnings():
            warnings.filterwarnings('error')
            try:
                feature_0_0 = feature_0[:, 0]
                self.condition_prob[0][0][1] = (np.sum(feature_0_0[feature_0_0 == 1])+1) / (feature_0_0.shape[0]+2)
                self.condition_prob[0][0][2] = (np.sum(feature_0_0[feature_0_0 == 2])+1) / (feature_0_0.shape[0]+2)
                feature_0_1 = feature_0[:, 1]
                self.condition_prob[0][1][1] = (np.sum(feature_0_1[feature_0_1 == 1])+1) / (feature_0_1.shape[0]+2)
                self.condition_prob[0][1][2] = (np.sum(feature_0_1[feature_0_1 == 2])+1) / (feature_0_1.shape[0]+2)
                feature_0_2 = feature_0[:, 2]
                self.condition_prob[0][2][1] = (np.sum(feature_0_2[feature_0_2 == 1])+1) / (feature_0_2.shape[0]+3)
                self.condition_prob[0][2][2] = (np.sum(feature_0_2[feature_0_2 == 2])+1) / (feature_0_2.shape[0]+3)
                self.condition_prob[0][2][3] = (np.sum(feature_0_2[feature_0_2 == 3])+1) / (feature_0_2.shape[0]+3)
                feature_1_0 = feature_1[:, 0]
                self.condition_prob[1][0][1] = (np.sum(feature_1_0[feature_1_0 == 1])+1) / (feature_1_0.shape[0]+2)
                self.condition_prob[1][0][2] = (np.sum(feature_1_0[feature_1_0 == 2])+1) / (feature_1_0.shape[0]+2)
                feature_1_1 = feature_1[:, 1]
                self.condition_prob[1][1][1] = (np.sum(feature_1_1[feature_1_1 == 1])+1) / (feature_1_1.shape[0]+2)
                self.condition_prob[1][1][2] = (np.sum(feature_1_1[feature_1_1 == 2])+1) / (feature_1_1.shape[0]+2)
                feature_1_2 = feature_1[:, 2]
                self.condition_prob[1][2][1] = (np.sum(feature_1_2[feature_1_2 == 1])+1) / (feature_1_2.shape[0]+3)
                self.condition_prob[1][2][2] = (np.sum(feature_1_2[feature_1_2 == 2])+1) / (feature_1_2.shape[0]+3)
                self.condition_prob[1][2][3] = (np.sum(feature_1_2[feature_1_2 == 3])+1) / (feature_1_2.shape[0]+3)
            except RuntimeWarning:
                pass
        # ********* End *********#

    def predict(self, feature):
        '''
        对数据进行预测，返回预测结果
        :param feature:测试数据集所有特征组成的ndarray
        :return:
        '''
        # ********* Begin *********#
        N = feature.shape[0]
        ans = np.zeros(N)
        for i in range(N):
            f = feature[i]
            prob1 = self.label_prob[0] * self.condition_prob[0][0][f[0]] * self.condition_prob[0][1][f[1]] * self.condition_prob[0][2][f[2]]
            prob2 = self.label_prob[1] * self.condition_prob[1][0][f[0]] * self.condition_prob[1][1][f[1]] * self.condition_prob[1][2][f[2]]
            pred = 0 if prob1 > prob2 else 1
            ans[i] = pred
        return ans
        # ********* End *********#


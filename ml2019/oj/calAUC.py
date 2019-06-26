import numpy as np

def calAUC(prob, labels):
    '''
    计算AUC并返回
    :param prob: 模型预测样本为Positive的概率列表，类型为ndarray
    :param labels: 样本的真实类别列表，其中1表示Positive，0表示Negtive，类型为ndarray
    :return: AUC，类型为float
    '''

    #********* Begin *********#
    M = np.sum(labels==1)
    N = np.sum(labels==0)
    sorted_indices = np.argsort(prob)
    sorted_labels = labels[sorted_indices]
    indices = np.arange(sorted_labels.shape[0])
    indices[sorted_labels==0] = -1
    indices += 1
    return (np.sum(indices) - M*(M+1)/2) / (M*N)
    #********* End *********#

if __name__ == '__main__':
    probs = np.array([0.1, 0.4, 0.3, 0.8])
    labels = np.array([0, 0, 1, 1])
    print(calAUC(probs, labels))

from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, roc_auc_score


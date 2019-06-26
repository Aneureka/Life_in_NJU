import numpy as np

def confusion_matrix(y_true, y_predict):
    '''
    构建二分类的混淆矩阵，并将其返回
    :param y_true: 真实类别，类型为ndarray
    :param y_predict: 预测类别，类型为ndarray
    :return: shape为(2, 2)的ndarray
    '''

    #********* Begin *********#
    tp = np.sum(np.logical_and(y_true == 1, y_predict == 1))
    tn = np.sum(np.logical_and(y_true == 0, y_predict == 0))
    fp = np.sum(np.logical_and(y_true == 0, y_predict == 1))
    fn = np.sum(np.logical_and(y_true == 1, y_predict == 0))
    return np.array([[tn, fp], [fn, tp]])
    #********* End *********#

if __name__ == '__main__':
    y_true = np.array([1, 0, 0, 1, 0, 1, 0])
    y_predict = np.array([0, 1, 0, 1, 0, 1, 0])
    print(confusion_matrix(y_true, y_predict))
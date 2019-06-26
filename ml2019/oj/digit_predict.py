from sklearn.linear_model import LogisticRegression

def digit_predict(train_image, train_label, test_image):
    '''
    实现功能：训练模型并输出预测结果
    :param train_sample: 包含多条训练样本的样本集，类型为ndarray,shape为[-1, 8, 8]
    :param train_label: 包含多条训练样本标签的标签集，类型为ndarray
    :param test_sample: 包含多条测试样本的测试集，类型为ndarry
    :return: test_sample对应的预测标签
    '''

    #************* Begin ************#
    logreg = LogisticRegression(solver='lbfgs', max_iter=10, C=10)
    logreg.fit(train_image, train_label)
    return logreg.fit(test_image)
    #************* End **************#
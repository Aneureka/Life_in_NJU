from sklearn.neighbors import KNeighborsClassifier
from sklearn.preprocessing import StandardScaler

def classification(train_feature, train_label, test_feature):
    '''
    对test_feature进行红酒分类
    :param train_feature: 训练集数据，类型为ndarray
    :param train_label: 训练集标签，类型为ndarray
    :param test_feature: 测试集数据，类型为ndarray
    :return: 测试集数据的分类结果
    '''

    #********* Begin *********#
    scaler = StandardScaler()
    scaled_train_feature = scaler.fit_transform(train_feature)
    knn_clf = KNeighborsClassifier()
    knn_clf.fit(scaled_train_feature, train_label)
    scaled_test_feature = scaler.transform(test_feature)
    return knn_clf.predict(scaled_test_feature)
    #********* End **********#

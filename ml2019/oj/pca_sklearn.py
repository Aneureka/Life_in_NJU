from sklearn import datasets

from sklearn.decomposition import PCA
from sklearn.linear_model import LogisticRegression


def cancer_predict(train_sample, train_label, test_sample):
    '''
    使用PCA降维，并进行分类，最后将分类结果返回
    :param train_sample:训练样本, 类型为ndarray
    :param train_label:训练标签, 类型为ndarray
    :param test_sample:测试样本, 类型为ndarray
    :return: 分类结果
    '''

    # ********* Begin *********#
    pca = PCA(n_components=11)
    reduced_train_sample = pca.fit_transform(train_sample)
    reduced_test_sample = pca.transform(test_sample)
    clf = LogisticRegression(solver='newton-cg', max_iter=100, C=1.0)
    clf.fit(reduced_train_sample, train_label)
    return clf.predict(reduced_test_sample)
    # ********* End *********#


if __name__ == '__main__':
    cancer = datasets.load_breast_cancer()
    X = cancer.data
    y = cancer.target
    print(cancer_predict(X, y, X))

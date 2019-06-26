# encoding=utf8
import numpy as np
import math

class smo:
    def __init__(self, max_iter=100, kernel='linear'):
        '''
        input:max_iter(int):最大训练轮数
              kernel(str):核函数，等于'linear'表示线性，等于'poly'表示多项式
        '''
        self.max_iter = max_iter
        self._kernel = kernel

    # 初始化模型
    def init_args(self, features, labels):
        self.m, self.n = features.shape
        self.X = features
        self.Y = labels
        self.b = 0.0
        # 将Ei保存在一个列表里
        self.alpha = np.ones(self.m)
        self.E = [self._E(i) for i in range(self.m)]
        # 错误惩罚参数
        self.C = 1.0

    # ********* Begin *********#
    # kkt条件
    def _KKT(self, i):
        return 0 <= self.alpha[i] <= self.C \
               and self.Y[i] * self._g(i) >= 1 \
               and self.alpha[i] * (self.Y[i] * self._g(i) - 1) == 0

    # g(x)预测值，输入xi（X[i]）
    def _g(self, i):
        res = 0.0
        for j in range(self.m):
            res += self.alpha[j] * self.Y[j] * self.kernel(self.X[i], self.X[j])
        # TODO 检查 b 正确性
        res += self.b
        return 1 if res >= 0 else -1

    # 核函数,多项式添加二次项即可
    def kernel(self, x1, x2):
        if self._kernel == 'linear':
            return x1.T.dot(x2)
        elif self._kernel == 'poly':
            return (x1**2).T.dot(x2**2) + x1.T.dot(x2)
        else:
            return 0

    # E（x）为g(x)对输入x的预测值和y的差
    def _E(self, i):
        return self._g(i) - self.Y[i]

    # 初始alpha
    def _init_alpha(self):
        i_star = -1
        # 外层循环首先遍历所有满足0<a<C的样本点，检验是否满足KKT
        for i in range(self.m):
            if 0 < self.alpha[i] < self.C:
                if not self._KKT(i):
                    i_star = i
                    break
        # 否则遍历整个训练集
        if i_star == -1:
            for i in range(self.m):
                if not self._KKT(i):
                    i_star = i
                    break
        # 选择alpha参数
        j_star = -1
        max_dist = 0
        for j in range(self.m):
            dist_ij = np.sqrt(np.sum(np.square(self.X[i_star] - self.X[j])))
            if dist_ij > max_dist:
                max_dist = dist_ij
                j_star = j
        return i_star, j_star

    # 训练
    def fit(self, features, labels):
        '''
        input:features(ndarray):特征
              label(ndarray):标签
        '''
        self.init_args(features, labels)
        # 执行 SMO 过程
        while True:
            i_star, j_star = self._init_alpha()
            print(i_star, j_star)
            epsilon = 0.0
            for i in range(self.m):
                if i != i_star and i != j_star:
                    epsilon -= self.alpha[i] * self.Y[i]
            alpha_i = 1
            for j in range(self.m):
                if j != i_star:
                    alpha_i -= self.alpha[j] * self.Y[i_star] * self.Y[j] * self.kernel(self.X[i_star], self.X[j])
            alpha_i /= 2 * self.Y[i_star]**2 * self.kernel(self.X[i_star], self.X[i_star])
            # 裁剪 alpha_i
            if self.Y[i_star] * self.Y[j_star] < 0:
                low = max(0, self.alpha[j_star] - self.alpha[i_star])
                high = min(self.C, self.C + self.alpha[j_star] - self.alpha[i_star])
            else:
                low = max(0, self.alpha[j_star] + self.alpha[i_star] - self.C)
                high = min(self.C, self.alpha[j_star] + self.alpha[i_star])
            if alpha_i < low:
                alpha_i = low
            elif alpha_i > high:
                alpha_i = high
            alpha_j = (epsilon - alpha_i * self.Y[i_star]) / self.Y[j_star]
            self.alpha[i_star] = alpha_i
            self.alpha[j_star] = alpha_j
            # 检查是否满足终止条件
            to_exit = True
            for i in range(self.m):
                if not self._KKT(i):
                    to_exit = False
                    break
            if to_exit:
                break
        # 更新 b
        support_vector_indices = []
        b = 0
        for i in range(self.m):
            if self.alpha[i] != 0:
                support_vector_indices.append(i)
        for s in support_vector_indices:
            b += self.Y[s]
            for i in support_vector_indices:
                b -= self.alpha[i] * self.Y[i] * self.kernel(self.X[i], self.X[s])
        b /= len(support_vector_indices)
        self.b = b

    def predict(self, data):
        '''
        input:data(ndarray):单个样本
        output:预测为正样本返回+1，负样本返回-1
        '''
        return self._g(data)

    # ********* End *********#


if __name__ == '__main__':
    s = smo()
    features = np.array([[0, 0], [1, 1], [1, 0], [2, 1]])
    labels = np.array([1, 1, -1, -1])
    s.fit(features, labels)
    s.predict(labels)

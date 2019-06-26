import matplotlib.pyplot as plt
import numpy as np


x = np.array([24,53,22,25,32,52,22,43,52,48])
y = np.array([40,52,25,77,48,110,38,44,27,65])
z = np.array([1,0,0,1,1,1,1,0,0,1])

plt.scatter(x[z==0], y[z==0], marker='^', color='green')
plt.scatter(x[z==1], y[z==1], marker='+')

div_x = np.arange(0, 60, 1)
div_y = 0.9*div_x + 11.6
plt.plot(div_x, div_y, label='0.078x + 0.086y - 1 = 0', color='red')
plt.legend()
plt.show()


# from sklearn import svm
# import numpy as np

# x = np.array([24,53,22,25,32,52,22,43,52,48]).reshape(-1, 1)
# y = np.array([40,52,25,77,48,110,38,44,27,65]).reshape(-1, 1)
# z = np.array([1,0,0,1,1,1,1,0,0,1])
# X_train = np.hstack([x, y])
# y_train = z.copy()

# svc = svm.SVC(kernel='linear').fit(X_train, y_train)
# W = svc.coef_[0]
# I = svc.intercept_
# a = - W[0] / W[1]
# b = I[0] / W[1]
# print(a, b)
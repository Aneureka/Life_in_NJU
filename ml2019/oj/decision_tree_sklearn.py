import pandas as pd
from sklearn.tree import DecisionTreeClassifier

# 加载数据
train_data = pd.read_csv('./step7/train_data.csv').as_matrix()
train_label = pd.read_csv('./step7/train_label.csv').as_matrix()
test_data = pd.read_csv('./step7/test_data.csv').as_matrix()

clf = DecisionTreeClassifier()
clf.fit(train_data, train_label)
pred_label = clf.predict(test_data)

result = pd.DataFrame(columns=['target'])
result['target'] = pred_label

result.to_csv('./step7/predict.csv', index=False)
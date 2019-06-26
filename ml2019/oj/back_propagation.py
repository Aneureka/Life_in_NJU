# encoding=utf8
import os
import pandas as pd
from sklearn.neural_network import MLPClassifier

if os.path.exists('./step2/result.csv'):
    os.remove('./step2/result.csv')

# ********* Begin *********#
train_data = pd.read_csv('./step2/train_data.csv').values
train_label = pd.read_csv('./step2/train_label.csv')
train_label = train_label['target'].values
test_data = pd.read_csv('./step2/test_data.csv').values

mlp = MLPClassifier(solver='lbfgs', max_iter=100, alpha=1e-3, hidden_layer_sizes=(10,5))

mlp.fit(train_data,train_label)
result = mlp.predict(test_data)

result_pd = pd.DataFrame(columns=['result'])
result_pd['result'] = result

result_pd.to_csv('./step2/result.csv', index=False)

# ********* End *********#

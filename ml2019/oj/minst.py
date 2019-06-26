# encoding=utf8
import torch
import torch.nn as nn
from torch.autograd import Variable
import torch.utils.data as Data
import torchvision
import torchvision.transforms as transforms
import os

if os.path.exists('./step3/cnn.pkl'):
    os.remove('./step3/cnn.pkl')

# 加载数据
train_data = torchvision.datasets.MNIST(
    root='./step3/mnist/',
    train=True,  # this is training data
    transform=transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.1307,), (0.3081,))
    ]),
    # Converts a PIL.Image or numpy.ndarray to
    download=False,
)
# 取6000个样本为训练集
train_data_tiny = []

for i in range(6000):
    train_data_tiny.append(train_data[i])

train_data = train_data_tiny

train_loader = torch.utils.data.DataLoader(train_data, batch_size=50, shuffle=True)

# ********* Begin *********#
class CNN(nn.Module):
    def __init__(self):
        super(CNN, self).__init__()
        self.conv1 = nn.Sequential(  # input shape (1, 28, 28)
            nn.Conv2d(
                in_channels=1,  # input height
                out_channels=16,  # n_filters
                kernel_size=5,  # filter size
                stride=1,  # filter movement/step
                padding=2,
                # if want same width and length of this image after con2d, padding=(kernel_size-1)/2 if stride=1
            ),  # output shape (16, 28, 28)
            nn.ReLU(),  # activation
            nn.MaxPool2d(kernel_size=2),  # choose max value in 2x2 area, output shape (16, 14, 14)
        )
        self.conv2 = nn.Sequential(  # input shape (16, 14, 14)
            nn.Conv2d(16, 32, 5, 1, 2),  # output shape (32, 14, 14)
            nn.ReLU(),  # activation
            nn.MaxPool2d(2),  # output shape (32, 7, 7)
        )
        self.out = nn.Linear(32 * 7 * 7, 10)  # fully connected layer, output 10 classes

    def forward(self, x):
        x = self.conv1(x)
        x = self.conv2(x)
        x = x.view(x.size(0), -1)  # flatten the output of conv2 to (batch_size, 32 * 7 * 7)
        output = self.out(x)
        return output


model = CNN()
optimizer = torch.optim.SGD(model.parameters(), lr=1e-2, momentum=0.9)
num_epoch = 2
loss_func = nn.CrossEntropyLoss()
model.train()
for epoch in range(num_epoch):
    for data, target in train_loader:
        data = Variable(data)
        target = Variable(target)
        optimizer.zero_grad()
        output = model(data)
        loss = loss_func(output, target)
        loss.backward()
        optimizer.step()

# ********* End *********#
# 保存模型
torch.save(model.state_dict(), './step3/cnn.pkl')

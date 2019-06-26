import torch
import torchvision
import torchvision.transforms as transforms
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
# import matplotlib.pyplot as plt


class Net(nn.Module):
    def __init__(self):
        super(Net, self).__init__()
        self.conv1 = nn.Conv2d(1, 10, kernel_size=5, stride=1)
        self.conv2 = nn.Conv2d(10, 30, kernel_size=5, stride=1)
        self.fc1 = nn.Linear(4*4*30, 300)
        self.fc2 = nn.Linear(300, 10)

    def forward(self, x):
        x = F.relu(self.conv1(x))
        x = F.max_pool2d(x, 2, 2)
        x = F.relu(self.conv2(x))
        x = F.max_pool2d(x, 2, 2)
        x = x.view(-1, 4*4*30)
        x = F.relu(self.fc1(x))
        x = self.fc2(x)
        return F.log_softmax(x, dim=1)


def load_data(batch_size=64, test_batch_size=1000, num_workers=2):
    # 0.1307 and 0.3801 are global mean and standard deviation of the MNIST dataset
    transform = transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.1307,), (0.3081,))
    ])
    train_set = torchvision.datasets.MNIST(root='./data', train=True, download=True, transform=transform)
    train_loader = torch.utils.data.DataLoader(train_set, batch_size=batch_size, shuffle=True, num_workers=num_workers)
    test_set = torchvision.datasets.MNIST(root='./data', train=False, download=True, transform=transform)
    test_loader = torch.utils.data.DataLoader(test_set, batch_size=test_batch_size, shuffle=True, num_workers=num_workers)

    return train_loader, test_loader


def train(model, train_loader, optimizer, epoch, train_counter, train_losses, log_interval=10):
    # set the module in training mode
    model.train()
    for batch_idx, (data, target) in enumerate(train_loader):
        optimizer.zero_grad()
        output = model(data)
        loss = F.nll_loss(output, target)
        loss.backward()
        optimizer.step()
        if batch_idx % log_interval == 0:
            print('train epoch: %d [%d/%d]\tloss: %.6f' % (epoch, batch_idx*len(data), len(train_loader.dataset), loss.item()))
            # keep track of loss
            train_losses.append(loss.item())
            train_counter.append((batch_idx*train_loader.batch_size) + ((epoch-1)*len(train_loader.dataset)))
            

def test(model, test_loader, epoch, test_counter, test_losses):
    model.eval()
    test_loss = 0
    correct_num = 0
    n = len(test_loader.dataset)
    with torch.no_grad():
        for data, target in test_loader:
            output = model(data)
            test_loss += F.nll_loss(output, target, reduction='sum').item() # sum up batch loss
            pred = output.argmax(dim=1, keepdim=True)
            correct_num += pred.eq(target.view_as(pred)).sum().item()
    test_loss /= n
    print('val set: average loss: %.4f, accuracy: %d/%d, %.1f%%\n' % (test_loss, correct_num, n, 100. * correct_num / n))
    test_losses.append(test_loss)


def plot_loss(train_counter, train_losses, test_counter, test_losses):
    plt.plot(train_counter, train_losses, color='blue', zorder=1)
    plt.scatter(test_counter, test_losses, color='red', zorder=2)
    plt.legend(['train loss', 'val loss'], loc='upper right')
    plt.xlabel('number of training examples')
    plt.ylabel('loss')
    plt.show()


def main():
    # configuration
    random_seed = 1
    # hyper-parameters
    learning_rate = 1e-2
    num_epochs = 5
    batch_size = 64
    optim_choice = 'sgd_momentum'
    # initialization
    torch.manual_seed(random_seed)
    train_loader, test_loader = load_data(batch_size=batch_size)
    network = Net()
    optimizers = {
        'sgd_momentum': optim.SGD(network.parameters(), lr=learning_rate, momentum=0.9),
        'adam': optim.SGD(network.parameters(), lr=learning_rate)
    }
    optimizer = optimizers[optim_choice]
    train_counter = []
    train_losses = []
    test_counter = [i*len(train_loader.dataset) for i in range(1, num_epochs+1)]
    test_losses = []
    for epoch in range(1, num_epochs+1):
        train(network, train_loader, optimizer, epoch, train_counter, train_losses)
        test(network, test_loader, epoch, test_counter, test_losses)
    # plot_loss(train_counter, train_losses, test_counter, test_losses)

if __name__ == "__main__":
    main()

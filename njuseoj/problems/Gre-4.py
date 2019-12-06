"""
硬币最小数量
Description

Given the list of coins of distinct denominations and total amount of money. Output the minimum number of coins required to make up that amount. Output -1 if that money cannot be made up using given coins. You may assume that there are infinite numbers of coins of each type.


Input

The first line contains 'T' denoting the number of test cases. Then follows description of test cases. Each cases begins with the two space separated integers 'n' and 'amount' denoting the total number of distinct coins and total amount of money respectively. The second line contains N space-separated integers A1, A2, ..., AN denoting the values of coins.

Constraints:1<=T<=30，1<=n<=100，1<=Ai<=1000，1<=amount<=100000


Output

Print the minimum number of coins required to make up that amount or return -1 if it is impossible to make that amount using given coins.



"""


def solve(coins, amounts):
    res = 0
    coins.sort(reverse=True)
    for coin in coins:
        res += amounts // coin
        amounts %= coin
        if amounts == 0:
            break
    if amounts == 0:
        print(res)
    else:
        print(-1)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, amounts = [int(x) for x in input().split()]
        coins = [int(x) for x in input().split()]
        solve(coins, amounts)

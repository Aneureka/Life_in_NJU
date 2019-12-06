"""
订单问题
Description

Rahul and Ankit are the only two waiters in Royal Restaurant. Today, the restaurant received N orders. The amount of tips may differ when handled by different waiters, if Rahul takes the ith order, he would be tipped Ai rupees and if Ankit takes this order, the tip would be Bi rupees.In order to maximize the total tip value they decided to distribute the order among themselves. One order will be handled by one person only. Also, due to time constraints Rahul cannot take more than X orders and Ankit cannot take more than Y orders. It is guaranteed that X + Y is greater than or equal to N, which means that all the orders can be handled by either Rahul or Ankit. Find out the maximum possible amount of total tip money after processing all the orders.


Input

• The first line contains one integer, number of test cases.

• The second line contains three integers N, X, Y.

• The third line contains N integers. The ith integer represents Ai.

• The fourth line contains N integers. The ith integer represents Bi.


Output

Print a single integer representing the maximum tip money they would receive.
"""


def solve(a, b, xx, yy):
    c = [x - y for x, y in zip(a, b)]
    c = [(x, i) for i, x in enumerate(c)]
    c.sort(key=lambda x: x[0], reverse=True)
    res = 0
    p = 0
    while p < xx and c[p][0] >= 0:
        res += a[c[p][1]]
        p += 1
    q = n - 1
    while n - 1 - q < yy and c[q][0] <= 0 and p <= q:
        res += b[c[q][1]]
        q -= 1
    if p <= q:
        if p < xx:
            for i in range(p, q + 1):
                res += a[c[i][1]]
        else:
            for i in range(p, q + 1):
                res += b[c[i][1]]
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, xx, yy = [int(x) for x in input().split()]
        a = [int(x) for x in input().split()]
        b = [int(x) for x in input().split()]
        solve(a, b, xx, yy)

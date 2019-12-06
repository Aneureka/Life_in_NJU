"""
如何花最少的钱购买蔬菜
Description

Rahul wanted to purchase vegetables mainly brinjal, carrot and tomato. There are N different vegetable sellers in a line. Each vegetable seller sells all three vegetable items, but at different prices. Rahul, obsessed by his nature to spend optimally, decided not to purchase same vegetable from adjacent shops. Also, Rahul will purchase exactly one type of vegetable item (only 1 kg) from one shop. Rahul wishes to spend minimum money buying vegetables using this strategy. Help Rahul determine the minimum money he will spend.


Input

First line indicates number of test cases T. Each test case in its first line contains N denoting the number of vegetable sellers in Vegetable Market. Then each of next N lines contains three space separated integers denoting cost of brinjal, carrot and tomato per kg with that particular vegetable seller.


Output

For each test case, output the minimum cost of shopping taking the mentioned conditions into account in a separate line.

Constraints:1 <= T <= 101 <= N <= 100000 Cost of each vegetable(brinjal/carrot/tomato) per kg does not exceed 10^4
"""


def solve(prices):
    dp = [0] * 3
    for i in range(1, n + 1):
        new_dp = [0] * 3
        new_dp[0] = min(dp[1], dp[2]) + prices[i - 1][0]
        new_dp[1] = min(dp[0], dp[2]) + prices[i - 1][1]
        new_dp[2] = min(dp[0], dp[1]) + prices[i - 1][2]
        dp = new_dp
    print(min(dp[0], dp[1], dp[2]))


if __name__ == '__main__':
    test_cnt = int(input())

    for t in range(test_cnt):
        n = int(input())
        prices = []
        for i in range(n):
            prices.append([int(x) for x in input().split()])
        solve(prices)


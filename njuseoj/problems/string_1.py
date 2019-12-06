"""
最长公共子序列
Description

给定两个字符串，返回两个字符串的最长公共子序列（不是最长公共子字符串），可能是多个。


Input

输入第一行为用例个数， 每个测试用例输入为两行，一行一个字符串


Output

如果没有公共子序列，不输出，如果有多个则分为多行，按字典序排序。
"""


def dfs(dp, str1, str2, i, j, max_length, path, res):
    if i == 0 or j == 0:
        if len(path) == max_length:
            res.append(path[::-1])
        return
    if str1[i - 1] == str2[j - 1]:
        path += str1[i - 1]
        dfs(dp, str1, str2, i - 1, j - 1, max_length, path, res)
        path = path[:-1]
    else:
        if dp[i - 1][j] > dp[i][j - 1]:
            dfs(dp, str1, str2, i - 1, j, max_length, path, res)
        elif dp[i - 1][j] < dp[i][j - 1]:
            dfs(dp, str1, str2, i, j - 1, max_length, path, res)
        else:
            dfs(dp, str1, str2, i - 1, j, max_length, path, res)
            dfs(dp, str1, str2, i, j - 1, max_length, path, res)


def solve(str1, str2):
    m = len(str1)
    n = len(str2)
    dp = []
    for i in range(m + 1):
        dp.append([0] * (n + 1))
    for i in range(1, m + 1):
        for j in range(1, n + 1):
            if str1[i - 1] == str2[j - 1]:
                dp[i][j] = 1 + dp[i - 1][j - 1]
            else:
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
    # backtracking
    res = []
    dfs(dp, str1, str2, m, n, dp[m][n], "", res)
    for path in sorted(set(res)):
        print(path)


if __name__ == "__main__":
    test_cnt = int(input())
    for t in range(test_cnt):
        str1 = input()
        str2 = input()
        solve(str1, str2)

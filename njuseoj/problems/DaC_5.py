"""
书本分发
Description

You are given N number of books. Every ith book has Pi number of pages. You have to allocate books to M number of students. There can be many ways or permutations to do so. In each permutation one of the M students will be allocated the maximum number of pages. Out of all these permutations, the task is to find that particular permutation in which the maximum number of pages allocated to a student is minimum of those in all the other permutations, and print this minimum value. Each book will be allocated to exactly one student. Each student has to be allocated atleast one book.


Input

The first line contains 'T' denoting the number of testcases. Then follows description of T testcases:Each case begins with a single positive integer N denoting the number of books.The second line contains N space separated positive integers denoting the pages of each book.And the third line contains another integer M, denoting the number of studentsConstraints:1<= T <=70，1<= N <=50，1<= A [ i ] <=250，1<= M <=50，Note: Return -1 if a valid assignment is not possible, and allotment should be in contiguous order (see explanation for better understanding)


Output

For each test case, output a single line containing minimum number of pages each student has to read for corresponding test case.
"""


def solve(pages, m):
    n = len(pages)
    if m > n:
        print(-1)
        return
    dp = []
    for i in range(m+1):
        dp.append([0] * (n+1))
    for i in range(1, m+1):
        dp[i][1] = pages[0]
    for i in range(1, n+1):
        dp[1][i] = sum(pages[:i])
    for i in range(2, m+1):
        for j in range(2, n+1):
            min_avg = sum(pages)
            for k in range(j):
                min_avg = min(min_avg, max(dp[i-1][k], sum(pages[k:j])))
            dp[i][j] = min_avg
    print(dp[m][n])


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        pages = [int(x) for x in input().split()]
        m = int(input())
        solve(pages, m)

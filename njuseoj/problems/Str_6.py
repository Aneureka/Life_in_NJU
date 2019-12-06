"""
能否成环
Description

Given an array of strings A[ ], determine if the strings can be chained together to form a circle. A string X can be chained together with another string Y if the last character of X is same as first character of Y. If every string of the array can be chained, it will form a circle. For example, for the array arr[] = {"for", "geek", "rig", "kaf"} the answer will be Yes as the given strings can be chained as "for", "rig", "geek" and "kaf".


Input

The first line of input contains an integer T denoting the number of test cases. Then T test cases follow.

The first line of each test case contains a positive integer N, denoting the size of the array.

The second line of each test case contains a N space seprated strings, denoting the elements of the array A[ ].

1 <= T

0 < N

0 < A[i]


Output

If chain can be formed, then print 1, else print 0.
"""


def permute(head_map, head, tail):
    full = True
    for v in head_map.values():
        if len(v) > 0:
            full = False
            break
    if full:
        return head == tail
    if head not in head_map:
        return False
    for i, item in enumerate(head_map[head]):
        tmp = head_map[head].pop(i)
        if permute(head_map, tmp[-1], tail):
            return True
        head_map[head].insert(i, tmp)
    return False


def solve(strs):
    head_map = {}
    for s in strs[1:]:
        if s[0] not in head_map.keys():
            head_map[s[0]] = [s]
        else:
            head_map[s[0]] += [s]
    if permute(head_map, strs[0][-1], strs[0][0]):
        print(1)
    else:
        print(0)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        strs = input().split()
        solve(strs)

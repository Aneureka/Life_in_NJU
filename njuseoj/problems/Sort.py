"""
按照数值个数排序
Description

对给定数组中的元素按照元素出现的次数排序，出现次数多的排在前面，如果出现次数相同，则按照数值大小排序。例如，给定数组为{2, 3, 2, 4, 5, 12, 2, 3, 3, 3, 12}，则排序后结果为{3, 3, 3, 3, 2, 2, 2, 12, 12, 4, 5}。


Input

输入的第一行为用例个数；后面每一个用例使用两行表示，第一行为数组长度，第二行为数组内容，数组元素间使用空格隔开。


Output

每一个用例的排序结果在一行中输出，元素之间使用空格隔开。
"""


def solve(nums):
    freq_of_num = {}
    for num in nums:
        freq_of_num[num] = (0 if num not in freq_of_num.keys() else freq_of_num[num]) + 1
    nums_of_freq = {}
    for num, freq in freq_of_num.items():
        nums_of_freq[freq] = ([] if freq not in nums_of_freq.keys() else nums_of_freq[freq]) + [num]
    res = []
    for k in sorted(list(nums_of_freq.keys()), reverse=True):
        nums = sorted(nums_of_freq[k])
        for num in nums:
            res += [num] * k
    print(" ".join([str(x) for x in res]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        solve(nums)

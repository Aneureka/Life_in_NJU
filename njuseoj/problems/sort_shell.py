"""
实现Shell排序
Description

实现Shell排序，对给定的无序数组，按照给定的间隔变化（间隔大小即同组数字index的差），打印排序结果，注意不一定是最终排序结果！


Input

输入第一行表示测试用例个数，后面为测试用例，每一个用例有两行，第一行为给定数组，第二行为指定间隔，每一个间隔用空格隔开。


Output

输出的每一行为一个用例对应的指定排序结果。
"""


def shell_sub_sort(nums, gap):
    n = len(nums)
    for i in range(gap):
        for j in range(i, n, gap):
            k = j
            while k > i and nums[k] < nums[k-gap]:
                nums[k], nums[k-gap] = nums[k-gap], nums[k]
                k -= gap
    return nums


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        nums = [int(x) for x in input().split(" ")]
        gaps = [int(x) for x in input().split(" ")]
        for gap in gaps:
            shell_sub_sort(nums, gap)
        print(" ".join([str(x) for x in nums]))

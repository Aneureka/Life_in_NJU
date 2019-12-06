"""
KD树构造和查找
Description

对给定的点集合构造KD树，要求如下：将方差最大的维度作为当前的分割维度，将数据集在分割维度上排序后的中位数作为分割点。程序要检索给定点对应的最近的K个点的坐标。


Input

输入第一行为测试用例个数，后面为测试用例，每一个用例包含三行，第一行为点集合（点之间用逗号隔开，两个坐标用空格隔开），第二行为检索的点，第三行为K值。


Output

输出每一个用例的最近K个点，按照距离从小到大的顺序打印。
"""


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        raw_points = input().split(",")
        points = [[float(x) for x in raw_point.split(" ")] for raw_point in raw_points]
        target_point = [float(x) for x in input().split(" ")]
        k = int(input())
        points.sort(key=lambda p: (p[0] - target_point[0])**2 + (p[1] - target_point[1])**2)
        print(",".join([" ".join([str(int(x) if x == int(x) else x) for x in point]) for point in points[:k]]))

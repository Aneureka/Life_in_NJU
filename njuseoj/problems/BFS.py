"""
广度优先遍历图
Description

按照给定的起始顶点广度优先遍历图，每一次通过字母顺序选择顶点查找下一层邻接点，打印遍历顺序。


Input

输入第一行为测试用例个数，后面每一个用例用多行表示，用例第一行是节点个数n和开始顶点，用空格隔开，后面n+1行为图的邻接矩阵，其中第一行为节点名称。值之间使用空格隔开。


Output

输出遍历顺序，用空格隔开
"""


def bfs(nodes, graph, start_node):
    visited = {}
    for node in nodes:
        visited[node] = False
    queue = [start_node]
    res = []
    while len(queue) > 0:
        node = queue.pop(0)
        if visited[node]:
            continue
        res.append(node)
        visited[node] = True
        for neighbor in graph[node]:
            if not visited[neighbor]:
                queue.append(neighbor)
    return res


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, start_node = input().split(" ")
        n = int(n)
        graph = {}
        nodes = input().split(" ")
        for node in nodes:
            graph[node] = []
        for i in range(n):
            row = input().split(" ")
            node = row[0]
            table = [int(x) for x in row[1:]]
            for idx, exist in enumerate(table):
                if exist == 1:
                    graph[node].append(nodes[idx])
        res = bfs(nodes, graph, start_node)
        print(" ".join(res))

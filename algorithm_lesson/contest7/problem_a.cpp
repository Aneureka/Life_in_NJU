#include <iostream>
#include <stdio.h>
#include <vector>

#define WHITE 0
#define GREY 1
#define BLACK 2

using namespace std;

typedef struct Edge {
    int v;
    int weight;
    Edge(int v1, int weight1) {
        v = v1;
        weight = weight1;
    }
} Edge;

void _dfs(vector<Edge>* graph, vector<int>& typo_list, int* color, int u) {
    color[u] = GREY;
    vector<Edge> neighbor = graph[u];
    for (int i = 0; i < neighbor.size(); ++i) {
        int v = neighbor[i].v;
        if (color[v] == WHITE) {
            _dfs(graph, typo_list, color, v);
        }
    }
    typo_list.push_back(u);
    color[u] = BLACK;
}

void dfs(vector<Edge>* graph, vector<int>& typo_list, int n) {
    int* color = new int[n];
    for (int i = 0; i < n; ++i) {
        color[i] = WHITE;
    }

    _dfs(graph, typo_list, color, 0);

    delete[] color;
}

int main() {

    int n = 0;
    cin >> n;

    vector<Edge>* graph = new vector<Edge>[n];
    int u, v, weight;
    while (scanf("%d %d %d", &u, &v, &weight) != EOF) {
        graph[u].push_back(Edge(v, weight));
    }

    // get typo-order list
    vector<int> typo_list;
    dfs(graph, typo_list, n);

    // dp
    int* maxs = new int[n];
    for (int i = 0; i < n; ++i) {
        maxs[i] = INT32_MIN;
    }
    maxs[0] = 0;



    for (int i = typo_list.size()-1; i >= 0; --i) {
        int v = typo_list[i];
        for (int j = 0; j < graph[v].size(); ++j) {
            int weight = graph[v][j].weight;
            int to = graph[v][j].v;
            if (maxs[to] < maxs[v] + weight) {
                maxs[to] = maxs[v] + weight;
            }
        }
    }

    cout << "I have read the rules about plagiarism punishment" << endl;
    for (int i = 1; i < n; ++i) {
        cout << maxs[i] << " ";
    }
    cout << endl;

    delete[] graph;
    delete[] maxs;
}

#include <iostream>
#include <stdio.h>
#include <vector>
#include <queue>

using namespace std;

typedef struct Edge {
    int v;
    int weight;
    Edge(int v1, int weight1) {
        v = v1;
        weight = weight1;
    }
} Edge;

typedef struct PQV {
    int from;
    int v;
    int max_weight;

    PQV(int from1, int v1, int max_weight1) {
        from = from1;
        v = v1;
        max_weight = max_weight1;
    }

    friend bool operator< (const PQV v1, const PQV v2) {
        return v1.max_weight > v2.max_weight;
    }

} pqv;


int dijstra(vector<Edge>* graph, int n, int s, int t) {
    // set all vertices as unseen

    int* froms = new int[n];
    for (int i = 0; i < n; ++i) {
        froms[i] = -1;
    }

    froms[s] = -2;

    priority_queue<pqv> pq;

    for (int i = 0; i < graph[s].size(); ++i) {
        int v = graph[s][i].v;
        int weight = graph[s][i].weight;
        pq.push(pqv(s, v, weight));
    }


    while (!pq.empty()) {
        pqv x = pq.top();
        pq.pop();
        int v = x.v;
        if (froms[v] != -1) {
            continue;
        }
        froms[v] = x.from;

        if (v == t) {
            delete[] froms;
            return x.max_weight;
        }

        // add neighbors of v
        vector<Edge> neighbor = graph[v];
        for (int i = 0; i < neighbor.size(); ++i) {
            int w = neighbor[i].v;
            int weight = neighbor[i].weight;
            if (froms[w] == -1) {
                int new_priority = max(x.max_weight, weight);
                pq.push(pqv(v, w, new_priority));
            }
        }
    }

    delete[] froms;
    return 0;

}

int main() {

    cout << "I have read the rules about plagiarism punishment" << endl;

    int n=0, s=0, t=0;
    cin >> n >> s >> t;

    vector<Edge>* graph = new vector<Edge>[n];
    int u, v, weight;
    while (scanf("%d %d %d", &weight, &u, &v) != EOF) {
        graph[u].push_back(Edge(v, weight));
        graph[v].push_back(Edge(u, weight));
    }

    priority_queue<pqv> pq;

    cout << dijstra(graph, n, s, t) << endl;

    delete[] graph;

}
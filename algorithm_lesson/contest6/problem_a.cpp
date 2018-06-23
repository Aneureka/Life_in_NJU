#include <stdio.h>
#include <iostream>
#include <vector>
#include <stack>
#include <sstream>
#include <set>
#include <algorithm>
using namespace std;

#define WHITE 0
#define GRAY 1
#define BLACK 2

void draw_white(vector<int>& color, int n) {
    bool empty = color.empty();
    for (int i = 0; i < n; ++i) {
        if (empty)
            color.push_back(WHITE);
        else
            color[i] = WHITE;
    }
}

void dfs(const vector<vector<int> >& graph, vector<int>& color, stack<int>& s, int v) {
    color[v] = GRAY;
    vector<int> v_neighbor = graph[v];
    for (int i = 0; i < v_neighbor.size(); ++i) {
        int w = v_neighbor[i];
        if (color[w] == WHITE) {
            dfs(graph, color, s, w);
        }
    }
    s.push(v);
    color[v] = BLACK;
}

void dfs_t(const vector<vector<int> >& graph, vector<int>& color, vector<int>& at_scc, vector<vector<int> >& graph_scc, vector<int>& scc_size, int v, int scc) {
    color[v] = GRAY;
    if (scc < 0) {
        // add "-1" to initialize
        vector<int> t; t.push_back(-1);
        graph_scc.push_back(t);
        scc_size.push_back(1);
        at_scc[v] = scc_size.size() - 1;
    } else {
        scc_size[scc_size.size()-1] += 1;
        at_scc[v] = scc;
    }

    vector<int> v_neighbor = graph[v];
    for (int i = 0; i < v_neighbor.size(); ++i) {
        int w = v_neighbor[i];
        if (color[w] == WHITE) {
            dfs_t(graph, color, at_scc, graph_scc, scc_size, w, at_scc[v]);
        }
        else if (color[w] == BLACK) {
            graph_scc[at_scc[w]].push_back(at_scc[v]);
        }
    }
    color[v] = BLACK;
}

void dfs_scc(const vector<vector<int> >& graph, const vector<int>& scc_size, vector<int>& color, vector<int>& impact, int v, int cur_scc) {
    color[v] = GRAY;
    impact[cur_scc] += scc_size[v];
    vector<int> v_neighbor = graph[v];
    for (int i = 0; i < v_neighbor.size(); ++i) {
        int w = v_neighbor[i];
        if (color[w] == WHITE) {
            dfs_scc(graph, scc_size, color, impact, w, cur_scc);
        }
    }
    color[v] = BLACK;
}

void dfs_wrapper(const vector<vector<int> >& graph, vector<int>& color, stack<int>& s) {
    int n = graph.size();
    draw_white(color, n);
    for (int i = 0; i < n; ++i) {
        if (color[i] == WHITE) {
            dfs(graph, color, s, i);
        }
    }
}

void dfs_t_wrapper(const vector<vector<int> >& graph, vector<int>& color, vector<int>& at_scc, vector<vector<int> >& graph_scc, vector<int>& scc_size, stack<int>& s) {
    int n = graph.size();
    draw_white(color, n);
    for (int i = 0; i < n; ++i) {
        at_scc.push_back(-1);
    }
    while (!s.empty()) {
        int i = s.top();
        s.pop();
        if (color[i] == WHITE) {
            dfs_t(graph, color, at_scc, graph_scc, scc_size, i, -1);
        }
    }
    // delete "-1" at the begin of the neighbor
    for (int i = 0; i < graph_scc.size(); ++i) {
        graph_scc[i].erase(graph_scc[i].begin());
    }
}

void dfs_scc_wrapper(const vector<vector<int> >& graph, const vector<int>& scc_size, vector<int>& color, vector<int>& impact) {
    int n = graph.size();
    for (int i = 0; i < n; ++i) {
        draw_white(color, n);
        dfs_scc(graph, scc_size, color, impact, i, i);
    }
}

void print_n(const vector<vector<int> >& graph) {
    for (int i = 0; i < graph.size(); ++i) {
        vector<int> t_n = graph[i];
        for (int j = 0; j < t_n.size(); ++j) {
            cout << t_n[j] << " ";
        }
        cout << endl;
    }
}

void print_stack(const stack<int>& s) {
    stack<int> temp = s;
    while (!temp.empty()) {
        cout << temp.top() << " ";
        temp.pop();
    }

    cout << endl;
}

int main() {

    vector<vector<int> > graph;

    string line;
    int t_neighbor;
    while (getline(cin, line)) {
        stringstream ss;
        ss << line;
        vector<int> t;
        while (ss >> t_neighbor) {
            t.push_back(t_neighbor);
        }
        graph.push_back(t);
    }

    // perform dfs at graph and store vertex in stack
    vector<int> color;
    stack<int> s;
    dfs_wrapper(graph, color, s);

    // transpose graph gT
    int n = graph.size();
    vector<vector<int> > graph_t(n);
    for (int i = 0; i < n; ++i) {
        vector<int> t = graph[i];
        for (int j = 0; j < t.size(); ++j) {
            graph_t[t[j]].push_back(i);
        }
    }

    // perform dfs at transposed graph and get transposed scc graph
    vector<int> color_t;
    vector<int> at_scc;
    vector<vector<int> > graph_scc;
    vector<int> scc_size;
    dfs_t_wrapper(graph_t, color_t, at_scc, graph_scc, scc_size, s);

    // now we have [scc graph], [vertex-scc assignment], [scc sizes]
    // so we can calculate impact of each scc, use dfs to achieve it
    int n_scc = graph_scc.size();
    vector<int> impact(n_scc);
    vector<int> color_scc;
    dfs_scc_wrapper(graph_scc, scc_size, color, impact);

    // get the max impact [inclusive]
    int max_impact = -1;
    set<int> max_scc_set;
    for (int i = 0; i < n_scc; ++i) {
        if (impact[i] > max_impact) {
            max_impact = impact[i];
            max_scc_set.clear();
            max_scc_set.insert(i);
        }
        else if (impact[i] == max_impact) {
            max_scc_set.insert(i);
        }
    }
    max_impact = max_impact > 0 ? max_impact-1 : 0;


    cout << "I have read the rules about plagiarism punishment" << endl;
    cout << max_impact << endl;
    for (int i = 0; i < n; ++i) {
        if (max_scc_set.find(at_scc[i]) != max_scc_set.end()) {
            cout << i << " ";
        }
    }
    cout << endl;
}

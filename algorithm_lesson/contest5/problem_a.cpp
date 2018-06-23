#include <iostream>
#include <vector>
#include <set>
#include <algorithm>
using namespace std;

#define WHITE 0
#define GRAY 1

struct Edge {
    int from;
    int to;
    Edge(int f, int t) {
        from = f;
        to = t;
    }
    bool operator< (Edge e2) const {
        if (from < e2.from) {
            return true;
        }
        else if (from > e2.from) {
            return false;
        }
        else {
            return to < e2.to;
        }
    }
};


typedef set<int> int_set;
typedef vector<Edge> edge_list;

void init_color(int* color, int n);
int articulation_point_dfs(int* color, int* discover_time, int* back, vector<int>* neighbor, int v, int parent, int_set& articulation_points);
void bridge_dfs(int* color, int* discover_time, int* back, vector<int>* neighbor, int u, int parent, edge_list& bridges);

int dfs_time;

int main() {

    int n = 0;
    cin >> n;

    vector<int>* neighbor = new vector<int>[n];
    int v, w;
    while (scanf("%d %d", &v, &w) != EOF) {
        neighbor[v].push_back(w);
        neighbor[w].push_back(v);
    }

    int* color = new int[n];
    int* discover_time = new int[n];
    int* back = new int[n];
    int_set articulation_points;
    edge_list bridges;

    init_color(color, n);
    dfs_time = 0;
    for (int i = 0; i < n; ++i) {
        if (color[i] == WHITE) {
            articulation_point_dfs(color, discover_time, back, neighbor, 0, -1, articulation_points);
        }
    }

    init_color(color, n);
    dfs_time = 0;
    for (int i = 0; i < n; ++i) {
        if (color[i] == WHITE) {
            bridge_dfs(color, discover_time, back, neighbor, 0, -1, bridges);
        }
    }
    sort(bridges.begin(), bridges.end());


    cout << "wo yi yue du guan yu chao xi de shuo ming" << endl;
    int_set::iterator p_itr;
    for (p_itr = articulation_points.cbegin(); p_itr != articulation_points.cend(); p_itr++) {
        cout << *p_itr << endl;
    }


    edge_list::iterator e_itr;
    for (e_itr = bridges.begin(); e_itr != bridges.end(); e_itr++) {
        cout << (*e_itr).from << " " << (*e_itr).to << endl;
    }

    delete[] color;
    delete[] discover_time;
    delete[] back;
    delete[] neighbor;

}

int articulation_point_dfs(int* color, int* discover_time, int* back, vector<int>* neighbor, int v, int parent, int_set& articulation_points) {
    color[v] = GRAY;
    dfs_time++;
    discover_time[v] = dfs_time;
    back[v] = discover_time[v];
    vector<int> t_neighbor = neighbor[v];
    int root_check = 0;

    for (int i = 0; i < t_neighbor.size(); ++i) {
        int w = t_neighbor[i];
        if (color[w] == WHITE) {
            root_check++;
            back[w] = articulation_point_dfs(color, discover_time, back, neighbor, w, v, articulation_points);
            if ((v != 0 && back[w] >= discover_time[v]) || (v == 0 && root_check > 1)) {
                articulation_points.insert(v);
            }
            back[v] = min(back[v], back[w]);
        }
        else {
            if (color[w] == GRAY && w != parent) {
                back[v] = min(back[v], discover_time[w]);
            }
        }
    }

    return back[v];

}

void bridge_dfs(int* color, int* discover_time, int* back, vector<int>* neighbor, int u, int parent, edge_list& bridges) {
    color[u] = GRAY;
    dfs_time++;
    discover_time[u] = dfs_time;
    back[u] = discover_time[u];
    vector<int> t_neighbor = neighbor[u];
    for (int i = 0; i < t_neighbor.size(); ++i) {
        int v = t_neighbor[i];
        if (color[v] == WHITE) {
            bridge_dfs(color, discover_time, back, neighbor, v, u, bridges);
            back[u] = min(back[u], back[v]);
            if (back[v] > discover_time[u]) {
                if (u < v) {
                    bridges.push_back(Edge(u, v));
                }
                else {
                    bridges.push_back(Edge(v, u));
                }
            }
        }
        else {
            if (color[v] == GRAY && v != parent) {
                back[u] = min(back[u], discover_time[v]);
            }
        }
    }
}

void init_color(int* color, int n) {
    for (int i = 0; i < n; ++i) {
        color[i] = WHITE;
    }
}
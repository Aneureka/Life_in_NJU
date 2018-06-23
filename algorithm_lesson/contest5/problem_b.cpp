#include <iostream>
#include <vector>
using namespace std;

void dfs(int* time, int* color, int* eft, vector<int>* neighbor, int index);

int main() {

    int n = 0;
    cin >> n;
    int* time = new int[n+1];
    int* color = new int[n+1];
    int* eft = new int[n+1];
    vector<int> *neighbor = new vector<int>[n+1];

    // initialization
    int t_number, t_time;
    for (int i = 1; i < n+1; ++i) {
        cin >> t_number >> t_time;
        time[t_number] = t_time;
    }

    int w = 0, v = 0;
    while (cin >> w >> v) {
        neighbor[w].push_back(v);
    }

    for (int i = 1; i < n+1; ++i) {
        color[i] = 0;
        eft[i] = 0;
    }

    for (int i = 1; i < n+1; ++i) {
        if (color[i] == 0) {
           dfs(time, color, eft, neighbor, i);
        }
    }

    int max_time = 0;
    for (int i = 1; i < n+1; ++i) {
        if (eft[i] > max_time)
            max_time = eft[i];
    }

    delete[] time;
    delete[] color;
    delete[] eft;
    delete[] neighbor;

    cout << "I have read the rules about plagiarism punishment" << endl;
    cout << max_time << endl;
}

void dfs(int* time, int* color, int* eft, vector<int>* neighbor, int index) {
    color[index] = 1;
    int est = 0;
    vector<int> t_neighbor = neighbor[index];
    for (int i = 0; i < t_neighbor.size(); ++i) {
        int n_index = t_neighbor[i];
        if (color[n_index] == 0) {
            dfs(time, color, eft, neighbor, n_index);
            if (eft[n_index] >= est) {
                est = eft[n_index];
            }
        }
        else {
            if (eft[n_index] >= est) {
                est = eft[n_index];
            }
        }
    }
    eft[index] = est + time[index];
}


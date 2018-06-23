#include <iostream>
#include <string>
using namespace std;

int compare(const string& a, const string& b);

long sort(string* items, int begin, int end);

long merge_and_count(string* items, int begin, int end, int mid);

int main() {
    int n;
    cin >> n;
    string *items = new string[n];
    for (int i = 0; i < n; ++i) {
        cin >> items[i];
    }
    cout << "wo yi yue du guan yu chao xi de shuo ming" << endl;

    cout << sort(items, 0, n-1);

    delete[] items;

}

int compare(const string& a, const string& b) {

    unsigned long la = a.length();
    unsigned long lb = b.length();

    for (unsigned long i = 0; i < min(la, lb); ++i) {
        if (a.at(i) < b.at(i))
            return -1;
        if (a.at(i) > b.at(i))
            return 1;
    }

    if (la < lb) return -1;
    else if (la > lb) return 1;
    else return 0;

}

long sort(string* items, int begin, int end) {
    if (begin >= end)
        return 0;
    int mid = begin + (end-begin) / 2;
    return sort(items, begin, mid)
           + sort(items, mid+1, end)
           + merge_and_count(items, begin, end, mid);
}

long merge_and_count(string* items, int begin, int end, int mid) {

    int length = end - begin + 1;
    string *aux = new string[length];

    int i = begin, j = mid+1;
    long res = 0;

    for (int index = 0; index < length; ++index) {
        if (i > mid) {
            aux[index] = items[j++];
        }
        else if (j > end) {
            aux[index] = items[i++];
        }
        else if (compare(items[i], items[j]) <= 0) {
            aux[index] = items[i++];
        }
        else {
            aux[index] = items[j++];
            res += mid-i+1;
        }
    }

    for (int k = 0; k < length; ++k) {
        items[begin+k] = aux[k];
    }

    delete[] aux;

    return res;

}

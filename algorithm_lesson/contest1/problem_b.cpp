#include <iostream>
#include <algorithm>
using namespace std;

int main() {

    int k, n;
    cin >> k >> n;
    int a[n];
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }

    sort(a, a+n);

    int mid_i = (n-1) >> 1;
    for (int i = mid_i-k; i <= mid_i+k; ++i) {
        cout << a[i] << " ";
    }

}
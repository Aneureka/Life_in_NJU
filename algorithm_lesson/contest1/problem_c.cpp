#include <iostream>
using namespace std;

int main() {
    int n, c;
    cin >> n;
    int arr[n];
    for (int i = 0; i < n; ++i) {
        cin >> arr[i];
    }
    cin >> c;

    int i = 0, j = n-1;
    int temp;
    while (i < j) {
        temp = arr[i] + arr[j];
        if (temp < c) {
            i++;
        }
        else if (temp > c) {
            j--;
        }
        else {
            cout << arr[i] << " " << arr[j] << endl;
            i++;
            j--;
        }
    }

    return 0;
}
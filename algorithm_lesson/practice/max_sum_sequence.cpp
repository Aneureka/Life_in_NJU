#include <iostream>
using namespace std;

int max(int a, int b, int c) {
    return a > b ? (a > c ? a : c) : (b > c ? b : c);
}

int mss(int A[], int low, int high) {

    if (low == high) {
        return A[low];
    }

    int mid = low + (high - low) / 2;

    int mss_left = mss(A, low, mid);
    int mss_right = mss(A, mid+1, high);

    int max_left = 0, left = 0;
    for (int i = mid; i >= low; --i) {
        left += A[i];
        if (left > max_left)
            max_left = left;
    }

    int max_right = 0, right = 0;
    for (int j = mid+1; j <= high; ++j) {
        right += A[j];
        if (right > max_right)
            max_right = right;
    }

    return max(mss_left, mss_right, max_left + max_right);

}




int main() {
    int A[6] = {-2, 11, -4, 13, -5, -2};
    cout << mss(A, 0, 5);
}



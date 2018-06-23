#include <iostream>
#include <algorithm>
using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int v) {
        val = v;
    }
};

void print(TreeNode* root);
int high_of(TreeNode* root);
int diameter_of(TreeNode* root);

TreeNode* build_tree(int pre_order[], int in_order[], int low1, int high1, int low2, int high2);
int index_of(const int nums[], int low, int high, int val);


int main() {

    int n = 0;
    cin >> n;
    auto pre_order = new int[n];
    auto in_order = new int[n];
    for (int i = 0; i < n; ++i) {
        cin >> pre_order[i];
    }
    for (int i = 0; i < n; ++i) {
        cin >> in_order[i];
    }

    TreeNode* root = build_tree(pre_order, in_order, 0, n-1, 0, n-1);
    cout << "I have read the rules about plagiarism punishment" << endl;
    cout << high_of(root) << endl;
    cout << diameter_of(root) << endl;

}

TreeNode* build_tree(int pre_order[], int in_order[], int low1, int high1, int low2, int high2) {
    int root_val = pre_order[low1];
    TreeNode* root = new TreeNode(root_val);
    if (low1 >= high1)
        return root;
    int index_in_order = index_of(in_order, low2, high2, root_val);
    int index_pre_order = low1+index_in_order-low2+1;
    if (index_in_order > low2)
        root->left = build_tree(pre_order, in_order, low1+1, index_pre_order-1, low2, index_in_order-1);
    if (index_in_order < high2)
        root->right = build_tree(pre_order, in_order, index_pre_order, high1, index_in_order+1, high2);
    return root;
}

int high_of(TreeNode* root) {
    if (root == NULL)
        return 0;
    if (root->left == NULL && root->right == NULL) {
        return 0;
    }
    return 1 + max(high_of(root->left), high_of(root->right));
}

int diameter_of(TreeNode* root) {
    if (root == NULL) {
        return 0;
    }
    if (root->left == NULL && root->right == NULL) {
        return 0;
    }
    int high_of_left = high_of(root->left);
    int high_of_right = high_of(root->right);
    int diameter = 0;
    if (root->left != NULL) {
        diameter += 1 + high_of_left;
    }
    if (root->right != NULL) {
        diameter += 1 + high_of_right;
    }
    return max(max(diameter, diameter_of(root->left)), max(diameter, diameter_of(root->right)));

}

void print(TreeNode* root) {
    if (root == NULL) {
        return;
    }
    cout << root->val << " ";
    print(root->left);
    print(root->right);
}

int index_of(const int nums[], int low, int high, int val) {
    for (int i = low; i <= high; ++i) {
        if (val == nums[i])
            return i;
    }
    return -1;
}
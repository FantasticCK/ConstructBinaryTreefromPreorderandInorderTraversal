package com.CK;

public class Main {

    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7}, inorder = {9, 3, 15, 20, 7};
        new Solution().buildTree(preorder, inorder);

    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0 || inorder.length == 0 || preorder.length != inorder.length) return null;
        else return dfs(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode dfs(int[] preorder, int preS, int preE, int[] inorder, int inS, int inE) {
        if (preS >= preorder.length || inS >= inorder.length || preE < 0 || inE < 0) return null;

        if (preE - preS == 0 || inorder.length == 0) {
            return new TreeNode(preorder[preE]);
        }

        int target = preorder[preS];
        TreeNode root = new TreeNode(target);
        int rightIdx = findFirstRightSide(preorder, preS, preE, inorder, inS, inE, target);

        if (rightIdx == -1) {
            preS++;
            inE = indexOf(inorder, target) - 1;
            root.right = null;
            root.left = dfs(preorder, preS, preE, inorder, inS, inE);
        } else if (rightIdx == preS + 1) {
            preS++;
            inS++;
            root.left = null;
            root.right = dfs(preorder, preS, preE, inorder, inS, inE);
        } else {
            int leftS = preS + 1, leftE = rightIdx - 1, leftInS = inS, leftInE = indexOf(inorder, target) - 1;
            root.left = dfs(preorder, leftS, leftE, inorder, leftInS, leftInE);
            int rightS = rightIdx, rightE = preE, rightInS = indexOf(inorder, target) + 1, rightInE = inE;
            root.right = dfs(preorder, rightS, rightE, inorder, rightInS, rightInE);
        }

        return root;
    }

    private int findFirstRightSide(int[] preorder, int preS, int preE, int[] inorder, int inS, int inE, int target) {
        int left = preS + 1, right = preE;
        if (left >= preorder.length) return -1;

        int targetIdx = indexOf(inorder, target);
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            int midVal = preorder[mid];
            if (indexOf(inorder, midVal) > targetIdx)
                right = mid;
            else
                left = mid;
        }
        if (indexOf(inorder, preorder[left]) > targetIdx) return left;
        if (indexOf(inorder, preorder[right]) > targetIdx) return right;
        return -1;
    }

    private int indexOf(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target)
                return i;
        }
        return -1;
    }
}
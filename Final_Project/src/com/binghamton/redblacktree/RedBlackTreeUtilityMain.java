package com.binghamton.redblacktree;

/**
 * @author Prashant Prabhu Kariyannavar
 *
 */
public class RedBlackTreeUtilityMain {
	
	public static void main(String[] args) {
		BinarySearchTreeNode root = null;
		RedBlackTreeImpl redBlackTree = new RedBlackTreeImpl();
		int[] arr = { 10, 18, 7, 15, 16, 30, 25, 40, 60, 2, 1, 70 };
		int[] arr2 = { 2, 3, 6, 7, 8, 10, 11, 13, 15, 22, 26 };
		// int[] arr1 = { 10, 15, -10, 20, 30, 40, 50, -15, 25, 17, 21, 24, 28, 34, 26,
		// 35, 19 };
		for (int index : arr2) {
			root = redBlackTree.insert(root, index);
		}
		redBlackTree.printTheWholeTree(root);

		System.out.println("");
		System.out.println("Pre-order Traversal after insertion of nodes are :");
		redBlackTree.preOrder(root);
		System.out.println("");
		System.out.println("");
		System.out.println("In-order Traversal after insertion of nodes are :");
		redBlackTree.inOrder(root);
		System.out.println(" ");
		System.out.println("");
		System.out.println("Post-order Traversal after insertion of nodes are :");
		redBlackTree.postOrder(root);
		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.out.println("After deletion");

		int[] arrDel = { 26 };
		for (int delIndex : arrDel) {
			root = redBlackTree.delete(root, delIndex);
			// System.out.println("deleted the node " + delIndex + " " +
			// redBlackTree.balanceRBTree(root));

		}
		redBlackTree.printTheWholeTree(root);
		System.out.println("");
		System.out.println("");
		System.out.println("Pre-order Traversal after deletion of nodes are :");
		redBlackTree.preOrder(root);
		System.out.println("");
		System.out.println("");
		System.out.println("In-order Traversal after deletion of nodes are :");
		redBlackTree.inOrder(root);
		System.out.println("");
		System.out.println("");
		System.out.println("Post-order Traversal after deletion of nodes are :");
		redBlackTree.postOrder(root);

	}
}

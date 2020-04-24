package com.binghamton.selfbalancedtree;

/**
 * @author Prashant Prabhu Kariyannavar
 *
 */
public class AVLTreeUtilityMain {

	public static void main(String[] args) {
		AVLTree avl = new AVLTree();
		int[] arr = { 14, 17, 11, 7, 53, 4, 13, 12, 8, 60, 19, 16, 20 };
		int[] arr1 = { 9, 5, 10, 0, 6, 11, -1, 1, 2 };
		int[] arr2 = { 10, 20, 30, 40, 50, 25 };
		for (int index : arr1) {
			avl.insert(index);
		}

		/* Pre-order Traversal is Root Left Right */
		System.out.println("Traversal using preorder" + " after insertion of all nodes are : ");
		avl.preOrder(avl.getRoot());

		System.out.println(" ");
		System.out.println(" ");

		System.out.println("Traversal using inorder" + " after insertion of all nodes are : ");
		avl.inOrder(avl.getRoot());

		System.out.println(" ");
		System.out.println(" ");

		System.out.println("Traversal using postorder" + " after insertion of all nodes are : ");
		avl.postOrder(avl.getRoot());

		System.out.println(" ");
		System.out.println(" ");

		System.out.println("Deletion process: ");
//		avl.delete(8);
//		avl.delete(7);
//		avl.delete(11);
//		avl.delete(14);
//		avl.delete(17);
		// avl.delete(10);
		System.out.println("");
		System.out.println(" ");
		System.out.println("Traversal using preorder" + " of constructed AVL tree after deletion of data are : ");
		avl.preOrder(avl.getRoot());

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Traversal using inorder" + " of constructed AVL tree after deletion of data are : ");
		avl.inOrder(avl.getRoot());

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Traversal using postorder" + " of constructed AVL tree after deletion of data are : ");
		avl.postOrder(avl.getRoot());

	}
}

package com.binghamton.selfbalancedtree;

/**
 * @author Prashant Prabhu Kariyannavar
 *
 */
public class AVLTree {

	private BinarySearchTreeNode root;
	private boolean insertBool;
	private boolean deleteBool;

	/* Constructor */
	public AVLTree() {

	}

	/**
	 * 
	 * @param data
	 */
	public void insert(int data) {

		root = insertIntoAVLTree(root, data);
	}

	/**
	 * 
	 * @param data
	 */
	public void delete(int data) {

		root = deletionOfNode(root, data);
	}

	/**
	 * 
	 * @param node
	 * @param data
	 * @return
	 */
	public BinarySearchTreeNode insertIntoAVLTree(BinarySearchTreeNode node, int data) {
		// TODO Auto-generated method stub
		insertBool = true;
		if (null == node)
			return new BinarySearchTreeNode(data);

		if (data < node.getData()) {
			node.setLeft(insertIntoAVLTree(node.getLeft(), data));

		} else if (data > node.getData()) {
			node.setRight(insertIntoAVLTree(node.getRight(), data));
		} else
			return node;

		return balanceAVLTree(node, data);
	}

	/***
	 * 
	 * @param node
	 * @param data
	 * @return
	 */
	private BinarySearchTreeNode deletionOfNode(BinarySearchTreeNode node, int data) {
		deleteBool = true;
		if (null == node) {
			return node;
		}
		if (data < node.getData())
			node.setLeft(deletionOfNode(node.getLeft(), data));
		else if (data > node.getData())
			node.setRight(deletionOfNode(node.getRight(), data));

		else {

			if ((null == node.getRight()) || null == node.getLeft()) {
				BinarySearchTreeNode temp = null;
				if (temp == node.getLeft())
					temp = node.getRight();
				else
					temp = node.getLeft();

				if (null == temp) {
					temp = node;
					node = null;
				} else
					node = temp;
			} else {
				BinarySearchTreeNode temp = minimumValueOfNodeInorderSuccessor(node.getRight());
				node.setData(temp.getData());
				node.setRight(deletionOfNode(node.getRight(), temp.getData()));
			}
		}
		if (null == node)
			return node;

		return balanceAVLTree(node, data);
	}

	/**
	 * 
	 * @param node
	 * @param data
	 * @return
	 */
	private BinarySearchTreeNode balanceAVLTree(BinarySearchTreeNode node, int data) {

		if (insertBool) {
			heightUpdate(node);

			int balanceFactor = checkBalanceFactor(node);

			if ((balanceFactor) > 1 && (data < node.getLeft().getData()))
				return rightRotate(node);

			if (balanceFactor < -1 && data > node.getRight().getData())
				return leftRotate(node);

			if (balanceFactor > 1 && data > node.getLeft().getData()) {
				node.setLeft(leftRotate(node.getLeft()));
				return rightRotate(node);
			}

			if (balanceFactor < -1 && data < node.getRight().getData()) {
				node.setRight(rightRotate(node.getRight()));
				return leftRotate(node);
			}
			insertBool = false;
			return node;
		} else if (deleteBool) {

			heightUpdate(node);

			int balanceFactor = checkBalanceFactor(node);

			if ((balanceFactor) > 1 && (checkBalanceFactor(node.getLeft()) >= 0))
				return rightRotate(node);

			if (balanceFactor < -1 && (checkBalanceFactor(node.getRight()) <= 0))
				return leftRotate(node);

			if (balanceFactor > 1 && (checkBalanceFactor(node.getLeft()) < 0)) {
				node.setLeft(leftRotate(node.getLeft()));
				return rightRotate(node);
			}

			if (balanceFactor < -1 && (checkBalanceFactor(node.getRight()) > 0)) {
				node.setRight(rightRotate(node.getRight()));
				return leftRotate(node);
			}
		}
		deleteBool = false;
		return node;

	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private BinarySearchTreeNode minimumValueOfNodeInorderSuccessor(BinarySearchTreeNode node) {
		BinarySearchTreeNode curr = node;
		while (curr.getLeft() != null) {
			curr = curr.getLeft();

		}
		return curr;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private BinarySearchTreeNode leftRotate(BinarySearchTreeNode node) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode yNode = node.getRight();
		BinarySearchTreeNode TNode = yNode.getLeft();

		yNode.setLeft(node);
		node.setRight(TNode);

		heightUpdate(node);
		heightUpdate(yNode);

		return yNode;

	}

	/***
	 * 
	 * @param y
	 * @return
	 */
	private BinarySearchTreeNode rightRotate(BinarySearchTreeNode node) {
		// TODO Auto-generated method stub

		BinarySearchTreeNode xNode = node.getLeft();
		BinarySearchTreeNode TNode = xNode.getRight();

		xNode.setRight(node);
		node.setLeft(TNode);

		heightUpdate(node);
		heightUpdate(xNode);

		return xNode;

	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private int checkBalanceFactor(BinarySearchTreeNode node) {
		// TODO Auto-generated method stub

		if (null == node)
			return 0;

		return (height(node.getLeft()) - height(node.getRight()));
	}

	/***
	 * 
	 * @param node
	 * @return
	 */
	public int height(BinarySearchTreeNode node) {
		// TODO Auto-generated method stub

		if (null == node) {

			return 0;
		}
		return node.getHeight();
	}

	/**
	 * 
	 * @param node
	 */
	public void heightUpdate(BinarySearchTreeNode node) {
		node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
	}

	/***
	 * 
	 * @return
	 */
	public BinarySearchTreeNode getRoot() {
		return root;
	}

	/**
	 * 
	 * @param root
	 */
	public void setRoot(BinarySearchTreeNode root) {
		this.root = root;
	}

	/**
	 * 
	 * @param node
	 */
	void inOrder(BinarySearchTreeNode node) {
		if (node != null) {
			inOrder(node.getLeft());
			System.out.print(node.getData() + " ");
			inOrder(node.getRight());

		}
	}

	/**
	 * 
	 * @param node
	 */
	void postOrder(BinarySearchTreeNode node) {
		if (node != null) {
			postOrder(node.getLeft());
			postOrder(node.getRight());
			System.out.print(node.getData() + " ");
		}
	}

	/***
	 * 
	 * @param node
	 */
	void preOrder(BinarySearchTreeNode node) {
		if (node != null) {
			System.out.print(node.getData() + " ");
			preOrder(node.getLeft());
			preOrder(node.getRight());
		}
	}

}

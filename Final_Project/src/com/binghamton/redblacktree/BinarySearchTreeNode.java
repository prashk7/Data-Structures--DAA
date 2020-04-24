package com.binghamton.redblacktree;

/**
 * @author Prashant Prabhu Kariyannavar
 *
 */

public class BinarySearchTreeNode {

	
	public enum NodeColour {
		BLACK, RED
	}

	private int data;
	NodeColour colour;
	private BinarySearchTreeNode right;
	private BinarySearchTreeNode left;
	private BinarySearchTreeNode parent;
	private boolean isLeafNull;

	/**
	 * 
	 * @return
	 */
	public int getData() {
		return data;
	}
	

	public void setData(int data) {
		this.data = data;
	}

	public BinarySearchTreeNode getRight() {
		return right;
	}

	public void setRight(BinarySearchTreeNode right) {
		this.right = right;
	}

	public BinarySearchTreeNode getLeft() {
		return left;
	}

	public void setLeft(BinarySearchTreeNode left) {
		this.left = left;
	}

	public BinarySearchTreeNode getParent() {
		return parent;
	}

	public void setParent(BinarySearchTreeNode parent) {
		this.parent = parent;
	}

	public boolean isLeafNull() {
		return isLeafNull;
	}

	public void setLeafNull(boolean isLeafNull) {
		this.isLeafNull = isLeafNull;
	}

	public static BinarySearchTreeNode blackNodeCreation(int data) {
		BinarySearchTreeNode node = new BinarySearchTreeNode();
		node.data = data;
		node.colour = NodeColour.BLACK;
		node.left = nillLeafNodeCreation(node);
		node.right = nillLeafNodeCreation(node);
		return node;
	}

	public static BinarySearchTreeNode nillLeafNodeCreation(BinarySearchTreeNode parent) {
		BinarySearchTreeNode leaf = new BinarySearchTreeNode();
		leaf.colour = NodeColour.BLACK;
		leaf.isLeafNull = true;
		leaf.parent = parent;
		return leaf;
	}

	public static BinarySearchTreeNode redNodeCreation(BinarySearchTreeNode parent, int data) {
		BinarySearchTreeNode node = new BinarySearchTreeNode();
		node.data = data;
		node.colour = NodeColour.RED;
		node.parent = parent;
		node.left = nillLeafNodeCreation(node);
		node.right = nillLeafNodeCreation(node);
		return node;
	}

}

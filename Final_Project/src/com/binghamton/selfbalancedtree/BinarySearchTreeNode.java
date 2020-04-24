package com.binghamton.selfbalancedtree;

/**
 * @author Prashant Prabhu Kariyannavar
 *
 */
public class BinarySearchTreeNode {

	private int data;
	private int height;
	private BinarySearchTreeNode left = null;
	private BinarySearchTreeNode right = null;

	public BinarySearchTreeNode() {

	}

	/* Declaring the parameterized Constructor */
	public BinarySearchTreeNode(int data) {
		super();
		this.data = data;
		this.height = 0;
		this.left = null;
		this.right = null;
	}
	/**
	 * 
	 * @return data
	 */
	public int getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(int data) {
		this.data = data;
	}
	
	/**
	 * 
	 * @return height
	 */

	public int getHeight() {
		return height;
	}
	
	/**
	 * 
	 * @param height
	 */

	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * 
	 * @return left node
	 */

	public BinarySearchTreeNode getLeft() {
		return left;
	}

	/**
	 * 
	 * @param left
	 */
	public void setLeft(BinarySearchTreeNode left) {
		this.left = left;
	}
	
	/**
	 * 
	 * @return right node
	 */
	
	public BinarySearchTreeNode getRight() {
		return right;
	}
	/**
	 * 
	 * @param right
	 */
	public void setRight(BinarySearchTreeNode right) {
		this.right = right;
	}

}

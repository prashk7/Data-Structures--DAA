package com.binghamton.redblacktree;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import com.binghamton.redblacktree.BinarySearchTreeNode.NodeColour;

/**
 * @author Prashant Prabhu Kariyannavar
 * 
 *         Insertion Steps:
 *         
 *         Step 1 :- If the tree is empty create new node as
 *         root node with colour black. Step 2:- If tree is not empty create new
 *         node or leaf node with colour red. Step 3:- If parent of new node is
 *         black then exit. Step 4:- If parent of new node is Red then check the
 *         colour of parent's sibling of new node. a) If colour is black or null
 *         then do suitable rotation and recolour! b) If colour is red then
 *         recolour and also check if parent's parent of new node is not root
 *         node then recolor it and recheck!
 *
 *
 *         Deletion Steps:
 *
 *         Step 1:- Perform BST deletion Step 2:-
 * 
 *         case 1: If node to be deleted is red, then just delete it
 * 
 *         case 2: If root is double black, just remove double black node.
 * 
 *         case 3:- If double black node sibling is black and both of its
 *         children are black then do the below: -> remove double black -> add
 *         double black to its parent -> If parent is red it becomes black -> If
 *         parent is black it becomes double black -> Make sibling red -> If
 *         still double black node exists, apply other cases by going to case1
 * 
 *         case 4:- If Double black node's sibling is red then do the below ->
 *         swap colours of parent and its sibiling -> rotate parent in Double
 *         black node direction -> reapply other cases because double black node
 *         still be double black node needs to be processed further
 * 
 * 
 *         case 5:-If Double black node's sibling is black , sibling's child who
 *         is far from double black node is black but near child to Double black
 *         is red. -> swap colour of Double black's sibling and siblings child
 *         who is near to Double black -> rotate sibling in opposite direction
 *         to double black(right rotation) Apply case 6
 * 
 * 
 *         case 6:- If double black node's sibling is black , far child is red
 *         -> swap colour of parent and sibling -> rotate parent in Double black
 *         node direction (left rotation) -> remove Double black node -> change
 *         colour of red child to black
 */

public class RedBlackTreeImpl {

	/* Declaring a constructor */
	public RedBlackTreeImpl() {

	}

	/**
	 * 
	 * @param root
	 * @param data
	 * @return
	 */
	public BinarySearchTreeNode insert(BinarySearchTreeNode root, int data) {
		// TODO Auto-generated method stub
		return insertIntoRBT(null, root, data);
	}

	/**
	 * 
	 * @param root
	 * @param data
	 * @return
	 */

	public BinarySearchTreeNode delete(BinarySearchTreeNode root, int data) {
		// TODO Auto-generated method stub
		AtomicReference<BinarySearchTreeNode> rootRef = new AtomicReference<>();
		deletionOfNode(root, data, rootRef);
		if (rootRef.get() != null)
			return rootRef.get();
		else
			return root;

	}

	/**
	 * 
	 * @param parent
	 * @param root
	 * @param data
	 * @return
	 */

	private BinarySearchTreeNode insertIntoRBT(BinarySearchTreeNode parent, BinarySearchTreeNode root, int data) {
		// TODO Auto-generated method stub
		boolean traverseLeft;// traverseLeft will be true if we traverse on left side
		// then traverseLeft will be false, if traverse on right side
		if (null == root || root.isLeafNull()) {
			// creation of red leaf node if parent is not null(i.e.tree is not empty)
			if (parent != null) {
				return root.redNodeCreation(parent, data);
			} else {
				// else if tree is empty create a black root node
				return root.blackNodeCreation(data);
			}
		}

		if (data == root.getData()) {
			// duplicate insertion is not allowed so i am throwing run time exception
			throw new IllegalArgumentException("Don't pass duplicate data " + data);
		}
		// insert into left node if the data is less than root data
		if (data < root.getData()) {
			BinarySearchTreeNode leftPath;
			leftPath = (insertIntoRBT(root, root.getLeft(), data));

			// Here I am checking if Left node becomes parent(root) of all nodes then
			// definitely rotation has happened at downward level, return leftPath so upper
			// level nodes can see their child correctly
			if (leftPath == root.getParent()) {
				return leftPath;
			}
			// set the left child returned to be left of root node
			root.setLeft(leftPath);
			// set traverseLeft to be true
			traverseLeft = true;
		} else {
			BinarySearchTreeNode rightPath;
			rightPath = insertIntoRBT(root, root.getRight(), data);

			// Here I am checking if right node becomes parent(root) of all nodes then
			// definitely rotation has happened at downward level, return rightPath so upper
			// level nodes can see their child correctly
			if (rightPath == root.getParent())
				return rightPath;

			// set the right child returned to be right of root node
			root.setRight(rightPath);
			// set isRight to be true
			traverseLeft = false;
		}

		if (traverseLeft) {
			// Here I am checking red-red conflict between root and its left child
			if (root.getLeft().colour == NodeColour.RED && root.colour == NodeColour.RED) {
				// Here I am getting the root's sibiling
				Optional<BinarySearchTreeNode> getSibling = searchSiblingNode(root);
				// Then I will check if sibling's colour is black\null then we do suitable
				// rotation and recolour
				if (!getSibling.isPresent() || getSibling.get().colour == NodeColour.BLACK) {
					// Here i am checking whether root is left child of its parent
					if (isItBelongsToLeftChild(root)) {
						// then do one right rotation, bcoz it creates left-left traversal and suitable
						// colour change will happen
						rotateRight(root, true);
					} else {
						// else it will create left-right traversal so first do right rotation and then
						// left rotate once
						// right rotation will be done without changing color, initializing
						// colourChange=false.
						// root has become right child of its leftchild after right rotation is done
						rotateRight(root.getLeft(), false);
						// root should be root's parent after rotation is done
						root = root.getParent();
						// left rotation is done by initializing colourChange=true.
						rotateLeft(root, true);
					}

				} else {
					// here in this we will get to know its sibling is red, re-colour root and its
					// sibling to black.
					// then change colour of their parent to red if its not a root.
					root.colour = NodeColour.BLACK;
					getSibling.get().colour = NodeColour.BLACK;
					// change the colour to red if parent's parent != null (i.e parent is not root)
					if (root.getParent().getParent() != null) {
						root.getParent().colour = NodeColour.RED;
					}
				}
			}
		} else {
			// same comments are applicable bcoz its mirror case
			if (root.getRight().colour == NodeColour.RED && root.colour == NodeColour.RED) {
				Optional<BinarySearchTreeNode> getSibling = searchSiblingNode(root);
				if (!getSibling.isPresent() || getSibling.get().colour == NodeColour.BLACK) {
					if (!isItBelongsToLeftChild(root)) {
						rotateLeft(root, true);
					} else {
						rotateLeft(root.getRight(), false);
						root = root.getParent();
						rotateRight(root, true);
					}
				} else {
					root.colour = NodeColour.BLACK;
					getSibling.get().colour = NodeColour.BLACK;
					if (root.getParent().getParent() != null) {
						root.getParent().colour = NodeColour.RED;
					}
				}
			}
		}
		return root;
	}

	/**
	 * 
	 * @param root
	 * @return
	 */

	private Optional<BinarySearchTreeNode> searchSiblingNode(BinarySearchTreeNode root) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode parent = root.getParent();
		if (isItBelongsToLeftChild(root)) {
			return Optional.ofNullable(parent.getRight().isLeafNull() ? null : parent.getRight());
		} else {
			return Optional.ofNullable(parent.getLeft().isLeafNull() ? null : parent.getLeft());
		}
	}

	/**
	 * 
	 * @param root
	 * @return
	 */

	private boolean isItBelongsToLeftChild(BinarySearchTreeNode root) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode parent = root.getParent();
		if (parent.getLeft() == root)
			return true;
		else
			return false;

	}

	/**
	 * 
	 * @param root
	 * @param colourChange
	 */
	private void rotateRight(BinarySearchTreeNode root, boolean colourChange) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode parent = root.getParent();
		root.setParent(parent.getParent());
		if (parent.getParent() != null) {
			if (parent.getParent().getRight() == parent) {
				parent.getParent().setRight(root);
			} else {
				parent.getParent().setLeft(root);
			}
		}
		BinarySearchTreeNode right = root.getRight();
		root.setRight(parent);

		parent.setParent(root);
		parent.setLeft(right);
		if (right != null) {
			right.setParent(parent);
		}
		if (colourChange) {
			root.colour = NodeColour.BLACK;
			parent.colour = NodeColour.RED;
		}
	}

	/**
	 * 
	 * @param root
	 * @param colourChange
	 */
	private void rotateLeft(BinarySearchTreeNode root, boolean colourChange) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode parent = root.getParent();
		root.setParent(parent.getParent());
		;
		if (parent.getParent() != null) {
			if (parent.getParent().getRight() == parent) {
				parent.getParent().setRight(root);
			} else {
				parent.getParent().setLeft(root);
			}
		}
		BinarySearchTreeNode left = root.getLeft();

		root.setLeft(parent);
		parent.setParent(root);
		parent.setRight(left);
		if (left != null) {
			left.setParent(parent);
		}
		if (colourChange) {
			root.colour = NodeColour.BLACK;
			parent.colour = NodeColour.RED;
		}
	}

	/**
	 * 
	 * @param root
	 * @param data
	 * @param rootRef
	 */
	private void deletionOfNode(BinarySearchTreeNode root, int data, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub
		if (null == root || root.isLeafNull()) {
			return;
		}
		if (data == root.getData()) {
			// if node has to be deleted have no children(null) or 1 children then I will
			// use deletionOfOneChild function
			if (root.getRight().isLeafNull() || root.getLeft().isLeafNull()) {
				deletionOfOneChild(root, rootRef);
			} else {
				// else I will replace the node by inorder successor from right subtree and
				// replace root with inorder successor value.
				// then I will delete inorder successor which might be having 0 or 1 children

				BinarySearchTreeNode inorderSuccessorImpl = findSmallestNodeRightSubtree(root.getRight());
				root.setData(inorderSuccessorImpl.getData());
				deletionOfNode(root.getRight(), inorderSuccessorImpl.getData(), rootRef);
			}
		}
		// search for the node to be deleted.
		if (data > root.getData()) {
			deletionOfNode(root.getRight(), data, rootRef);
		} else {
			deletionOfNode(root.getLeft(), data, rootRef);
		}

	}

	/**
	 * 
	 * @param root
	 * @return
	 */

	private BinarySearchTreeNode findSmallestNodeRightSubtree(BinarySearchTreeNode root) {
		// TODO Auto-generated method stub

		BinarySearchTreeNode prev = null;
		while (root != null && !root.isLeafNull()) {
			prev = root;
			root = root.getLeft();
		}
		return prev != null ? prev : root;
	}

	/**
	 * 
	 * @param deleteNode
	 * @param rootRef
	 */

	private void deletionOfOneChild(BinarySearchTreeNode deleteNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode child = deleteNode.getRight().isLeafNull() ? deleteNode.getLeft() : deleteNode.getRight();
		// replace node with either one not null child if it exists or null child.
		replaceNodeWithNullOrNot(deleteNode, child, rootRef);
		// if the node to be deleted is BLACK. See if it has one red child.
		if (deleteNode.colour == NodeColour.BLACK) {
			// if it has one red child then change color of that child to be Black.
			if (child.colour == NodeColour.RED) {
				child.colour = NodeColour.BLACK;
			} else {
				// otherwise we have double black situation.
				case1Deletion(child, rootRef);
			}
		}
	}

	/**
	 * 
	 * If root has become double node then we are done.We just turn it into single
	 * black node it just reduces one black in every path
	 * 
	 * @param doubleBlackNode
	 * @param rootRef
	 */
	private void case1Deletion(BinarySearchTreeNode doubleBlackNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub
		if (null == doubleBlackNode.getParent()) {
			rootRef.set(doubleBlackNode);
			return;
		}
		case2Deletion(doubleBlackNode, rootRef);

	}

	/**
	 * If Double black node's sibling is red then do the below -> swap colours of
	 * parent and its sibiling -> rotate parent in Double black node direction ->
	 * reapply other cases because double black node still be double black node
	 * needs to be processed further
	 * 
	 * @param doubleBlackNode
	 * @param rootRef
	 */
	private void case2Deletion(BinarySearchTreeNode doubleBlackNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub

		BinarySearchTreeNode getSiblingNode = searchSiblingNode(doubleBlackNode).get();
		if (getSiblingNode.colour == NodeColour.RED) {
			if (isItBelongsToLeftChild(getSiblingNode)) {
				rotateRight(getSiblingNode, true);
			} else {
				rotateLeft(getSiblingNode, true);
			}
			if (null == getSiblingNode.getParent()) {
				rootRef.set(getSiblingNode);
			}
		}
		case3Deletion(doubleBlackNode, rootRef);

	}

	/**
	 * If double black node sibling is black and both of its children are black then
	 * do the below: -> remove double black -> add double black to its parent -> If
	 * parent is red it becomes black -> If parent is black it becomes double black
	 * -> Make sibling red -> If still double black node exists, apply other cases
	 * by going to case1
	 * 
	 * @param doubleBlackNode
	 * @param rootRef
	 */
	private void case3Deletion(BinarySearchTreeNode doubleBlackNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode getSiblingNode = searchSiblingNode(doubleBlackNode).get();

		if (doubleBlackNode.getParent().colour == NodeColour.BLACK && getSiblingNode.colour == NodeColour.BLACK
				&& getSiblingNode.getLeft().colour == NodeColour.BLACK
				&& getSiblingNode.getRight().colour == NodeColour.BLACK) {
			getSiblingNode.colour = NodeColour.RED;
			case1Deletion(doubleBlackNode.getParent(), rootRef);
		} else {
			case4Deletion(doubleBlackNode, rootRef);
		}

	}

	/**
	 * If double black node sibling is black and both of its children are black then
	 * do the below: -> remove double black -> add double black to its parent -> If
	 * parent is red it becomes black swap colour between sibling anf parent
	 * 
	 * 
	 * @param doubleBlackNode
	 * @param rootRef
	 */
	private void case4Deletion(BinarySearchTreeNode doubleBlackNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode getSiblingNode = searchSiblingNode(doubleBlackNode).get();
		if (doubleBlackNode.getParent().colour == NodeColour.RED && getSiblingNode.colour == NodeColour.BLACK
				&& getSiblingNode.getLeft().colour == NodeColour.BLACK
				&& getSiblingNode.getRight().colour == NodeColour.BLACK) {
			getSiblingNode.colour = NodeColour.RED;
			doubleBlackNode.getParent().colour = NodeColour.BLACK;
			return;
		} else {
			case5Deletion(doubleBlackNode, rootRef);
		}
	}

	/**
	 */

	/**
	 * 
	 * Here double black = left child of parent If Double black node's sibling is
	 * black , sibling's child who is far from double black node is black but near
	 * child to Double black is red. -> swap colour of Double black's sibling and
	 * siblings child who is near to Double black -> rotate sibling in opposite
	 * direction to double black(right rotation) Apply case 6
	 * 
	 * @param doubleBlackNode
	 * @param rootRef
	 */
	private void case5Deletion(BinarySearchTreeNode doubleBlackNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub

		BinarySearchTreeNode getSiblingNode = searchSiblingNode(doubleBlackNode).get();
		if (getSiblingNode.colour == NodeColour.BLACK) {
			if (isItBelongsToLeftChild(doubleBlackNode) && getSiblingNode.getRight().colour == NodeColour.BLACK
					&& getSiblingNode.getLeft().colour == NodeColour.RED) {
				rotateRight(getSiblingNode.getLeft(), true);
			} else if (!isItBelongsToLeftChild(doubleBlackNode) && getSiblingNode.getLeft().colour == NodeColour.BLACK
					&& getSiblingNode.getRight().colour == NodeColour.RED) {
				rotateLeft(getSiblingNode.getRight(), true);
			}
		}
		case6Deletion(doubleBlackNode, rootRef);
	}

	/**
	 * Here double black = left child of parent If double black node's sibling is
	 * black , far child is red -> swap colour of parent and sibling -> rotate
	 * parent in Double black node direction (left rotation) -> remove Double black
	 * node -> change colour of red child to black
	 * 
	 * @param doubleBlackNode
	 * @param rootRef
	 */
	private void case6Deletion(BinarySearchTreeNode doubleBlackNode, AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub

		BinarySearchTreeNode getSiblingNode = searchSiblingNode(doubleBlackNode).get();
		getSiblingNode.colour = getSiblingNode.getParent().colour;
		getSiblingNode.getParent().colour = NodeColour.BLACK;
		if (isItBelongsToLeftChild(doubleBlackNode)) {
			getSiblingNode.getRight().colour = NodeColour.BLACK;
			rotateLeft(getSiblingNode, false);
		} else {
			getSiblingNode.getLeft().colour = NodeColour.BLACK;
			rotateRight(getSiblingNode, false);
		}
		if (null == getSiblingNode.getParent()) {
			rootRef.set(getSiblingNode);
		}

	}

	/**
	 * 
	 * @param root
	 * @param child
	 * @param rootRef
	 */
	private void replaceNodeWithNullOrNot(BinarySearchTreeNode root, BinarySearchTreeNode child,
			AtomicReference<BinarySearchTreeNode> rootRef) {
		// TODO Auto-generated method stub

		child.setParent(root.getParent());
		if (null == root.getParent()) {
			rootRef.set(child);
		} else {
			if (isItBelongsToLeftChild(root)) {
				root.getParent().setLeft(child);
			} else {
				root.getParent().setRight(child);
			}
		}

	}

	/**
	 * 
	 * @param root
	 * @return
	 */
	public boolean balanceRBTree(BinarySearchTreeNode root) {
		// TODO Auto-generated method stub

		if (null == root) {
			return true;
		}
		// check if root is black
		if (root.colour != NodeColour.BLACK) {
			return false;
		}
		AtomicInteger countOfBlackNodes = new AtomicInteger(0);
		// making sure count of black nodes is same on all path and there is no red red
		// conflict relationship
		return blackNodesCounting(root, countOfBlackNodes, 0) && noRedRedConflict(root, NodeColour.BLACK);
	}

	/**
	 * 
	 * @param root
	 * @param parentColour
	 * @return
	 */
	private boolean noRedRedConflict(BinarySearchTreeNode root, NodeColour parentColour) {
		// TODO Auto-generated method stub
		if (null == root) {
			return true;
		}
		if (root.colour == NodeColour.RED && parentColour == NodeColour.RED) {
			return false;
		}
		return noRedRedConflict(root.getLeft(), root.colour) && noRedRedConflict(root.getRight(), root.colour);
	}

	/**
	 * 
	 * @param root
	 * @param blackCount
	 * @param count
	 * @return
	 */
	private boolean blackNodesCounting(BinarySearchTreeNode root, AtomicInteger blackCount, int count) {
		// TODO Auto-generated method stub

		if (root.colour == NodeColour.BLACK) {
			count++;
		}
		if (root.getLeft() == null && root.getRight() == null) {
			if (blackCount.get() == 0) {
				blackCount.set(count);
				return true;
			} else {
				return count == blackCount.get();
			}
		}
		return blackNodesCounting(root.getLeft(), blackCount, count)
				&& blackNodesCounting(root.getRight(), blackCount, count);
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

		} else
			return;
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
		} else
			return;
	}

	/**
	 * 
	 * @param node
	 */
	void preOrder(BinarySearchTreeNode node) {
		if (node != null) {
			System.out.print(node.getData() + " ");
			preOrder(node.getLeft());
			preOrder(node.getRight());
		} else
			return;
	}

	/**
	 * 
	 * @param root
	 */
	public void printTheWholeTree(BinarySearchTreeNode root) {
		// TODO Auto-generated method stub
		printingTheWholeTree(root, 0);
	}

	/**
	 * 
	 * @param root
	 * @param pos
	 */
	private void printingTheWholeTree(BinarySearchTreeNode root, int pos) {
		// TODO Auto-generated method stub
		if (root == null || root.isLeafNull()) {
			return;
		}
		printingTheWholeTree(root.getRight(), pos + 5);
		for (int i = 0; i < pos; i++) {
			System.out.print(" ");
		}
		System.out.println(root.getData() + " " + (root.colour == NodeColour.BLACK ? "B" : "R"));
		printingTheWholeTree(root.getLeft(), pos + 5);
	}

}

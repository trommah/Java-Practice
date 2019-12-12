package codingChallenges;
/*
 * Given a binary tree, each node has a null "next node" , which points to the next node in the same level (left to right).
	Update the given tree so that each node has a value in their "next" field.
 */
import java.util.LinkedList;

import classes.BinaryTree;
import classes.TreeNode;

//
public class nextNode {
	
	public void nextTreeNode(BinaryTree tree)
	{
		TreeNode root = tree.getRoot();
		LinkedList<TreeNode> TreeNodeQueue = new LinkedList<TreeNode>();
		TreeNodeQueue.add(root);
		levelOrderTraversal(TreeNodeQueue);
	}
	public void levelOrderTraversal(LinkedList<TreeNode> TreeNodeQueue)
	{
		int processedTreeNodes = 0;
		TreeNode parent;
		while(!TreeNodeQueue.isEmpty())
		{
			parent = TreeNodeQueue.pop();
			if(parent.getLeft() != null)
				TreeNodeQueue.add(parent.getLeft());
			if(parent.getRight()!= null)
				TreeNodeQueue.add(parent.getRight());
			processedTreeNodes++;
			if((Math.log(processedTreeNodes + 1)/Math.log(2)) % 1 != 0)
				parent.setNext(TreeNodeQueue.getFirst());
		}
	}
}

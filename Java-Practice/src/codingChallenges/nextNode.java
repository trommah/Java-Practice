package codingChallenges;
/*
 * Write a program where, given a binary tree with nodes that have an extra 'next' Node variable, set next to be 
 * the Node in the tree to the right at the same height. 
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

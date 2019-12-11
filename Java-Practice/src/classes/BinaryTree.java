package classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class BinaryTree {
	TreeNode root;
	int height, size;
	public BinaryTree()
	{
		root = null;
		height = -1;
		size = 0;
	}
	public void setParent(TreeNode parent)
	{
		this.root = parent;
		height = 0;
		size++;
	}

	public TreeNode getRoot()
	{
		return root;
	}
	public void buildTree (ArrayList<TreeNode> nodeArray)
	{
		
		ListIterator<TreeNode> it = nodeArray.listIterator();
		LinkedList<TreeNode> nodeQueue = new LinkedList<TreeNode>();
		size = nodeArray.size();
		height = (int) Math.ceil(Math.log10(size)/Math.log10(2));
		root = nodeArray.get(0);
		nodeQueue.add(it.next());
		while(it.hasNext())
		{
			nodeAdd(it, nodeQueue);
		}
	}
	
	private void nodeAdd(ListIterator<TreeNode> it, LinkedList<TreeNode> nodeQueue)
	{
		TreeNode node = nodeQueue.pop();
		if(it.hasNext())
		{
			TreeNode left = it.next();
			node.setLeft(left);
			node.getLeft().setParent(node);
			nodeQueue.add(left);
			if(it.hasNext())
			{
				TreeNode right = it.next();
				node.setRight(right);
				node.getRight().setParent(node);
				nodeQueue.add(right);
			}
		}
	}
}
	
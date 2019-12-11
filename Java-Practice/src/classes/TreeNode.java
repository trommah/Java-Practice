package classes;

public class TreeNode {
	private int data;
	private TreeNode parent, left, right, next;
	public TreeNode (int data){
		this.next = null;
		this.data = data;
	}
	public TreeNode getLeft()
	{
		return left;
	}
	public void setLeft(TreeNode left)
	{
		this.left = left;
	}
	public TreeNode getRight()
	{
		return right;
	}
	public void setRight(TreeNode right)
	{
		this.right = right;
	}
	public TreeNode getParent()
	{
		return parent;
	}
	public void setParent(TreeNode parent)
	{
		this.parent = parent;
	}
	public void setNext(TreeNode next)
	{
		this.next = next;
	}
	public TreeNode getNext()
	{
		return next;
	}
	public int getData()
	{
		return data;
	}
}

package meeting;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Node class for representing nodes in the graph.
 * 
 * @author Kyle Trom
 *
 */
public class Node {

	private String address; // The address name
	private HashSet<Node> outgoingEdges, incomingEdges; // Outgoing/Incoming edges of the node (Peggy takes outgoing
														// paths, Sam takes incoming paths)
	private boolean peggyVisited, samVisited; // The markers for whether a fish has been to this node

	public Node(String data) {
		this.address = data;
		outgoingEdges = new HashSet<Node>();
		incomingEdges = new HashSet<Node>();
		peggyVisited = false;
		samVisited = false;
	}

	public String getName() {
		return address;
	}

	/**
	 * Used for constructing the graph by adding outgoing nodes
	 * 
	 * @param node The node that the outgoing edge goes to
	 */
	public void addOutgoing(Node node) {
		outgoingEdges.add(node);
	}

	/**
	 * Used for constructing the graph by adding incoming nodes
	 * 
	 * @param node The node that the incoming edge comes from
	 */
	public void addIncoming(Node node) {
		incomingEdges.add(node);
	}

	/**
	 * Marks the current node as visited by the given fish
	 * 
	 * @param who The fish that is visiting this node
	 */
	public void visit(String who) {
		if (who.equals("Peggy"))
			peggyVisited = true;
		if (who.equals("Sam"))
			samVisited = true;
	}

	/**
	 * Used for testing if the node has been visted by both fish
	 * 
	 * @return true if both fish have visited this node, false otherwise
	 */
	public boolean bothVisited() {
		return (peggyVisited && samVisited);
	}

	/**
	 * @param who Which fish we are testing for visitation
	 * @return true if the given fish has visited the node
	 */
	public boolean isVisitedBy(String who) {
		if (who.equals("Peggy"))
			return peggyVisited;
		else
			return samVisited;
	}

	/**
	 * @param who The fish we want the iterator for
	 * @return Iterator of edges that the given fish can travel along
	 */
	public Iterator<Node> Edges(String who) {
		if (who.equals("Peggy"))
			return outgoingEdges.iterator();
		else
			return incomingEdges.iterator();
	}

}

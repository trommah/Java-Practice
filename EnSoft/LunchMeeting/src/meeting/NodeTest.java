package meeting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This Junit tests the basic functions of the Node class.
 * 
 * @author Kyle Trom
 *
 */
class NodeTest {
	Node node1;
	Node node2;

	@BeforeEach
	void setupNode1() {
		node1 = new Node("Node1");
		node2 = new Node("Node2");
	}

	@Test
	void testGetName() {
		assertTrue(node1.getName().equals("Node1"));
		assertFalse(node1.getName().equals("Node5"));
	}

	@Test
	void testIncomingOutgoingAndReturn() {
		node1.addOutgoing(node2);
		node2.addIncoming(node1);
		assertTrue(node1.Edges("Peggy").next() == node2);
		assertTrue(node2.Edges("Sam").next() == node1);
		assertFalse(node1.Edges("Sam").hasNext());
		assertFalse(node2.Edges("Peggy").hasNext());

	}

	@Test
	void testVisitAndIsVisitedBy() {
		node1.visit("Peggy");
		assertTrue(node1.isVisitedBy("Peggy"));
		node1.visit("Sam");
		assertTrue(node1.isVisitedBy("Sam"));
	}

	@Test
	void bothVisited() {
		node1.visit("Peggy");
		node2.visit("Sam");
		assertFalse(node1.bothVisited());
		node2.visit("Peggy");
		assertTrue(node2.bothVisited());
	}

}

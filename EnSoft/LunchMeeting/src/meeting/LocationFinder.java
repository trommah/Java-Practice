package meeting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The fishes Peggy and Sam are trying to find a location where they can meet
 * for lunch. The local Post Office provides a map of the watershed they live
 * in, which lists each segment of river as a pair of addresses. Peggy can also
 * only go downstream and Sam can only go upstream. Possible starting locations
 * for Peggy and Sam are given. Peggy and Sam also want to avoid certain
 * addresses which are listed in the input file.
 * 
 * Given the above information, this program outputs a list of possible meeting
 * locations for Peggy and Sam.
 * 
 * This program can be viewed to a layman as each fish swimming to every
 * possible address they can, marking every address they reach, and designating
 * an address as a meeting location if they both reached it.
 * 
 * @author Kyle Trom
 *
 */
public class LocationFinder {

	public HashMap<String, Node> Nodes = new HashMap<String, Node>(); // A Hashmap that can be used for finding a node
																		// using the node's name
	public ArrayList<String> samStarts = new ArrayList<String>(); // A List of all the addresses Sam can start from
	public ArrayList<String> peggyStarts = new ArrayList<String>(); // A list of all the addresses Peggy can start from
	public ArrayList<String> addresses = new ArrayList<String>(); // A list for all valid meeting addresses
	public HashSet<String> avoid = new HashSet<String>();

	/**
	 * This method simply scans the file and calls the line processing method on
	 * each line.
	 * 
	 * @param fileName The input file's directory
	 */
	public void processInputStreamIntoMap() // Make a map from the given file
	{
		Scanner reader;
		String currentData = "";
		reader = new Scanner(System.in);
		while (!currentData.equals("EOF")) {
			currentData = processLineIntoMap(reader.nextLine(), currentData);
		}
		reader.close();
	}

	/**
	 * This method takes a line from the input file and stores it accordingly. If we
	 * are in the Map: section of the file, nodes are created for our graph.
	 * Otherwise, the nodes are recorded as avoid nodes, start addresses for Peggy,
	 * or start addresses for Sam.
	 * 
	 * @param line        The line we are currently on in the file
	 * @param currentData Record if we are in the Map, Avoid, Peggy, or Sam section
	 *                    of the input file
	 * @return Returns which segment of the file we are in after this line.
	 */
	public String processLineIntoMap(String line, String currentData) // Takes a line in the text file and
																		// updates our recorded data
	{
		Scanner lineScanner = new Scanner(line);
		String firstString = "";
		if (!line.isEmpty())
			firstString = lineScanner.next();
		if (firstString.equals("Map:")) // These if statements test if we have entered
		{ // a new segment of the input file
			lineScanner.close();
			return "Map:";
		} else if (firstString.equals("Avoid:")) {
			lineScanner.close();
			return "Avoid:";
		} else if (firstString.equals("Peggy:")) {
			lineScanner.close();
			return "Peggy:";
		} else if (firstString.equals("Sam:")) {
			lineScanner.close();
			return "Sam:";
		} else {
			lineScanner.close();
			lineScanner = new Scanner(line);

			if (currentData.equals("Map:")) { // Creates address nodes if necessary and stores the edge between them
				String fromAddress = lineScanner.next();
				String toAddress = lineScanner.next();
				if (!Nodes.containsKey(fromAddress))
					Nodes.put(fromAddress, new Node(fromAddress));
				if (!Nodes.containsKey(toAddress)) //
					Nodes.put(toAddress, new Node(toAddress)); //
				Nodes.get(fromAddress).addOutgoing(Nodes.get(toAddress));
				Nodes.get(toAddress).addIncoming(Nodes.get(fromAddress));
			} else if (currentData.equals("Avoid:")) { // Records the nodes to avoid in a list
				String avoidAddress;
				while (lineScanner.hasNext()) {
					avoidAddress = lineScanner.next();
					if (!avoidAddress.isEmpty())
						avoid.add(avoidAddress);
				}
			} else if (currentData.equals("Peggy:")) { // Adds all of Peggy's starting addresses to a list
				String peggyAddress;
				while (lineScanner.hasNext()) {
					peggyAddress = lineScanner.next();
					peggyStarts.add(peggyAddress);
				}
			} else if (currentData.equals("Sam:")) { // Adds all of Sam's starting addresses to a list
				String samAddress;
				while (lineScanner.hasNext()) {
					samAddress = lineScanner.next();
					samStarts.add(samAddress);
				}
				currentData = "EOF";
			}

		}

		lineScanner.close();
		return currentData;
	}

	/**
	 * This method will visit the given node by the given fish, add the node to the
	 * valid meeting addresses if each fish has visited the node, and call itself on
	 * its incoming edges or outgoing edges depending on if we are checking Sam's or
	 * Peggy's reachable addresses.
	 * 
	 * @param curNode The Node that is being visited
	 * @param who     The fish that is visiting the node
	 */
	public void DFSVisitReachableNodes(Node curNode, String who) {
		if (!avoid.contains(curNode.getName())) {
			if (!curNode.isVisitedBy(who)) {
				curNode.visit(who);
				if (who.equals("Sam")) {
					if (curNode.bothVisited()) // If both fish have visited this node, add it to the list of meeting
												// addresses
						addNodeToList(curNode);
				}
				Iterator<Node> edgeIterator = curNode.Edges(who);
				while (edgeIterator.hasNext()) {
					DFSVisitReachableNodes(edgeIterator.next(), who); // Recursive call to appropriate edges
				}
			}
		}
	}

	/**
	 * This method runs the Depth First Search for each fish for each starting
	 * address.
	 */
	public void findAddresses() {
		Iterator<String> peggyStartsIterator = peggyStarts.iterator();
		Iterator<String> samStartsIterator = samStarts.iterator();
		while (peggyStartsIterator.hasNext())
			DFSVisitReachableNodes(Nodes.get(peggyStartsIterator.next()), "Peggy");
		while (samStartsIterator.hasNext())
			DFSVisitReachableNodes(Nodes.get(samStartsIterator.next()), "Sam");
	}

	/**
	 * This method prints all the addresses the fish can meet
	 */
	private void printMeetingAddresses() {
		Iterator<String> addressIterator = addresses.iterator();
		while (addressIterator.hasNext())
			System.out.println(addressIterator.next());
	}

	/**
	 * This method will insert an address' name into its lexicographical location in
	 * the list of meeting addresses
	 * 
	 * @param node The node to insert into the meeting address list, "addresses"
	 */
	private void addNodeToList(Node node) {
		addNodeToMeetingAddresses(0, addresses.size() - 1, node.getName());
	}

	/**
	 * 
	 * @param start      The left side of the subarray we wish to binary search
	 * @param end        The right side of the subarray we wish to binary search
	 * @param newaddress The string to be inserted into "addresses"
	 */
	public void addNodeToMeetingAddresses(int start, int end, String newAddress) {
		if (addresses.size() > 0) {
			if (!(addresses.size() == 1)) {
				int middle = (start + end) / 2;
				if (newAddress.toLowerCase().compareTo(addresses.get(0).toLowerCase()) < 0)
					addresses.add(0, newAddress);
				if (newAddress.toLowerCase().compareTo(addresses.get(addresses.size() - 1).toLowerCase()) > 0)
					addresses.add(newAddress);
				else if (newAddress.toLowerCase().compareTo(addresses.get(middle).toLowerCase()) < 0
						&& newAddress.toLowerCase().compareTo(addresses.get(middle - 1).toLowerCase()) > 0)
					addresses.add(middle, newAddress);
				else if (newAddress.toLowerCase().compareTo(addresses.get(middle).toLowerCase()) < 0) {
					addNodeToMeetingAddresses(start, middle - 1, newAddress); // Recursive call on half the list
				} else if (newAddress.toLowerCase().compareTo(addresses.get(middle).toLowerCase()) > 0) {
					addNodeToMeetingAddresses(middle + 1, end, newAddress);
				}
			} else {
				if (newAddress.toLowerCase().compareTo(addresses.get(0).toLowerCase()) < 0) // Handling if ArrayList had
																							// 1 element
					addresses.add(0, newAddress);
				else
					addresses.add(newAddress);
			}
		} else // Handling if ArrayList was empty
			addresses.add(newAddress);
	}

	public static void main(String[] args) {
		LocationFinder lf = new LocationFinder();
		lf.processInputStreamIntoMap(); // Create Map
		lf.findAddresses(); // DFS the Map
		lf.printMeetingAddresses(); // Print meeting locations
	}
}

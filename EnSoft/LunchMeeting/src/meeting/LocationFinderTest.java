package meeting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This JUnit tests the program and runs multiple test cases to ensure the code
 * works when encountering certain obstacles.
 * 
 * @author Kyle Trom
 *
 */
class LocationFinderTest {

	LocationFinder locationFinder;

	String[][] inputs =
	      { { "src/Simple Node Case" },                         { "src/Simple Node Case With Bad Nodes" },
			{ "src/Simple Expanding Tree" },                    { "src/Simple Graph With Cycles" },
			{ "src/Graph With Good and Bad Paths With Loops" }, { "src/Simple Graph With a Valid Loop" },
			{ "src/Simple Invalid Loop" },                      { "src/Complex Graph" }, 
			{ "src/Avoid Blocks Path" },                        { "src/Simple Graph with an Avoid Node" }, 
			{ "src/100FullyConnectedNodes"},	                { "src/1000FullyConnectedNodes"},
			{ "src/Avoid Node Is Starting Node"}, 				{ "src/LastTest"}};

	@BeforeEach
	void CreateNewInstance() {
		locationFinder = new LocationFinder();
	}

	/**
	 * Tests a very simple map 
	 */
	@Test
	void TestMakeMap1() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[1][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		// Test if outgoing edges set up correctly
		assertTrue(locationFinder.Nodes.get("a").Edges("Peggy").next() == locationFinder.Nodes.get("b")
				|| locationFinder.Nodes.get("a").Edges("Peggy").next() == locationFinder.Nodes.get("d")
				|| locationFinder.Nodes.get("a").Edges("Peggy").next() == locationFinder.Nodes.get("f"));
		// Test if incoming edges set up correctly
		assertTrue(locationFinder.Nodes.get("b").Edges("Sam").next() == locationFinder.Nodes.get("a"));
		// Test if Sam's start location is set up correctly
		assertTrue(locationFinder.samStarts.get(0).equals("b"));
		// Test if Peggy's starting addresses are set up correctly
		assertTrue(locationFinder.peggyStarts.get(0).equals("a") && locationFinder.peggyStarts.get(1).equals("d"));
	}
	/**
	 * Tests if meeting locations are correct (there should be none) when the avoid node is the only starting node 
	 */
	@Test
	void TestAvoidNodeWhenStartingNode() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[12][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertFalse(locationFinder.addresses.contains("a"));
		assertFalse(locationFinder.addresses.contains("b"));
		assertFalse(locationFinder.addresses.contains("c"));
	}

	@Test
	void TestDFS() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[2][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.DFSVisitReachableNodes(locationFinder.Nodes.get(locationFinder.peggyStarts.get(0)), "Peggy");
		Iterator<String> keyIterator = locationFinder.Nodes.keySet().iterator();
		while (keyIterator.hasNext()) {
			assertTrue(locationFinder.Nodes.get(keyIterator.next()).isVisitedBy("Peggy"));
		}
	}

	/**
	 * This Test ensures the program handles cycles of valid meeting addresses
	 */
	@Test
	void TestDFSWithGoodCycles() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[3][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.DFSVisitReachableNodes(locationFinder.Nodes.get(locationFinder.peggyStarts.get(0)), "Peggy");
		Iterator<String> keyIterator = locationFinder.Nodes.keySet().iterator();
		while (keyIterator.hasNext()) {
			assertTrue(locationFinder.Nodes.get(keyIterator.next()).isVisitedBy("Peggy"));
		}
	}

	/**
	 * This Test ensures the program handles cycles of invalid meeting addresses
	 */
	@Test
	void TestDFSWithBadCycles() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[4][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.DFSVisitReachableNodes(locationFinder.Nodes.get(locationFinder.peggyStarts.get(0)), "Peggy");
		Iterator<String> keyIterator = locationFinder.Nodes.keySet().iterator();
		while (keyIterator.hasNext()) {
			assertTrue(locationFinder.Nodes.get(keyIterator.next()).isVisitedBy("Peggy"));
		}
	}

	/**
	 * 2 node case
	 */
	@Test
	void TestFindAndPrintMeetingAddresses1() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[0][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("b"));
	}

	/**
	 * 2 node case with extra nodes unreachable by Sam
	 */
	@Test
	void TestFindAndPrintMeetingAddresses2() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[1][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("b"));
		assertFalse(locationFinder.addresses.contains("d"));
		assertFalse(locationFinder.addresses.contains("f"));
	}

	/**
	 * More complex graph with a cycle of valid meeting addresses
	 */
	@Test
	void TestFindAndPrintMeetingAddresses3() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[5][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("b"));
		assertTrue(locationFinder.addresses.contains("c"));
		assertTrue(locationFinder.addresses.contains("d"));
		assertTrue(locationFinder.addresses.contains("e"));
		assertTrue(locationFinder.addresses.contains("f"));
	}

	/**
	 * More complex graph with a cycle of invalid meeting addresses
	 */
	@Test
	void TestFindAndPrintMeetingAddresses4() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[6][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("b"));
		assertFalse(locationFinder.addresses.contains("c"));
		assertFalse(locationFinder.addresses.contains("d"));
		assertFalse(locationFinder.addresses.contains("e"));

	}

	/**
	 * Graph with cycles of both valid and invalid meeting addresses
	 */
	@Test
	void TestFindAndPrintMeetingAddresses5() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[4][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("b"));
		assertTrue(locationFinder.addresses.contains("c"));
		assertTrue(locationFinder.addresses.contains("d"));
		assertTrue(locationFinder.addresses.contains("e"));
		assertTrue(locationFinder.addresses.contains("f"));
		assertTrue(locationFinder.addresses.contains("g"));
		assertTrue(locationFinder.addresses.contains("h"));
		assertFalse(locationFinder.addresses.contains("i"));
		assertFalse(locationFinder.addresses.contains("j"));
		assertFalse(locationFinder.addresses.contains("k"));
		assertFalse(locationFinder.addresses.contains("l"));
		assertFalse(locationFinder.addresses.contains("m"));
	}

	/**
	 * Complicated Graph
	 */
	@Test
	void TestFindAndPrintMeetingAddresses6() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[7][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("e"));
		assertTrue(locationFinder.addresses.contains("c"));
		assertTrue(locationFinder.addresses.contains("d"));
		assertTrue(locationFinder.addresses.contains("b"));
		assertTrue(locationFinder.addresses.contains("f"));
		assertTrue(locationFinder.addresses.contains("h"));
		assertFalse(locationFinder.addresses.contains("g"));
		assertFalse(locationFinder.addresses.contains("i"));
		assertFalse(locationFinder.addresses.contains("j"));
		assertFalse(locationFinder.addresses.contains("k"));
		assertFalse(locationFinder.addresses.contains("l"));
		assertFalse(locationFinder.addresses.contains("m"));
		assertFalse(locationFinder.addresses.contains("n"));
		assertFalse(locationFinder.addresses.contains("o"));
		assertFalse(locationFinder.addresses.contains("p"));
		assertFalse(locationFinder.addresses.contains("q"));
		assertFalse(locationFinder.addresses.contains("r"));
		assertFalse(locationFinder.addresses.contains("s"));
	}

	/**
	 * Simple graph with Avoid node in the only path
	 */
	@Test
	void TestFindAndPrintMeetingAddresses7() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[8][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertFalse(locationFinder.addresses.contains("a"));
		assertFalse(locationFinder.addresses.contains("b"));
		assertFalse(locationFinder.addresses.contains("c"));
		assertFalse(locationFinder.addresses.contains("d"));
	}

	/**
	 * Simple graph with one path blocked by Avoid node and another path possible
	 */
	@Test
	void TestFindAndPrintMeetingAddresses8() {
		InputStream targetStream = null;
		File initialFile = new File(inputs[9][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		locationFinder.processInputStreamIntoMap();
		locationFinder.findAddresses();
		assertTrue(locationFinder.addresses.contains("a"));
		assertTrue(locationFinder.addresses.contains("e"));
		assertTrue(locationFinder.addresses.contains("f"));
		assertTrue(locationFinder.addresses.contains("g"));
		assertTrue(locationFinder.addresses.contains("d"));
		assertFalse(locationFinder.addresses.contains("b"));
		assertFalse(locationFinder.addresses.contains("c"));
	}

	/**
	 * Testing output on simple graph with one path blocked by Avoid node and
	 * another path possible
	 */
	@Test
	void TestOutputOnFile9() {
		InputStream targetStream = null;
		PrintStream outputStream = null;
		File initialFile = new File(inputs[9][0]);
		File outputFile = new File("src/testFile.txt");
		try {
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Already Exists");
		}
		try {
			targetStream = new FileInputStream(initialFile);
			outputStream = new PrintStream("src/testFile.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream stdout = System.out;
		System.setIn(targetStream);
		System.setOut(outputStream);
		LocationFinder.main(new String[0]);
		outputStream.close();
		System.setOut(stdout);
		Scanner outputFileScanner = null;
		try {
			outputFileScanner = new Scanner(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] lines = { "a", "d", "e", "f", "g" };
		for (int i = 0; i < lines.length; i++) {
			assertTrue(outputFileScanner.nextLine().equals(lines[i]));
		}
		outputFileScanner.close();
		outputFile.delete();
	}

	/**
	 * Tests the output of the program on another graph
	 */
	@Test
	void TestOutputOnFile7() {
		InputStream targetStream = null;
		PrintStream outputStream = null;
		File initialFile = new File(inputs[7][0]);
		File outputFile = new File("src/testFile.txt");
		try {
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Already Exists");
		}
		try {
			targetStream = new FileInputStream(initialFile);
			outputStream = new PrintStream("src/testFile.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream stdout = System.out;
		System.setIn(targetStream);
		System.setOut(outputStream);
		LocationFinder.main(new String[0]);
		outputStream.close();
		System.setOut(stdout);
		Scanner outputFileScanner = null;
		try {
			outputFileScanner = new Scanner(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] lines = { "a", "b", "c", "d", "e", "f", "h" };
		for (int i = 0; i < lines.length; i++) {
			assertTrue(outputFileScanner.nextLine().equals(lines[i]));
		}
		outputFileScanner.close();
		outputFile.delete();
	}

	/**
	 * Testing output on a graph with no locations for Peggy and Sam to meet
	 */
	@Test
	void TestEmptyOutputOnFile8() {
		InputStream targetStream = null;
		PrintStream outputStream = null;
		File initialFile = new File(inputs[8][0]);
		File outputFile = new File("src/testFile.txt");
		try {
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Already Exists");
		}
		try {
			targetStream = new FileInputStream(initialFile);
			outputStream = new PrintStream("src/testFile.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream stdout = System.out;
		System.setIn(targetStream);
		System.setOut(outputStream);
		LocationFinder.main(new String[0]);
		outputStream.close();
		System.setOut(stdout);
		Scanner outputFileScanner = null;
		try {
			outputFileScanner = new Scanner(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(!outputFileScanner.hasNextLine());
		outputFileScanner.close();
		outputFile.delete();
	}

	/**
	 * This test makes sure path doesn't go through avoid location even when avoid
	 * location is a starting point. It also tests to make sure output is
	 * alphabetical, even when there are capital letters in the names.
	 */
	@Test
	void TestOutputOnFile13() {
		InputStream targetStream = null;
		PrintStream outputStream = null;
		File initialFile = new File(inputs[13][0]);
		File outputFile = new File("src/testFile.txt");
		try {
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Already Exists");
		}
		try {
			targetStream = new FileInputStream(initialFile);
			outputStream = new PrintStream("src/testFile.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream stdout = System.out;
		System.setIn(targetStream);
		System.setOut(outputStream);
		LocationFinder.main(new String[0]);
		outputStream.close();
		System.setOut(stdout);
		Scanner outputFileScanner = null;
		try {
			outputFileScanner = new Scanner(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] lines = { "e", "HI" };
		for (int i = 0; i < lines.length; i++) {
			assertTrue(outputFileScanner.nextLine().equals(lines[i]));
		}
		outputFileScanner.close();
		outputFile.delete();
	}

	/**
	 * Test to ensure the binary string insert works on a random string of 40
	 * letters
	 */
	@Test
	void testAddNodeToMeetingAddresses() {
		Random r = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		locationFinder.addresses = new ArrayList<String>();
		for (int i = 0; i < 40; i++) {
			locationFinder.addNodeToMeetingAddresses(0, locationFinder.addresses.size(),
					"" + alphabet.charAt(r.nextInt(alphabet.length())));
		}
		Iterator<String> testStringIterator = locationFinder.addresses.iterator();
		boolean goodString = true;
		String temp = testStringIterator.next();
		String temp2;
		while (testStringIterator.hasNext()) {
			temp2 = testStringIterator.next();
			if (temp.compareTo(temp2) > 0) {
				goodString = false;
				break;
			}
			temp = temp2;
		}
		assertTrue(goodString);
	}

	/**
	 * Tests to ensure the 100 node fully connected graph takes less than 10 seconds
	 * in our program
	 */
	@Test
	void test100FullyConnectedGraphTime() {
		long timeDiff = System.nanoTime();
		InputStream targetStream = null;
		File initialFile = new File(inputs[10][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		LocationFinder.main(new String[0]);
		timeDiff = System.nanoTime() - timeDiff;
		assertTrue(timeDiff * Math.pow(10, -9) < 10);

	}

	/**
	 * Tests to ensure the 1000 node fully connected graph takes less than 60
	 * seconds in our program
	 */
	@Test
	void test1000FullyConnectedGraphTime() {
		long timeDiff = System.nanoTime();
		InputStream targetStream = null;
		File initialFile = new File(inputs[11][0]);
		try {
			targetStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setIn(targetStream);
		LocationFinder.main(new String[0]);
		timeDiff = System.nanoTime() - timeDiff;
		assertTrue(timeDiff * Math.pow(10, -9) < 20);

	}

}

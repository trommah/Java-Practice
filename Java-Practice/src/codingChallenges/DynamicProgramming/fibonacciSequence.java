package codingChallenges.DynamicProgramming;

import java.util.Scanner;

public class fibonacciSequence {
	//we can use a tabulated solution to calculate the fibonacci sequence, a dynamic programming bottom-up method
	public static void main(String[] args) {
		//given int i;
		Scanner scan = new Scanner(args[0]);
		int i = scan.nextInt();
		int[] fib = new int[i + 1];
		fib[0] = 0;
		fib[1] = 1;
		for(int j = 2; j <= i; j++)
			fib[j] = fib[j - 1] + fib[j-2]; 
		System.out.print(fib[i]);
		scan.close();
	}

}

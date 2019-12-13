package codingChallenges.DynamicProgramming;

import java.util.Scanner;

//Given a rod of length n inches and a
//table of prices p(i) for i = 1, 2, 3, ... , n, determine the 
//maximum revenue rn obtainable by cutting up the rod and 
//selling the pieces.
public class CostOfRod {
	public static void main(String[] args) {
		
	}
}

public int maxRevenue(int[] p, int n)
{
	int[] maxRevOfPieces = new int[n];
	for(int i = 0; i < n; i ++)
	{
		maxRevOfPieces[i] = -1;
	}
	return calculateCost(p, n, maxRevOfPieces);
}

public int calculateCost(int[] p, int n, int[] maxRevOfPieces)
{
	if(maxRevOfPieces[n] >= 0)
		return maxRevOfPieces[n];
	int cost;
	if(n == 0)
		cost = 0;
	else
		for(int i = 1; i < n; i++)
			cost = Math.max(cost, p[i] + calculateCost(p, n - i, maxRevOfPieces));
	maxRevOfPieces[n] = cost;
	return cost;
}


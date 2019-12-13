package codingChallenges.DynamicProgramming;
//Given a gold mine of n*m dimensions. Each field in this mine contains a 
///positive integer which is the amount of gold in tons. Initially the miner is 
//at first column but can be at any row. He can move only (right->,right up /,right down\) that is from a given cell,
//the miner can move to the cell diagonally up towards the right or right or diagonally down towards the right. 
//Find out maximum amount of gold he can collect.

import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
		 Scanner s=new Scanner("testCase.txt");
     int t = s.nextInt();
		 int[][] gold;
		 int n, m;
		 GFG gfg = new GFG();
		 for(int i=0;i<t;i++)
		 {
		    n = s.nextInt();
		    m = s.nextInt();
        gold = new int[n][m];
		    Scanner lineScanner = new Scanner(s.nextLine());
		    for(int j = 0; j < n; j++)
		        for(int k = 0; k < m; k++)
		        {
		            gold[j][k] = lineScanner.nextInt();
		        }
		        System.out.print(gfg.findGold(gold));
		 }
     s.close();
		 
	}

  public int findGold(int[][] gold)
{
    int[][] maxGold = new int[gold.length][gold[0].length];
    for(int l = 0; l < gold.length; l++)
    {
        for(int k = 0; k < gold[0].length; k++)
        {
            maxGold[l][k] = -1;
        }
    }
    int maximumGold = 0;
    for(int i = 0; i < gold.length; i++)
    {
        maximumGold = Math.max(maximumGold, maximizedGold(gold, i, 0, maxGold));
    }
    return maximumGold;
}

public int maximizedGold(int[][] gold, int i, int j, int[][] maxGold)
{
    int q = 0;
    if(i < 0 || j < 0 || i > gold.length - 1 || j > gold[0].length - 1)
        return q;
    if(maxGold[i][j] > 0)
        return maxGold[i][j];
    q = Math.max(Math.max(maximizedGold(gold, i - 1, j + 1, maxGold), maximizedGold(gold, i, j + 1, maxGold)),                                     maximizedGold(gold, i + 1, j + 1, maxGold));
    maxGold[i][j] = q;
    return q;
}
}




package codingChallenges.DynamicProgramming;
//Given a gold mine of n*m dimensions. Each field in this mine contains a 
///positive integer which is the amount of gold in tons. Initially the miner is 
//at first column but can be at any row. He can move only (right->,right up /,right down\) that is from a given cell,
//the miner can move to the cell diagonally up towards the right or right or diagonally down towards the right. 
//Find out maximum amount of gold he can collect.
public class GoldMine {

	public static void main(int[][] gold, int i, int j) {		//gold contains the gold at a location, (i,j)
		GoldMine G = new GoldMine();
		G.maxGold(gold, i, j);
	}
	
	public int maxGold(int[][] gold, int i, int j) //finds the max gold attained from the starting point with the miner's defined movements
	{
		int[][] maximizedGold = new int[gold.length][gold[0].length];
		for(int k = 0; k < maximizedGold.length; k++)
		{
			for(int l = 0; l < maximizedGold[0].length; l++)
			{
				maximizedGold[k][l] = -1;
			}
		}
		
		int mostGold = findGold(gold, i, j, maximizedGold);
		return mostGold;
	}
	
	public int findGold(int[][] gold, int i, int j, int[][] maximizedGold)
	{
		int q;
		if(i < 0 || j < 0 || i > gold.length || j > gold[0].length)
			return 0;
		if(maximizedGold[i][j] >= 0)
			return maximizedGold[i][j];
		q = gold[i][j] + Math.max(Math.max(findGold(gold, i - 1, j + 1, maximizedGold), findGold(gold, i, j + 1, maximizedGold)), findGold(gold, i + 1, j + 1, maximizedGold));
		maximizedGold[i][j] = q;
		return q;
	}

}

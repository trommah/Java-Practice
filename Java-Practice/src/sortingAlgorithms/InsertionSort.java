package sortingAlgorithms;

public class InsertionSort {

	public static void main(String[] args) {
		int[] arrayToSort = new int[100];
		//given an array, in-place sort
		int j = 2; 
		//assume everything before j has been sorted
		while(j < arrayToSort.length)
		{
			int temp = arrayToSort[j];
			int i = j - 1;
			while(temp < arrayToSort[i] && i >= 0)
				i--;
			//once we've found our location for j to fit
			while(j != i) {
				arrayToSort[j] = arrayToSort[j-1];
				j--;
			}
			arrayToSort[i] = temp; 
		}
		
	}

}

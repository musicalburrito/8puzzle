import java.util.Arrays;

public class node {
	int [][] puzzle = new int[3][3];
	
	node(int[] row1, int[] row2, int[] row3){
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				if(i == 0){
					puzzle[i][j] = row1[j];
				}
				else if(i == 1){
					puzzle[i][j] = row2[j];
				}
				else if(i == 2){
					puzzle[i][j] = row3[j];
				}
			}
		}

		System.out.println(Arrays.deepToString(puzzle));
	}
}

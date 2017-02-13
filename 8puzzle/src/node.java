import java.util.ArrayList;
import java.util.List;

public class node {
	List<List<Integer>> puzzle = new ArrayList<List<Integer>>();
	node up;
	node down;
	node right;
	node left;
	
	node(List<Integer> row1, List<Integer> row2, List<Integer> row3){
		puzzle.add(row1);
		puzzle.add(row2);
		puzzle.add(row3);
		
		up = down = left = right = null;
		
		System.out.println(puzzle);
	}
	
	void addLeft(node a){
		left = a;
	}
	
	void addRight(node a){
		right = a;
	}
	
	void addUp(node a){
		up = a;
	}
	
	void addDown(node a){
		down = a;
	}
}

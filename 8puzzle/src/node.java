import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class node {
	Integer [][] puzzle = new Integer[3][3];
	node up;
	node down;
	node right;
	node left;
	double f;
	double g;
	double h;
	Integer row;
	Integer col;
	
	
	node(Integer[][] puz){
		puzzle = puz;
		up = down = left = right = null;
	}
	
	void print(Integer[][] a){
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				System.out.print(a[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	// Finds and sets index of 0 if exists. If does not exist, returns false.
	boolean find0(Integer[][] a){
		for (Integer i = 0; i < 3; ++i){
			for (Integer j = 0; j < 3; ++j){
				if(puzzle[i][j] == 0){
					row = i;
					col = j;
					return true;
				}
			}
		}
		return false;
	}
	
	boolean addLeft(){
		find0(puzzle);
		if(col == 0){
			left = null;
			return false;
		}
		Integer [][] new_puz = new Integer[3][3];
		copy(puzzle, new_puz);
		
		Integer temp = new_puz[row][col - 1];
		new_puz[row][col - 1] = 0;
		new_puz[row][col] = temp;
		
		node new_left = new node(new_puz); //cost is wrong
		left = new_left;
		return true;
	}
	
	boolean addRight(){
		find0(puzzle);
		if(col == 2){
			right = null;
			return false;
		}
		Integer [][] new_puz = new Integer[3][3];
		copy(puzzle, new_puz);
		
		Integer temp = puzzle[row][col + 1];
		new_puz[row][col + 1] = 0;
		new_puz[row][col] = temp;
		
		node new_right = new node(new_puz); //cost is wrong
		right = new_right;
		return true;
	}
	
	boolean addUp(){
		find0(puzzle);
		if(row == 0){
			up = null;
			return false;
		}
		Integer [][] new_puz = new Integer[3][3];
		copy(puzzle, new_puz);
		
		Integer temp = puzzle[row - 1][col];
		new_puz[row - 1][col] = 0;
		new_puz[row][col] = temp;
		
		node new_up = new node(new_puz); //cost is wrong
		up = new_up;
		return true;
	}
	
	boolean addDown(){
		find0(puzzle);
		if(row == 2){
			down = null;
			return false;
		}
		Integer [][] new_puz = new Integer[3][3];
		copy(puzzle, new_puz);
		
		Integer temp = puzzle[row + 1][col];
		new_puz[row + 1][col] = 0;
		new_puz[row][col] = temp;
		
		node new_down = new node(new_puz); //cost is wrong
		down = new_down;
		return true;
	}
	
	void copy(Integer[][] src, Integer[][] dest){
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				dest[i][j] = src[i][j];
			}
		}
	}
	
	void expand(){
		addRight();
		addLeft();
		addUp();
		addDown();
	}
	
	void ucs(){
		Comparator<node> comparator = new nodeComparator();
		PriorityQueue<node> q = new PriorityQueue<node>(10, comparator);
		ArrayList<node> explored = new ArrayList<node>();
		
//		Integer test = 0;
		q.add(this);
		node temp;
//		while(test != 2){
		while(q.size() != 0){
			temp = q.poll();
			if(temp.left != null){
				if(temp.left.checkFinal()){
					System.out.println("Found solution left");
					break;
				}
				temp.left.f = f + 1;
				if(!temp.left.checkList(explored)){
					System.out.println("adding :");
					print(temp.left.puzzle);
					q.add(temp.left);
				}
			}
			if(temp.right != null){
				if(temp.right.checkFinal()){
					System.out.println("Found solution right");
					break;
				}
				temp.right.f = f + 1;
				if(!temp.right.checkList(explored)){
					System.out.println("adding :");
					print(temp.right.puzzle);
					q.add(temp.right);
				}
			}
			if(temp.up != null){
				if(temp.up.checkFinal()){
					System.out.println("Found solution up");
					break;
				}
				temp.up.f = f + 1;
				if(!temp.up.checkList(explored)){
					System.out.println("adding :");
					print(temp.up.puzzle);
					q.add(temp.up);
				}
			}
			if(temp.down != null){
				if(temp.down.checkFinal()){
					System.out.println("Found solution down");
					break;
				}
				temp.down.f = f + 1;
				if(!temp.down.checkList(explored)){
					System.out.println("adding :");
					print(temp.down.puzzle);
					q.add(temp.down);
				}
			}
			explored.add(temp);
			temp.expand();
			System.out.println("Expanding this node: ");
			print(temp.puzzle);
//			++test;
		}
		System.out.println("Ran out of node to expands");
	}
	
	void mpt(){
		
	}
	
	void md(){
		
	}
	
	boolean checkFinal(){
		Integer[][] final_puz = new Integer[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}};
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				if(!(puzzle[i][j] == final_puz[i][j])){
					return false;
				}
			}
		}
		return true;
	}
	
	boolean check_equals(Integer[][] a, Integer[][] b){
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				if(!(a[i][j] == b[i][j])){
					System.out.print("does not match");
					return false;
				}
			}
		}
		return true;
	}
	
	boolean checkList(ArrayList<node> a){
		System.out.println(a.size());
		for(Integer i = 0; i < a.size(); ++i){
			System.out.println(i + ":");
			print(a.get(i).puzzle);
			if(check_equals(puzzle, a.get(i).puzzle)){
				System.out.println("Has been explored");
				return true;
			}
		}
		return false;
	}
}
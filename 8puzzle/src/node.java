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
	Integer f;
	Integer g;
	Integer h;
	Integer row;
	Integer col;
	
	
	node(Integer[][] puz){
		puzzle = puz;
		up = down = left = right = null;
		g = 0;
		h = 0;
		f = 0;
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
	
	void algo(int choice){
		Comparator<node> comparator = new nodeComparator();
		PriorityQueue<node> q = new PriorityQueue<node>(10, comparator);
		ArrayList<node> explored = new ArrayList<node>();
		ArrayList<node> neighbors = new ArrayList<node>();
		q.add(this);
		node temp = this;
		Integer depth = 0;
		int test = 0;
		int cost = 43453543;
		
//		temp.expand();
//		while(test != 5){
		while(q.size() != 0){
			temp = q.poll();
			System.out.println("Expanding this node: ");
			print(temp.puzzle);
			temp.expand();
			System.out.println("This is g(n): " + temp.g);
			System.out.println("This is h(n): " + temp.h);
			++depth;
			explored.add(temp);
			neighbors.add(temp.down);
			neighbors.add(temp.up);
			neighbors.add(temp.left);
			neighbors.add(temp.right);
			
			for(node e : neighbors){
				if(e != null){
					e.g = temp.g + 1;
					if(choice == 1){
						e.h = 0;
						e.f = e.g + e.h;
					}
					else if(choice == 2){
						e.h = e.mpt();
						e.f = e.g + e.h;
					}
					else if(choice == 3){
						e.h = e.md();
						e.f = e.g + e.h;
					}
					if(!e.checkFinal()){
						if(!e.checkList(explored)){
							System.out.println("This is the puzzle: ");
							print(e.puzzle);
							System.out.println("cost of node being added: " + e.f);
//							System.out.println("this is the depth: " + e.g);
//							System.out.println("------------");
//							print(e.puzzle);
							q.add(e);
						}
					}
					else{
						System.out.println("solution found : ");
						print(e.puzzle);
						return;
					}
				}
			}
			
			neighbors.clear();
			
			++test;
		}
		System.out.println("Ran out of nodes to expand");
	}
	
	Integer mpt(){
		Integer a = 1;
		Integer misplaced = 0;
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				if(puzzle[i][j] != a){
					++misplaced;
				}
				++a;
			}
		}
		return misplaced;
	}
	
	Integer md(){
		Integer md = 0;
//		System.out.println("Costs for this puzzle");
//		print(puzzle);
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
//				System.out.println("this is the number: " + puzzle[i][j]);
//				System.out.println("this is the distance from: " +
//						findDist(puzzle[i][j], i, j));
				if(puzzle[i][j] != 0){
					md += findDist(puzzle[i][j], i, j);
				}
			}
		}
		return md;
	}
	
	Integer findDist(Integer num, Integer x, Integer y){
		int md = 0;
		Integer[][] final_puz = new Integer[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}};
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				if(num == final_puz[i][j]){
//					if(num == 0){
//						System.out.println("this is row: " + Math.abs(x)
//						+ " this is col: " + Math.abs(y));
//					}
					return Math.abs(x - i) + Math.abs(y - j);
				}
			}
		}
		return 0;
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
//					System.out.print("does not match");
					return false;
				}
			}
		}
		return true;
	}
	
	boolean checkList(ArrayList<node> a){
//		System.out.println("checking explored for this: ");
//		print(puzzle);
		for(Integer i = 0; i < a.size(); ++i){
//			System.out.println("index" + i + ":" + " cost: " + a.get(i).f);
//			print(a.get(i).puzzle);
			if(check_equals(puzzle, a.get(i).puzzle)){
//				System.out.println("Has been explored");
				return true;
			}
		}
		return false;
	}
	
//	boolean checkq(PriorityQueue<node> a){
//		for(node e : a){
//			if(check_equals(puzzle, e.puzzle)){
//				return true;
//			}
//		}
//		return false;
//	}
}
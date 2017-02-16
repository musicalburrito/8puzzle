import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class node {
	Integer [][] puzzle = new Integer[3][3];
	node up;
	node down;
	node right;
	node left;
	node parent;
	Integer f;
	Integer g;
	Integer h;
	Integer row;
	Integer col;
	
	
	node(Integer[][] puz){
		puzzle = puz;
		up = down = left = right = parent = null;
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
		
		node new_left = new node(new_puz);
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
		
		node new_right = new node(new_puz);
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
		
		node new_up = new node(new_puz); 
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
		
		node new_down = new node(new_puz); 
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
	
	void printTrace(Integer total_expanded, Integer max_q, node e){
		Stack<node> a = new Stack<node>();
		while(e.parent != null){
			a.push(e.parent);
			e = e.parent;
		}
		
		while(!a.empty()){
			node temp = a.pop();
			System.out.println("The best state to expand with a g(n) = " + temp.g  +
					" and h(n) = " + temp.h + " is.. ");
			print(temp.puzzle);
		}
	}
	
	void algo(int choice){
		Comparator<node> comparator = new nodeComparator();
		PriorityQueue<node> q = new PriorityQueue<node>(10, comparator);
		ArrayList<node> explored = new ArrayList<node>();
		ArrayList<node> neighbors = new ArrayList<node>();
		q.add(this);
		node temp = this;
		int total_expanded = 0;
		int max_q = 0;
		while(q.size() != 0){
			if(q.size() > max_q){
				max_q = q.size();
			}
			temp = q.poll();
			temp.expand();
			++total_expanded;
			System.out.println("The best state to expand with a g(n) = " + temp.g  +
					" and h(n) = " + temp.h + " is.. ");
			print(temp.puzzle);
			explored.add(temp);
			neighbors.add(temp.down);
			neighbors.add(temp.up);
			neighbors.add(temp.left);
			neighbors.add(temp.right);
			
			for(node e : neighbors){
				if(e != null){
					e.g = temp.g + 1;
					e.parent = temp;
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
							q.add(e);
						}
					}
					else{
						System.out.println("Reached goal: ");
						print(e.puzzle);
						System.out.println("\n");
						System.out.println("To solve this problem the search algorithm expanded"
								+ " a total of " + total_expanded + " nodes.");
						System.out.println("The maximum number of nodes in the queue at one time" +
								" was " + max_q + ".");
						System.out.println("The depth of the goal node was " + e.g + "\n\n");
						
						Scanner trace = new Scanner(System.in);
						System.out.println("Press 1 to see trace.");
						String yes = trace.nextLine();
						
						if(yes.equals("1")){
							System.out.println("----- Start Trace -----");
							printTrace(total_expanded, max_q, e);
							System.out.println("Reached goal: ");
							print(e.puzzle);
							System.out.println("----- End Trace -----");
						}
						
						trace.close();
						return;
					}
				}
			}
			
			neighbors.clear();
		}
		
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

		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				if(puzzle[i][j] != 0){
					md += findDist(puzzle[i][j], i, j);
				}
			}
		}
		return md;
	}
	
	Integer findDist(Integer num, Integer x, Integer y){
		Integer[][] final_puz = new Integer[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}};
		for(Integer i = 0; i < 3; ++i){
			for(Integer j = 0; j < 3; ++j){
				if(num == final_puz[i][j]){
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
					return false;
				}
			}
		}
		return true;
	}
	
	boolean checkList(ArrayList<node> a){
		for(Integer i = 0; i < a.size(); ++i){
			if(check_equals(puzzle, a.get(i).puzzle)){
				return true;
			}
		}
		return false;
	}
}
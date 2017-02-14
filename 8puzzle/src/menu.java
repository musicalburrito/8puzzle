import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class menu {
	public void display(){
		Scanner input = new Scanner(System.in);
		String selection = "";
		node puzzle = null;
		System.out.println("Welcome to 861102031's 8-puzzle solver. \n"
				+ "Type \"1\" to use a default puzzle, or \"2\" to enter your own puzzle."
				+ "\n");
		
		while (!selection.equals("1") && !selection.equals("2")){
			selection = input.nextLine();
			if (selection.equals("1")){
				puzzle = defaultPuzzle();
			}
			else if (selection.equals("2")){
				puzzle = customPuzzle(input);
			}
			else System.out.println("Invalid selection. Try again.");
		}
		
		System.out.println("Enter choice of algorithm: \n"
				+ "1. Uniform Cost Search \n"
				+ "2. A* with Misplaced Tile heuristic \n"
				+ "3. A* with Manhattan Distance heuristic \n");
		selection = "";
		
		while(!selection.equals("1") && !selection.equals("2") && !selection.equals("3")){
			selection = input.nextLine();
			if (selection.equals("1")){
				puzzle.ucs();
			}
			else if (selection.equals("2")){
				puzzle.mpt();
			}
			else if (selection.equals("3")){
				puzzle.md();
			}
		}
	}
	
	public node defaultPuzzle(){
		Integer[][] puzzle = new Integer[][]{{1, 2, 3},{4, 0, 5},{6, 7, 8}};
		node defaultNode = new node(puzzle);
		return defaultNode;
	}
	
	public node customPuzzle(Scanner input){
//		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter your pizzle, use a zero to represent the blank\n"
				+ "Enter the first row, use space or tabs between numbers");
		String r1 = input.nextLine();
		System.out.println("Enter the second row, use space or tabs between numbers");
		String r2 = input.nextLine();
		System.out.println("Enter the third row, use space or tabs between numbers");
		String r3 = input.nextLine();
		
		String[] srow1 = r1.split(" ");
		String[] srow2 = r2.split(" ");
		String[] srow3 = r3.split(" ");

		Integer[] al_r1 = new Integer[3]; 
		Integer[] al_r2 = new Integer[3];
		Integer[] al_r3 = new Integer[3];
		
		for(Integer i = 0; i < srow1.length; ++i){
			al_r1[i] = Integer.parseInt(srow1[i]);
			al_r2[i] = Integer.parseInt(srow2[i]);
			al_r3[i] = Integer.parseInt(srow3[i]);
		}
		
		Integer[][] puzzle = new Integer[3][3];
		for(Integer i = 0; i < 3; ++i){
			puzzle[0][i] = al_r1[i];
			puzzle[1][i] = al_r2[i];
			puzzle[2][i] = al_r3[i];
		}
		
		node custom = new node(puzzle);
		return custom;
	}
}

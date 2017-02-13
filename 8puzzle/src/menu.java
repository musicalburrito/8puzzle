import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class menu {
	public void display(){
		Scanner input = new Scanner(System.in);
		String selection = "";
		System.out.println("Welcome to 861102031's 8-puzzle solver. \n"
				+ "Type \"1\" to use a default puzzle, or \"2\" to enter your own puzzle."
				+ "\n");
		
		while (!selection.equals("1") && !selection.equals("2")){
			selection = input.nextLine();
			if (selection.equals("1")){
				input.close();
				defaultPuzzle();
			}
			else if (selection.equals("2")){
//				input.close();
				customPuzzle(input);
				input.close();
			}
			else System.out.println("Invalid selection. Try again.");
		}
	}
	
	public void defaultPuzzle(){
		List<Integer> row1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		List<Integer> row2 = new ArrayList<Integer>(Arrays.asList(4, 0, 5));
		List<Integer> row3 = new ArrayList<Integer>(Arrays.asList(6, 7, 8));
		node defaultNode = new node(row1, row2, row3);
	}
	
	public void customPuzzle(Scanner input){
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
		
		System.out.println(Arrays.deepToString(srow1));
		System.out.println(Arrays.deepToString(srow2));
		System.out.println(Arrays.deepToString(srow3));

		List<Integer> al_r1 = new ArrayList<Integer>();
		List<Integer> al_r2 = new ArrayList<Integer>();
		List<Integer> al_r3 = new ArrayList<Integer>();
		
		for(int i = 0; i < srow1.length; ++i){
			al_r1.add(Integer.parseInt(srow1[i]));
			al_r2.add(Integer.parseInt(srow2[i]));
			al_r3.add(Integer.parseInt(srow3[i]));
		}
		
		node custom = new node(al_r1, al_r2, al_r3);
	}
}

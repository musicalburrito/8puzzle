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
				System.out.println("here");
				input.close();
				defaultPuzzle();
			}
			else if (selection.equals("2")){
				input.close();
				customPuzzle();
			}
			else System.out.println("Invalid selection. Try again.");
		}
	}
	
	public void defaultPuzzle(){
		int[] row1 = {1,2,3};
		int[] row2 = {4,0,5};
		int[] row3 = {6,7,8};
		node defaultNode = new node(row1, row2, row3);
	}
	
	public void customPuzzle(){
		Scanner input = new Scanner(System.in);
		int[] row1 = new int[3];
		int[] row2 = new int[3];
		int[] row3 = new int[3];
		
		System.out.println("Enter your pizzle, use a zero to represent the blank\n"
				+ "Enter the first row, use space or tabs between numbers");
		String r1 = input.nextLine();
		System.out.println("Enter the second row, use space or tabs between numbers");
		String r2 = input.nextLine();
		System.out.println("Enter the third row, use space or tabs between numbers");
		String r3 = input.nextLine();
		
		String[] srow1 = r1.split(",");
		String[] srow2 = r2.split(",");
		String[] srow3 = r3.split(",");
		
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				if(i == 0) row1[j] = Integer.parseInt(srow1[j]);
				else if(i == 1) row2[j] = Integer.parseInt(srow2[j]);
				else if(i == 2) row3[j] = row1[j] = Integer.parseInt(srow3[j]);
			}
		}
		
		node custom = new node(row1, row2, row3);
	}
}

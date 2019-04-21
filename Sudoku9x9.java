import java.util.Scanner;



public class Sudoku9x9 {
	
	public static char[][] entries = new char[9][9];
	public static char[][] givenEntries = new char[9][9];
	
	
	public static void printBoard(){
		String board;
		board =         "                                             Sudoku                         \n";
		board = board + "                       ";
		board = board + "=====================================================\n";
		for (int r = 0; r < 3; r++){
			board = board + "                       ";
			for (int c = 0; c < 9; c= c + 3) {
				board = board + "||[ " + entries[r][c] + " ][ " + entries[r][c + 1] + " ][ " + entries[r][c + 2] + " ]";
			}
			board = board + "||\n";
		}
		board = board + "                       ";
		board = board + "=====================================================\n";
		for (int r = 3; r < 6; r++){
			board = board + "                       ";
			for (int c = 0; c < 9; c= c + 3) {
				board = board + "||[ " + entries[r][c] + " ][ " + entries[r][c + 1] + " ][ " + entries[r][c + 2] + " ]";
			}
			board = board + "||\n";
		}
		board = board + "                       ";
		board = board + "=====================================================\n";
		for (int r = 6; r < 9; r++){
			board = board + "                       ";
			for (int c = 0; c < 9; c= c + 3) {
				board = board + "||[ " + entries[r][c] + " ][ " + entries[r][c + 1] + " ][ " + entries[r][c + 2] + " ]";
			}
			board = board + "||\n";
		}
		board = board + "                       ";
		board = board + "=====================================================\n";
			
		System.out.print(board);
	}
	
	
	
	
	public static boolean checkRowsLegal(char[][] entries){
		
		boolean isLegal = true;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 8; col++) {
				for (int i = col + 1; i < 9; i++) {
					if (entries[row][col] != ' ' && entries[row][col] == entries[row][i]) {
						isLegal = false;
					}
				}
			}
		}
		return isLegal;
	}
	
	public static boolean checkColsLegal(char[][] entries){
		
		boolean isLegal = true;
		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 8; row++) {
				for (int i = row + 1; i < 9; i++) {
					if (entries[row][col] != ' ' && entries[row][col] == entries[i][col]) {
						isLegal = false;
					}
				}
			}
		}
		return isLegal;
	}
	
	
	public static boolean checkSquaresLegal(char[][] entries) {
		boolean isLegal = true;
		for (int r = 0; r < 9; r = r + 3){
			for (int c = 0; c < 9; c = c + 3){
				for (int row = r; row < r + 3; row++) {
					for (int col = c; col < c + 3; col++){
						for (int i = r; i < r + 3; i++){
							for (int j = c; j < c + 3; j ++){
								boolean same_box = (row == i) && (col == j);
								if (same_box == false && entries[row][col] != ' ' && entries[row][col] == entries[i][j]) {
									isLegal = false;
								}
							}
						}
					}
				}
			}
		}
		return isLegal;
	}
	
	
	public static boolean isLegal(char[][] entries){
		if (checkRowsLegal(entries) == false || checkColsLegal(entries) == false || checkSquaresLegal(entries) == false){
			return false;
		}
		return true;
	}
	
	public static boolean isComplete(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 49; col++){
				if (entries[row][col] == ' '){
					return false;
				}		
			}
		}
		return true;
	}
	
	public static boolean canChangeEntry(int r, int c){
		if (givenEntries[r][c] == 'y') {
			return false;
		}
		return true;
	}
	
	
	public static void solvePuzzle(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++) {
				if (canChangeEntry(row, col) == true) {
					for (int entry = 1; entry <= 9; entry++) {
						char charEntry = Integer.toString(entry).charAt(0);
						entries[row][col] = charEntry;
						char[][] entriesCheck = new char[9][9];
						entriesCheck = entries;
						if (isLegal(entries) == true && forwardChecking(row, col, entriesCheck) == true)
							break;			
					}			
				}
			}
		}
		
	}
	
	public static boolean forwardChecking(int r, int c, char[][] plays){
		boolean firstLoop = false;
		if (c < 8) {
			firstLoop = true;
			for (int col = c + 1; col < 9; col++){
				if (canChangeEntry(r, col) == true) {
					for (int entry = 1; entry <= 9; entry++) {

						char charEntry = Integer.toString(entry).charAt(0);
						plays[r][col] = charEntry;
						if (isLegal(plays) == true){
							boolean next_hop = forwardChecking(r, col, plays);
							if (next_hop == true){
								return true;
							}
						}
					}
					plays[r][col] = ' ';
					return false;
				}	
			}
		}
		if (r < 8 && (c == 8 || firstLoop == true)){
		int row = r + 1;
		for (int col = 0; col < 9; col++){
			if (canChangeEntry(row, col) == true) {
				for (int entry = 1; entry <= 9; entry++) {
					char charEntry = Integer.toString(entry).charAt(0);
					plays[row][col] = charEntry;
					if (isLegal(plays) == true){
						boolean next_hop = forwardChecking(row, col, plays);
						if (next_hop == true){
							return true;
						}
					}
				}
				plays[row][col] = ' ';
				return false;
			}
		}		
		}
		
		return true;
	}
	
	
	public static void generateGivenEntries(char[][] entryArray){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++){
				if (entryArray[row][col] == ' ')
					givenEntries[row][col] = 'n';
				else
					givenEntries[row][col] = 'y';
			}
		}
		
		
	}
	
	
	
	
	
	
	public void playSudoku() {
		char [][] entriesC = { {' ', ' ', ' ', '6', ' ', ' ', '4', ' ', ' '},
							   {'7', ' ', ' ', ' ', ' ', '3', '6', ' ', ' '},
							   {' ', ' ', ' ', ' ', '9', '1', ' ', '8', ' '},
							   {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							   {' ', '5', ' ', '1', '8', ' ', ' ', ' ', '3'},
							   {' ', ' ', ' ', '3', ' ', '6', ' ', '4', '5'},
							   {' ', '4', ' ', '2', ' ', ' ', ' ', '6', ' '},
							   {'9', ' ', '3', ' ', ' ', ' ', ' ', ' ', ' '},
							   {' ', '2', ' ', ' ', ' ', ' ', '1', ' ', ' '}				   
		};
		
		generateGivenEntries(entriesC);
		entries = entriesC;
		printBoard();
		
		
		solvePuzzle();
		
		
		printBoard();
		
	}

	public static void main(String[] args) {
		Sudoku9x9 game = new Sudoku9x9();
		game.playSudoku();
	}

}

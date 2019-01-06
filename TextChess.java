package textchess;
import java.lang.Integer;
import java.util.Scanner;
import textchess.Board;
import java.util.Random;

public class TextChess {
	static final int WHITE = 1;
	static final int BLACK = 0;
	
	public static void main(String[] args) {
		System.out.println("Generating chess game of size " + args[0] + " by " 
					+ args[1] + " with " + args[2] + " knights\n");
		
		Random rand = new Random(Integer.parseInt(args[3]));
		Board borad = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]), rand);
		
		createKnights(Integer.parseInt(args[2]), WHITE, board);
		createKnights(Integer.parseInt(args[2]), BLACK, board);
		
		while (true) {
			board.draw();
			while (!manualMove(board)) {};
			aiMove(board);
			printPiecesTaken(board);
			System.out.println("");
		}
	}
	
	static boolean manualMove(Board board) {
		Scanner input = new Scanner(System.in);
		System.out.print("Player (upper case) move: ");
		String startLetter = input.next();
		int startNumber = input.nextInt();
		String endLetter = input.next();
		int endNumber = input.nextInt();
		
		boolean moveIsValid = board.manualMove(startLetter, startNumber, endLetter, endNumber);
		return moveIsValid;
	}
	
	static void aiMove(Board board) {
		board.aiMove();
	}
	
	static void createKnights(int num, int color, Board board) {
		for (int i = 0; i < num; i ++) {
			Knight knight = new Knight(color);
			board.randomPlace(knight);
		}
	}
	
	static void printPiecesTaken(Board board) {
		System.out.println("Player lost " + board.getCapturedPieces(WHITE) + " piece(s)."); 
	    System.out.println("AI lost " + board.getCapturedPieces(BLACK) + " piece(s)");
	}
}

import java.util.Random;
import java.util.Scanner;

// A chess game using only knights for user and AI
public class ChessGame {
	
	static int width;
	static int height;
	static int nOfKnightsN;
	static int[] validSpots;
	static int captured_n;
	static int[] firstlast;
	static int captured_N;
	static boolean existN = true;
	static boolean existn = true;
	static int endGame = 0;
	static boolean NE = true;
	static int[] boardInfo = new int[3];
	static String[][] occupancy;
	static boolean move;
	
	public static void main(String[] args) {
		
		boardInfo = getBoardinfo();
		System.out.println(boardInfo[0]);
		drawlett();
		drawdash();
		occupancy = new String[boardInfo[1]][boardInfo[0]];
		randomPlace(boardInfo[0],boardInfo[1],boardInfo[2]);
		
		captured_n = 0;
		captured_N = 0;
		
		
		while (endGame == 0) {
			
			//check if Knights of N and n exist on the board
			//This is checked each time because AI might have captured all N already
			NE = checkIfKnightsExist(occupancy);
			if (NE == false) {
				break;
			}
			
			move = true;
			label:
			while (move) { // user loop until valid move occurs
				
				firstlast = makeAMove();
				int startWidth = firstlast[0];
				int startHeight = firstlast[1];
				int endWidth  = firstlast[2];
				int endHeight  = firstlast[3];
				
				// create 8 possible spots based on Knight's moving rule
				int[] spots = createSpots(startHeight, startWidth);
				
				
					if ( (startWidth <= width-1 && startWidth >= 0 && startHeight <= height-1 && startHeight >= 0)) {
						if (occupancy[startHeight][startWidth] == "|N") {
						
						// Check if end point is outside the board
						if (endWidth > width-1 || endWidth < 0 || endHeight > height-1 || endHeight < 0) {
							System.out.println("End position is not inside the board");
							continue label;
						}
						
						// check if the player move follows the knight rule
						if (	   (endWidth == spots[0] && endHeight == spots[1])    
								|| (endWidth == spots[2] && endHeight == spots[3])
								|| (endWidth == spots[4] && endHeight == spots[5])
								|| (endWidth == spots[6] && endHeight == spots[7])
								|| (endWidth == spots[8] && endHeight == spots[9])
								|| (endWidth == spots[10] && endHeight == spots[11])
								|| (endWidth == spots[12] && endHeight == spots[13])
								|| (endWidth == spots[14] && endHeight == spots[15])) {
							
							
							// Now that the move is valid, only empty and n positions are valid
							if (occupancy[endHeight][endWidth] == "| " ||  occupancy[endHeight][endWidth] == "|n") {
								
								// update the board with the move
								if (occupancy[endHeight][endWidth] == "| ") {
									occupancy[endHeight][endWidth] = "|N";
								} else {
									occupancy[endHeight][endWidth] = "|N";
									char x = (char) (endWidth+65);
									int y = endHeight+1;
									System.out.println("AI lost one piece at " + x + " " + y);
									captured_n++;
								}
		
								occupancy[startHeight][startWidth] = "| ";
								System.out.println("*********************");
								drawlett();
								drawdash();
								draw();
								
								System.out.println("Total captured Knights by Player = " + captured_n);
								System.out.println("Total captured knights by AI = " + captured_N);
								// to break the loop and switch to AI turn
								move = false;
								
									
								
							} else {
								System.out.println("Position is pre-occupied by N");
								
								}
						} else {
							System.out.println("Invalid move");
							
							}
					}  else {
						System.out.println("The Player does not have a piece at " + (char) (startWidth+65) + " " + (startHeight+1));
					}
					}else {
						System.out.println("Start point out of board " + (char) (startWidth+65) + " " + (startHeight+1));
						
						}
				
				
			} // while - user's loop turn

			// Check if any n is left for the AI to move
			NE = checkIfKnightsExist(occupancy);
			if (NE == false) {
				break;
			}
						
			System.out.println("*********************");
			AIMove(occupancy);
			System.out.println("*********************");
			drawlett();
			drawdash();
			draw();
			System.out.println("Total captured Knights by Player = " + captured_n);
			System.out.println("Total captured knights by AI = " + captured_N);

		}// while for end of game
		
	System.out.println("Thank you for play this game designed by Ahmad Mohaddes Pour");
	} //main method

	// End of main method *************************************************************
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* ************************************************/
	/* ************************************************/
	//draw board occupied with number of N and n knights
	public static void randomPlace(int width, int height, int nOfKnightsN) {
		//Knight knight = new Knight("N");

		Random r = new Random();
		
		for (int i = 0; i < height; i++ ) {
			for (int j=0; j < width; j++) {
				occupancy[i][j] = "| ";
			}
		}
		
		// random place N
		fillspace(1);
		
		// random place n
		fillspace(0);
		
		//draw the board with occupied spaces
		draw();	
	} // randomPlace()
	/* ***************************************************/	

		public static void drawdash() {
			for (int i = 1; i < width+1; i++) {
				System.out.print("--");
			}
			System.out.print("-");
			System.out.println("");
		}

		
		
		//draw the letters
		public static void drawlett() {
				char lett = '@';
				for (int i = 1; i < width+1; i++) {
					lett += 1;
					String let = Character.toString(lett);
					System.out.print(" " + let);
				}
				System.out.println("");
		}
		

		
		//generate a board with random occupancy of N (type=1) or n (type = 0)
		public static void fillspace(int type) {
			String  ff = "|N";
			Random r = new Random();
			int repeat = 0;
			if (type == 0) {
				 ff = "|n";
			}
			
			for (int i = 0; i < nOfKnightsN ; i++) {
				repeat = 0;
				while (repeat == 0) {
					int rw = r.nextInt(width);
					int rh = r.nextInt(height);
					if (occupancy[rh][rw] == "| ") {
						occupancy[rh][rw] = ff;
						repeat = 1;
					} 
				}
			}
			}
		
		public static void draw() {
			
			int count = 1;
			for (int i = 0; i < height; i++ ) {
				for (int j=0; j < width; j++) {
					System.out.print(occupancy[i][j]);
				}
				System.out.print("| ");
				System.out.print(count);
				count++;
				System.out.println("");
				drawdash();
			}
			
		}
	
		public static int[] makeAMove() {
		System.out.print("Player (upper case) Move: ");
		Scanner in = new Scanner(System.in);
		int[] movearray = new int[4];
		
		//user start point based on i j in array
		int sw = (int) in.next().charAt(0);
		movearray[0] = sw - 65; //A = 65
		movearray[1]= in.nextInt()-1;
		
		//user end point
		int ew = (int) in.next().charAt(0);
		movearray[2] = ew - 65;
		movearray[3] = in.nextInt()-1;
		
		return movearray;
		}
		
		
		//AI Move ***********************************************
		public static void AIMove(String[][] occupiedArray) {

			Scanner in = new Scanner(System.in);
			Random r = new Random();
			int[] movearray = new int[4];
			int endSpotAI_w;
			int endSpotAI_h;
			int startWidth;
			int startHeight;
			boolean AIcapturedN = false;
			//AI start point 
			boolean startvalid = true;
			char startCharAI = 'a';
			char endCharAI = 'a';
			boolean contiueMoveAI = true;
			
			label: // loop captures N if N is in valid positions around n at i j

				
				//start from cell 0 to end and if any n can capture N
				for (int i = 0; i < height; i++ ) {
					for (int j=0; j < width; j++) {
						//Starts from the first element in the array and finds n, then
						//check if N exists in valid spots around n
						if (occupiedArray[i][j] == "|n") {
							
							//first possible spot
							endSpotAI_w = j + 2;
							endSpotAI_h = i - 1;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//second possible spot
							endSpotAI_w = j + 2;
							endSpotAI_h = i + 1;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//third possible spot
							endSpotAI_w = j - 2;
							endSpotAI_h = i - 1;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//4th possible spot
							endSpotAI_w = j - 2;
							endSpotAI_h = i + 1;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//5th possible spot
							endSpotAI_w = j + 1;
							endSpotAI_h = i - 2;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//6th possible spot
							endSpotAI_w = j - 1;
							endSpotAI_h = i - 2;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//7th possible spot
							endSpotAI_w = j + 1;
							endSpotAI_h = i + 2;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
							//8th possible spot
							endSpotAI_w = j - 1;
							endSpotAI_h = i + 2;
							occupiedArray = aiCaptureN(height,width,i,j,endSpotAI_h,endSpotAI_w,occupiedArray);
							if (occupiedArray[i][j] == "| ") {
								startCharAI = (char) (j+65);
								endCharAI = (char) (endSpotAI_w+65);
								System.out.println("AI moved from " + startCharAI + " " + (i+1) + " to " + endCharAI + " " + (endSpotAI_h+1) + " and captured N");
								contiueMoveAI = false;
								break label;
							}
							
						}
						
					} // inner for

				} // our for
				
			
			if (contiueMoveAI == true) {
				
				System.out.println("AI could NOT captur any N");
				//Since AI did not capture an N, now randomly selects an n and make a valid move
				// Randomly select a position containing n
				int endWidth;
				int endHeight;
				int repeat = 0;
				
				label1:
				while (repeat == 0) { // loop until a position is found containing n
					int rw = r.nextInt(width);
					int rh = r.nextInt(height);
					if (occupiedArray[rh][rw] == "|n") {
						
						boolean validMoveAI = false;
						while(validMoveAI == false) {
						//make a random selection between 8 moves
							int AIRandompPath = r.nextInt(8);
							
							if (AIRandompPath == 0) {
								//Valid spot #1 right top
								endWidth = rw + 2;
								endHeight = rh - 1;
							}else if(AIRandompPath == 1){
								//Valid spot #2 right down
								endWidth = rw + 2;
								endHeight = rh + 1;
							}else if(AIRandompPath == 2) {
								//Valid spot #3 left top
								endWidth = rw - 2;
								endHeight = rh - 1;
							}else if (AIRandompPath == 3) {
								//Valid spot #4 left down
								endWidth = rw - 2;
								endHeight = rh + 1;
							}else if(AIRandompPath == 4) {
								//Valid spot #5 top right
								endWidth = rw + 1;
								endHeight = rh - 2;
							}else if(AIRandompPath == 5) {
								//Valid spot #6 top left
								endWidth = rw - 1;
								endHeight = rh - 2;
							}else if(AIRandompPath == 6) {
								//Valid spot #7 down right
								endWidth = rw + 1;
								endHeight = rh + 2;
							}else {
								//Valid spot #8 down left
								endWidth = rw - 1;
								endHeight = rh + 2;
							}
							
							// Check if move is outside the board
							if (endWidth > width-1 || endWidth < 0 || endHeight > height-1 || endHeight < 0) {
								continue;
							}else { 
								// move n to an empty position 
								if(occupiedArray[endHeight][endWidth] == "| ") {
								occupiedArray[endHeight][endWidth] = "|n";
								occupiedArray[rh][rw] = "| ";
								startCharAI = (char) (rw+65);
								endCharAI = (char) (endWidth+65);
								System.out.println("Thus, AI made a free move from " + startCharAI + " " + (rh + 1) + " to " + endCharAI + " " + (endHeight+1));
								break label1;
								}
							}
						}//while validMoveAI

					}  else {
						continue;
					}
				} // while repeat until a position is found containing n
				contiueMoveAI = false;
				
				
			}
				
				
				

		} // method
		 	// ***********************************************
		
		
		
		
		
		
		
		
		public static String[][] aiCaptureN(int height,int width,int i, int j, int endSpotAI_h,int endSpotAI_w, String[][] occupiedArray) {
			//check if possible spot is not out of board
			if (endSpotAI_w <= width-1 && endSpotAI_w >= 0 && endSpotAI_h <= height-1 && endSpotAI_h >= 0) {
				//take N if N exists
				if(occupiedArray[endSpotAI_h][endSpotAI_w] == "|N") {
					occupiedArray[i][j] = "| ";
					occupiedArray[endSpotAI_h][endSpotAI_w] = "|n";
					captured_N++;					
				}
			}
			return occupiedArray;
		}
		
		
		public static boolean checkExistance(String[][] m, String s) {
			for (int i = 0; i < height; i++ ) {
				for (int j=0; j < width; j++) {
					if (m[i][j] == s) {
						return true;
					}
				}
			}
			return false;
		}
		
		
		//winning condition: when all pieces of one type is finished
		public static boolean checkIfKnightsExist(String[][] oc) {
		existN = checkExistance(oc, "|N");
		existn = checkExistance(oc, "|n");
		if (existN == false || existn == false) {
			endGame = 1; 
			if(captured_n < captured_N) {
				System.out.println("AI won by Total of " + captured_N + " captured pieces of N");
				System.out.println("Total n captured by Player: " + captured_n);
				System.out.println("End of Game");
				return false;
			}
			else if(captured_n > captured_N) {
				System.out.println("Player won by Total of " + captured_n + " captured pieces of n");
				System.out.println("Total N captured by AI: " + captured_N);
				System.out.println("End of Game");
				return false;
			}
			else {
				System.out.println("Both sides are equal with " + captured_N + " captured pieces.");
				System.out.println("End of Game");
				return false;
			}
			
		} else {
			endGame = 0;
		}
		return true;
		}
		
		
		
		//get board info
		public static int[] getBoardinfo() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter [width] [height] [No Knights]: ");
		 width = in.nextInt();
		 height= in.nextInt();
		 nOfKnightsN = in.nextInt();
		 boardInfo[0] = width;
		 boardInfo[1] = height;
		 boardInfo[2] = nOfKnightsN;
		 System.out.println("Generating chess game of size " + width + " by " 
					+ height + " with " + nOfKnightsN + " knights\n");
		 return boardInfo;
		}
		
		
		// create 8 valid spots to move
		public static int[] createSpots(int h, int w) {
			
			int[] spots = new int[16];
			//Valid spot #1 right top
			spots[0] = w + 2;
			spots[1] = h - 1;
			
			//Valid spot #2 right down
			spots[2] = w + 2;
			spots[3] = h + 1;
			
			//Valid spot #3 left top
			spots[4] = w - 2;
			spots[5] = h - 1;
			
			//Valid spot #4 left down
			spots[6] = w - 2;
			spots[7] = h + 1;
			
			//Valid spot #5 top right
			spots[8] = w + 1;
			spots[9] = h - 2;
			
			//Valid spot #6 top left
			spots[10] = w - 1;
			spots[11] = h - 2;
			
			//Valid spot #7 down right
			spots[12] = w + 1;
			spots[13] = h + 2;
			
			//Valid spot #8 down left
			spots[14] = w - 1;
			spots[15] = h + 2;
			
			return spots;
		}
		
}

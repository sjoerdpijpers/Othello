import java.util.ArrayList;

/**
* Our AI Algorithm implementing alpha-beta pruning */
public class OthelloAI16 implements IOthelloAI {
	private int depth = 5;

	/**
     * Returns the best move to make for the given game state and prints out the time for calculating the best move.
     * @param s The current state of the game in which it is the AI's turn.
     * @return the position where the AI wants to put its token.
     * Returns (-1, -1) if no moves are possible.
     */
    public Position decideMove(GameState s) { // Minimax-Decision method
		Timer t = new Timer();
		ArrayList<Position> moves = s.legalMoves();
		Position bestP = new Position(-1,-1);
		if (!moves.isEmpty()){
            int bestV = Integer.MIN_VALUE; // -infinity
            int alpha = Integer.MIN_VALUE; // -infinity
            int beta = Integer.MAX_VALUE; // +infinity			
			for (Position p : moves){
				GameState newS = new GameState(s.getBoard(), 1) ;
				newS.insertToken(p);
				int v = maxValue(newS, alpha, beta, depth);
				if (v >= bestV) { // Keeping track of the best position to insert a token, corresponding with the maximum value of v
					bestV = v;
					bestP = p;
				}
			}
		}
		System.out.printf("Time AI_16: %6.2f s%n", t.check());
		return bestP;
    }

	public int maxValue(GameState s, int alpha, int beta, int depth){
		if (s.isFinished() || depth == 0) {
			return (int)coinParity(s) + (int)mobility(s) + (int)capturedCorners(s);
			// return s.countTokens()[1];
		}		
		int v = Integer.MIN_VALUE; // -Infinity
		ArrayList<Position> moves = s.legalMoves();
		if (!moves.isEmpty()){
			for (Position p : moves){
				GameState newS = move(p, s);
				v = Math.max(v, minValue(newS, alpha, beta, depth - 1));
				if (v >= beta) return v; // beta-cut
				alpha = Math.max(alpha, v);
			} 
			return v;	
		} else { // if there are no available moves, then change player and go to MIN-node
			s.changePlayer();
			return minValue(s, alpha, beta, depth);
		}
	}

	public int minValue(GameState s, int alpha, int beta, int depth){
		if (s.isFinished() || depth == 0) { 
			return (int)coinParity(s) + (int)mobility(s) + (int)capturedCorners(s);
			// return s.countTokens()[1];
		}			
		int v = Integer.MAX_VALUE; // +Infinity
		ArrayList<Position> moves = s.legalMoves();
		if (!moves.isEmpty()){
			for (Position p : moves){
				GameState newS = move(p, s);
				v = Math.min(v, maxValue(newS, alpha, beta, depth - 1));
				if (v <= alpha) return v; // alpha-cut
				beta = Math.min(beta, v);
			}
			return 	v;
		} else { // if there are no available moves, then change player and go to MAX-node
			s.changePlayer();
			return maxValue(s, alpha, beta, depth);
		}
	}

	/**
     * Gives the GameState in which a move has been made by inserting a token at position p
     * @param s The state of the game before the move is made
     * @param p possible position within the legal moves
     * @return The duplicated state when the move is simulated
     */
	public GameState move(Position p, GameState s) {
        GameState newS = new GameState(s.getBoard(), s.getPlayerInTurn());
        newS.insertToken(p);
        return newS;
    }

	public double coinParity(GameState s){
		double tokensBlack = s.countTokens()[0];
		double tokensWhite = s.countTokens()[1];
		return 100 * (tokensBlack - tokensWhite) / (tokensBlack + tokensWhite);
	}

	public double mobility(GameState s){
		double movesBlack = 0;
		double movesWhite = 0;
		if(s.getPlayerInTurn() == 1){
			movesBlack = s.legalMoves().size();
			s.changePlayer();
			movesWhite = s.legalMoves().size();
		} else{
			movesWhite = s.legalMoves().size();
			s.changePlayer();
			movesBlack = s.legalMoves().size();
		}
		if(movesWhite+movesBlack != 0) {
			return 100*(movesWhite - movesBlack)/(movesWhite + movesBlack);
		}else {
			return 0;
		}
	}

	public double capturedCorners(GameState s){
		double cornersWhite = 0;
		double cornersBlack = 0;
		int[][] board = s.getBoard();
		if(board[0][0] == 2) cornersWhite++;
		if(board[0][0] == 1) cornersBlack--;
		if(board[0][board.length-1] == 2) cornersWhite++;
		if(board[0][board.length-1] == 1) cornersBlack--;
		if(board[board.length-1][0] == 2) cornersWhite++;
		if(board[board.length-1][0] == 1) cornersBlack--;
		if(board[board.length-1][board.length-1] == 2) cornersWhite++;
		if(board[board.length-1][board.length-1] == 1) cornersBlack--;
		if(cornersBlack+cornersWhite != 0) {
			return 100*(cornersWhite - cornersBlack) / (cornersWhite + cornersBlack);
		}else { 
			return 0;
		}
	}

}
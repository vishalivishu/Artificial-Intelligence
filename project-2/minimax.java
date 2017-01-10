/*import java.io.IOException;
import java.util.ArrayList;

class orderPoints1
{
	int row;
	int col;
	int type = -1;
	orderPoints1(int x, int y, int type)
	{
		this.row = x;
		this.col = y;
		this.type = type;
	}
}
class minimax {
	
	int No = -1;
	char playturn = '\0';
	char opponent_turn = '\0';
	
	public ArrayList<orderPoints1> nextPossibleMovesmini(char[][] BoardArray, char player)
	{
		int N = BoardArray.length;
		char opponent;
		opponent = (player == 'X')?'O':'X';
		checkMmoves m = new checkMmoves();
		ArrayList<orderPoints1> stake_type = new ArrayList<orderPoints1>();
		ArrayList<orderPoints1> raid_type = new ArrayList<orderPoints1>();
		ArrayList<rowColList> row_col = new ArrayList<rowColList>();
		
		
		for(int i = 0;i < N; i++)
    	{
    		for(int j = 0;j < N; j++)
    		{
    			if(BoardArray[i][j]=='.') 
    			{
    				stake_type.add(new orderPoints1(i,j,0));
    			}
    		}
    	}
		
		for(int i = 0;i < N; i++)
    	{
    		for(int j = 0;j < N; j++)
    		{
    			if(BoardArray[i][j]=='.') 
    			{
    				BoardArray[i][j] = player;
    				if(m.isMoveRaid(BoardArray,i,j,player,N)) 
    				{
    				row_col = m.raidedBoard(BoardArray, i, j, player, opponent, N);

    				if(!row_col.isEmpty())
    					{
    					raid_type.add(new orderPoints1(i,j,1));
    					}
    				}
       			BoardArray[i][j]='.';
    			}
    		}
    	}
		
    	stake_type.addAll(raid_type);
    	return stake_type;
	}
	
	/**********************************************************
	#Minimax algorithm
	#**********************************************************/
	/*public ArrayList<Integer> minimax_algo(char BoardArray[][], int cellArray[][], int depth, int max_depth, boolean maximizer)
	{
	
		checkMmoves m = new checkMmoves();
    	int selectRow = -1;
    	int selectCol = -1;
    	int maximum = Integer.MIN_VALUE;
    	int minimum = Integer.MAX_VALUE;
       	int score = 0;
       	int flagForRaid = -1;
    	int N = BoardArray.length;
    	
    	ArrayList<rowColList> row_col = new ArrayList<rowColList>();
    	ArrayList<orderPoints1> stakeraid;
    	
        	if(m.isGameOver(BoardArray, N) || depth == max_depth) {
    		ArrayList <Integer> arr = new ArrayList<Integer>();

			arr.add(m.evaluateGameScore(N, BoardArray, cellArray, playturn));
			arr.add(selectRow);
			arr.add(selectCol);
			arr.add(flagForRaid);
			return arr;
    	}
        	
        stakeraid = nextPossibleMovesmini(BoardArray, playturn);
    	
		for(orderPoints1 order : stakeraid)
		  {
				int r = order.row;
				int c = order.col;
				int raid_move = order.type;
				if(BoardArray[r][c]=='.')
				{
				if(maximizer)
				{
			    	BoardArray[r][c]=playturn;
					if((raid_move == 1) && (m.isMoveRaid(BoardArray,r,c,playturn,N))) 
					{
						row_col = m.raidedBoard(BoardArray, r, c, playturn, opponent_turn, N);
						if (!row_col.isEmpty()) {
							for (rowColList rc : row_col)
							{
								BoardArray[rc.row][rc.col] = playturn;
							}
						}
					}

					ArrayList<Integer> list = minimax_algo(BoardArray, cellArray, depth+1, max_depth, !maximizer);
					score=list.get(0);
					
					if(score>maximum)
					{
						maximum=score;
						selectRow=r;
						selectCol=c;
						flagForRaid = raid_move;
					}

					if(raid_move == 1 && !row_col.isEmpty())
					{
						for(rowColList rc: row_col)
						{
							BoardArray[rc.row][rc.col]=opponent_turn;
						}
					}
					BoardArray[r][c]='.';
				}	
				else
				{
			 		BoardArray[r][c]=opponent_turn;
					if((raid_move == 1) && (m.isMoveRaid(BoardArray,r,c,opponent_turn,N)))
					{
						row_col = m.raidedBoard(BoardArray, r, c, opponent_turn, playturn, N);
						if (!row_col.isEmpty()) {
							for (rowColList rc : row_col)
							{
								BoardArray[rc.row][rc.col] = opponent_turn;
							}
						}
					}
					
					ArrayList<Integer> list = minimax_algo(BoardArray, cellArray, depth+1, max_depth, !maximizer);
					score=list.get(0);
					
					if(score < minimum)
					{
						minimum=score;
						selectRow=r;
						selectCol=c;
						flagForRaid = raid_move;
					}

					if(raid_move == 1 && !row_col.isEmpty())
					{
						for(rowColList rc: row_col)
						{
							BoardArray[rc.row][rc.col]= playturn;
						}
					}
					BoardArray[r][c]='.';
				}
			}
	}
		
	ArrayList<Integer> arr = new ArrayList<Integer>();
	int val = maximizer?maximum:minimum;
	arr.add(val);
	arr.add(selectRow);
	arr.add(selectCol);
	arr.add(flagForRaid);
	return arr;	
}		

	/**********************************************************
	# Choose best move in minimax
	#
	 * @throws IOException **********************************************************/
/*	public void minmax_Decision(char BoardArray[][], int cellArray[][], int max_depth, int N, char player, char opponent) throws IOException
	{

		checkMmoves m = new checkMmoves();
		homework h = new homework();
		
		ArrayList<rowColList> row_col = new ArrayList<rowColList>();
		ArrayList <Integer> result = new ArrayList<Integer>(3);
		No = N;
		playturn = player;
		opponent_turn = opponent;
		
		boolean isRaid = false;
		
		result = minimax_algo(BoardArray, cellArray,0, max_depth, true);
		System.out.println("val "+result.get(0));
		int fRow = result.get(1);
		int fCol = result.get(2);
		int flagForRaid = result.get(3);
		System.out.println(fRow+" "+ fCol+" "+flagForRaid);
		BoardArray[fRow][fCol] = player;
		if ((flagForRaid == 1) && (m.isMoveRaid(BoardArray, fRow, fCol, player, N))) {
			row_col = m.raidedBoard(BoardArray, fRow, fCol, player, opponent, N);
			if (!row_col.isEmpty()) {
				isRaid = true;
				for (rowColList rc : row_col)
				{
					BoardArray[rc.row][rc.col] = player;
				}
			}
		}
		h.writeOutput(fRow, fCol, BoardArray, isRaid);
}
}
*/
import java.io.IOException;
import java.util.ArrayList;

class orderPoints1
{
	int row;
	int col;
	int type = -1;
	orderPoints1(int x, int y, int type)
	{
		this.row = x;
		this.col = y;
		this.type = type;
	}
}
class minimax {
	
	int No = -1;
	char playturn = '\0';
	char opponent_turn = '\0';
	
	public ArrayList<orderPoints1> nextPossibleMovesmini(char[][] BoardArray, char player)
	{
		int N = BoardArray.length;
		char opponent;
		opponent = (player == 'X')?'O':'X';
		checkMmoves m = new checkMmoves();
		ArrayList<orderPoints1> stake_type = new ArrayList<orderPoints1>();
		ArrayList<orderPoints1> raid_type = new ArrayList<orderPoints1>();
		ArrayList<rowColList> row_col = new ArrayList<rowColList>();
		
		
		for(int i = 0;i < N; i++)
    	{
    		for(int j = 0;j < N; j++)
    		{
    			if(BoardArray[i][j]=='.') 
    			{
    				stake_type.add(new orderPoints1(i,j,0));
    			}
    		}
    	}
		
		for(int i = 0;i < N; i++)
    	{
    		for(int j = 0;j < N; j++)
    		{
    			if(BoardArray[i][j]=='.') 
    			{
    				BoardArray[i][j] = player;
    				if(m.isMoveRaid(BoardArray,i,j,player,N)) 
    				{
    				row_col = m.raidedBoard(BoardArray, i, j, player, opponent, N);

    				if(!row_col.isEmpty())
    					{
    					raid_type.add(new orderPoints1(i,j,1));
    					}
    				}
       			BoardArray[i][j]='.';
    			}
    		}
    	}
		
    	stake_type.addAll(raid_type);
    	return stake_type;
	}
	
	/**********************************************************
	#Minimax algorithm
	#**********************************************************/
	public ArrayList<Integer> minimax_algo(char BoardArray[][], int cellArray[][], int depth, int max_depth, boolean maximizer)
	{
	
		checkMmoves m = new checkMmoves();
    	int selectRow = -1;
    	int selectCol = -1;
    	int maximum = Integer.MIN_VALUE;
    	int minimum = Integer.MAX_VALUE;
       	int score = 0;
       	int flagForRaid = -1;
    	int N = BoardArray.length;
    	
    	ArrayList<rowColList> row_col = new ArrayList<rowColList>();
    	ArrayList<orderPoints1> stakeraid;
    	
        	if(m.isGameOver(BoardArray, N) || depth == max_depth) {
    		ArrayList <Integer> arr = new ArrayList<Integer>();

			arr.add(m.evaluateGameScore(N, BoardArray, cellArray, playturn));
			arr.add(selectRow);
			arr.add(selectCol);
			arr.add(flagForRaid);
			return arr;
    	}
        	
        stakeraid = nextPossibleMovesmini(BoardArray, playturn);
    	
		for(orderPoints1 order : stakeraid)
		  {
				int r = order.row;
				int c = order.col;
				int raid_move = order.type;
				if(BoardArray[r][c]=='.')
				{
				if(maximizer)
				{
			    	BoardArray[r][c]=playturn;
					if((raid_move == 1) && (m.isMoveRaid(BoardArray,r,c,playturn,N))) 
					{
						row_col = m.raidedBoard(BoardArray, r, c, playturn, opponent_turn, N);
						if (!row_col.isEmpty()) {
							for (rowColList rc : row_col)
							{
								BoardArray[rc.row][rc.col] = playturn;
							}
						}
					}

					ArrayList<Integer> list = minimax_algo(BoardArray, cellArray, depth+1, max_depth, !maximizer);
					score=list.get(0);
					
					if(score>maximum)
					{
						maximum=score;
						selectRow=r;
						selectCol=c;
						flagForRaid = raid_move;
					}

					if(raid_move == 1 && !row_col.isEmpty())
					{
						for(rowColList rc: row_col)
						{
							BoardArray[rc.row][rc.col]=opponent_turn;
						}
					}
					BoardArray[r][c]='.';
				}	
				else
				{
			 		BoardArray[r][c]=opponent_turn;
					if((raid_move == 1) && (m.isMoveRaid(BoardArray,r,c,opponent_turn,N)))
					{
						row_col = m.raidedBoard(BoardArray, r, c, opponent_turn, playturn, N);
						if (!row_col.isEmpty()) {
							for (rowColList rc : row_col)
							{
								BoardArray[rc.row][rc.col] = opponent_turn;
							}
						}
					}
					
					ArrayList<Integer> list = minimax_algo(BoardArray, cellArray, depth+1, max_depth, !maximizer);
					score=list.get(0);
					
					if(score < minimum)
					{
						minimum=score;
						selectRow=r;
						selectCol=c;
						flagForRaid = raid_move;
					}

					if(raid_move == 1 && !row_col.isEmpty())
					{
						for(rowColList rc: row_col)
						{
							BoardArray[rc.row][rc.col]= playturn;
						}
					}
					BoardArray[r][c]='.';
				}
			}
	}
		
	ArrayList<Integer> arr = new ArrayList<Integer>();
	int val = maximizer?maximum:minimum;
	arr.add(val);
	arr.add(selectRow);
	arr.add(selectCol);
	arr.add(flagForRaid);
	return arr;	
}		

	/**********************************************************
	# Choose best move in minimax
	#
	 * @throws IOException **********************************************************/
	public void minmax_Decision(char BoardArray[][], int cellArray[][], int max_depth, int N, char player, char opponent) throws IOException
	{

		checkMmoves m = new checkMmoves();
		homework h = new homework();
		
		ArrayList<rowColList> row_col = new ArrayList<rowColList>();
		ArrayList <Integer> result = new ArrayList<Integer>(3);
		No = N;
		playturn = player;
		opponent_turn = opponent;
		
		boolean isRaid = false;
		
		result = minimax_algo(BoardArray, cellArray,0, max_depth, true);
		//System.out.println("val "+result.get(0));
		int fRow = result.get(1);
		int fCol = result.get(2);
		int flagForRaid = result.get(3);
		//System.out.println(fRow+" "+ fCol+" "+flagForRaid);
		BoardArray[fRow][fCol] = player;
		if ((flagForRaid == 1) && (m.isMoveRaid(BoardArray, fRow, fCol, player, N))) {
			row_col = m.raidedBoard(BoardArray, fRow, fCol, player, opponent, N);
			if (!row_col.isEmpty()) {
				isRaid = true;
				for (rowColList rc : row_col)
				{
					BoardArray[rc.row][rc.col] = player;
				}
			}
		}
		h.writeOutput(fRow, fCol, BoardArray, isRaid);
}
}


import java.io.IOException;
import java.util.ArrayList;

class orderPoints
{
	int row;
	int col;
	int type = -1;
	orderPoints(int x, int y, int type)
	{
		this.row = x;
		this.col = y;
		this.type = type;
	}
}
public class alpha_beta {
	
	int maxDepth = -1;
	
	int getMax(int a, int b)
	{
		return (a>b)?a:b;
	}
	
	int getMin(int a, int b)
	{
		return (a<b)?a:b;
	}	
	
	public ArrayList<orderPoints> nextPossibleMoves(char[][] BoardArray, char player)
	{
		int N = BoardArray.length;
		char opponent;
		opponent = (player == 'X')?'O':'X';
		checkMmoves m = new checkMmoves();
		ArrayList<orderPoints> stake_type = new ArrayList<orderPoints>();
		ArrayList<orderPoints> raid_type = new ArrayList<orderPoints>();
		ArrayList<rowColList> row_col = new ArrayList<rowColList>();
		
		
		for(int i = 0;i < N; i++)
    	{
    		for(int j = 0;j < N; j++)
    		{
    			if(BoardArray[i][j]=='.') 
    			{
    				stake_type.add(new orderPoints(i,j,0));
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
    					raid_type.add(new orderPoints(i,j,1));
    					}
    				}
       			BoardArray[i][j]='.';
    			}
    		}
    	}
		
    	stake_type.addAll(raid_type);
    	return stake_type;
	}
	
	public ArrayList<Integer> alphaBeta_algo(char BoardArray[][], int cellArray[][], int depth, char player, char opponent,boolean maximizer, int alpha, int beta)
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
    	ArrayList<orderPoints> stakeraid;
    	
        	if(m.isGameOver(BoardArray, N) || depth == maxDepth) {
    		ArrayList <Integer> arr = new ArrayList<Integer>();

			arr.add(m.evaluateGameScore(N, BoardArray, cellArray, player));
			arr.add(selectRow);
			arr.add(selectCol);
			arr.add(flagForRaid);
			return arr;
    	}
        	
        stakeraid = nextPossibleMoves(BoardArray, player);
    	
		for(orderPoints order : stakeraid)
		  {
				int r = order.row;
				int c = order.col;
				int raid_move = order.type;
				if(BoardArray[r][c]=='.')
				{
				if(maximizer)
				{
			    	BoardArray[r][c]=player;
					if((raid_move == 1) && (m.isMoveRaid(BoardArray,r,c,player,N))) 
					{
						row_col = m.raidedBoard(BoardArray, r, c, player, opponent, N);
						if (!row_col.isEmpty()) {
							for (rowColList rc : row_col)
							{
								BoardArray[rc.row][rc.col] = player;
							}
						}
					}

					ArrayList<Integer> list = alphaBeta_algo(BoardArray, cellArray, depth+1, player, opponent, !maximizer, alpha, beta);
					score=list.get(0);
					maximum = getMax(score, maximum);
					
					if(score>alpha)
					{
						alpha=score;
						selectRow=r;
						selectCol=c;
						flagForRaid = raid_move;
					}

					if(raid_move == 1 && !row_col.isEmpty())
					{
						for(rowColList rc: row_col)
						{
							BoardArray[rc.row][rc.col]=opponent;
						}
					}
					BoardArray[r][c]='.';
				}	
				else
				{
			 		BoardArray[r][c]=opponent;
					if((raid_move == 1) && (m.isMoveRaid(BoardArray,r,c,opponent,N)))
					{
						row_col = m.raidedBoard(BoardArray, r, c, opponent, player, N);
						if (!row_col.isEmpty()) {
							for (rowColList rc : row_col)
							{
								BoardArray[rc.row][rc.col] = opponent;
							}
						}
					}
					
					ArrayList<Integer> list = alphaBeta_algo(BoardArray, cellArray, depth+1, player, opponent, !maximizer, alpha, beta);
					score=list.get(0);
					minimum=getMin(score, minimum);
					
					if(score<beta)
					{
						beta=score;
						selectRow=r;
						selectCol=c;
						flagForRaid = raid_move;
					}
					//System.out.println("min score alp bet"+score+" "+alpha+" "+beta);
					if(raid_move == 1 && !row_col.isEmpty())
					{
						for(rowColList rc: row_col)
						{
							BoardArray[rc.row][rc.col]= player;
						}
					}
					BoardArray[r][c]='.';
				}
			}
				if( beta <= alpha)
					break;
	}
		
	ArrayList<Integer> arr = new ArrayList<Integer>();
	int val = maximizer?alpha:beta;
	arr.add(val);
	arr.add(selectRow);
	arr.add(selectCol);
	arr.add(flagForRaid);
	return arr;	
	
	}
	public void alphaBeta_Decision(char BoardArray[][], int cellArray[][], int max_depth, int N, char player, char opponent) throws IOException
	{
		alpha_beta ab = new alpha_beta();
		homework h = new homework();
		checkMmoves m = new checkMmoves();
		ab.maxDepth = max_depth;
		boolean isRaid = false;
		
		ArrayList <Integer> result = new ArrayList<Integer>(3);
		ArrayList <rowColList> row_col = new ArrayList <rowColList>();
		
		result = ab.alphaBeta_algo(BoardArray, cellArray,0, player, opponent, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

		int fRow = result.get(1);
		int fCol = result.get(2);
		int flagForRaid = result.get(3);
		System.out.println(fRow+" "+ fCol+" "+flagForRaid+" "+result.get(0));
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

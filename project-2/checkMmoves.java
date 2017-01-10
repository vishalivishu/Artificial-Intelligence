import java.util.ArrayList;

class rowColList{
	int row = -1;
	int col = -1;
}

public class checkMmoves {
	public int evaluateGameScore(int N, char boardArray[][], int cellArray[][], char player) {
		int gameScore = 0;
		int i, j;
		char opponent;
		opponent = (player == 'X')?'O':'X';
		
		for(i = 0;i < N; i++)
		{
			for(j = 0;j < N;j++)
			{
				if(player == boardArray[i][j])
					gameScore += (cellArray[i][j]);
				else if(opponent == boardArray[i][j])
					gameScore -= cellArray[i][j];
		
			}
		}

		return gameScore;
	}
	public boolean isMoveRaid(char BoardArray[][],int i,int j,char player, int N) {
		
		if (i != N-1 && BoardArray[i+1][j] == player)
			return true;
		else if (i!= 0 && BoardArray[i-1][j] == player)
			return true;
		else if (j!= N-1 && BoardArray[i][j+1] == player)
			return true;
		else if (j!=0 && BoardArray[i][j-1] == player)
			return true;
		
		return false;
	}
	/**********************************************************
	Raided BoardArray
	**********************************************************/
	public ArrayList<rowColList> raidedBoard(char BoardArray[][], int i, int j, char player, char opponent, int N) 
	{
		ArrayList<rowColList> row_col = new ArrayList<rowColList>();
		if (i != N-1 && BoardArray[i+1][j] == opponent) {
			rowColList r1 = new rowColList();
			r1.row = i+1;
			r1.col = j;
			row_col.add(r1);
		}
		
		if (i!= 0 && BoardArray[i-1][j] == opponent) {
			rowColList r2 = new rowColList();
			r2.row = i-1;
			r2.col = j;
			row_col.add(r2);
		}
		
		if (j!= N-1 && BoardArray[i][j+1] == opponent) {
			rowColList r3 = new rowColList();
			r3.row = i;
			r3.col = j+1;
			row_col.add(r3);

		}
		
		if (j!=0 && BoardArray[i][j-1] == opponent) {
			rowColList r4 = new rowColList();
			r4.row = i;
			r4.col = j-1;
			row_col.add(r4);

		}	
		return row_col;
	}
	/**********************************************************
	#checking the game termination point
	#**********************************************************/
	public boolean isGameOver(char BoardArray[][], int N) {
		int i,j;
		for (i = 0;i < N; i++) {
			for (j = 0;j < N; j++){
				if(BoardArray[i][j] == '.')
					return false;
		  }
	    }
		return true;
	 }
}

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class homework {
	
	int N, depth;
	int cellArray[][];
	char boardArray[][];
	char player = '\0', opponent = '\0';
	
  public void time()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
	}
	public void get_input() throws IOException
	{
		minimax o = new minimax();
		alpha_beta ab = new alpha_beta();
		
		int iter = 0;
		File fin = new File("C:\\Users\\vishali somaskanthan\\workspace\\assignment2-AI\\src\\input.txt");
		String mode = null;
		
		BufferedReader br = new BufferedReader(new FileReader(fin));
		String inp_line = null;
		
		N = Integer.parseInt(br.readLine().trim());
		mode = br.readLine().trim();
		player = br.readLine().charAt(0);
		
		cellArray= new int[N][N];
		boardArray = new char[N][N];
		if(player == 'X')
		{
			opponent = 'O';
		}
		else
		{
			opponent = 'X';
		}
		
		depth = Integer.parseInt(br.readLine().trim());
		
		while (iter < N) {
			inp_line = br.readLine().trim();
			String obj[] = inp_line.split(" ");
			for(int j = 0;j < N; j++) {
				cellArray[iter][j] = Integer.parseInt(obj[j].trim());
			}
			iter+=1;
		}
		
		iter =  0;
		while (iter < N) {
			inp_line = br.readLine().trim();
			char obj[] = inp_line.toCharArray();
			for(int j = 0;j < N; j++) {
				boardArray[iter][j] = obj[j];
			}
			
			iter+=1;
		}
		br.close();
		
		if (mode.equals("MINIMAX")) {
		o.minmax_Decision(boardArray,cellArray,depth,N,player,opponent);
		}
		else {
			ab.alphaBeta_Decision(boardArray, cellArray, depth, N, player, opponent);
		}
	}
	/**********************************************************
	#output processing
	@throws IOException **********************************************************/
	public void writeOutput(int selectRow, int selectCol, char[][] BoardArray, boolean isRaid) throws IOException
	{
		File f = new File("C:\\Users\\vishali somaskanthan\\workspace\\assignment2-AI\\src\\output.txt");
		FileOutputStream fout = new FileOutputStream(f);
		String pos="";
		char ascii = (char)(65+selectCol);
		pos+=String.valueOf(ascii);
		pos+=String.valueOf(selectRow+1)+" ";
		byte[] posBytes = pos.getBytes();
		fout.write(posBytes);
		if (isRaid)
			fout.write("Raid".getBytes());
		else
			fout.write("Stake".getBytes());
		
		for (int i = 0;i<BoardArray.length; i++) {
			fout.write("\n".getBytes());			
			for (int j = 0; j<BoardArray.length; j++) {
				String board = BoardArray[i][j]+"";
				fout.write(board.getBytes());
			}
		}
		fout.close();
	}
	 public static void main(String[] args) throws IOException {

		homework h = new homework();
		h.time();
		h.get_input();
		h.time();

	  }
}


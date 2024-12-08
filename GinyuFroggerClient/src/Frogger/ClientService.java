package Frogger;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ClientService implements Runnable {
	private Frog Frog;
	private JLabel FrogLabel;
	private Blast1 Blast1Row1[ ] = new Blast1[4];
	private Blast1 Blast1Row2[ ] = new Blast1[4];
	private Blast2 Blast2Row1[ ] = new Blast2[4];
	private Blast2 Blast2Row2[ ] = new Blast2[4];
	private Log Log1Row1[ ] = new Log[4];
	private Log Log1Row2[ ] = new Log[4];
	private Log Log1Row3[ ] = new Log[4];
	private Log2 Log2Row1[ ] = new Log2[4];
	private Log2 Log2Row2[ ] = new Log2[4];
	
	private JLabel Blast1Labels[] = new JLabel[8];
	private JLabel Blast2Labels[] = new JLabel[8];
	private JLabel Log1Labels[] = new JLabel[12];
	private JLabel Log2Labels[] = new JLabel[8];
	
	private JLabel highscore;
	private JButton start;

	private Socket s;
	private Scanner in;

	public ClientService (Socket aSocket, Frog frog, JLabel frogLabel, Blast1[] blast1Row1, Blast1[] blast1Row2, Blast2[] blast2Row1, Blast2[] blast2Row2, Log[] log1Row1, Log[] log1Row2, Log[] log1Row3, Log2[] log2Row1, Log2[] log2Row2, JButton startButton, JLabel[] blast1Labels, JLabel[] blast2Labels, JLabel[] log1Labels, JLabel[] log2Labels, JLabel Highscore) {
		this.s = aSocket;
		this.Frog = frog;
		this.FrogLabel = frogLabel;
		
		for (int i = 0; i<4; i++) {
			this.Blast1Row1[i] = blast1Row1[i];
			this.Blast1Row2[i] = blast1Row2[i];
			this.Blast2Row1[i] = blast2Row1[i];
			this.Blast2Row2[i] = blast2Row2[i];
			
			this.Log1Row1[i] = log1Row1[i];
			this.Log1Row2[i] = log1Row2[i];
			this.Log1Row3[i] = log1Row3[i];
			this.Log2Row1[i] = log2Row1[i];
			this.Log2Row2[i] = log2Row2[i];
		}
		
		for (int i = 0; i < 8; i++) {
			this.Blast1Labels[i] = blast1Labels[i];
			this.Blast2Labels[i] = blast2Labels[i];
			this.Log2Labels[i] = log2Labels[i];
		}
		
		for (int i = 0; i < 12; i++) {
			this.Log1Labels[i] = log1Labels[i];
		}
		
		this.start = startButton;
		this.highscore = Highscore;
	}
	
	public void run() {
		
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	//processing the requests
	public void processRequest () throws IOException {
		//if next request is empty then return
		while(true) {
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}
	
	public void executeCommand(String command) throws IOException{
		if ( command.equals("FROG")) {
			
			int playerNo = in.nextInt();
			
			String playerAction = in.next();
			
			Frog.setX(in.nextInt());
			
			Frog.setY(in.nextInt());
			
			FrogLabel.setLocation(Frog.getX(), Frog.getY());
			
			System.out.println("Player "+playerNo+" "+playerAction + " "+Frog.getX()+", "+Frog.getY());
		}else if (command.equals("SETBLAST1ROW1")) {
			int position = in.nextInt();
			
			Blast1Row1[position].setX(in.nextInt());
			Blast1Row1[position].setY(in.nextInt());
			
			Blast1Labels[position].setLocation(Blast1Row1[position].getX(), Blast1Row1[position].getY());
			
		} else if (command.equals("SETBLAST1ROW2")) {
			int position = in.nextInt();
			
			Blast1Row2[position].setX(in.nextInt());
			Blast1Row2[position].setY(in.nextInt());
			
			Blast1Labels[position + 4].setLocation(Blast1Row2[position].getX(), Blast1Row2[position].getY());
		} else if (command.equals("SETBLAST2ROW1")) {
			int position = in.nextInt();
			
			Blast2Row1[position].setX(in.nextInt());
			Blast2Row1[position].setY(in.nextInt());
			
			Blast2Labels[position].setLocation(Blast2Row1[position].getX(), Blast2Row1[position].getY());
		} else if (command.equals("SETBLAST2ROW2")) {
			int position = in.nextInt();
			
			Blast2Row2[position].setX(in.nextInt());
			Blast2Row2[position].setY(in.nextInt());
			
			Blast2Labels[position + 4].setLocation(Blast2Row2[position].getX(), Blast2Row2[position].getY());
		}
	}
}

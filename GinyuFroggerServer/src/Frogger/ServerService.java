package Frogger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ServerService implements Runnable {
	final int CLIENT_PORT = 5656;
	
	private Frog frog;
	private Blast1 Blast1Row1[ ] = new Blast1[4];
	private Blast1 Blast1Row2[ ] = new Blast1[4];
	private Blast2 Blast2Row1[ ] = new Blast2[4];
	private Blast2 Blast2Row2[ ] = new Blast2[4];
	private Log Log1Row1[ ] = new Log[4];
	private Log Log1Row2[ ] = new Log[4];
	private Log Log1Row3[ ] = new Log[4];
	private Log2 Log2Row1[ ] = new Log2[4];
	private Log2 Log2Row2[ ] = new Log2[4];

	private Socket s;
	private Scanner in;

	public ServerService (Socket aSocket, Frog frog, Blast1[] blast1Row1, Blast1[] blast1Row2, Blast2[] blast2Row1, Blast2[] blast2Row2, Log[] log1Row1, Log[] log1Row2, Log[] log1Row3, Log2[] log2Row1, Log2[] log2Row2) {
		this.s = aSocket;
		this.frog = frog;
		
		for (int i = 0; i < 4; i++) {
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
		int y = frog.getY();
		int x = frog.getX();
		if (command.equals("MOVEFROG")) {
			int playerNo = in.nextInt();
			
			String direction = in.next();
			
			if (direction.equals("UP")) {
				y -= GameProperties.CHARACTER_STEP;

			}else if (direction.equals("DOWN")) {
				y += GameProperties.CHARACTER_STEP;

			}else if (direction.equals("LEFT")) {
				x -= GameProperties.CHARACTER_STEP;

			}else if (direction.equals("RIGHT")) {
				x += GameProperties.CHARACTER_STEP;

			}
			
			frog.setY(y);
			frog.setX(x);
			
			Socket s2 = new Socket("localhost", CLIENT_PORT);
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);
			String commandOut = "FROG "+playerNo+" POSTION "+x+" "+y+"\n";
			System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();
				
			s2.close();
		} else if (command.equals("GETBLAST")) {
			int blastx=0;
			int blasty=0;
			Socket s5 = new Socket("localhost", CLIENT_PORT);
			
			for (int i = 0; i < 4; i++) {
				blastx = Blast1Row1[i].getX();
				blasty = Blast1Row1[i].getY();
				
				//Initialize data stream to send data out
				OutputStream outstream = s5.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				String commandOut = "SETBLAST1ROW1 "+i+" "+blastx+" "+blasty+"\n";
				System.out.println("Sending: " + commandOut);
				out.println(commandOut);
				out.flush();
			}
			
			for (int i = 0; i < 4; i++) {
				blastx = Blast1Row2[i].getX();
				blasty = Blast1Row2[i].getY();
				
				//Initialize data stream to send data out
				OutputStream outstream = s5.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				String commandOut = "SETBLAST1ROW2 "+i+" "+blastx+" "+blasty+"\n";
				System.out.println("Sending: " + commandOut);
				out.println(commandOut);
				out.flush();
			}
			
			for (int i = 0; i < 4; i++) {
				blastx = Blast2Row1[i].getX();
				blasty = Blast2Row1[i].getY();
				
				//Initialize data stream to send data out
				OutputStream outstream = s5.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				String commandOut = "SETBLAST2ROW1 "+i+" "+blastx+" "+blasty+"\n";
				System.out.println("Sending: " + commandOut);
				out.println(commandOut);
				out.flush();
			}
			
			for (int i = 0; i < 4; i++) {
				blastx = Blast2Row2[i].getX();
				blasty = Blast2Row2[i].getY();
				
				//Initialize data stream to send data out
				OutputStream outstream = s5.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				String commandOut = "SETBLAST2ROW2 "+i+" "+blastx+" "+blasty+"\n";
				System.out.println("Sending: " + commandOut);
				out.println(commandOut);
				out.flush();
			}
			
			s5.close();
			
		} else if (command.equals("STARTGAME")) {
				
				if (frog.getY() != 800) {
					System.out.println("An additional coin has been inserted. You'll get it this time!");
				}
				
				System.out.println("Get to hopping!");	
				
				frog.setX(264);
				frog.setY(800);
				
				for (int i=0; i < Blast1Row1.length; i++) {
					if ( Blast1Row1[i].getMoving() ) {
						Blast1Row1[i].stopThread();
					} else {
						Blast1Row1[i].startThread();
					}
				}
				
				for (int i=0; i < Blast1Row2.length; i++) {
					if ( Blast1Row2[i].getMoving() ) {
						Blast1Row2[i].stopThread();
					} else {
						Blast1Row2[i].startThread();
					}
				}
				
				for (int i=0; i < Blast2Row1.length; i++) {
					if ( Blast2Row1[i].getMoving() ) {
						Blast2Row1[i].stopThread();
					} else {
						Blast2Row1[i].startThread();
					}
				}
				
				for (int i=0; i < Blast2Row2.length; i++) {
					if ( Blast2Row2[i].getMoving() ) {
						Blast2Row2[i].stopThread();
					} else {
						Blast2Row2[i].startThread();
					}
				}
				
				for (int i=0; i < Log1Row1.length; i++) {
					if ( Log1Row1[i].getMoving() ) {
						Log1Row1[i].stopThread();
					} else {
						Log1Row1[i].startThread();
					}
				}
				
				for (int i=0; i < Log1Row2.length; i++) {
					if ( Log1Row2[i].getMoving() ) {
						Log1Row2[i].stopThread();
					} else {
						Log1Row2[i].startThread();
					}
				}
				
				for (int i=0; i < Log1Row3.length; i++) {
					if ( Log1Row3[i].getMoving() ) {
						Log1Row3[i].stopThread();
					} else {
						Log1Row3[i].startThread();
					}
				}
				
				for (int i=0; i < Log2Row1.length; i++) {
					if ( Log2Row1[i].getMoving() ) {
						Log2Row1[i].stopThread();
					} else {
						Log2Row1[i].startThread();
					}
				}
				
				for (int i=0; i < Log2Row2.length; i++) {
					if ( Log2Row2[i].getMoving() ) {
						Log2Row2[i].stopThread();
					} else {
						Log2Row2[i].startThread();
					}
				}
		}
	
		
		if ( command.equals("PLAYER")) {
			int playerNo = in.nextInt();
			String playerAction = in.next();
			System.out.println("Player "+playerNo+" moves "+playerAction);
			
			
			//send a response
			Socket s2 = new Socket("localhost", CLIENT_PORT);
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String commandOut = "PLAYER "+playerNo+" POSTION 500 400\n";
			System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();
				
			s2.close();

		}
	}
}
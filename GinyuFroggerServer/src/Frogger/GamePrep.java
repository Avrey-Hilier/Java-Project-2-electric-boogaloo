package Frogger;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GamePrep extends JFrame implements KeyListener, ActionListener {
	
	public static void main(String[] args){
		//declare copies of our character
		Frog frog;
		Background background;
		frog = new Frog(264, 800, 60, 66, "Frog.png");
		background = new Background(0, 0, 600, 800, "Background.png");
		Blast1 Blast1Row1[ ] = new Blast1[4];
		Blast1 Blast1Row2[ ] = new Blast1[4];
		Blast2 Blast2Row1[ ] = new Blast2[4];
		Blast2 Blast2Row2[ ] = new Blast2[4];
		Log Log1Row1[ ] = new Log[4];
		Log Log1Row2[ ] = new Log[4];
		Log Log1Row3[ ] = new Log[4];
		Log2 Log2Row1[ ] = new Log2[4];
		Log2 Log2Row2[ ] = new Log2[4];
		boolean aliveFrog = true;
		
		int score = 0;
		
		String input;
		
		Connection conn = null;

		int spacing = 0;
		
		for (int i=0; i < Blast1Row1.length; i++) {
			Blast1Row1[i]= new Blast1();
			Blast1Row1[i].setX(0 + spacing);
			Blast1Row1[i].setY(668);
			Blast1Row1[i].setWidth(60);
			Blast1Row1[i].setHeight(66);
			Blast1Row1[i].setImage("Blast.png");
			Blast1Row1[i].setFrog(frog);
			spacing += 132;
		}
		spacing = 0;
		
		for (int i=0; i < Blast1Row2.length; i++) {
			Blast1Row2[i]= new Blast1();
			Blast1Row2[i].setX(0 + spacing);
			Blast1Row2[i].setY(536);
			Blast1Row2[i].setWidth(60);
			Blast1Row2[i].setHeight(66);
			Blast1Row2[i].setImage("Blast.png");
			Blast1Row2[i].setFrog(frog);
			spacing += 132;
		}
		spacing = 0;
		
		for (int i=0; i < Blast2Row1.length; i++) {
			Blast2Row1[i]= new Blast2();
			Blast2Row1[i].setX(0 + spacing);
			Blast2Row1[i].setY(602);
			Blast2Row1[i].setWidth(60);
			Blast2Row1[i].setHeight(66);
			Blast2Row1[i].setImage("Blast.png");
			Blast2Row1[i].setFrog(frog);
			spacing += 132;
		}
		spacing = 0;
		
		for (int i=0; i < Blast2Row2.length; i++) {
			Blast2Row2[i]= new Blast2();
			Blast2Row2[i].setX(0 + spacing);
			Blast2Row2[i].setY(470);
			Blast2Row2[i].setWidth(60);
			Blast2Row2[i].setHeight(66);
			Blast2Row2[i].setImage("Blast.png");
			Blast2Row2[i].setFrog(frog);
			spacing += 132;
		}
		spacing = 0;

		for (int i=0; i < Log1Row1.length; i++) {
			Log1Row1[i]= new Log();
			Log1Row1[i].setX(66 + spacing);
			Log1Row1[i].setY(338);
			Log1Row1[i].setWidth(80);
			Log1Row1[i].setHeight(56);
			Log1Row1[i].setImage("Log.png");
			Log1Row1[i].setFrog(frog);
			spacing += 132;
		}
		spacing = 0;
		
		for (int i=0; i < Log1Row2.length; i++) {
			Log1Row2[i]= new Log();
			Log1Row2[i].setX(66 + spacing);
			Log1Row2[i].setY(206);
			Log1Row2[i].setWidth(80);
			Log1Row2[i].setHeight(56);
			Log1Row2[i].setImage("Log.png");
			Log1Row2[i].setFrog(frog);
			spacing += 132;
		}
		spacing = 0;

		for (int i=0; i<Log2Row1.length; i++) {
			Log2Row1[i]= new Log2();
			Log2Row1[i].setX(66 + spacing);
			Log2Row1[i].setY(272);
			Log2Row1[i].setWidth(80);
			Log2Row1[i].setHeight(56);
			Log2Row1[i].setImage("Log.png");
			Log2Row1[i].setFrog(frog);
			spacing += 132;
		}

		spacing = 0;
		for (int i=0; i<Log2Row2.length; i++) {
			Log2Row2[i]= new Log2();
			Log2Row2[i].setX(66 + spacing);
			Log2Row2[i].setY(140);
			Log2Row2[i].setWidth(80);
			Log2Row2[i].setHeight(56);
			Log2Row2[i].setImage("Log.png");
			Log2Row2[i].setFrog(frog);
			spacing += 132;
		}
		
		spacing = 0;
		
		for (int i=0; i < Log1Row3.length; i++) {
			Log1Row3[i]= new Log();
			Log1Row3[i].setX(66 + spacing);
			Log1Row3[i].setY(66);
			Log1Row3[i].setWidth(80);
			Log1Row3[i].setHeight(56);
			Log1Row3[i].setImage("Log.png");
			Log1Row3[i].setFrog(frog);
			spacing += 132;
		}
		
		background.setX(0);
		background.setY(0);
		background.setWidth(600);
		background.setHeight(800);
		background.setImage("Background.png");
		
		//Blast1Label HAS a memory address

		//move button
		//startButton = new JButton("Start");
		//startButton.setSize(100, 100);
		//startButton.setLocation(GameProperties.SCREEN_WIDTH - 100, GameProperties.SCREEN_HEIGHT - 200);
		

		final int SERVER_PORT = 5556;

		//game variables go here, pass to service

		//run server in own thread
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {

					ServerSocket server;
					try {
						
						server = new ServerSocket(SERVER_PORT);
						System.out.println("Waiting for clients to connect...");
						while(true) {
							Socket s = server.accept();
							System.out.println("client connected");
							
							ServerService myService = new ServerService (s, frog, Blast1Row1, Blast1Row2, Blast2Row1, Blast2Row2, Log1Row1, Log1Row2, Log1Row3, Log2Row1, Log2Row2);
							Thread t2 = new Thread(myService);
							t2.start();
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		
				}
			}
		});
		t1.start( );

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		//get current position
		
		//detect direction
		if ( e.getKeyCode() == KeyEvent.VK_UP ) {
			
		} else if ( e.getKeyCode() == KeyEvent.VK_DOWN ) {
			
		} else if ( e.getKeyCode() == KeyEvent.VK_LEFT ) {
			
		}  else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	/*
	public void blastImpact() {
		aliveFrog = false;
		startButton.setText("Restart");
	}*/
}
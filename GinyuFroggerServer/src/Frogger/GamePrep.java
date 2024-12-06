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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GamePrep extends JFrame implements KeyListener, ActionListener {

		
	//GUI setup
	public GamePrep() {

		//https://www.geeksforgeeks.org/java-close-awt-window/
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Ok, shutting down...");
                
                String sqlUpdate = "UPDATE HIGHSCORE SET SCORE = ? WHERE NAME = ?";
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                    pstmtUpdate.setDouble(1, score);
                    pstmtUpdate.setString(2, input);
                    pstmtUpdate.executeUpdate();
                } catch (Exception f) {
                    f.printStackTrace();
                }

                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
		//declare copies of our character
		Frog frog;
		Background background;
		Blast1 Blast1Row1[ ] = new Blast1[4];
		Blast1 Blast1Row2[ ] = new Blast1[4];
		Blast2 Blast2Row1[ ] = new Blast2[4];
		Blast2 Blast2Row2[ ] = new Blast2[4];
		Log Log1Row1[ ] = new Log[4];
		Log Log1Row2[ ] = new Log[4];
		Log Log1Row3[ ] = new Log[4];
		Log2 Log2Row1[ ] = new Log2[4];
		Log2 Log2Row2[ ] = new Log2[4];
		
		//GUI variables
		Container content;
		JLabel frogLabel, BackgroundLabel;
		JLabel Blast1Labels[] = new JLabel[8];
		ImageIcon Blast1Images[] = new ImageIcon[8];
		JLabel Blast2Labels[] = new JLabel[8];
		ImageIcon Blast2Images[] = new ImageIcon[8];
		JLabel Log1Labels[] = new JLabel[12];
		ImageIcon Log1Images[] = new ImageIcon[12];
		JLabel Log2Labels[] = new JLabel[8];
		ImageIcon Log2Images[] = new ImageIcon[8];
		ImageIcon frogImage, BackgroundImage;
		JLabel Highscore;
		
		//2 buttons
		JButton startButton;
		
		boolean aliveFrog = true;
		
		int score = 0;
		
		String input;
		
		Connection conn = null;

		input = JOptionPane.showInputDialog("Enter your name: ");
        
        if (input == null || input == "") {
            input = "Doofus";
        } 
        
		try {
			//load the database driver
			Class.forName("org.sqlite.JDBC");
			System.out.print("Driver Loaded");
			
			//create connection string and connect to database
			String dbURL = "jdbc:sqlite:Scoreboard.db";
			conn = DriverManager.getConnection(dbURL);
			
			if (conn != null) {
				System.out.println("connected to database");
				
				//show meta data for database
				DatabaseMetaData db = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver Name: " + db.getDriverName());
				System.out.println("Driver Version: " + db.getDriverVersion());
				System.out.println("Product Name: " + db.getDatabaseProductName());
				System.out.println("Product Version: " + db.getDatabaseProductVersion());
				
				//create table using prepared statement
                String sqlCreateTable = "CREATE TABLE IF NOT EXISTS HIGHSCORE " +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " NAME TEXT NOT NULL, " +
                        " SCORE INT NOT NULL)";

                try (PreparedStatement pstmtCreateTable = conn.prepareStatement(sqlCreateTable)) {
                	pstmtCreateTable.executeUpdate();
                	System.out.println("Table Successfully Created");
                }
                
				//insert data using a prepared statement
                String sqlInsert = "INSERT INTO HIGHSCORE (NAME, SCORE) VALUES (?, ?)";
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

                	//execute calls to prepared statement
                	pstmtInsert.setString(1, input);
                	pstmtInsert.setInt(2, score);
                	pstmtInsert.executeUpdate();
                	
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		frog = new Frog(264, 800, 60, 66, "Frog.png");
		background = new Background(0, 0, 600, 800, "Background.png");
		Highscore = new JLabel("Current score: " + score);
		
		//set up screen
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		content.setBackground(Color.gray);
		setLayout(null);
		
		

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
							
							ServerService myService = new ServerService (s);
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
		GamePrep myGame = new GamePrep();
		myGame.setVisible(true);
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


	public void blastImpact() {
		aliveFrog = false;
		startButton.setText("Restart");
	}
}
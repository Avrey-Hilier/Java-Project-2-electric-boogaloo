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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;
	

	//declare copies of our character
	private Frog frog;
	private Background background;
	private Blast1 Blast1Row1[ ] = new Blast1[4];
	private Blast1 Blast1Row2[ ] = new Blast1[4];
	private Blast2 Blast2Row1[ ] = new Blast2[4];
	private Blast2 Blast2Row2[ ] = new Blast2[4];
	private Log Log1Row1[ ] = new Log[4];
	private Log Log1Row2[ ] = new Log[4];
	private Log Log1Row3[ ] = new Log[4];
	private Log2 Log2Row1[ ] = new Log2[4];
	private Log2 Log2Row2[ ] = new Log2[4];
	
	//GUI variables
	private Container content;
	private JLabel frogLabel, BackgroundLabel;
	private JLabel Blast1Labels[] = new JLabel[8];
	private ImageIcon Blast1Images[] = new ImageIcon[8];
	private JLabel Blast2Labels[] = new JLabel[8];
	private ImageIcon Blast2Images[] = new ImageIcon[8];
	private JLabel Log1Labels[] = new JLabel[12];
	private ImageIcon Log1Images[] = new ImageIcon[12];
	private JLabel Log2Labels[] = new JLabel[8];
	private ImageIcon Log2Images[] = new ImageIcon[8];
	private ImageIcon frogImage, BackgroundImage;
	private JLabel Highscore;
	
	//2 buttons
	private JButton startButton;
	
	boolean aliveFrog = true;
	
	static int score = 0;
	
	static String input;
	
	static Connection conn = null;
	
	//GUI setup
	public GamePrep() {
		super("Frogger");
		frog = new Frog(264, 800, 60, 66, "Frog.png");
		background = new Background(0, 0, 600, 800, "Background.png");
		Highscore = new JLabel("Current score: " + score);
		
		//set up screen
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		content.setBackground(Color.gray);
		setLayout(null);
		
		frogLabel = new JLabel();
		frogImage = new ImageIcon(getClass().getResource("images/" + frog.getImage()));
		frogLabel.setIcon(frogImage);
		frogLabel.setSize(frog.getWidth(), frog.getHeight());
		frogLabel.setLocation(frog.getX(), frog.getY());

		int spacing = 0;
		
		for (int i=0; i < Blast1Row1.length; i++) {
			Blast1Row1[i]= new Blast1();
			Blast1Row1[i].setX(0 + spacing);
			Blast1Row1[i].setY(668);
			Blast1Row1[i].setWidth(60);
			Blast1Row1[i].setHeight(66);
			Blast1Row1[i].setImage("Blast.png");
			Blast1Row1[i].setFrog(frog);
				
			Blast1Labels[i] = new JLabel();
			Blast1Images[i] = new ImageIcon(getClass().getResource("images/" + Blast1Row1[i].getImage()));
			Blast1Labels[i].setIcon(Blast1Images[i]);
			Blast1Labels[i].setSize(Blast1Row1[i].getWidth(), Blast1Row1[i].getHeight());
			Blast1Labels[i].setLocation(Blast1Row1[i].getX(), Blast1Row1[i].getY());
			
			Blast1Row1[i].setBlast1Label(Blast1Labels[i]);
			Blast1Row1[i].setFrogLabel(frogLabel);
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
				
			Blast1Labels[4 + i] = new JLabel();
			Blast1Images[4 + i] = new ImageIcon(getClass().getResource("images/" + Blast1Row2[i].getImage()));
			Blast1Labels[4 + i].setIcon(Blast1Images[i]);
			Blast1Labels[4 + i].setSize(Blast1Row2[i].getWidth(), Blast1Row2[i].getHeight());
			Blast1Labels[4 + i].setLocation(Blast1Row2[i].getX(), Blast1Row2[i].getY());
			
			Blast1Row2[i].setBlast1Label(Blast1Labels[4 + i]);
			Blast1Row2[i].setFrogLabel(frogLabel);
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
				
			Blast2Labels[i] = new JLabel();
			Blast2Images[i] = new ImageIcon(getClass().getResource("images/" + Blast2Row1[i].getImage()));
			Blast2Labels[i].setIcon(Blast2Images[i]);
			Blast2Labels[i].setSize(Blast2Row1[i].getWidth(), Blast2Row1[i].getHeight());
			Blast2Labels[i].setLocation(Blast2Row1[i].getX(), Blast2Row1[i].getY());
			
			Blast2Row1[i].setBlast2Label(Blast2Labels[i]);
			Blast2Row1[i].setFrogLabel(frogLabel);
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
				
			Blast2Labels[4 + i] = new JLabel();
			Blast2Images[4 + i] = new ImageIcon(getClass().getResource("images/" + Blast2Row2[i].getImage()));
			Blast2Labels[4 + i].setIcon(Blast2Images[i]);
			Blast2Labels[4 + i].setSize(Blast2Row2[i].getWidth(), Blast2Row2[i].getHeight());
			Blast2Labels[4 + i].setLocation(Blast2Row2[i].getX(), Blast2Row2[i].getY());
			
			Blast2Row2[i].setBlast2Label(Blast2Labels[4 + i]);
			Blast2Row2[i].setFrogLabel(frogLabel);
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
				
			Log1Labels[i] = new JLabel();
			Log1Images[i] = new ImageIcon(getClass().getResource("images/" + Log1Row1[i].getImage()));
			Log1Labels[i].setIcon(Log1Images[i]);
			Log1Labels[i].setSize(Log1Row1[i].getWidth(), Log1Row1[i].getHeight());
			Log1Labels[i].setLocation(Log1Row1[i].getX(), Log1Row1[i].getY());
			
			Log1Row1[i].setLogLabel(Log1Labels[i]);
			Log1Row1[i].setFrogLabel(frogLabel);
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
				
			Log1Labels[4 + i] = new JLabel();
			Log1Images[4 + i] = new ImageIcon(getClass().getResource("images/" + Log1Row2[i].getImage()));
			Log1Labels[4 + i].setIcon(Log1Images[4 + i]);
			Log1Labels[4 + i].setSize(Log1Row2[i].getWidth(), Log1Row2[i].getHeight());
			Log1Labels[4 + i].setLocation(Log1Row2[i].getX(), Log1Row2[i].getY());
			
			Log1Row2[i].setLogLabel(Log1Labels[4 + i]);
			Log1Row2[i].setFrogLabel(frogLabel);
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
			
			Log2Labels[i] = new JLabel();
			Log2Images[i] = new ImageIcon(getClass().getResource("images/" + Log2Row1[i].getImage()));
			Log2Labels[i].setIcon(Log2Images[i]);
			Log2Labels[i].setSize(Log2Row1[i].getWidth(), Log2Row1[i].getHeight());
			Log2Labels[i].setLocation(Log2Row1[i].getX(), Log2Row1[i].getHeight());
			
			Log2Row1[i].setLog2Label(Log2Labels[i]);
			Log2Row1[i].setFrogLabel(frogLabel);
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
			
			Log2Labels[4 + i] = new JLabel();
			Log2Images[4 + i] = new ImageIcon(getClass().getResource("images/" + Log2Row2[i].getImage()));
			Log2Labels[4 + i].setIcon(Log2Images[4 + i]);
			Log2Labels[4 + i].setSize(Log2Row2[i].getWidth(), Log2Row2[i].getHeight());
			Log2Labels[4 + i].setLocation(Log2Row2[i].getX(), Log2Row2[i].getHeight());
			
			Log2Row2[i].setLog2Label(Log2Labels[4 + i]);
			Log2Row2[i].setFrogLabel(frogLabel);
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
				
			Log1Labels[8 + i] = new JLabel();
			Log1Images[8 + i] = new ImageIcon(getClass().getResource("images/" + Log1Row2[i].getImage()));
			Log1Labels[8 + i].setIcon(Log1Images[8 + i]);
			Log1Labels[8 + i].setSize(Log1Row3[i].getWidth(), Log1Row3[i].getHeight());
			Log1Labels[8 + i].setLocation(Log1Row3[i].getX(), Log1Row3[i].getY());
			
			Log1Row3[i].setLogLabel(Log1Labels[8 + i]);
			Log1Row3[i].setFrogLabel(frogLabel);
			spacing += 132;
		}
		
		background.setX(0);
		background.setY(0);
		background.setWidth(600);
		background.setHeight(800);
		background.setImage("Background.png");
		
		BackgroundLabel = new JLabel();
		BackgroundImage = new ImageIcon(getClass().getResource("images/" + background.getImage()));
		BackgroundLabel.setIcon(BackgroundImage);
		BackgroundLabel.setSize(background.getWidth(), background.getHeight());
		BackgroundLabel.setLocation(background.getX(), background.getY());
		
		//Blast1Label HAS a memory address

		//move button
		startButton = new JButton("Start");
		startButton.setSize(100, 100);
		startButton.setLocation(GameProperties.SCREEN_WIDTH - 100, GameProperties.SCREEN_HEIGHT - 200);
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		
		Highscore = new JLabel("Highscore: " + score);
		Highscore.setSize(200,200);
		Highscore.setLocation(250,-80/*GameProperties.SCREEN_WIDTH-400, GameProperties.SCREEN_HEIGHT-880*/);
		Highscore.setForeground(Color.white);
		
		for (int i = 0; i < Log1Row1.length; i++) {
			Log1Row1[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Log1Row2.length; i++) {
			Log1Row2[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Log1Row3.length; i++) {
			Log1Row3[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Log2Row1.length; i++) {
			Log2Row1[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Log2Row2.length; i++) {
			Log2Row2[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Blast1Row1.length; i++) {
			Blast1Row1[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Blast1Row2.length; i++) {
			Blast1Row2[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Blast2Row1.length; i++) {
			Blast2Row1[i].setStartButton(startButton);
		}
		
		for (int i = 0; i < Blast2Row2.length; i++) {
			Blast2Row2[i].setStartButton(startButton);
		}
		
		
		add(startButton);
		add(frogLabel);
		for (int i = 0; i < Log1Labels.length; i++) {
			add(Log1Labels[i]);
		}
		
		for (int i = 0; i < Log2Labels.length; i++) {
			add(Log2Labels[i]);
		}
		
		for (int i = 0; i < Blast1Labels.length; i++) {
			add(Blast1Labels[i]);
		}
		
		for (int i = 0; i < Blast2Labels.length; i++) {
			add(Blast2Labels[i]);
		}
		add(Highscore);
		add(BackgroundLabel);
		
		
		content.addKeyListener(this);
		content.setFocusable(true);
		
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
		
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
					
					ServerSocket client;
					
					try {
						
						client = new ServerSocket(CLIENT_PORT);
						while(true) {
							Socket s2;
							try {
								s2 = client.accept();
								ClientService myService = new ClientService (s2, frog, frogLabel, Blast1Row1, Blast1Row2, Blast2Row1, Blast2Row2, Log1Row1, Log1Row2, Log1Row3, Log2Row1, Log2Row2, startButton, Blast1Labels, Blast2Labels, Log1Labels, Log2Labels, Highscore);
								Thread t2 = new Thread(myService);
								t2.start();
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("client connected");
							
						}
					
					
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Waiting for server responses...");

					
				}
			}
		});
		t1.start( );
		
		Thread t2 = new Thread(new Runnable ( ) {
			public void run() {
				synchronized(this) {
					while(true) {
						Socket s3;
						try {
							s3 = new Socket("localhost", SERVER_PORT);
						
						
						OutputStream outstream = s3.getOutputStream();
						PrintWriter out = new PrintWriter(outstream);
						
						String command = "GETBLAST\n";
						System.out.println("Sending " + command);
						out.println(command);
						out.flush();
						
						command = "GETLOG\n";
						System.out.println("Sending " + command);
						out.println(command);
						out.flush();
						
						s3.close();
						
						Thread.sleep(500);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		t2.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
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
		int x = frog.getX();
		int y = frog.getY();
		Socket s;
		//detect direction
		if ( e.getKeyCode() == KeyEvent.VK_UP ) {
			
			try {
				s = new Socket("localhost", SERVER_PORT);
			
			
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);

				String command = "MOVEFROG 1 UP\n";
				System.out.println("Sending: " + command);
				out.println(command);
				out.flush();
				s.close();
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if ( y + frog.getHeight() <=  0) {

				System.out.println("Oops, you fell into the vaccuum of space... be more careful in the future");
				
				score -= 50;
				
				Highscore.setText("Highscore: " + score);
				
				x = 264;
				y = 800;
				
				frogLabel.setLocation(frog.getX(), frog.getY());
				
				String sqlUpdate = "UPDATE HIGHSCORE SET SCORE = ? WHERE NAME = ?";
		        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

		        	pstmtUpdate.setDouble(1, score);
		        	pstmtUpdate.setString(2, input);
		        	pstmtUpdate.executeUpdate();
		        } catch (Exception f){
		        	f.printStackTrace();
		        }

			}
			
		} else if ( e.getKeyCode() == KeyEvent.VK_DOWN ) {
			

			try {
				s = new Socket("localhost", SERVER_PORT);
			
			
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);

				String command = "MOVEFROG 1 DOWN\n";
				System.out.println("Sending: " + command);
				out.println(command);
				out.flush();
				s.close();
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			if ( y >= GameProperties.SCREEN_HEIGHT) {
				
				System.out.println("Oops, you fell into the vaccuum of space... be more careful in the future");
				
				score -= 50;
				
				Highscore.setText("Highscore: " + score);
				
				x = 264;
				y = 800;
				
				frogLabel.setLocation(frog.getX(), frog.getY());
				
				String sqlUpdate = "UPDATE HIGHSCORE SET SCORE = ? WHERE NAME = ?";
		        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

		        	pstmtUpdate.setDouble(1, score);
		        	pstmtUpdate.setString(2, input);
		        	pstmtUpdate.executeUpdate();
		        } catch (Exception f){
		        	f.printStackTrace();
		        }
				
			}
			
		} else if ( e.getKeyCode() == KeyEvent.VK_LEFT ) {
			

			try {
				s = new Socket("localhost", SERVER_PORT);
			
			
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);

				String command = "MOVEFROG 1 LEFT\n";
				System.out.println("Sending: " + command);
				out.println(command);
				out.flush();
				s.close();
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			if (x + frog.getWidth() <= 0) {

				System.out.println("Oops, you fell into the vaccuum of space... be more careful in the future");
				
				score -= 50;
				
				Highscore.setText("Highscore: " + score);
				
				x = 264;
				y = 800;
				
				frogLabel.setLocation(frog.getX(), frog.getY());
				
				String sqlUpdate = "UPDATE HIGHSCORE SET SCORE = ? WHERE NAME = ?";
		        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

		        	pstmtUpdate.setDouble(1, score);
		        	pstmtUpdate.setString(2, input);
		        	pstmtUpdate.executeUpdate();
		        } catch (Exception f){
		        	f.printStackTrace();
		        }

			}
			
		}  else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			

			try {
				s = new Socket("localhost", SERVER_PORT);
			
			
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);

				String command = "MOVEFROG 1 RIGHT\n";
				System.out.println("Sending: " + command);
				out.println(command);
				out.flush();
				s.close();
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			if ( x >= GameProperties.SCREEN_WIDTH ) {

				System.out.println("Oops, you fell into the vaccuum of space... be more careful in the future");
				
				score -= 50;
				
				Highscore.setText("Highscore: " + score);
				
				x = 264;
			    y = 800;
				
				String sqlUpdate = "UPDATE HIGHSCORE SET SCORE = ? WHERE NAME = ?";
		        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

		        	pstmtUpdate.setDouble(1, score);
		        	pstmtUpdate.setString(2, input);
		        	pstmtUpdate.executeUpdate();
		        } catch (Exception f){
		        	f.printStackTrace();
		        }

			}
			
		} 
		
		//update frog
		frog.setX(x);
		frog.setY(y);
		
		//move label
		frogLabel.setLocation(frog.getX(), frog.getY() );
		//fix this
		
		if (frogLabel.getY() > 139 && frogLabel.getY() < 339) {
			aliveFrog = false;
			for (int i = 0; i < Log1Row1.length; i++ ) {
				if(Log1Row1[i].r.intersects( frog.getRectangle() ) || Log1Row2[i].r.intersects( frog.getRectangle() ) || Log1Row3[i].r.intersects( frog.getRectangle() ) || Log2Row1[i].r.intersects( frog.getRectangle() ) || Log2Row2[i].r.intersects( frog.getRectangle() )) {
					aliveFrog = true;
				}
			}
			if (aliveFrog == false){
				frog.setImage("Splash.png");
				frogLabel.setIcon(new ImageIcon(getClass().getResource("images/" + frog.getImage())));
				System.out.println("SPLASH!!!");
				startButton.setText("Restart");
			}
		}
		
		if (frogLabel.getY() < 65 && aliveFrog == true) {
			System.out.println("Congratulations!");
			frog.setX(264);
			frog.setY(800);
			
			frogLabel.setLocation(frog.getX(), frog.getY());
			score += 50;
			Highscore.setText("Current score: " + score);
		}
		
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == startButton ) {
			
			if (frog.getY() != 800 || aliveFrog == false) {
				System.out.println("An additional coin has been inserted. You'll get it this time!");
				score -= 50;
				
				String sqlUpdate = "UPDATE HIGHSCORE SET SCORE = ? WHERE NAME = ?";
		        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

		        	pstmtUpdate.setDouble(1, score);
		        	pstmtUpdate.setString(2, input);
		        	pstmtUpdate.executeUpdate();
		        } catch (Exception f){
		        	f.printStackTrace();
		        }
			}
			
			System.out.println("Get to hopping!");	
			
			frog.setX(264);
			frog.setY(800);
			
			frogLabel.setLocation(frog.getX(), frog.getY());
			
			startButton.setText("Start");
			
			Highscore.setText("Highscore: " + score);
			
			aliveFrog = true;
			
			Socket s;
			try {
				s = new Socket("localhost", SERVER_PORT);
			
			
			//Initialize data stream to send data out
			OutputStream outstream = s.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);

			String command = "STARTGAME\n";
			System.out.println("Sending: " + command);
			out.println(command);
			out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}


	public void blastImpact() {
		aliveFrog = false;
		startButton.setText("Restart");
	}
}
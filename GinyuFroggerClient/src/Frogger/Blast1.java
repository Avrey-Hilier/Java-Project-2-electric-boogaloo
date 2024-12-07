package Frogger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Blast1 extends Sprite{
	private Boolean moving;
	//private Thread t;
	
	private JLabel Blast1Label;	
	private JButton startButton;
	
	private Frog frog;
	private JLabel frogLabel;
	
	public void setFrog (Frog temp) {
		frog = temp;
	}
	
	public void setFrogLabel(JLabel temp) {
		frogLabel = temp;
	}
	
	public void setBlast1Label(JLabel temp) {
		Blast1Label = temp;
	}

	public void setStartButton(JButton temp) {
		startButton = temp;
	}

	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}

	public Blast1() {
		super();
		// TODO Auto-generated constructor stub
		this.moving = false;
	}

	public Blast1(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
		// TODO Auto-generated constructor stub
		this.moving = false;
	}
	
	private GamePrep game;

	public void setGamePrep(GamePrep game) {
	    this.game = game;
	}
	/*
	public void startThread() {
		//run will be triggered
		System.out.println("Current moving: " + this.moving);
		
		//if already moving, do not start again
		if ( !this.moving ) {
			this.moving = true;
			
			startButton.setText("Stop");
			
			this.setImage("Blast.png");
			Blast1Label.setIcon(new ImageIcon(getClass().getResource("images/" + this.getImage())));

			frog.setImage("Frog.png");
			frogLabel.setIcon(new ImageIcon(getClass().getResource("images/" + frog.getImage())));

			//System.out.println("Starting thread");
			t = new Thread(this, "Blast1 thread");
			t.start(); //automatic call to the run method
		}
		
	}
	
	public void stopThread() {
		//will end the thread on next repeated cycle
		if (this.moving) {
			this.moving = false;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("run triggered");
		
		while (this.moving) {
			
			int x = this.x;
			
			x += GameProperties.CHARACTER_STEP;
			
			if ( x >= GameProperties.SCREEN_WIDTH) {
				x = -1 * this.width;
			}
			
			this.setX(x); //this.x = x; //rectangle doesn't update
			
			Blast1Label.setLocation(this.x, this.y);
			
			//detect collisions between character1 r and char2
			this.detectCollision();
			
			//System.out.println("x, y: " + this.x + " " + this.y);
			
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//System.out.println("Thread Stopped");
		
	}
	
	private void detectCollision() {
		if ( this.r.intersects( frog.getRectangle() ) ) {
			//collision detected
			
			System.out.println("BOOM!");
			
			this.setImage("Kaboom.png");
			Blast1Label.setIcon(new ImageIcon(getClass().getResource("images/" + this.getImage())));

			frog.setImage("Kaboom.png");
			frogLabel.setIcon(new ImageIcon(getClass().getResource("images/" + frog.getImage())));
			
			if (this.game !=null) {
				game.blastImpact();
			}
			
		}
	}*/

}

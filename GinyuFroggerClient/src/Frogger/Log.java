package Frogger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Log extends Sprite implements Runnable{
	private Boolean moving;
	private Thread t;
	
	private JLabel LogLabel;	
	private JButton startButton;
	
	private Frog frog;
	private JLabel frogLabel;
	
	public void setFrog (Frog temp) {
		frog = temp;
	}
	
	public void setFrogLabel(JLabel temp) {
		frogLabel = temp;
	}
	
	public void setLogLabel(JLabel temp) {
		LogLabel = temp;
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

	public Log() {
		super();
		// TODO Auto-generated constructor stub
		this.moving = false;
	}

	public Log(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
		// TODO Auto-generated constructor stub
		this.moving = false;
	}
	
	public void startThread() {
		//run will be triggered
		System.out.println("Current moving: " + this.moving);
		
		//if already moving, do not start again
		if ( !this.moving ) {
			this.moving = true;
			
			this.setImage("Log.png");
			LogLabel.setIcon(new ImageIcon(getClass().getResource("images/" + this.getImage())));

			frog.setImage("Frog.png");
			frogLabel.setIcon(new ImageIcon(getClass().getResource("images/" + frog.getImage())));

			//System.out.println("Starting thread");
			t = new Thread(this, "Log thread");
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
			LogLabel.setLocation(this.getX(), this.getY());
			
			//detect collisions between character1 r and char2
			if (this.r.intersects( frog.getRectangle() )) {
				frog.setX(x+= GameProperties.CHARACTER_STEP);
				frogLabel.setLocation(frog.getX(), frog.getY());
			}
			
			//System.out.println("x3, y3: " + this.x + " " + this.y);
			
			try {
				Thread.sleep(400);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//System.out.println("Thread Stopped");
		
	}
}

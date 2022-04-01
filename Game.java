import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{

	public static int WIDTH = 160, HEIGHT = 120, SCALE = 3;
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
	public BufferedImage layer;
	public static boolean singleplayer, coop;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addKeyListener(this);
		layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		player = new Player(100, HEIGHT-5);
		enemy = new Enemy(100, 0);
		ball = new Ball(100, HEIGHT/2 - 1);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame =  new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);	
		frame.pack();
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
		new Thread(game).start();
	}
	
	
	public void tick() {
		if(singleplayer||coop == true) {
		player.tick();
		enemy.tick();
		ball.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player.render(g);
		enemy.render(g);
		ball.render(g);
		g= bs.getDrawGraphics();	
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}
	
	public void run() {
		while(true) {	
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_1){
			singleplayer = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_2) {
			coop = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D){
			enemy.rightPla = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			enemy.leftPla = true;
		}
	}
	

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D){
			enemy.rightPla = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			enemy.leftPla = false;
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}
}

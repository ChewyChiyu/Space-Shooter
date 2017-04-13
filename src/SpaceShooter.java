import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SpaceShooter extends JPanel implements Runnable {
	Fighter player = new Fighter(250,550);
	ArrayList<SpaceShips> enemys = new ArrayList<SpaceShips>();
	ArrayList<Utilitys> drops = new ArrayList<Utilitys>();
	BufferedImage backDrop;
	boolean isRunning;
	boolean fatalWound = false;
	Timer fireTimer;
	Thread t;
	final int HEIGHT = 800;
	final int WIDTH = 600;
	int score = 0;
	public static void main(String[] args){
		new SpaceShooter();
	}
	public SpaceShooter(){
		addEnemysS1();
		setUpPanel();
		setUpBackGround();
		start();
	}
	public void addEnemysS1(){
		int xBuffer =WIDTH-100; // 100 pixel buffer
		int yBuffer = 0;
		final int SPACER = 5;
		for(int row = 0; row < 6; row++){
		enemys.add(new WheelShip(xBuffer,yBuffer));
		xBuffer-=(100-SPACER);
		yBuffer-=(100+SPACER);
		}
		xBuffer = 0;
		for(int row = 0; row < 6; row++){
			enemys.add(new WheelShip(xBuffer,yBuffer));
			xBuffer+=(100-SPACER);
			yBuffer-=(100+SPACER);
			}
		xBuffer = WIDTH-100;
		for(int row = 0; row < 6; row++){
			enemys.add(new WheelShip(xBuffer,yBuffer));
			xBuffer-=(100-SPACER);
			yBuffer-=(100+SPACER);
			}
		xBuffer = 0;
		for(int row = 0; row < 6; row++){
			enemys.add(new WheelShip(xBuffer,yBuffer));
			xBuffer+=(100-SPACER);
			yBuffer-=(100+SPACER);
			}
		xBuffer = -100; // -10 pixel buffer
		yBuffer -= 500;
		enemys.add(new StageOneBoss(xBuffer, yBuffer));
	}
	public void setUpBackGround(){
		URL imageUrl = getClass().getResource("/Images/spaceback.jpeg");
		try {
			backDrop = ImageIO.read(imageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Space Shooter!");
		frame.add(this);
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "A");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "S");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "D");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "W");

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "rA");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "rS");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "rD");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "rW");

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released SPACE"), "rSPACE");

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "pause");

		this.getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeXVelocity(-player.getSpeed());
			}
			
		});
		this.getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeXVelocity(player.getSpeed());
			}
			
		});
		this.getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeYVelocity(-player.getSpeed());
			}
			
		});
		this.getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeYVelocity(player.getSpeed());
			}
			
		});
		this.getActionMap().put("rA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeXVelocity(0);

			}
			
		});
		this.getActionMap().put("rD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeXVelocity(0);
			}
			
		});
		this.getActionMap().put("rW", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeYVelocity(0);
			}
			
		});
		this.getActionMap().put("rS", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.changeYVelocity(0);

			}
			
		});
		
		
		
		this.getActionMap().put("SPACE", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.isFiring = true;
			}
			
		});
		this.getActionMap().put("rSPACE", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.isFiring = false;

			}
			
		});
		this.getActionMap().put("pause", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isRunning){
					stop();
					repaint();
				}else
					start();

			}
			
		});
		fireTimer = new Timer(100, e->{
			if(player.isFiring){
				player.fire();
			}
		});
		fireTimer.start();
	}
	public synchronized void start(){
		t = new Thread(this);
		isRunning = true;
		t.start();
	}
	public synchronized void stop(){
		try{
			isRunning = false;
			t.join();
		}catch(Exception e){
			
		}
	}
	public void run(){
		while(isRunning){
			updatePanel();
			try{
				Thread.sleep(10);
			}catch(Exception e){
				
			}
		}
	}
	public void updatePanel(){
		updateShipLocations();
		updateBulletLocations();
		updateUpgradeLocations();
		checkForHit();
		checkOutOfBounds();
		checkForDead();
		checkForWin();
		repaint();	
	}
	public void checkForWin(){
		if(enemys.size()<=0){
			int reply = JOptionPane.showConfirmDialog(null, "You WON! Score : " + score + "\n Play Again?" , "Win", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				player = new Fighter(250,550);
				enemys.clear();
				drops.clear();
				score = 0;
				addEnemysS1();
				fatalWound = false;
				return;
			}
			else {
				System.exit(0);
			}
		}
	}
	public void checkForDead(){
		if(fatalWound){
			int reply = JOptionPane.showConfirmDialog(null, "You Died!" + "\n Play Again?" , "Lose", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				player = new Fighter(250,550);
				enemys.clear();
				drops.clear();
				score = 0;
				addEnemysS1();
				fatalWound = false;
				return;
			}
			else {
				System.exit(0);
			}
		}
	}
	public void checkForHit(){
		for(int index = 0; index < drops.size(); index++){
				int playerX1 = player.getX();
				int playerX2 = player.getX() + player.getWidth();
				int playerY1 = player.getY();
				int playerY2 = player.getY() + player.getHeight();
				
				Utilitys u = drops.get(index);
				if(u.getX()>playerX1&&u.getX()<playerX2&&u.getY()>playerY1&&u.getY()<playerY2){
					drops.remove(u);
					if(u.getType().equals(UtilityType.REPAIR)){
						player.heal();
					}
					if(u.getType().equals(UtilityType.UPGRADE)){
						player.upgrade();
					}
				}
		}
		for(int index = 0; index < enemys.size(); index++){
			for(int index2 = 0; index2 < enemys.get(index).getBulletArray().size(); index2++){
				int playerX1 = player.getX();
				int playerX2 = player.getX() + player.getWidth();
				int playerY1 = player.getY();
				int playerY2 = player.getY() + player.getHeight();
				
				Projectiles p = enemys.get(index).getBulletArray().get(index2);
				if(p.getX()>playerX1&&p.getX()<playerX2&&p.getY()>playerY1&&p.getY()<playerY2){
					enemys.get(index).getBulletArray().remove(p);
					if(player.fatalHit(p.getPower())){
						fatalWound = true;
					}
				}
			}
		}
		for(int index = 0; index < player.getBulletArray().size(); index++){
			Projectiles p = player.getBulletArray().get(index);
			for(int index2 = 0; index2 < enemys.size(); index2++){
				SpaceShips enemy = enemys.get(index2);
				int enemyX1 = enemy.getX();
				int enemyX2 = enemy.getX() + enemy.getWidth();
				int enemyY1 = enemy.getY();
				int enemyY2 = enemy.getY() + enemy.getHeight();
				
				if(enemyX1<p.getX()&&enemyX2>p.getX()&&enemyY1<p.getY()&&enemyY2>p.getY()){
					player.getBulletArray().remove(p);
					if(enemy.fatalHit(p.getPower())){
						enemys.remove(enemy);
						score+=100;
						if((int)(Math.random()*3)==1){ //1 in 4 change for a utility spawn
							UtilityType u = ((int)(Math.random()*2)==1)?UtilityType.REPAIR:UtilityType.UPGRADE;
							drops.add(new Utilitys(u,enemy.getX(),enemy.getY()));
						}
					}
					
				}
			}
		}
	}
	public void checkOutOfBounds(){
		for(int index = 0; index < drops.size(); index++){
			Utilitys u = drops.get(index);
			if(u.getY()>HEIGHT){
				drops.remove(u);
			}
			
	}
		for(int index = 0; index < player.getBulletArray().size(); index++){
			Projectiles p = player.getBulletArray().get(index);
			if(p.getX()<0||p.getX()>WIDTH||p.getY()<0||p.getY()>HEIGHT){
				player.getBulletArray().remove(p);
			}
		}
		for(int index = 0; index < enemys.size(); index++){
			for(int index2 = 0; index2 < enemys.get(index).getBulletArray().size(); index2++){
				Projectiles p = enemys.get(index).getBulletArray().get(index2);
				if(p.getY()>HEIGHT){
					enemys.get(index).getBulletArray().remove(p);	
				}
			}
		}
		for(int index = 0; index < enemys.size(); index++){
			SpaceShips s = enemys.get(index);
			if(s.getY()>HEIGHT){
				enemys.remove(s);
			}
		}
	}
	public void updateUpgradeLocations(){
		for(int index = 0; index < drops.size(); index++){
			Utilitys u = drops.get(index);
			u.changeX(u.getXVelocity());
			u.changeY(u.getYVelocity());
		}
	}
	public void updateBulletLocations(){
		for(int index = 0; index < player.getBulletArray().size(); index++){
			Projectiles p = player.getBulletArray().get(index);
			p.changeY(p.getYVelocity());
			p.changeX(p.getXVelocity());
		}
		for(int index = 0; index < enemys.size(); index++){
			for(int index2 = 0; index2 < enemys.get(index).getBulletArray().size(); index2++){
				Projectiles p = enemys.get(index).getBulletArray().get(index2);
				p.changeY(p.getYVelocity());
				p.changeX(p.getXVelocity());
			}
		}
	}
	public void updateShipLocations(){
		if(player.getX()<0){
			player.changeX(player.getSpeed());
		}
		if(player.getX()>WIDTH-100){ //100 pixel buffer
			player.changeX(-player.getSpeed());
		}
		if(player.getY()<0){
			player.changeY(player.getSpeed());
		}
		if(player.getY()>HEIGHT){
			player.changeY(-player.getSpeed());
		}
		player.changeX(player.getXVelocity());
		player.changeY(player.getYVelocity());
		
		for(int index = 0; index < enemys.size(); index++){
			SpaceShips s = enemys.get(index);
			s.changeX(s.getXVelocity());
			s.changeY(s.getYVelocity());
		}
		
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBackDrop(g);
		drawShips(g);
		drawBullets(g);
		drawUpgrades(g);
		drawString(g);

	}
	public void drawUpgrades(Graphics g){
		for(int index = 0; index < drops.size(); index++){
			Utilitys u = drops.get(index);
			u.draw(g, u.getX(), u.getY());
		}
	}
	public void drawString(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD, 20));
		g.drawString("Press p to pause", 10, 700);
		g.drawString("Score : " + score, 10, 650);
		if(!isRunning){
			g.setFont(new Font("Arial",Font.BOLD, 70));
			g.drawString("PAUSED", (int)(WIDTH/4), (int) (HEIGHT/2));
		}
	}
	public void drawBackDrop(Graphics g){
		g.drawImage(backDrop, 0, 0, WIDTH, HEIGHT, null);
	}
	public void drawBullets(Graphics g){
		for(int index = 0; index < player.getBulletArray().size(); index++){
			Projectiles p = player.getBulletArray().get(index);
			p.draw(g, p.getX(), p.getY());
		}
		for(int index = 0; index < enemys.size(); index++){
			for(int index2 = 0; index2 < enemys.get(index).getBulletArray().size(); index2++){
				Projectiles p = enemys.get(index).getBulletArray().get(index2);
				p.draw(g, p.getX(), p.getY());
			}
		}
	}
	public void drawShips(Graphics g){
		player.draw(g, player.getX(), player.getY());
		for(int index = 0; index < enemys.size(); index++){
			SpaceShips s = enemys.get(index);
			s.draw(g, s.getX(), s.getY());
		}
	}
}


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class SpaceShips {
	private BufferedImage ship; 
	public static BufferedImage[] bullets = new BufferedImage[2];
	public static BufferedImage shield;
	private int upgradeStage = 1;
	private final String imagePath;
	private int x;
	private int y;
	private int health; 
	private final int initialHealth;
	private int width;
	private int height;
	private int xVelocity;
	private int yVelocity;
	private boolean shieldActive = false;
	private int shieldHealth = 0;
	private int ds = 20;
	public ArrayList<Projectiles> bulletsFired = new ArrayList<Projectiles>();

	public SpaceShips(String imagePath, int x , int y, int width, int height, int health){
		this.imagePath = imagePath;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.health = health;
		initialHealth = health;
		openProjectileImage();
		loadImage();
	}
	public void openProjectileImage(){
		URL imageUrl = getClass().getResource("/Images/Bullet1.png");
		try {
			bullets[0] = ImageIO.read(imageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		URL imageUrl2 = getClass().getResource("/Images/Bullet2.png");
		try {
			bullets[1] = ImageIO.read(imageUrl2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		URL imageUrl3 = getClass().getResource("/Images/Shield.png");
		try {
			shield = ImageIO.read(imageUrl3);
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	
	public void loadImage(){
		URL imageUrl = getClass().getResource(imagePath);
		try {
			ship = ImageIO.read(imageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public abstract void fire();
	
	public void draw(Graphics g, int x, int y){
		g.drawImage(ship, x, y, width,height, null);
		g.setColor(Color.green);
		int totalHealth = health;
		int xBuffer = x;
		int yBuffer = y+height;
		while(totalHealth-->=0){
		g.fillRect(xBuffer++, yBuffer, 10, 6);		
		if(Math.abs(xBuffer-x)>width){ //100 pixel buffer
			xBuffer = x;
			yBuffer+= 10;
		}
		}
		if(shieldToggled()){
			g.drawImage(shield,(int)(getX()-width*.5), (int)(getY()-height*.5),width*2,height*2, null);
			g.setColor(new Color(66, 134, 244));
			g.fillRect(getX(),getY()+height+10,getShieldHealth()*5, 7);
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void changeX(int inc){
		x += inc;
	}
	public void changeY(int inc){
		y += inc;
	}
	public int getXVelocity(){
		return xVelocity;
	}
	public int getYVelocity(){
		return yVelocity;
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public void changeXVelocity(int inc){
		xVelocity = inc;
	}
	public void changeYVelocity(int inc){
		yVelocity = inc;
	}
	public ArrayList<Projectiles> getBulletArray(){
		return bulletsFired;
	}
	public boolean fatalHit(int pow){
		if((health-=pow)<=0){
			return true;
		}
		return false;
	}
	public void heal(){
		health = initialHealth;
	}
	public void upgrade(){
		upgradeStage++;
	}
	public int getUpgradeStage(){
		return upgradeStage;
	}
	public void toggleShield(){
		shieldActive = true;
	}
	public boolean shieldToggled(){
		return shieldActive;
	}
	public int getShieldHealth(){
		return shieldHealth;
	}
	public boolean blockedAttack(){
		if(shieldHealth-->=0){
			return true;
		}
		shieldActive = false;
		return false;
	}
	public void setShieldHealth(int inc){
		shieldHealth = inc;
	}
	public int getDefaultSheild(){
		return ds;
	}
}

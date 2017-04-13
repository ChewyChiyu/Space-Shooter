import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class SpaceShips {
	private BufferedImage ship; 
	private final String imagePath;
	private int x;
	private int y;
	private int health; 
	private int width;
	private int height;
	private int xVelocity;
	private int yVelocity;
	public ArrayList<Projectiles> bulletsFired = new ArrayList<Projectiles>();

	public SpaceShips(String imagePath, int x , int y, int width, int height, int health){
		this.imagePath = imagePath;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.health = health;
		loadImage();
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
		g.fillRect(x, y+height, health*10, 5);		
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
}

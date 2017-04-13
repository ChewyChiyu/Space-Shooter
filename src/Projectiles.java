import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Projectiles {
	private int x;
	private int y;
	private int yVelocity;
	private int xVelocity;
	private int power;
	private byte type;
	private BufferedImage bullet;
	public Projectiles(int x, int y,int yVelocity,int xVelocity, byte type, int power){
		this.x = x;
		this.y = y;
		this.type = type;
		this.yVelocity = yVelocity;
		this.xVelocity = xVelocity;
		this.power = power;
		setUpImage();
	}
	public void setUpImage(){
		if(type==0){
			URL imageUrl = getClass().getResource("/Images/Bullet1.png");
			try {
				bullet = ImageIO.read(imageUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(type==1){
			URL imageUrl = getClass().getResource("/Images/Bullet2.png");
			try {
				bullet = ImageIO.read(imageUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	public void draw(Graphics g, int x, int y){
		g.drawImage(bullet, x, y, 20, 50 , null);
	}
	public int getYVelocity(){
		return yVelocity;
	}
	public int getXVelocity(){
		return xVelocity;
	}
	public int getPower(){
		return power;
	}
}

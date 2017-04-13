import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Projectiles {
	private int x;
	private int y;
	private int yVelocity;
	private int xVelocity;
	private int power;
	private BufferedImage b;
	public Projectiles(int x, int y,int yVelocity,int xVelocity, BufferedImage b, int power){
		this.x = x;
		this.y = y;
		this.b = b;
		this.yVelocity = yVelocity;
		this.xVelocity = xVelocity;
		this.power = power;
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
		g.drawImage(b, x, y, 20, 50 , null);
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

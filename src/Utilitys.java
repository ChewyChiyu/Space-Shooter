import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utilitys {
	private UtilityType u;
	private BufferedImage sprite;
	private int x;
	private int y;
	private int xVelocity = 0;
	private int yVelocity = 1;
	public Utilitys(UtilityType u, int x, int y){
		this.u = u;
		this.x = x;
		this.y = y;
		setUpImage();
	}
		public void setUpImage(){
			if(u.equals(UtilityType.UPGRADE)){
				URL imageUrl = getClass().getResource("/Images/WeaponUpgrade.png");
				try {
					sprite = ImageIO.read(imageUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(u.equals(UtilityType.REPAIR)){
				URL imageUrl = getClass().getResource("/Images/HealthKit.png");
				try {
					sprite = ImageIO.read(imageUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	public UtilityType getType(){
		return u;
	}
	public void draw(Graphics g, int x, int y){
		g.drawImage(sprite, x, y, 60,60,null); //all upgrades / repair kits are 60 x 60
	}
	public void changeX(int inc){
		x += inc;
	}
	public void changeY(int inc){
		y += inc;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void changeXVelocity(int inc){
		xVelocity = inc;
	}
	public void changeYVelocity(int inc){
		yVelocity = inc;
	}
	public int getXVelocity(){
		return xVelocity;
	}
	public int getYVelocity(){
		return yVelocity;
	}
	
}

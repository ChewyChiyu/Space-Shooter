
public class Fighter extends SpaceShips {
	private int speed = 5;
	boolean isFiring = false;
	public Fighter(int x, int y){
		super("/Images/fighter.png",x,y,100,100,10);
	}
	public int getSpeed(){ return speed; }
	
	public void fire(){
		bulletsFired.add(new Projectiles(getX()+50, getY(), -5,0, (byte) 0,5)); // + 50 for center 100+ for height
	}
	
}

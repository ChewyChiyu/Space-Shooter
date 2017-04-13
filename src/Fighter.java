
public class Fighter extends SpaceShips {
	private int speed = 5;
	boolean isFiring = false;
	public Fighter(int x, int y){
		super("/Images/fighter.png",x,y,100,100,100);
	}
	public int getSpeed(){ return speed; }
	
	public void fire(){
		int xBuffer = 0;
		for(int index = 0; index < getUpgradeStage(); index++){
			bulletsFired.add(new Projectiles(getX()+xBuffer, getY(), -5,0, (byte) 0,5)); // + 50 for center 100+ for height
			xBuffer+=30;
		}
	}

}

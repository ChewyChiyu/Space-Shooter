import javax.swing.Timer;

public class MetalShip extends SpaceShips{
	Timer t;
	public MetalShip(int x, int y) {
		super("/Images/MetalShip.png", x, y,100,100,50);
		changeYVelocity(1);
		t = new Timer(150,e->{
			if((int)(Math.random()*2)==1 && getY()>-200)
			fire();
		});
		t.start();
	}

	@Override
	public void fire() {
		bulletsFired.add(new Projectiles(getX()+50, getY()+100, 5,0, bullets[1],1)); // + 50 for center 100+ for height
	}

}

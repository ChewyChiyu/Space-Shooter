import javax.swing.Timer;

public class WheelShip extends SpaceShips{
	Timer t;
	public WheelShip(int x, int y) {
		super("/Images/WheelShip.png", x, y,100,100,50);
		changeYVelocity(1);
		t = new Timer(150,e->{
			if((int)(Math.random()*2)==1 && getY()>-200)
			fire();
		});
		t.start();
	}

	@Override
	public void fire() {
		bulletsFired.add(new Projectiles(getX()+50, getY()+100, 5,0, (byte) 1,1)); // + 50 for center 100+ for height
	}

}

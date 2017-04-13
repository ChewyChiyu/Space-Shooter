import javax.swing.Timer;

public class StageOneBoss extends SpaceShips{
	Timer onBoard;
	public StageOneBoss(int x, int y) {
		super("/Images/Stage1Boss.png", x, y, 800, 500, 2000);
		onBoard = new Timer(100, e->{
			if(getY()<-320){
				changeYVelocity(1);
			}else
				changeYVelocity(0);
			if(getY()>-520){
				fire();
			}
		});
		onBoard.start();
	}

	@Override
	public void fire() {
		int xVelo = (int)(Math.random()*10);
		xVelo = ((int)(Math.random()*2)==1)?-xVelo:xVelo;
		bulletsFired.add(new Projectiles(getX()+400, getY()+500, 5,xVelo, bullets[1],1)); // + 50 for center 100+ for height
	}
}

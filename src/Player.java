import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Player extends UnicastRemoteObject implements PlayerModel {
	
	private static final long serialVersionUID = -6838003901446012858L;
	private Room room;
	private String name;
	private int x, y;
	private String password;
	
	protected Player(Room x, int i, int j) throws RemoteException {
		super();
		room = x;
		this.x = i;
		this.y = j;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Feedback observe() throws RemoteException {
		return new Feedback(room.toString(), room.describe());
	}

	@Override
	public Feedback moveLeft() {
		String feedback = this.room.updatePosition(this, 0, -1);
		return new Feedback(room.toString(), feedback);
	}

	@Override
	public Feedback moveRight() {
		String feedback = this.room.updatePosition(this, 0, +1);
		return new Feedback(room.toString(), feedback);
	}

	@Override
	public Feedback moveUp() {
		String feedback = this.room.updatePosition(this, -1, 0);
		return new Feedback(room.toString(), feedback);
	}

	@Override
	public Feedback moveDown() {
		String feedback = this.room.updatePosition(this, +1, 0);
		return new Feedback(room.toString(), feedback);
	}

	@Override
	public String toString() {
		return " ⚗ ";
	}

	public String handshake() {
		return "Hey, my name is " + name;
	}
	
}

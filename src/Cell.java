
public class Cell {

	private int x, y;
	private Artifact object;
	private Player player;
	
	public Cell(int x, int y) {
		object = null;
		player = null;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Artifact getObject() {
		return object;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setObject(Artifact object) {
		this.object = object;
	}
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public boolean isOccupied() { return object != null || player != null; }
	public void removeObject() { object = null; }
	public void removePlayer() { player = null; }
	
	@Override
	public String toString() {
		if (object != null)
			return object.toString();
		if (player != null)
			return player.toString();
		return "   ";
	}
	
}

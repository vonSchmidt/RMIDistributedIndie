import java.rmi.RemoteException;
import java.util.ArrayList;


public class Room {

	private Cell [][] cells;
	public final int LENGTH 	= 10;
	public final int WIDTH 		= 10;
	public int number 			= 1;
	
	public ArrayList<Player> players = new ArrayList<Player>();
	
	public final String [] ARTIFACTS = {"FlowerPot", "TV", "Lamp"};
	public final int NBR_OBJECTS = 5;
	
	
	public Room() {
		cells = new Cell [WIDTH][LENGTH];
		for (int i = 0; i < WIDTH; i++)
			for (int j = 0; j < LENGTH; j++)
				cells[i][j] = new Cell(i, j);
		randomize();
	}
	
	public void randomize() {
		for(int i = 0; i < NBR_OBJECTS; i++) {
			int rand = (int)Math.floor(Math.random() * ARTIFACTS.length);
			try {
				Artifact a = (Artifact) Class.forName(ARTIFACTS[rand]).newInstance();
				int x, y;
				do {
					x = (int)Math.floor(Math.random() * LENGTH);
					y = (int)Math.floor(Math.random() * WIDTH);
				} while (x == LENGTH/2 && y == WIDTH/2 && cells[x][y].isOccupied());
				setCellObject(a, x, y);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setCellObject(Artifact a, int x, int y) {
		cells[x][y].setObject(a);
	}
	
	public Player spawnPlayer(String name, String pw) { 
		Player p = null;
		try {
			p = new Player(this, LENGTH/2, WIDTH/2);
			p.setName(name);
			p.setPassword(pw);
			cells[LENGTH/2][WIDTH/2].setPlayer(p);
			registerPlayer(p);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return p;
	}
	public void registerPlayer(Player p) { this.players.add(p); }
	public void unregisterPlayer(Player p) { this.players.remove(p); }
	
	public String updatePosition(Player p, int offsetX, int offsetY) {
		int x = p.getX(),
			y = p.getY();
		
		if (outOfBound(x+offsetX, y+offsetY)) return "Can't go there!";
		if (!cells[x+offsetX][y+offsetY].isOccupied()) {
			// can step there
			p.setX(x+offsetX);
			p.setY(y+offsetY);
			cells[x][y].removePlayer();
			cells[x+offsetX][y+offsetY].setPlayer(p);
			return "";
		} else {
			if (cells[x+offsetX][y+offsetY].getObject() != null)
				return cells[x+offsetX][y+offsetY].getObject().describe();
			return cells[x+offsetX][y+offsetY].getPlayer().handshake();
		}
	}
	
	public boolean outOfBound(int x, int y) {
		return !(x < LENGTH && y < WIDTH
				&& x >=0 && y >= 0);
	}
	
	@Override
	public String toString() {
		String s = "\t┌";
		for (int i = 0; i < LENGTH - 1; i++)
			s += "───┬";
		s += "───┐\n\t│";
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < LENGTH; j++)
				s += cells[i][j] + "│";
			if (i < WIDTH - 1) {
				s += "\n\t├";
				for (int j = 0; j < LENGTH - 1; j++)
					s += "───┼";
				s += "───┤\n\t";
				s += "│";
			}
		}
		s += "\n\t└";
		for (int i = 0; i < LENGTH - 1; i++)
			s += "───┴";
		s += "───┘";
		return s;
	}

	public String describe() {
		return "Room #" + this.number;
	}
	
}

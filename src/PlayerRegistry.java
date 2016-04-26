import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class PlayerRegistry extends UnicastRemoteObject implements PlayerRegistryModel {

	private static final long serialVersionUID = -736915450099728597L;
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private Room room;

	public PlayerRegistry(Room room) throws RemoteException {
		this.room = room;
	}

	public boolean registerPlayer(String name, String password) {
		Player p = room.spawnPlayer(name, password);
		if (players.containsKey(name)) return false;
		players.put(name, p);
		try {
			Naming.rebind("_player_" + name, p);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deletePlayer(String name) {
		try {
			Naming.unbind("_player_" + name);
			players.remove(name);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasPlayer (String name) {
		return players.containsKey(name);
	}

	public PlayerModel signIn(String name, String password) {
		if (!players.containsKey(name)) return null;
		if (players.get(name).getPassword().equals(password)) {
			try {
				return (PlayerModel) Naming.lookup("rmi://localhost/_player_"+name);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		else return null;
	}
}

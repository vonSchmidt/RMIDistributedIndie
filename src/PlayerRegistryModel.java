
public interface PlayerRegistryModel extends java.rmi.Remote {

	public boolean registerPlayer(String name, String password) throws java.rmi.RemoteException;
	public boolean deletePlayer(String name) throws java.rmi.RemoteException;
	public boolean hasPlayer (String name) throws java.rmi.RemoteException;
	public PlayerModel signIn(String name, String password) throws java.rmi.RemoteException;
	
}

import java.rmi.RemoteException;


public interface PlayerModel extends java.rmi.Remote {
	
	Feedback moveLeft() throws RemoteException;
	Feedback moveRight() throws RemoteException;
	Feedback moveUp() throws RemoteException;
	Feedback moveDown() throws RemoteException;
	Feedback observe() throws RemoteException;
	
}

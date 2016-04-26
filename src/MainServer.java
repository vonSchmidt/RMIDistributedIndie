import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;


public class MainServer {

	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		Room x = new Room();

		try {
			PlayerRegistry reg = new PlayerRegistry(x);
			Naming.rebind("RegistryService", reg);
			System.out.println("`RegistryService` is available.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}

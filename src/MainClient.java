import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class MainClient {

	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		PlayerModel p = null;
		PlayerRegistryModel reg;
		@SuppressWarnings("resource")
		Scanner q = new Scanner(System.in);
		try {
			System.out.println("Connecting to the Registry Service");
			reg = (PlayerRegistryModel) Naming.lookup("rmi://" + args[0] + "/RegistryService");
			System.out.println("Successfully connected\n");
			Console console = System.console();
			String name, password;
			name = console.readLine("Username: ");
			System.out.println("Checking username availability...");
			if(reg.hasPlayer(name)) {
				password = new String(console.readPassword("Password: "));
				password = hashPassword(password);
				p = reg.signIn(name, password);
				if (p == null) {
					System.err.println("Couldn't retrieve player");
					System.exit(0);
				}
			} else {
				System.out.println("Would you like to create the player named " + name + "?");
				char c;
				do {
					c = q.next().toLowerCase().trim().charAt(0);
				} while (c != 'y' && c != 'n');
				if (c == 'y') {
					String confirmation;
					do {
					System.out.println("Please enter a new password.");
					password = new String(console.readPassword("Password: "));
					confirmation = new String(console.readPassword("Confirm password: "));
					} while (!confirmation.equals(password));
					password = hashPassword(password);
					if (!reg.registerPlayer(name, password)) {
						System.err.println("Problem! Exiting.");
						System.exit(1);
					}
					p = reg.signIn(name, password);
				} else {
					System.out.println("Ok. Bye bye.");
					System.in.read();
					System.exit(0);
				}
			}
			System.out.println(p.observe());
			String c;
			while(true) {
				System.out.print(" > ");
				c = q.next().trim();
				if (c.equals("quit")) System.exit(0);
				switch (c.charAt(0)) {
				case 'a':
					System.out.println(p.moveLeft());
					break;
				case 'w':
					System.out.println(p.moveUp());
					break;
				case 'd':
					System.out.println(p.moveRight());
					break;
				case 's':
					System.out.println(p.moveDown());
					break;
				default:
					break;
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
	}

	public static String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest c = MessageDigest.getInstance("SHA-1");
		c.reset();
		c.update(password.getBytes("UTF-8"));
		return new BigInteger(1, c.digest()).toString(16);
	}
	
}

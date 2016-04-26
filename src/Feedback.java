import java.io.Serializable;


public class Feedback implements Serializable{

	private static final long serialVersionUID = -5344701052096106178L;
	String room;
	String status;
	
	public Feedback(String room, String status) {
		this.room = room;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "\033[H\033[2J\n\n" + room + "\n\t\t" + status+"\n";
	}
	
}

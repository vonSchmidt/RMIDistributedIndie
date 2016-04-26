
public abstract class Artifact {

	protected Cell cell = null;
	
	public String describe() {
		String name = getClass().getSimpleName();
		return "This is a " + name;
	}
	
	@Override
	public String toString() {
		return " o ";
	}
	
}

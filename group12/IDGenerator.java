package group12;

public class IDGenerator {

	private int id = 0;

	public int next() {
		return id++;
	}
}

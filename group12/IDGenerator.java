package group12;

public class IDGenerator {

	private int individualID = 0;
	private int islandID = 0;

	public int nextIndividual() {
		return individualID++;
	}
	public int nextIsland() { return islandID++; }
}

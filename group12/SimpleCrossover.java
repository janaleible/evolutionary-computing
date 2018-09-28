package group12;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(IDGenerator idGenerator) {
		super(null, idGenerator);
	}

	@Override
	public String toString() {
		return "Simple Crossover";
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

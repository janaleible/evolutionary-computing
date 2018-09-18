package group12;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(IDGenerator idGenerator) {
		super(null, idGenerator);
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

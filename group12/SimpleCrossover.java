package group12;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator) {
		super(crossoverRate, random, idGenerator);
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

package group12;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction) {
		super(crossoverRate, random, idGenerator, rangeFunction);
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

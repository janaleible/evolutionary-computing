package group12;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(IDGenerator idGenerator, RangeFunction rangeFunction) {
		super(null, idGenerator, rangeFunction);
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

package group12;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover() {
		super(null);
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

package group12;

public abstract class Crossover {

	private double crossoverRate;
	protected ExtendedRandom random;
	protected IDGenerator idGenerator;
	protected RangeFunction rangeFunction;

	public Crossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction) {
		this.crossoverRate = crossoverRate;
		this.random = random;
		this.idGenerator = idGenerator;
		this.rangeFunction = rangeFunction;
	}

	protected abstract Individual[] getOffspring(Individual one, Individual another, int generation);

	public Individual[] cross(Individual one, Individual another, int generation) {
		if(this.random.coinflip(this.crossoverRate)) {
			return getOffspring(one, another, generation);
		} else {
			return new Individual[]{one, another};
		}
	}
}

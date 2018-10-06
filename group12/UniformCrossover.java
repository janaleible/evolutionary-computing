package group12;

public class UniformCrossover extends Crossover {

	public UniformCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction) {
		super(crossoverRate, random, idGenerator, rangeFunction);
	}

	@Override
	public String toString() {
		return "Uniform Crossover";
	}

	@Override
    public Individual[] getOffspring(Individual one, Individual another, int generation) {

        Individual[] parents = new Individual[2];
        parents[0] = one;
        parents[1] = another;

        double[][] childGenomes = new double[2][10];

        for (int i = 0; i <10; i++) {
            if(this.random.coinflip()) {
                childGenomes[0][i] = one.genome()[i];
                childGenomes[1][i] = another.genome()[i];
            }
            else {
                childGenomes[0][i] = another.genome()[i];
                childGenomes[1][i] = one.genome()[i];
            }
        }

		return new Individual[] {
			new Individual(childGenomes[0], generation, parents, this.idGenerator, this.rangeFunction),
			new Individual(childGenomes[1], generation, parents, this.idGenerator, this.rangeFunction)
		};
    }
}
package group12;

import java.util.Random;

public class RandomCrossover extends Crossover {

	public RandomCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction) {
		super(crossoverRate, random, idGenerator, rangeFunction);
	}

	@Override
	public Individual[] getOffspring(Individual one, Individual another, int generation) {

		Individual[] parents = {one, another};

		double[][] childGenomes = new double[2][10];

		int cutoff = cutoff();

		for (int i = 0; i < 10; i++) {
			if(i < cutoff) {
				childGenomes[0][i] = one.genome()[i];
				childGenomes[1][i] = another.genome()[i];
			} else {
				childGenomes[0][i] = another.genome()[i];
				childGenomes[1][i] = one.genome()[i];
			}
		}

		return new Individual[]{
			new Individual(childGenomes[0], generation, parents, this.idGenerator, this.rangeFunction),
			new Individual(childGenomes[1], generation, parents, this.idGenerator, this.rangeFunction)
		};
	}

	protected int cutoff() {
		return this.random.nextInt(10);
	}

	@Override
	public String toString() {
		return "Random Crossover";
	}
}

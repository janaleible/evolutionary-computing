package group12;

import java.util.Random;

public class RandomCrossover extends Crossover {

	private Random random;
	private IDGenerator idGenerator;
	private RangeFunction rangeFunction;

	private int cutoff;

	public RandomCrossover(Random random, IDGenerator idGenerator, RangeFunction rangeFunction) {
		this.random = random;
		this.idGenerator = idGenerator;
		this.cutoff = this.random.nextInt(10); // return random number from 0 to 9
		this.rangeFunction = rangeFunction;
	}

	@Override
	public Individual[] cross(Individual one, Individual another, int generation) {

		Individual[] parents = new Individual[2];
		parents[0] = one;
		parents[1] = another;

		double[][] childGenomes = new double[2][10];

		for (int i = 0; i < 10; i++) {
			if(i < cutoff()) {
				childGenomes[0][i] = one.genome()[i];
				childGenomes[1][i] = another.genome()[i];
			} else {
				childGenomes[0][i] = another.genome()[i];
				childGenomes[1][i] = one.genome()[i];
			}
		}

		Individual[] children = new Individual[2];
		children[0] = new Individual(childGenomes[0], generation, parents, this.idGenerator, this.rangeFunction);
		children[1] = new Individual(childGenomes[1], generation, parents, this.idGenerator, this.rangeFunction);

		return children;
	}

	protected int cutoff() {
		return this.cutoff;
	}
}

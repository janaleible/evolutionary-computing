package group12;

import org.vu.contest.ContestEvaluation;

public class ArithmeticCrossover extends Crossover {

	public ArithmeticCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, ContestEvaluation evaluation, EvaluationsCounter counter, double sigma) {
		super(crossoverRate, random, idGenerator, rangeFunction, evaluation, counter, sigma);
	}

	@Override
	public String toString() {
		return "Arithmetic Crossover";
	}

	@Override
	public Individual[] getOffspring(Individual one, Individual another, int generation) {

		double[] childGenome1 = new double[10];
		double[] childGenome2 = new double[10];

		Individual[] parents = {one, another};

		for (int i = 0; i < 10; i++) {
			childGenome1[i] = (parents[0].genome()[i] + parents[1].genome()[i]) / 2;
			childGenome2[i] = (parents[0].genome()[i] + parents[1].genome()[i]) / 2;
		}

		return new Individual[] {
			new Individual(childGenome1, generation, parents, this.idGenerator, this.contestEvaluation, this.evaluationsCounter, this.rangeFunction, this.sigma, random.coinflip() ? Gender.male : Gender.female),
			new Individual(childGenome2, generation, parents, this.idGenerator, this.contestEvaluation, this.evaluationsCounter, this.rangeFunction, this.sigma, random.coinflip() ? Gender.male : Gender.female)
		};
	}
}

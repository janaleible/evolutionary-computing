package group12;

import org.vu.contest.ContestEvaluation;

public class RandomCrossover extends Crossover {

	public RandomCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, ContestEvaluation evaluation, EvaluationsCounter counter, double sigma) {
		super(crossoverRate, random, idGenerator, rangeFunction, evaluation, counter, sigma);
	}

	@Override
	public Individual[] getOffspring(Individual one, Individual another, int generation, double maleIndividualsRatio) {

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

		Individual[] children = new Individual[2];
		children[0] = new Individual(childGenomes[0], generation, parents, this.idGenerator, this.contestEvaluation, this.evaluationsCounter, this.rangeFunction, this.sigma, random.coinflip(maleIndividualsRatio) ? Gender.female : Gender.male);
		children[1] = new Individual(childGenomes[1], generation, parents, this.idGenerator, this.contestEvaluation, this.evaluationsCounter, this.rangeFunction, this.sigma, random.coinflip(maleIndividualsRatio) ? Gender.female : Gender.male);

		return children;
	}

	protected int cutoff() {
		return this.random.nextInt(10);
	}

	@Override
	public String toString() {
		return "Random Crossover";
	}
}

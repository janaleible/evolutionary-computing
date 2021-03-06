package group12;

import org.vu.contest.ContestEvaluation;

public class BlendCrossover extends Crossover {

	private double alpha;

	public BlendCrossover(double alpha, double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, ContestEvaluation evaluation, EvaluationsCounter counter, double sigma) {
		super(crossoverRate, random, idGenerator, rangeFunction, evaluation, counter, sigma);
		this.alpha = alpha;
	}

	public BlendCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, ContestEvaluation evaluation, EvaluationsCounter counter, double sigma) {
		super(crossoverRate, random, idGenerator, rangeFunction, evaluation, counter, sigma);
		this.alpha = 0.5;
	}

	@Override
	public String toString() {
		return "Blend Crossover";
	}

	@Override
	public Individual[] getOffspring(Individual one, Individual another, int generation, double maleIndividualsRatio) {

		double[] childGenome1 = new double[one.genome().length];
		double[] childGenome2 = new double[one.genome().length];

		for (int i = 0; i < one.genome().length; i++) {
			double x = Math.min(one.genome()[i], another.genome()[i]);
			double y = Math.max(one.genome()[i], another.genome()[i]);

			double d = y - x;

			double lowerBound = Math.max(-5, x - this.alpha * d);
			double upperBound = Math.min(5, y + this.alpha * d);

			childGenome1[i] = random.sampleFromRange(lowerBound, upperBound);
			childGenome2[i] = random.sampleFromRange(lowerBound, upperBound);
			// upper bound according to the definition in the original paper, contrary to slide content
		}

		Individual[] parents = {one, another};

		return new Individual[]{
			new Individual(childGenome1, generation, parents, idGenerator, this.contestEvaluation, this.evaluationsCounter, this.rangeFunction, this.sigma, random.coinflip(maleIndividualsRatio) ? Gender.female : Gender.male),
			new Individual(childGenome2, generation, parents, idGenerator, this.contestEvaluation, this.evaluationsCounter, this.rangeFunction, this.sigma, random.coinflip(maleIndividualsRatio) ? Gender.female : Gender.male)
		};
	}
}

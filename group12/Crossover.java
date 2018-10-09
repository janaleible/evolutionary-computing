package group12;

import org.vu.contest.ContestEvaluation;

public abstract class Crossover {

	private double crossoverRate;
	protected ExtendedRandom random;
	protected IDGenerator idGenerator;
	protected RangeFunction rangeFunction;
	protected double sigma;
	protected ContestEvaluation contestEvaluation;
	protected EvaluationsCounter evaluationsCounter;

	public Crossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, ContestEvaluation contestEvaluation, EvaluationsCounter counter, double sigma) {
		this.crossoverRate = crossoverRate;
		this.random = random;
		this.idGenerator = idGenerator;
		this.rangeFunction = rangeFunction;
		this.contestEvaluation = contestEvaluation;
		this.evaluationsCounter = counter;
		this.sigma = sigma;
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

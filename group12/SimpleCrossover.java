package group12;

import org.vu.contest.ContestEvaluation;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(double crossoverRate, ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, ContestEvaluation evaluation, EvaluationsCounter counter, double sigma) {
		super(crossoverRate, random, idGenerator, rangeFunction, evaluation, counter, sigma);
	}

	@Override
	public String toString() {
		return "Simple Crossover";
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

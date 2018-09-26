package group12;

import org.vu.contest.ContestEvaluation;

public class SimpleCrossover extends RandomCrossover {

	public SimpleCrossover(IDGenerator idGenerator, ContestEvaluation contestEvaluation, EvaluationsCounter counter) {
		super(null, idGenerator, contestEvaluation, counter);
	}

	@Override
	protected int cutoff() {
		return 5;
	}
}

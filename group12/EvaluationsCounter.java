package group12;

public class EvaluationsCounter {

	private int evaluationsLimit;
	private int evaluations;

	public EvaluationsCounter(int evaluationsLimit) {
		this.evaluationsLimit = evaluationsLimit;
		this.evaluations = 0;
	}

	public void count() throws EvaluationsLimitExceededException {
		this.evaluations++;
		if (this.evaluationsLimit < this.evaluations) {
			throw new EvaluationsLimitExceededException();
		}
	}

	public int getEvaluations() {
		return evaluations;
	}
}

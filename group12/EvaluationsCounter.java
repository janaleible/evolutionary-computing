package group12;

public class EvaluationsCounter {

	private int evaluationsLimit;
	private int evaluations;

	public EvaluationsCounter(int evaluationsLimit) {
		this.evaluationsLimit = evaluationsLimit;
		this.evaluations = 0;
	}

	public void count() throws EvaluationsLimitExceededException {
		this.evaluationsLimit++;
		if (this.evaluations > this.evaluationsLimit) throw new EvaluationsLimitExceededException();
	}
}

package group12;

import org.vu.contest.ContestEvaluation;

public class Individual {

	private IDGenerator idGenerator;
	private ContestEvaluation contestEvaluation;
	private EvaluationsCounter evaluationsCounter;

	private double[] genotype;
	private Double fitness;
	private int generation;
	private Individual[] parents;
	public final int id;
	private RangeFunction rangeFunction;

	private Gender gender;
	private double sigma;

	public Individual(double[] genotype, int generation, Individual[] parents, IDGenerator idGenerator, ContestEvaluation contestEvaluation, EvaluationsCounter evaluationsCounter, RangeFunction rangeFunction, double sigma, Gender gender) {

		this.idGenerator = idGenerator;
		this.contestEvaluation = contestEvaluation;
		this.evaluationsCounter = evaluationsCounter;
		this.id = this.idGenerator.nextIndividual();
		this.rangeFunction = rangeFunction;

		this.genotype = this.rangeFunction.limitToRange(genotype);
		this.fitness = null;
		this.generation = generation;
		this.parents = parents;

		this.gender = gender;
		this.sigma = sigma;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double[] genome() {
		return this.genotype;
	}


	public void mutateGenome(double[] newGenome){
		this.genotype = this.rangeFunction.limitToRange(newGenome);
	}

	public static Individual createRandom(ExtendedRandom random, IDGenerator idGenerator, ContestEvaluation contestEvaluation, EvaluationsCounter evaluationsCounter, RangeFunction rangeFunction, double sigma, Gender gender) {

		return new Individual(
			random.array(10, -5, 5),
			0,
			null,
			idGenerator,
			contestEvaluation,
			evaluationsCounter,
			rangeFunction,
			sigma,
			gender
		);
	}

	public int generation() {
		return this.generation;
	}

	public Double getFitness() throws EvaluationsLimitExceededException {

		if (this.fitness == null) {
			this.fitness = (Double) this.contestEvaluation.evaluate(this.genotype);
			this.evaluationsCounter.count();
		}

		return this.fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Individual[] parents() {
		return this.parents;
	}

	public Individual clone() {
		Individual clone = new Individual(
			this.genotype.clone(),
			this.generation,
			this.parents,
			this.idGenerator,
			this.contestEvaluation,
			this.evaluationsCounter,
			this.rangeFunction,
			this.sigma,
			this.gender
		);
		clone.setFitness(this.fitness);
		return clone;
	}
		
	public Gender gender() {
		return this.gender;
	}
}
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

	private double sigma;

	public Individual(double[] genotype, int generation, Individual[] parents, IDGenerator idGenerator, ContestEvaluation contestEvaluation, EvaluationsCounter evaluationsCounter) {

		this.idGenerator = idGenerator;
		this.contestEvaluation = contestEvaluation;
		this.evaluationsCounter = evaluationsCounter;
		this.id = this.idGenerator.nextIndividual();

		this.genotype = wrapGenome(genotype);
		this.fitness = null;
		this.generation = generation;
		this.parents = parents;

		this.sigma = 1;
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

	private double[] clipGenome(double[] genome) {
		for(int i = 0; i < genome.length; i++) {
			if (genome[i] > 5) genome[i] = 5;
			if (genome[i] < -5) genome[i] = -5;
		}
		return genome;
	}

	private double[] wrapGenome(double[] genome){
		for(int i = 0; i < genome.length; i++) {
			if (genome[i] > 5){
				double remainder = genome[i] - 5;
				double mod = remainder % 10;
				genome[i] = -5 + mod;
			}
			if (genome[i] < -5){
				double remainder = genome[i] + 5;
				double mod = remainder % 10;
				genome[i] = 5 + mod;
			}
		}
		return genome;
	}

	public void mutateGenome(double[] newGenome){
		this.genotype = wrapGenome(newGenome);
	}

	public static Individual createRandom(ExtendedRandom random, IDGenerator idGenerator, ContestEvaluation contestEvaluation, EvaluationsCounter evaluationsCounter) {
		return new Individual(
			random.array(10, -5, 5),
			0,
			null,
			idGenerator,
			contestEvaluation,
			evaluationsCounter
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
			this.evaluationsCounter
		);
		clone.setFitness(this.fitness);
		return clone;
	}
}
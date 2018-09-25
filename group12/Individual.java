package group12;

import org.vu.contest.ContestEvaluation;

import java.util.Random;

public class Individual {

	private double[] genotype;
	private Double fitness;
	private int generation;
	private Individual[] parents;
	public final int id;

	private double sigma;

	public Individual(double[] genotype, int generation, Individual[] parents, IDGenerator idGenerator) {

		this.id = idGenerator.next();

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

	public static Individual createRandom(ExtendedRandom random, IDGenerator idGenerator) {
		return new Individual(
			random.array(10, -5, 5),
			0,
			null,
			idGenerator
		);
	}

	public int generation() {
		return this.generation;
	}

	public Double evaluate(ContestEvaluation contestEvaluation, EvaluationsCounter evaluationsCounter) throws EvaluationsLimitExceededException {

		if (this.fitness == null) {
			this.fitness = (Double) contestEvaluation.evaluate(this.genotype);
			evaluationsCounter.count();
		}

		return this.fitness;
	}

	public Double getFitness() {
		return fitness;
	}

	public Individual[] parents() {
		return this.parents;
	}
}
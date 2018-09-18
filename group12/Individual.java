package group12;

import org.vu.contest.ContestEvaluation;

import java.util.Random;

public class Individual {

	private double[] genotype;
	private Double fitness;
	private int generation;
	private Individual[] parents;
	public final int id;

	public Individual(double[] genotype, int generation, Individual[] parents, IDGenerator idGenerator) {

		this.id = idGenerator.next();

		this.genotype = clipGenome(genotype);
		this.fitness = null;
		this.generation = generation;
		this.parents = parents;
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

	public static Individual createRandom(Random random, IDGenerator idGenerator) {
		return new Individual(
			randomArray(10, random),
			0,
			null,
			idGenerator
		);
	}

	private static double[] randomArray(int size, Random random) {
		double[] array = new double[size];
		for (int i = 0; i < size; i++) {
			array[i] = (random.nextDouble() * 10) - 5; // scale result to [-5;5]
		}
		return array;
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
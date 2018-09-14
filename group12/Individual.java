package group12;

import java.util.Random;

public class Individual {

	private double[] genotype;
	private Double fitness;
	private int generation;
	private Individual[] parents;

	public Individual(double[] genotype, int generation, Individual[] parents) {
		this.genotype = genotype;
		this.fitness = null;
		this.generation = generation;
		this.parents = parents;
	}

	public double[] genome() {
		return this.genotype;
	}

	public static Individual createRandom(Random random) {
		return new Individual(
			randomArray(10, random),
			0,
			null
		);
	}

	private static double[] randomArray(int size, Random random) {
		double[] array = new double[size];
		for (int i = 0; i < size; i++) {
			array[i] = random.nextDouble();
		}
		return array;
	}

	public boolean hasFitness() {
		return this.fitness != null;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	public int generation() {
		return this.generation;
	}
}
package group12;

import org.vu.contest.ContestEvaluation;

import java.util.Random;

public class Individual {

	private double[] genotype;
	private Double fitness;
	private int generation;
	private Individual[] parents;
	public final int id;
	private RangeFunction rangeFunction;

	private Gender gender;
	private double sigma;

	public Individual(double[] genotype, int generation, Individual[] parents, IDGenerator idGenerator, RangeFunction rangeFunction, double sigma, Gender gender) {

		this.id = idGenerator.next();
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

	public static Individual createRandom(ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction, double sigma) {

		Gender gender = random.coinflip() ? Gender.male : Gender.female;

		return new Individual(
			random.array(10, -5, 5),
			0,
			null,
			idGenerator,
			rangeFunction,
			sigma,
			gender
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

	public Gender gender() {
		return this.gender;
	}
}
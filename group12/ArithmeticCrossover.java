package group12;

import org.vu.contest.ContestEvaluation;

import java.util.Random;

public class ArithmeticCrossover extends Crossover {

	private Random random;
	private IDGenerator idGenerator;
	private ContestEvaluation contestEvaluation;
	private EvaluationsCounter evaluationsCounter;

	public ArithmeticCrossover(Random random, IDGenerator idGenerator, ContestEvaluation contestEvaluation, EvaluationsCounter counter) {
		this.random = random;
		this.idGenerator = idGenerator;
		this.contestEvaluation = contestEvaluation;
		this.evaluationsCounter = counter;
	}

	@Override
	public Individual[] cross(Individual one, Individual another, int generation) {

		double[] childGenome1 = new double[10];
		double[] childGenome2 = new double[10];

		Individual[] parents = new Individual[2];
		parents[0] = one;
		parents[1] = another;

		for (int i = 0; i < 10; i++) {
			childGenome1[i] = minMaxMean(one.genome()[i], another.genome()[i]);
			childGenome2[i] = minMaxMean(one.genome()[i], another.genome()[i]);
		}

		Individual[] children = new Individual[2];
		children[0] = new Individual(childGenome1, generation, parents, this.idGenerator, this.contestEvaluation, this.evaluationsCounter);
		children[1] = new Individual(childGenome2, generation, parents, this.idGenerator, this.contestEvaluation, this.evaluationsCounter);

		return children;

	}

	private double minMaxMean(double first, double second) {

		switch (this.random.nextInt(3)) {
			case 0: return (first + second) / 2;
			case 1: return Math.max(first, second);
			case 2: return Math.min(first, second);
			default: return 0; // never happens, required to make compiler happy :)
		}
	}
}

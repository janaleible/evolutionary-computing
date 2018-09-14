package group12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;

public class FitnessProportionalSelection extends ParentSelection {

	private Random random;

	public FitnessProportionalSelection(Random random) {
		this.random = random;
	}

	@Override
	public ArrayList<Individual> select(int numberOfParents, Population population) {

		DoubleStream fitnesses = population.iterable().stream().mapToDouble(individual -> individual.getFitness());
		double totalFitness = fitnesses.sum();


		double[] picks = new double[numberOfParents];
		for (int i = 0; i < numberOfParents; i++) {
			picks[i] = this.random.nextDouble();
		}
		Arrays.sort(picks);

		ArrayList<Individual> parents = new ArrayList<>(numberOfParents);
		double runningFitnessTotal = 0;
		int pickIndex = 0;
		for (Individual individual : population.iterable()) {
			runningFitnessTotal += (individual.getFitness() / totalFitness);
			while (picks[pickIndex] < runningFitnessTotal) {
				parents.add(individual);
				pickIndex++;
				if (pickIndex >= numberOfParents) { break; }
			}
			if (pickIndex >= numberOfParents) { break; }
		}

		return parents;
	}
}

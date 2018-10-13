package group12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FitnessProportionalSelection extends Selection {

	@Override
	public String toString() {
		return "Fitness-proportional Selection";
	}

	private Random random;

	public FitnessProportionalSelection(Random random) {
		this.random = random;
	}

	@Override
	public List<Individual> select(int numberOfPicks, List<Individual> population) {

		if (numberOfPicks == 0) return new ArrayList<Individual>();

		double totalFitness = population.stream().mapToDouble(Individual::getFitness).sum();
		
		double[] picks = new double[numberOfPicks];
		for (int i = 0; i < numberOfPicks; i++) {
			picks[i] = this.random.nextDouble();
		}
		Arrays.sort(picks);

		List<Individual> parents = new ArrayList<>(numberOfPicks);
		if (totalFitness == 0) { // pick uniformly if all fitnesses are 0
			for (int i = 0; i < numberOfPicks; i++) {
				parents.add(population.get(this.random.nextInt(population.size())));
			}
			System.out.println("fitness 0");
		} else {
			double runningFitnessTotal = 0;
			int pickIndex = 0;
			for (Individual individual : population) {
				runningFitnessTotal += (individual.getFitness() / totalFitness);
				while (picks[pickIndex] < runningFitnessTotal) {
					parents.add(individual);
					pickIndex++;
					if (pickIndex >= numberOfPicks) {
						break;
					}
				}
				if (pickIndex >= numberOfPicks) {
					break;
				}
			}
		}

		return parents;
	}
}

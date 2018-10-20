package group12;

import java.util.*;
import java.util.stream.Collectors;

public class Archipelago {

	private final DiversityMeasure diversityMeasure;
	private List<Population> islands;
	private ExtendedRandom random;

	public Archipelago(ExtendedRandom random, List<Population> islands, DiversityMeasure diversityMeasure) {
		this.random = random;
		this.islands = islands;
		this.diversityMeasure = diversityMeasure;
	}

	public List<Population> islands() {
		return this.islands;
	}

	public void migration(int numberOfMigrants) {

		Collections.shuffle(this.islands, this.random);

		for (Population island : this.islands) {
			island.iterable().sort(Comparator.comparingDouble(Individual::getFitness));
		}

 		for (int i = 0; i < this.islands.size(); i++) {

 			List<Individual> fromIsland = this.islands.get(i).iterable();
 			List<Individual> toIsland = this.islands.get(i > 0 ? i - 1 : this.islands.size() - 1).iterable();

			toIsland.subList(0, numberOfMigrants).clear();

			List<Individual> immigrants = new ArrayList<>(numberOfMigrants);
			int numberOfInhabitants = fromIsland.size();
			for (Individual immigrant : fromIsland.subList(numberOfInhabitants - numberOfMigrants - 1, numberOfInhabitants - 1)) {
				immigrants.add(immigrant.clone());
			}
			toIsland.addAll(immigrants);
		}
	}

	public double getMaximumFitness() {
		return this.islands().stream().mapToDouble(Population::getMaximumFitness).max().orElse(-1000000);
	}

	public double getAverageFitness() {
		return this.islands().stream().mapToDouble(Population::getAverageFitness).average().orElse(-100000);
	}

	public double getAverageAge(int generation) {
		return this.islands().stream().mapToDouble(population -> population.getAverageAge(generation)).average().orElse(-1000000);
	}

	public double getDiversity() {
		return this.diversityMeasure.measure(
			this.islands().stream().map(Population::iterable).flatMap(List::stream).collect(Collectors.toList())
		);
	}
}

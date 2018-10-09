package group12;

import java.util.*;

public class Archipelago {

	private List<Population> islands;
	private ExtendedRandom random;

	public Archipelago(ExtendedRandom random, List<Population> islands) {
		this.random = random;
		this.islands = new ArrayList<>(islands.size());
		this.islands.addAll(islands);
	}

	public List<Population> islands() {
		return this.islands;
	}

	public void migration(double migrationRate) {

		Collections.shuffle(this.islands, this.random);

		for (Population island : this.islands) {
			island.iterable().sort(Comparator.comparingDouble(Individual::getFitness));
		}

 		for (int i = 0; i < this.islands.size(); i++) {

 			List<Individual> fromIsland = this.islands.get(i).iterable();
 			List<Individual> toIsland = this.islands.get(i > 0 ? i - 1 : this.islands.size() - 1).iterable();

 			int numberOfImmigrants = (int)Math.ceil(toIsland.size() * migrationRate);
			toIsland.subList(0, numberOfImmigrants).clear();

			List<Individual> immigrants = new ArrayList<>(numberOfImmigrants);
			int numberOfInhabitants = fromIsland.size();
			for (Individual immigrant : fromIsland.subList(numberOfInhabitants - numberOfImmigrants - 1, numberOfInhabitants - 1)) {
				immigrants.add(immigrant.clone());
			}
			toIsland.addAll(immigrants);
		}
	}
}

package group12;

import java.util.ArrayList;
import java.util.List;

public class AgeBasedSurvivorSelection extends Selection {

	@Override
	public String toString() {
		return "Age-based Survivor Selection";
	}

	@Override
	public List<Individual> select(int numberOfPicks, List<Individual> population) {

		// sort oldest individuals to beginning
		population.sort((o1, o2) -> o2.generation() - o1.generation());

		ArrayList<Individual> survivors = new ArrayList<>(numberOfPicks);

		int pick = -1;
		while (++pick < numberOfPicks) {
			survivors.add(population.get(pick));
		}

		return survivors;
	}
}

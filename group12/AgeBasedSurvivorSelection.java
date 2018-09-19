package group12;

import java.util.ArrayList;

public class AgeBasedSurvivorSelection extends SurvivorSelection {

	@Override
	public ArrayList<Individual> select(int numberOfPicks, Population population) {

		// sort oldest individuals to beginning
		population.iterable().sort((o1, o2) -> o2.generation() - o1.generation());

		ArrayList<Individual> survivors = new ArrayList<>(numberOfPicks);

		int pick = -1;
		while (++pick < numberOfPicks) {
			survivors.add(population.iterable().get(pick));
		}

		return survivors;
	}
}

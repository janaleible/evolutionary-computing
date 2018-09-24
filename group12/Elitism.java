package group12;

import java.util.Comparator;
import java.util.List;

public class Elitism extends Selection {

	private Selection selection;
	private int sizeOfElite;

	public Elitism(Selection selection, int sizeOfElite) {
		this.selection = selection;
		this.sizeOfElite = sizeOfElite;
	}

	@Override
	public List<Individual> select(int numberOfPicks, List<Individual> population) {

		population.sort(Comparator.comparingDouble(Individual::getFitness).reversed());
		List<Individual> elite = population.subList(0, sizeOfElite);
		List<Individual> commoners = this.selection.select(numberOfPicks - sizeOfElite, population.subList(sizeOfElite, population.size() - 1));

		commoners.addAll(elite);

		return commoners;
	}

}

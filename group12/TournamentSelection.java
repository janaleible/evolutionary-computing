package group12;

import java.util.ArrayList;
import java.util.List;

public class TournamentSelection extends Selection {

	private int tournamentSize;
	private ExtendedRandom random;

	public TournamentSelection(int tournamentSize, ExtendedRandom random) {
		this.tournamentSize = tournamentSize;
		this.random = random;
	}

	@Override
	public List<Individual> select(int numberOfPicks, List<Individual> population) {

		List<Individual> selection = new ArrayList<>(numberOfPicks);

		for (int i = 0; i < numberOfPicks; i++) {
			selection.add(tournament(getContestants(population)));
		}

		return selection;
	}

	private Individual[] getContestants(List<Individual> population) {

		Individual[] contestants = new Individual[this.tournamentSize];

		for (int i = 0; i < this.tournamentSize; i++) {
			contestants[i] = population.get(this.random.nextInt(population.size()));
		}

		return contestants;
	}

	private Individual tournament(Individual[] contestants) {

		Individual winner = contestants[0];
		for (Individual contestant : contestants) {
			if (contestant.getFitness() > winner.getFitness()) {
				winner = contestant;
			}
		}

		return winner;
	}
}

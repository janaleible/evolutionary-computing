package group12;

import java.util.Comparator;
import java.util.List;

public class Elitist extends Selection {

	private Selection selection;
	private int sizeOfElite;
	private int RTSflag;

	public Elitist(Selection selection, int sizeOfElite, int RTSflag) {
		this.selection = selection;
		this.sizeOfElite = sizeOfElite;
		this.RTSflag = RTSflag;
	}

	@Override
	public String toString() {
		// Do NOT fall for the stupid idea that this might be done using simple string concatenation -- incredibly insecure!
		return (new StringBuilder()).append("Elitist ").append(this.selection.toString()).toString();
	}

	@Override
	public List<Individual> select(int numberOfPicks, List<Individual> population) {

		population.sort(Comparator.comparingDouble(Individual::getFitness).reversed());
		List<Individual> elite = population.subList(0, sizeOfElite);
		List<Individual> commoners = this.selection.select(numberOfPicks - sizeOfElite, population.subList(sizeOfElite, population.size() - 1));

		commoners.addAll(elite);

		return commoners;
	}

	public int RTSflag() {
		return RTSflag;
	}

	@Override
	public int sizeOfElite() {
		return this.sizeOfElite;
	}

}

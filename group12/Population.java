package group12;

import java.util.ArrayList;
import java.util.Random;

public class Population {

	private ParentSelection parentSelection;
	private SurvivorSelection survivorSelection;
	private ArrayList<Individual> population;

	private Random random;

	public Population(
		ParentSelection parentSelection,
		SurvivorSelection survivorSelection,
		Random random,
		int populationSize
	) {
		this.parentSelection = parentSelection;
		this.survivorSelection = survivorSelection;

		this.random = random;

		intialisePopulation(populationSize);
	}

	private void intialisePopulation(int populationSize) {

		System.out.println("initialising population");

		for (int i = 0; i < populationSize; i++) {
			this.population.add(Individual.createRandom(this.random));
		}
	}

	public ArrayList<Individual> selectParents(int numberOfPicks) {
		return this.parentSelection.select(numberOfPicks, this);
	}

	public ArrayList<Individual> selectSurvivors(int numberOfPicks) {
		return this.survivorSelection.select(numberOfPicks, this);
	}

	public void replace(ArrayList<Individual> survivors, ArrayList<Individual> offspring) {
		this.population = new ArrayList<>(survivors.size() + offspring.size());
		this.population.addAll(survivors);
		this.population.addAll(offspring);
	}

	public ArrayList<Individual> iterable() {
		return this.population;
	}

}

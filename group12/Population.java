package group12;

import java.util.ArrayList;

public class Population {

	private ParentSelection parentSelection;
	private SurvivorSelection survivorSelection;
	private ArrayList<Individual> population;

	private ExtendedRandom random;
	private IDGenerator idGenerator;
	private DiversityMeasure diversityMeasure;

	private int populationSize;

	public Population(
		IDGenerator idGenerator,
		ParentSelection parentSelection,
		SurvivorSelection survivorSelection,
		DiversityMeasure diversityMeasure,
		ExtendedRandom random,
		int populationSize
	) {
		this.parentSelection = parentSelection;
		this.survivorSelection = survivorSelection;
		this.diversityMeasure = diversityMeasure;

		this.random = random;
		this.idGenerator = idGenerator;

		this.populationSize = populationSize;

		intialisePopulation(this.populationSize);
	}

	private void intialisePopulation(int populationSize) {

		this.population = new ArrayList<>(populationSize);

		for (int i = 0; i < populationSize; i++) {
			this.population.add(Individual.createRandom(this.random, this.idGenerator));
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

	public double getAverageFitness() {
		return this.population.stream().mapToDouble(Individual::getFitness).average().orElse(-10000);
	}

	public double getMaximumFitness() {
		return this.population.stream().mapToDouble(individual -> individual.getFitness() != null ? individual.getFitness() : -100000).max().orElse(-10000);
	}

	public double getAverageAge(int currentGeneration) {
		return this.population.stream().mapToInt(individual -> currentGeneration - individual.generation()).average().orElse(-10000);
	}

	public double getDiversity() {
		return this.diversityMeasure.measure(this);
	}

	public Individual getFittestIndividual() {
		return this.population.stream().filter(individual -> individual.getFitness() == this.getMaximumFitness()).findAny().orElse(null);
	}

	public int getPopulationSize() {
		return this.populationSize;
	}
}

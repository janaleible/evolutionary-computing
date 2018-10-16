package group12;

import org.vu.contest.ContestEvaluation;

import java.util.ArrayList;
import java.util.List;

public class Population {

	private Selection parentSelection;
	private Selection survivorSelection;
	private ArrayList<Individual> population;

	private ExtendedRandom random;
	private IDGenerator idGenerator;
	private DiversityMeasure diversityMeasure;

	public final int islandID;

	private int populationSize;
	private RangeFunction rangeFunction;

	private double sigma;

	public Population(
		IDGenerator idGenerator,
		ContestEvaluation contestEvaluation,
		EvaluationsCounter evaluationsCounter,
		Selection parentSelection,
		Selection survivorSelection,
		DiversityMeasure diversityMeasure,
		ExtendedRandom random,
		int populationSize,
		RangeFunction rangeFunction,
		double sigma
	) {
		this.parentSelection = parentSelection;
		this.survivorSelection = survivorSelection;
		this.diversityMeasure = diversityMeasure;

		this.random = random;
		this.idGenerator = idGenerator;

		this.populationSize = populationSize;
		this.islandID = this.idGenerator.nextIsland();

		this.rangeFunction = rangeFunction;
		this.sigma = sigma;

		intialisePopulation(this.populationSize, contestEvaluation, evaluationsCounter);
	}

	private void intialisePopulation(int populationSize, ContestEvaluation contestEvaluation, EvaluationsCounter counter) {

		this.population = new ArrayList<>(populationSize);

		for (int i = 0; i < populationSize; i++) {
			this.population.add(Individual.createRandom(
				this.random,
				this.idGenerator,
				contestEvaluation,
				counter,
				this.rangeFunction,
				this.sigma,
				this.random.coinflip(1 - maleIndividualsRatio(population)) ? Gender.male : Gender.female
			));
		}
	}

	private double maleIndividualsRatio(List<Individual> population) {

		if(population.size() == 0) return 0.5;

		return (double)population.stream().filter(individual -> individual.gender() == Gender.male).count() / (double) population.size();
	}

	public List<Individual> selectParents(int numberOfPicks) {
		return this.parentSelection.select(numberOfPicks, this.population);
	}

	public List<Individual> selectSurvivors(int numberOfPicks) {
		return this.survivorSelection.select(numberOfPicks, this.population);
	}

	public boolean getRTSflag() { return this.survivorSelection.RTSflag(); }

	public void replace(List<Individual> survivors, List<Individual> offspring) {
		this.population = new ArrayList<>(survivors.size() + offspring.size());
		this.population.addAll(survivors);
		this.population.addAll(offspring);
	}

	public ArrayList<Individual> iterable() {
		return this.population;
	}

	public double getAverageFitness() throws EvaluationsLimitExceededException {
		return this.population.stream().mapToDouble(Individual::getFitness).average().orElse(-10000);
	}

	public double getMaximumFitness() throws EvaluationsLimitExceededException {
		return this.population.stream().mapToDouble(individual -> individual.getFitness() != null ? individual.getFitness() : -100000).max().orElse(-10000);
	}

	public double getAverageAge(int currentGeneration) {
		return this.population.stream().mapToInt(individual -> currentGeneration - individual.generation()).average().orElse(-10000);
	}

	public double getDiversity() {
		return this.diversityMeasure.measure(this);
	}

	public Individual getFittestIndividual() throws EvaluationsLimitExceededException {
		return this.population.stream().filter(individual -> individual.getFitness() == this.getMaximumFitness()).findAny().orElse(null);
	}

	public int getPopulationSize() {
		return this.populationSize;
	}
}

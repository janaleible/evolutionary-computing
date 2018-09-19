import group12.*;
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import visual.PopulationVisualiser;

import java.util.*;

public class Player12 implements ContestSubmission {

	private Random random;
	private ContestEvaluation contestEvaluation;
	private EvaluationsCounter evaluationsCounter;

	private IDGenerator idGenerator;

	private Crossover crossover;
	private Mutation mutation;

	private ParentSelection parentSelection;
	private SurvivorSelection survivorSelection;

	private Population population;
	private Map<Integer, Individual> ancestry;

	private PopulationStatistics populationStatistics;

	public Player12() {

		this.populationStatistics = new PopulationStatistics();

		this.random = new Random();
		this.idGenerator = new IDGenerator();

		// TODO: make used implementations configurable
		this.crossover = new ArithmeticCrossover(this.random, this.idGenerator);
		this.mutation = new RandomMutation();

		this.parentSelection = new FitnessProportionalSelection(this.random);
		this.survivorSelection = new AgeBasedSurvivorSelection();

		this.population = new Population(
			this.idGenerator,
			this.parentSelection,
			this.survivorSelection,
			null,//this.diversityMeasure,
			this.random,
			64
		);

		this.ancestry = new HashMap<>();
		for (Individual individual : this.population.iterable()) {
			this.ancestry.put(individual.id, individual);
		}
	}

	public void setSeed(long seed) {
		this.random.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation) {

		// Set evaluation problem used in the run
		this.contestEvaluation = evaluation;

		// Get evaluation properties
		Properties properties = evaluation.getProperties();

		int evaluationsLimit = Integer.parseInt(properties.getProperty("Evaluations"));
		evaluationsLimit = 300;
        this.evaluationsCounter = new EvaluationsCounter(evaluationsLimit);

        // Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(properties.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(properties.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(properties.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal) {
            // Do sth
        } else {
            // Do sth else
        }
    }

	public void run() {

		int generation = 0;

		try {
			while (true) {

				generation++;

				for(Individual individual : this.population.iterable()) {
					individual.evaluate(this.contestEvaluation, this.evaluationsCounter);
				}

				this.populationStatistics.update(generation, this.population.getMaximumFitness(), this.population.getAverageFitness(), this.population.getAverageAge(generation));

				// TODO: make reproduction method on population
				// TODO: find generic way to set parameters
				ArrayList<Individual> parents = this.population.selectParents(12);
				ArrayList<Individual> offspring = new ArrayList<>(parents.size());

				Collections.shuffle(parents); // make sure that random parents mate
				for (int i = 0; i < (parents.size() / 2); i++) {
					Individual[] children = this.crossover.cross(parents.get(i), parents.get(i + (parents.size() / 2)), generation);
					offspring.add(this.mutation.mutate(children[0]));
					offspring.add(this.mutation.mutate(children[1]));
					this.ancestry.put(children[0].id, children[0]);
					this.ancestry.put(children[1].id, children[1]);
				}

				ArrayList<Individual> survivors = this.population.selectSurvivors(this.population.iterable().size() - offspring.size());

				this.population.replace(survivors, offspring);
			}

		} catch (EvaluationsLimitExceededException exception) {
			// TODO: think of better solution

			PopulationVisualiser.visualise("population", this.ancestry, this.population.getFittestIndividual());
			this.populationStatistics.write();

			return;
		}
	}
}

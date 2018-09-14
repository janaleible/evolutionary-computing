import group12.*;
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.*;

public class Player12 implements ContestSubmission {

	private Random random;
	private ContestEvaluation contestEvaluation;
	private EvaluationsCounter evaluationsCounter;

	private Crossover crossover;
	private Mutation mutation;

	private ParentSelection parentSelection;
	private SurvivorSelection survivorSelection;

	private Population population;

	public Player12() {

		this.random = new Random();

		// TODO: make used implementations configurable
		this.crossover = new SimpleCrossover();
		this.mutation = new NoMutation();

		this.parentSelection = new FitnessProportionalSelection(this.random);
		this.survivorSelection = new AgeBasedSurvivorSelection();

		this.population = new Population(
			this.parentSelection,
			this.survivorSelection,
			this.random,
			128
		);
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

		System.out.println("Calling run()");

		try {

			// TODO: make iterative
			for(Individual individual : this.population.iterable()) {
				individual.setFitness((Double)this.contestEvaluation.evaluate(individual.genome()));
				this.evaluationsCounter.count();
			}

			// TODO: make reproduction method on population
			// TODO: find generic way to set parameters
			ArrayList<Individual> parents = this.population.selectParents(26);
			ArrayList<Individual> offspring = new ArrayList<>(parents.size());

			Collections.shuffle(parents); // make sure that random parents mate
			for (int i = 0; i < (parents.size() / 2); i++) {
				Individual child1 = this.crossover.cross(parents.get(i), parents.get(i + (parents.size() / 2)), 1);
				Individual child2 = this.crossover.cross(parents.get(i), parents.get(i + (parents.size() / 2)), 1);
				offspring.add(this.mutation.mutate(child1));
				offspring.add(this.mutation.mutate(child2));
			}

			ArrayList<Individual> survivors = this.population.selectSurvivors(128 - 26);

			this.population.replace(survivors, offspring);

		} catch (EvaluationsLimitExceededException exception) {
			// TODO: think of better solution
			return;
		}
	}
}

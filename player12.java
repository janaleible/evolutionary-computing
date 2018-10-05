import group12.*;
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.*;

public class player12 implements ContestSubmission {

	private DiversityMeasure diversityMeasure;
	private ExtendedRandom random;
	private ContestEvaluation contestEvaluation;
	private EvaluationsCounter evaluationsCounter;

	private IDGenerator idGenerator;

	private Crossover crossover;
	private Mutation mutation;

	private Selection parentSelection;
	private Selection survivorSelection;

	private PopulationStatistics populationStatistics;

	private RangeFunction rangeFunction;

	public player12() {

		this.populationStatistics = new PopulationStatistics();

		this.random = new ExtendedRandom();
		this.idGenerator = new IDGenerator();
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

		this.rangeFunction = new ClipRange(-5, 5);

		// TODO: make used implementations configurable
		this.crossover = new ArithmeticCrossover(0.8, this.random, this.idGenerator, this.rangeFunction, this.contestEvaluation, this.evaluationsCounter);
		this.mutation = new AdaptiveMutation(this.random);

		this.parentSelection = new FitnessProportionalSelection(this.random);
		this.survivorSelection = new Elitist(new FitnessProportionalSelection(this.random), 5);

		this.diversityMeasure = new InertiaDiversityMeasure();


		int generation = 0;

		Population population = new Population(
			this.idGenerator,
			this.contestEvaluation,
			this.evaluationsCounter,
			this.parentSelection,
			this.survivorSelection,
			this.diversityMeasure,
			this.random,
			128,
			this.rangeFunction
		);

		Population population2 = new Population(
			this.idGenerator,
			this.contestEvaluation,
			this.evaluationsCounter,
			this.parentSelection,
			this.survivorSelection,
			this.diversityMeasure,
			this.random,
			128,
				this.rangeFunction
		);

		Archipelago galapagos = new Archipelago(this.random, population, population2);

		Map<Integer, Individual> ancestry = new HashMap<>();
		for (Individual individual : population.iterable()) {
			ancestry.put(individual.id, individual);
		}

		try {
			while (true) {

				generation++;

				for (Population island : galapagos.islands()) {

					this.populationStatistics.update(
						generation,
						island.islandID,
						island.getMaximumFitness(),
						island.getAverageFitness(),
						island.getAverageAge(generation),
						island.getDiversity()
					);

					// TODO: make reproduction method on population
					// TODO: find generic way to set parameters
					List<Individual> parents = island.selectParents(64);
					List<Individual> offspring = new ArrayList<>(parents.size());

					Collections.shuffle(parents, this.random); // make sure that random parents mate
					for (int i = 0; i < (parents.size() / 2); i++) {
						Individual[] children = this.crossover.cross(parents.get(i), parents.get(i + (parents.size() / 2)), generation);
						offspring.add(this.mutation.mutate(children[0]));
						offspring.add(this.mutation.mutate(children[1]));
						ancestry.put(children[0].id, children[0]);
						ancestry.put(children[1].id, children[1]);
					}

					List<Individual> survivors = island.selectSurvivors(island.iterable().size() - offspring.size());

					island.replace(survivors, offspring);
				}

				if (generation % 10 == 0) galapagos.migration(0.02);
			}

		} catch (EvaluationsLimitExceededException exception) {
			// TODO: think of better solution

			//PopulationVisualiser.visualise("population", this.ancestry, island.getFittestIndividual());
			//this.populationStatistics.write();

			return;
		}
	}
}

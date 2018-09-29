import group12.*;
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.*;

public class player12 implements ContestSubmission {

	private ExtendedRandom random;
	private ContestEvaluation contestEvaluation;
	private EvaluationsCounter evaluationsCounter;

	private IDGenerator idGenerator;
	private DiversityMeasure diversityMeasure;
	private PopulationStatistics populationStatistics;
	private long seed;
	private int configID;

	private String function;

	public player12() {

		this.populationStatistics = new PopulationStatistics();

		this.random = new ExtendedRandom();
		this.idGenerator = new IDGenerator();

		this.diversityMeasure = new InertiaDiversityMeasure();
	}

	public void setSeed(long param) {

		// Don't judge me, I'm desperate. Can't see any other way to get a parameter passed to the algorithm.
		// Pass seed and configID concatenated as long, where the last two digits are the seed and everything else is the configID

		this.seed = param % 100;
		this.random.setSeed(this.seed);

		this.configID = (int)(param / 100);
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

        if (!isMultimodal && !hasStructure && !isSeparable) this.function = "BentCigar";
        else if (!hasStructure) this.function = "Katsuura";
        else if (isMultimodal) this.function = "Schaffers";
        else this.function = "Sphere";
    }

	public void run() {

		ConfigurationsFactory.Configuration config = (new ConfigurationsFactory(this.random, this.idGenerator)).get(this.configID);

		int generation = 0;

		Population population = new Population(
				this.idGenerator,
				config.parentSelection,
				config.survivorSelection,
				this.diversityMeasure,
				this.random,
				config.populationSize
		);

		Map<Integer, Individual> ancestry = new HashMap<>();
		for (Individual individual : population.iterable()) {
			ancestry.put(individual.id, individual);
		}

		try {
			while (true) {

				generation++;

				for(Individual individual : population.iterable()) {
					individual.evaluate(this.contestEvaluation, this.evaluationsCounter);
				}

				this.populationStatistics.update(
					generation,
					population.getMaximumFitness(),
					population.getAverageFitness(),
					population.getAverageAge(generation),
					population.getDiversity()
				);

				List<Individual> parents = population.selectParents(config.childrenPerGeneration - config.survivorSelection.sizeOfElite());
				List<Individual> offspring = new ArrayList<>(parents.size());

				Collections.shuffle(parents, this.random); // make sure that random parents mate
				for (int i = 0; i < (parents.size() / 2); i++) {
					Individual[] children = config.crossover.cross(parents.get(i), parents.get(i + (parents.size() / 2)), generation);
					offspring.add(config.mutation.mutate(children[0]));
					offspring.add(config.mutation.mutate(children[1]));
					ancestry.put(children[0].id, children[0]);
					ancestry.put(children[1].id, children[1]);
				}

				List<Individual> survivors = population.selectSurvivors(population.iterable().size() - offspring.size());

				population.replace(survivors, offspring);
			}

		} catch (EvaluationsLimitExceededException exception) {
			// TODO: think of better solution

			//PopulationVisualiser.visualise("population", this.ancestry, this.population.getFittestIndividual());
			//this.populationStatistics.write();
			System.out.print(config.toString());

			return;
		}
	}
}

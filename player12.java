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

	private String function;

	public player12() {

		this.populationStatistics = new PopulationStatistics();

		this.random = new ExtendedRandom();
		this.idGenerator = new IDGenerator();

		this.diversityMeasure = new InertiaDiversityMeasure();
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

        if (!isMultimodal && !hasStructure && !isSeparable) this.function = "BentCigar";
        else if (!hasStructure) this.function = "Katsuura";
        else if (isMultimodal) this.function = "Schaffers";
        else this.function = "Sphere";
    }

	public void run() {

		Configuration config = new Configuration(this.random, this.idGenerator, this.function);

		int generation = 0;

		Population population = new Population(
			this.idGenerator,
			config.parentSelection,
			config.survivorSelection,
			this.diversityMeasure,
			this.random,
			config.populationSize,
			config.rangeFunction,
			config.sigma_adaptiveMutation
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

				List<Individual> parents = population.selectParents((int) (config.generationGap * (config.populationSize - config.survivorSelection.sizeOfElite())));
				List<Individual> offspring = new ArrayList<>(parents.size());

				ParentMatching parentMatching = new GenderAwareParentMatching();

				for (Individual[] couple : parentMatching.getMatches(parents)) {
					Individual[] children = config.crossover.cross(couple[0], couple[1], generation);
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

			//PopulationVisualiser.visualise("population", ancestry, this.population.getFittestIndividual());
			//this.populationStatistics.write();

			System.out.println(config.toString());

			return;
		}
	}
}

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

		Configuration config = new Configuration(this.random, this.idGenerator, this.function, this.contestEvaluation, this.evaluationsCounter);

		int generation = 0;

		List<Population> islands = new ArrayList<>(config.numberOfIslands);

		for (int i = 0; i < config.numberOfIslands; i++) {
			islands.add(new Population(
				this.idGenerator,
				this.contestEvaluation,
				this.evaluationsCounter,
				config.parentSelection,
				config.survivorSelection,
				this.diversityMeasure,
				this.random,
				config.populationSize,
				config.rangeFunction,
				config.sigma_adaptiveMutation
			));
		}

		Archipelago galapagos = new Archipelago(this.random, islands, this.diversityMeasure);
		Map<Integer, Individual> ancestry = new HashMap<>();

		try {
			while (true) {

				generation++;

				for (Population island : galapagos.islands()) {

					for (Individual individual : island.iterable()) {
						ancestry.put(individual.id, individual);
					}

					List<Individual> parents = island.selectParents((int) (config.generationGap * (config.populationSize - config.survivorSelection.sizeOfElite())));
					List<Individual> offspring = new ArrayList<>(parents.size());

					for (Individual[] couple : config.parentMatching.getMatches(parents)) {
						Individual[] children = config.crossover.cross(couple[0], couple[1], generation);
						offspring.add(config.mutation.mutate(children[0]));
						offspring.add(config.mutation.mutate(children[1]));
						ancestry.put(children[0].id, children[0]);
						ancestry.put(children[1].id, children[1]);
					}

					if (offspring.size() > config.populationSize) {
						offspring = offspring.subList(0, config.populationSize);
					}

					if (island.getRTSflag()) {
						island.replace(island.iterable(), offspring);
						List<Individual> survivors = island.selectSurvivors(offspring.size());
						island.replace(survivors, new ArrayList<>());
					} else {
						List<Individual> survivors = island.selectSurvivors(island.iterable().size() - offspring.size());

						island.replace(survivors, offspring);
					}
				}

				if(
					config.numberOfIslands > 1
					&& (generation + 1) % config.generationsPerEpoch == 0
				) galapagos.migration(config.numberOfMigrants);

				this.populationStatistics.update(
					generation,
					-1,
					galapagos.getMaximumFitness(),
					galapagos.getAverageFitness(),
					galapagos.getAverageAge(generation),
					galapagos.getDiversity()
				);
			}

		} catch (EvaluationsLimitExceededException exception) {

			//PopulationVisualiser.visualise("population", ancestry, island.getFittestIndividual());
			 this.populationStatistics.write();
			 System.out.println(config.toString());

			return;
		}
	}
}

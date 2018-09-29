package group12;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationsFactory {

	private int idCounter = 0;

	private List<Selection> parentSelections = new ArrayList<>();
	private List<Selection> survivorSelections = new ArrayList<>();
	private List<Integer> populationSizes = new ArrayList<>();
	private List<Integer> childrenPerGenerations = new ArrayList<>();
	private List<Crossover> crossovers = new ArrayList<>();
	private List<Mutation> mutations = new ArrayList<>();
	private List<Double> mutationRates = new ArrayList<>();

	private List<Configuration> configurations = new ArrayList<>();

	public ConfigurationsFactory(ExtendedRandom random, IDGenerator idGenerator) {

		parentSelections.add(new FitnessProportionalSelection(random));

		survivorSelections.add(new FitnessProportionalSelection(random));
		survivorSelections.add(new Elitist(new FitnessProportionalSelection(random), 2));
		survivorSelections.add(new Elitist(new AgeBasedSurvivorSelection(), 2));

		populationSizes.add(64);
		populationSizes.add(128);

		childrenPerGenerations.add(32);
		childrenPerGenerations.add(64);
		childrenPerGenerations.add(128);

		crossovers.add(new UniformCrossover(random, idGenerator));
		crossovers.add(new SimpleCrossover(idGenerator));
		crossovers.add(new ArithmeticCrossover(random, idGenerator));

		mutationRates.add(0.1);
		mutationRates.add(0.5);

		mutations.add(new AdaptiveMutation(random));
		for (Double mutationRate : mutationRates) {
			mutations.add(new RandomMutation(random, mutationRate));
		}

		for (Selection parentSelection : parentSelections) {
			for (Selection survivorSelection : survivorSelections) {
				for (int populationSize : populationSizes) {
					for (int childrenPerGeneration : childrenPerGenerations) {
						for (Mutation mutation : mutations) {
							for (Crossover crossover : crossovers) {

								if (childrenPerGeneration > populationSize) continue;

								configurations.add(new Configuration(
									idCounter++,
									parentSelection,
									survivorSelection,
									populationSize,
									childrenPerGeneration,
									crossover,
									mutation
								));
							}
						}
					}
				}
			}
		}
	}

	public Configuration get(int index) {
		return this.configurations.get(index);
	}

	public String serialise() {

		StringBuilder serial = new StringBuilder();

		for (Configuration configuration : this.configurations) {
			serial.append(configuration.toString()).append("\n");
		}

		return serial.toString();
	}

	public class Configuration {

		public final int id;

		public final Selection parentSelection;
		public final Selection survivorSelection;
		public final int populationSize;
		public final int childrenPerGeneration;
		public final Crossover crossover;
		public final Mutation mutation;

		public Configuration(int id, Selection parentSelection, Selection survivorSelectio, int populationSize, int childrenPerGeneration, Crossover crossover, Mutation mutation) {
			this.id = id;
			this.parentSelection = parentSelection;
			this.survivorSelection = survivorSelectio;
			this.populationSize = populationSize;
			this.childrenPerGeneration = childrenPerGeneration;
			this.crossover = crossover;
			this.mutation = mutation;
		}

		@Override
		public String toString() {
			return (new StringBuilder()) // don't be fooled by what the IDE says: simple string concatenation crashes the system
					.append("configuration: ").append(id)
					.append(", parentSelection: ").append(parentSelection)
					.append(", survivorSelection: ").append(survivorSelection)
					.append(", populationSize: ").append(populationSize)
					.append(", childrenPerGeneration: ").append(childrenPerGeneration)
					.append(", crossover: ").append(crossover)
					.append(", mutation: ").append(mutation)
				.append(", ")
				.toString();
		}
	}
}

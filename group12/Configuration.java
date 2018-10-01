package group12;

public class Configuration {

	public final int id;

	public final Selection parentSelection;
	public final Selection survivorSelection;
	public final int populationSize;
	public final int childrenPerGeneration;
	public final Crossover crossover;
	public final Mutation mutation;

	public Configuration(int id, Selection parentSelection, Selection survivorSelection, int populationSize, int childrenPerGeneration, Crossover crossover, Mutation mutation) {
		this.id = id;
		this.parentSelection = parentSelection;
		this.survivorSelection = survivorSelection;
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

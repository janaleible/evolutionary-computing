package group12;

public class Configuration {

	public Selection parentSelection;
	public Selection survivorSelection;
	public int populationSize;
	public double generationGap;
	public Crossover crossover;
	public Mutation mutation;

	public Configuration(Selection parentSelection, Selection survivorSelection, int populationSize, double generationGap, Crossover crossover, Mutation mutation) {
		this.parentSelection = parentSelection;
		this.survivorSelection = survivorSelection;
		this.populationSize = populationSize;
		this.generationGap = generationGap;
		this.crossover = crossover;
		this.mutation = mutation;
	}

	public Configuration(ExtendedRandom random, IDGenerator idGenerator, String function) {
		
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration(function);

		this.populationSize = Integer.parseInt(System.getProperty("populationsize", defaultConfiguration.populationSize));
		this.generationGap = Double.parseDouble(System.getProperty("generationgap", defaultConfiguration.generationGap));
		
		switch(System.getProperty("crossover", defaultConfiguration.crossover)) {
			case "arithmetic": 
				this.crossover = new ArithmeticCrossover(random, idGenerator);
				break;
			case "blend":
				double alpha = Double.parseDouble(System.getProperty("alpha", defaultConfiguration.alpha));
				this.crossover = new BlendCrossover(random, alpha, idGenerator);
				break;
			case "random":
				this.crossover = new RandomCrossover(random, idGenerator);
				break;
			case "simple":
				this.crossover = new SimpleCrossover(idGenerator);
				break;
			case "uniform":
				this.crossover = new UniformCrossover(random, idGenerator);
				break;
		}
		
		double mutationRate = Double.parseDouble(System.getProperty("mutationrate", defaultConfiguration.mutationRate));
		
		switch(System.getProperty("mutation", defaultConfiguration.mutation)) {
			case "no":
				this.mutation = new NoMutation();
				break;
			case "random":
				this.mutation = new RandomMutation(random, mutationRate);
				break;
			case "adaptive":
				double learningRate = Double.parseDouble(System.getProperty("learningrate", defaultConfiguration.learningRate));
				this.mutation = new AdaptiveMutation(random, learningRate);
				break;
		}
		
		switch(System.getProperty("parentselection", defaultConfiguration.parentSelection)) {
			case "fitnessproportional":
				this.parentSelection = new FitnessProportionalSelection(random);
				break;
			case "rankbased":
				// TODO: add
				break;
		}
		
		Selection survivorSelection = null;
		switch(System.getProperty("survivorselection", defaultConfiguration.survivorSelection)) {
			case "fitnessproportional":
				survivorSelection = new FitnessProportionalSelection(random);
				break;
			case "rankbased":
				//TODO: add
				break;
		}
		
		boolean elitism = Boolean.parseBoolean(System.getProperty("elitism", defaultConfiguration.elitism));
		if (elitism) {
			int sizeOfElite = Integer.parseInt(System.getProperty("sizeofelite", defaultConfiguration.sizeOfElite));
			survivorSelection = new Elitist(survivorSelection, sizeOfElite);
		}
		this.survivorSelection = survivorSelection;
	}

	@Override
	public String toString() {
		return (new StringBuilder()) // don't be fooled by what the IDE says: simple string concatenation crashes the system
				.append(", parentSelection: ").append(parentSelection)
				.append(", survivorSelection: ").append(survivorSelection)
				.append(", populationSize: ").append(populationSize)
				.append(", generationGap: ").append(generationGap)
				.append(", crossover: ").append(crossover)
				.append(", mutation: ").append(mutation)
			.append(", ")
			.toString();
	}
}

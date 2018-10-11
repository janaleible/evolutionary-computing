package group12;

public class Configuration {

	public Selection parentSelection;
	public ParentMatching parentMatching;
	public Selection survivorSelection;
	public int populationSize;
	public double generationGap;
	public Crossover crossover;
	public Mutation mutation;
	public RangeFunction rangeFunction;
	public double sigma_adaptiveMutation;

	public Configuration(ExtendedRandom random, IDGenerator idGenerator, String function) {
		
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration(function);

		this.populationSize = Integer.parseInt(System.getProperty("populationsize", defaultConfiguration.populationSize));
		this.generationGap = Double.parseDouble(System.getProperty("generationgap", defaultConfiguration.generationGap));

		this.sigma_adaptiveMutation = Double.parseDouble(System.getProperty("sigma_adaptivemutation", defaultConfiguration.sigma_adaptivemutation));

		double crossoverRate = Double.parseDouble(System.getProperty("crossoverrate", defaultConfiguration.crossOverRate));

		switch (System.getProperty("rangefunction", defaultConfiguration.rangeFunction)) {
			case "wrap":
				this.rangeFunction = new WrapRange(-5, 5);
				break;
			case "clip":
				this.rangeFunction = new ClipRange(-5, 5);
				break;
		}

		switch(System.getProperty("crossover", defaultConfiguration.crossover)) {
			case "arithmetic": 
				this.crossover = new ArithmeticCrossover(crossoverRate, random, idGenerator, rangeFunction, this.sigma_adaptiveMutation);
				break;
			case "blend":
				double alpha = Double.parseDouble(System.getProperty("alpha_blendCrossover", defaultConfiguration.alpha_blendCrossover));
				this.crossover = new BlendCrossover(alpha, crossoverRate, random,  idGenerator, this.rangeFunction, this.sigma_adaptiveMutation);
				break;
			case "random":
				this.crossover = new RandomCrossover(crossoverRate, random, idGenerator, this.rangeFunction, this.sigma_adaptiveMutation);
				break;
			case "simple":
				this.crossover = new SimpleCrossover(crossoverRate, random, idGenerator, this.rangeFunction, this.sigma_adaptiveMutation);
				break;
			case "uniform":
				this.crossover = new UniformCrossover(crossoverRate, random, idGenerator, this.rangeFunction, this.sigma_adaptiveMutation);
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
				double learningRate = Double.parseDouble(System.getProperty("learningrate_adaptivemutation", defaultConfiguration.learningRate_adaptiveMutation));
				this.mutation = new AdaptiveMutation(random, learningRate);
				break;
		}

		Selection parentSelection = null;
		boolean genderAware = Boolean.parseBoolean(System.getProperty("genderaware", defaultConfiguration.genderAware));
		boolean ancestryAware = Boolean.parseBoolean(System.getProperty("ancestryaware", defaultConfiguration.ancestryAware));
		boolean inertiaAware = Boolean.parseBoolean(System.getProperty("inertiaaware", defaultConfiguration.inertiaAware));
		switch(System.getProperty("parentselection", defaultConfiguration.parentSelection)) {
			case "fitnessproportional":
				parentSelection = new FitnessProportionalSelection(random);
				break;
			case "rankbased":
				double sigma = Double.parseDouble(System.getProperty("sigma_rankbasedselection", defaultConfiguration.sigma_rankbasedselection));
				parentSelection = new RankBasedSelection(random, sigma);
				break;
			case "tournament":
				int tournamentSize = Integer.parseInt(System.getProperty("tournamentsize", defaultConfiguration.tournamentSize));
				parentSelection = new TournamentSelection(tournamentSize, random);
				break;
		}

		if (genderAware) {
			parentSelection = new GenderAware(parentSelection);
			this.parentMatching = new GenderAwareParentMatching();
		}
		else if (ancestryAware){
			parentSelection = new AncestryAware(parentSelection);
			this.parentMatching = new AncestryAwareParentMatching();
		}
		else if (inertiaAware){
			parentSelection = new InertiaAware(parentSelection);
			this.parentMatching = new InertiaAwareParentMatching();
		}
		else {
			this.parentMatching = new ParentMatching(random);
		}
		this.parentSelection = parentSelection;
		
		Selection survivorSelection = null;
		switch(System.getProperty("survivorselection", defaultConfiguration.survivorSelection)) {
			case "fitnessproportional":
				survivorSelection = new FitnessProportionalSelection(random);
				break;
			case "rankbased":
				double sigma = Double.parseDouble(System.getProperty("sigma_rankbasedselection", defaultConfiguration.sigma_rankbasedselection));
				this.survivorSelection = new RankBasedSelection(random, sigma);
				break;
			case "tournament":
				int tournamentSize = Integer.parseInt(System.getProperty("tournamentsize", defaultConfiguration.tournamentSize));
				survivorSelection = new TournamentSelection(tournamentSize, random);
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

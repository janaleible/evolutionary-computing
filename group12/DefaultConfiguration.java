package group12;

public class DefaultConfiguration {

	public String genderAware = "false";
	public String ancestryAware = "false";
	public String inertiaAware = "false";
	public String crossover = "arithmetic";
	public String mutation = "adaptive";
	
	public String alpha_blendCrossover = "0.5";

	public String parentSelection = "tournament";
	public String survivorSelection = "tournament";
	
	public String elitism = "true";
	public String sizeOfElite = "2";

	public String crossOverRate = "1";
	public String mutationRate = "0.3";
	public String learningRate_adaptiveMutation = "0.32"; // 1 / sqrt(10) as recommended

	public String populationSize = "100";
	public String generationGap = "0.5";
	public String rangeFunction = "clip";
	public String sigma_rankbasedselection = "1.5";
	public String tournamentSize = "5";
	public String sigma_adaptivemutation = "0.5";

	public String numberOfIslands = "1";
	public String generationsPerEpoch = "25";
	public String numberOfMigrants = "2";


	public DefaultConfiguration(String function) {

		// use to set specific values
		switch (function) {
			case "Sphere":
				this.parentSelection = "rankbased";
				this.elitism = "true";
				this.populationSize = "30";
				this.generationGap = "1";
				this.crossover = "arithmetic";
				this.crossOverRate = "1";
				this.mutation = "random";
				this.mutationRate = "0.01";
				this.rangeFunction = "wrap";
				break;
			case "BentCigar":
				this.crossover = "arithmetic";
				this.crossOverRate = "1";
				this.elitism = "false";
				this.generationGap = "0.5";
				this.mutation = "random";
				this.mutationRate = "0.01";
				this.parentSelection = "fitnessproportional";
				this.populationSize = "30";
				this.rangeFunction = "wrap";
				this.survivorSelection = "tournament";
				this.tournamentSize = "5";
				break;
			case "Schaffers":
				this.crossover = "arithmetic";
				this.crossOverRate = "1";
				this.elitism = "true";
				this.generationGap = "0.5";
				this.mutation = "random";
				this.mutationRate = "0.3";
				this.parentSelection = "fitnessproportional";
				this.populationSize = "250";
				this.rangeFunction = "wrap";
				this.survivorSelection = "tournament";
				this.tournamentSize = "2";
				this.numberOfIslands = "2";
				this.numberOfMigrants = "1";
				this.generationsPerEpoch = "25";
				break;
			case "Katsuura":
				this.crossover = "arithmetic";
				this.crossOverRate = "1";
				this.elitism = "true";
				this.generationGap = "1";
				this.mutation = "random";
				this.mutationRate = "0.01";
				this.parentSelection = "rankbased";
				this.populationSize = "250";
				this.rangeFunction = "wrap";
				this.numberOfIslands = "2";
				this.numberOfMigrants = "1";
				this.generationsPerEpoch = "25";
				break;
		}
	}
}
 
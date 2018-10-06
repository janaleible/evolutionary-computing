package group12;

public class DefaultConfiguration {

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

	public DefaultConfiguration(String function) {

		// TODO: use to set specific values
		switch (function) {
			case "Sphere":
				this.tournamentSize = "25";
				break;
			case "BentCigar":
				this.mutation = "random";
				this.parentSelection = "fitnessproportional";
				this.survivorSelection = "fitnessproportional";
				break;
			case "Schaffers":
				this.mutation = "random";
				this.parentSelection = "fitnessproportional";
				this.survivorSelection = "fitnessproportional";
				break;
			case "Katsuura":
				this.crossover = "simple";
				break;
		}
	}
}
 
package group12;

public class DefaultConfiguration {

	public String crossover = "arithmetic";
	public String mutation = "random";
	
	public String alpha = "0.5";

	public String parentSelection = "fitnessproportional";
	public String survivorSelection = "fitnessproportional";
	
	public String elitism = "true";
	public String sizeOfElite = "2";

	public String crossOverRate = "1";
	public String mutationRate = "0.3";
	public String learningRate = "0.1";

	public String populationSize = "100";
	public String generationGap = "0.5";

	public DefaultConfiguration(String function) {

		// TODO: use to set specific values
		switch (function) {
			case "Sphere":
				break;
			case "BentCigar":
				break;
			case "Schaffers":
				break;
			case "Katsuura":
				break;
		}
	}
}
 
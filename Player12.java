import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class Player12 implements ContestSubmission {

	private Random random;
	private ContestEvaluation contestEvaluation;
	private int evaluationsLimit;

	public Player12() {
		this.random = new Random();
	}

	public void setSeed(long seed) {
		// Set seed of algortihms random process
		this.random.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation) {

		// Set evaluation problem used in the run
		this.contestEvaluation = evaluation;

		// Get evaluation properties
		Properties properties = evaluation.getProperties();
        // Get evaluation limit
        this.evaluationsLimit = Integer.parseInt(properties.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. doubl		e param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(properties.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(properties.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(properties.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal) {
            // Do sth
        } else {
            // Do sth else
        }
    }

	public void run() {

		// Run your algorithm here

        int evaluations = 0;
        // init population
        // calculate fitness
        while(evaluations < this.evaluationsLimit){
            // Select parents
            // Apply crossover / mutation operators
            double[] child = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            // Check fitness of unknown fuction
            Double fitness = (double) this.contestEvaluation.evaluate(child);
            evaluations++;
            // Select survivors
        }
	}
}

package group12;

public class AdaptiveMutation extends Mutation {

	private ExtendedRandom random;
	private double learningRate;

	public AdaptiveMutation(ExtendedRandom random) {
		this(random, 1 / Math.sqrt(10)); // rule of thumb depending on size of genome
	}

	public AdaptiveMutation(ExtendedRandom random, double learningRate) {
		this.random = random;
		this.learningRate = learningRate;
	}

	@Override
	public Individual mutate(Individual individual) {
		individual.setSigma(mutateSigma(individual.getSigma()));

		double[] newGenome = new double[individual.genome().length];

		for (int i = 0; i < individual.genome().length; i++) {
			newGenome[i] = mutateX(individual.genome()[i], individual.getSigma());
		}

		individual.mutateGenome(newGenome);

		return individual;
	}

	private double mutateX(double x, double sigma) {
		return x + sigma * this.random.nextGaussian();
	}

	private double mutateSigma(double sigma) {
		return Math.max(0.1, sigma * Math.exp(this.learningRate * this.random.nextGaussian()));
	}
}

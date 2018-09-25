package group12;

public class BlendCrossover extends Crossover {

	private double alpha;
	private ExtendedRandom random;
	private IDGenerator idGenerator;

	public BlendCrossover(ExtendedRandom random, double alpha, IDGenerator idGenerator) {
		this.alpha = alpha;
		this.random = random;
		this.idGenerator = idGenerator;
	}

	@Override
	public Individual[] cross(Individual self, Individual other, int generation) {

		double[] childGenome1 = new double[self.genome().length];
		double[] childGenome2 = new double[self.genome().length];

		for (int i = 0; i < self.genome().length; i++) {
			double x = Math.min(self.genome()[i], other.genome()[i]);
			double y = Math.max(self.genome()[i], other.genome()[i]);

			double d = y - x;

			childGenome1[i] = random.sampleFromRange(x - this.alpha * d, y + this.alpha * d);
			childGenome2[i] = random.sampleFromRange(x - this.alpha * d, y + this.alpha * d);
			// upper bound according to the definition in the original paper, contrary to slide content
		}

		Individual[] parents = {self, other};

		return new Individual[]{
			new Individual(childGenome1, generation, parents, idGenerator),
			new Individual(childGenome2, generation, parents, idGenerator)
		};
	}
}

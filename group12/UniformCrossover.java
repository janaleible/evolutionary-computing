package group12;

public class UniformCrossover extends Crossover {

    private ExtendedRandom random;
    private IDGenerator idGenerator;
    private RangeFunction rangeFunction;

    public UniformCrossover(ExtendedRandom random, IDGenerator idGenerator, RangeFunction rangeFunction) {
        this.random = random;
        this.idGenerator = idGenerator;
        this.rangeFunction = rangeFunction;
    }

    @Override
    public Individual[] cross(Individual one, Individual another, int generation) {

        Individual[] parents = new Individual[2];
        parents[0] = one;
        parents[1] = another;

        double[][] childGenomes = new double[2][10];

        for (int i = 0; i <10; i++) {
            if(this.random.coinflip()) {
                childGenomes[0][i] = one.genome()[i];
                childGenomes[1][i] = another.genome()[i];
            }
            else {
                childGenomes[0][i] = another.genome()[i];
                childGenomes[1][i] = one.genome()[i];
            }
        }

        Individual[] children = new Individual[2];
		children[0] = new Individual(childGenomes[0], generation, parents, this.idGenerator, this.rangeFunction);
		children[1] = new Individual(childGenomes[1], generation, parents, this.idGenerator, this.rangeFunction);

		return children;
    }
}
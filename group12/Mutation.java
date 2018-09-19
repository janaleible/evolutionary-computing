package group12;

public abstract class Mutation {
	public abstract Individual mutate(Individual self);

	public abstract Individual mutate(Individual self, float mutationRate);
}

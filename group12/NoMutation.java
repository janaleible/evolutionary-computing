package group12;

public class NoMutation extends Mutation {

	@Override
	public Individual mutate(Individual self) {
		return self;
	}

	@Override
	public Individual mutate(Individual self, float mutationRate) {
		//Throw exception here?
		return self;
	}
}

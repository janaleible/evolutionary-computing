package group12;

import java.util.List;
import java.util.stream.Collectors;

public class GenderAwareParentMatching extends ParentMatching {

	public GenderAwareParentMatching() {
		super(null);
	}

	@Override
	public List<Individual[]> getMatches(List<Individual> parents) {

		return match(
			parents.stream().filter(individual -> individual.gender() == Gender.female).collect(Collectors.toList()),
			parents.stream().filter(individual -> individual.gender() == Gender.male).collect(Collectors.toList())
		);
	}
}

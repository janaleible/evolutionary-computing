package group12;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenderAware extends Selection {

	Selection selection;

	public GenderAware(Selection selection) {
		this.selection = selection;
	}

	@Override
	public List<Individual> select(int numberOfPicks, List<Individual> population) {

		List<Individual> males = population.stream().filter(individual -> individual.gender() == Gender.male).collect(Collectors.toList());
		List<Individual> females = population.stream().filter(individual -> individual.gender() == Gender.female).collect(Collectors.toList());

		List<Individual> parents = new ArrayList<>(numberOfPicks);
		parents.addAll(this.selection.select((int)Math.floor(numberOfPicks / 2.0), males));
		parents.addAll(this.selection.select((int)Math.ceil(numberOfPicks / 2.0), females));

		return parents;
	}

	@Override
	public boolean isGenderAware() {
		return true;
	}
}

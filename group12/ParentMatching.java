package group12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentMatching {

	ExtendedRandom random;

	public ParentMatching(ExtendedRandom random) {
		this.random = random;
	}

	public List<Individual[]> getMatches(List<Individual> parents) {

		Collections.shuffle(parents, this.random); // make sure that random parents mate
		List<Individual> fathers = parents.subList(0, parents.size() / 2);
		List<Individual> mothers = parents.subList((parents.size() / 2) + 1, parents.size());

		return match(mothers, fathers);
	}

	protected List<Individual[]> match(List<Individual> mothers, List<Individual> fathers) {

		int numberOfMatches = Math.min(mothers.size(), fathers.size());
		List<Individual[]> matches = new ArrayList<>(numberOfMatches);

		for (int i = 0; i < numberOfMatches; i++) {
			matches.add(new Individual[] {mothers.get(i), fathers.get(i)});
		}

		return matches;
	}
}

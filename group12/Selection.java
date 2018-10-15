package group12;

import java.util.List;

public abstract class Selection {

	public abstract List<Individual> select(int numberOfPicks, List<Individual> population);

	public boolean RTSflag() { return false; }

	public boolean isGenderAware() { return false; }

	public boolean isAncestryAware() { return false; }

	public boolean isInertiaAware() { return false; }

	public int sizeOfElite() { return 0; }
}

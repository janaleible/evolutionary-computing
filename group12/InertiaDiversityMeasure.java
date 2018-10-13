package group12;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InertiaDiversityMeasure extends DiversityMeasure {

	/* Implementation following
	 * @InProceedings{morrison.2002,
	 * 		author={Morrison, Ronald W. and De Jong, Kenneth A.},
	 * 		editor={Collet, Pierre and Fonlupt, Cyril and Hao, Jin-Kao and Lutton, Evelyne and Schoenauer, Marc},
	 * 		title={Measurement of Population Diversity},
	 * 		booktitle={Artificial Evolution}, year={2002},
	 * 		publisher={Springer Berlin Heidelberg},
	 * 		address={Berlin, Heidelberg},
	 * 		pages={31--41}
	 * }
	 * */

	@Override
	public double measure(Population population) {

		List<double[]> genomes = population.iterable().stream().map(Individual::genome).collect(Collectors.toList());

		double[] centroid = DiversityMeasure.centroid(genomes);
		double inertia = 0;

		for (double[] genome : genomes) {
			inertia += DiversityMeasure.hammingDistance(genome, centroid);
		}

		return inertia / population.getPopulationSize();
	}



}

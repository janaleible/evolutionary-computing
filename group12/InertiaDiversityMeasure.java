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

		double[] centroid = this.centroid(genomes);
		double inertia = 0;

		for (double[] genome : genomes) {
			inertia += hammingDistance(genome, centroid);
		}

		return inertia / population.getPopulationSize();
	}

	private double hammingDistance(double[] from, double[] to) {

		double distance = 0;

		for (int i = 0; i < from.length; i++) {
			distance += Math.pow(from[i] - to[i], 2);
		}

		return distance;
	}

	private double[] centroid(List<double[]> vectors) {

		double result[] = new double[vectors.get(0).length];

		for (double[] vector : vectors) {
    		Arrays.setAll(result, i -> result[i] + (vector[i] / vectors.size()));
		}

		return result;
	}
}

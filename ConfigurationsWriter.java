import group12.ConfigurationsFactory;
import group12.ExtendedRandom;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationsWriter {

	public static void main(String [] args) {

		String configurations = (new ConfigurationsFactory(null, null)).serialise();

		BufferedWriter writer;

		try{
			writer = new BufferedWriter(new FileWriter("gridsearch/configurations.txt"));
			writer.write(configurations);
			writer.close();
		} catch (IOException e) {}
	}
}

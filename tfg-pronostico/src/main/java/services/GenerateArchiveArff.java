
package services;

import java.util.ArrayList;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.pmml.jaxbbindings.Attribute;

public class GenerateArchiveArff {

	public static void generarArchivoWeka() {

		double[][] data = {
			{
				4058.0, 4059.0, 4060.0, 214.0, 1710.0, 2452.0, 2473.0, 2474.0, 2475.0, 2476.0, 2477.0, 2478.0, 2688.0, 2905.0, 2906.0, 2907.0, 2908.0, 2909.0, 2950.0, 2969.0, 2970.0, 3202.0, 3342.0, 3900.0, 4007.0, 4052.0, 4058.0, 4059.0, 4060.0
			}, {
				19.0, 20.0, 21.0, 31.0, 103.0, 136.0, 141.0, 142.0, 143.0, 144.0, 145.0, 146.0, 212.0, 243.0, 244.0, 245.0, 246.0, 247.0, 261.0, 270.0, 271.0, 294.0, 302.0, 340.0, 343.0, 354.0, 356.0, 357.0, 358.0
			}
		};

		int numInstances = data[0].length;

		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		List<Instance> instances = new ArrayList<Instance>();
		for (int dim = 0; dim < numInstances; dim++) {
			Attribute current = new Attribute();
			if (dim == 0) {
				for (int obj = 0; obj < numInstances; obj++) {
					instances.add(new SparseInstance(numInstances));
				}
			}

			for (int obj = 0; obj < numInstances; obj++) {
				instances.get(obj).setValue(current, data[dim][obj]);
			}

			atts.add(current);
		}

		Instances newDataset = new Instances("Dataset", atts, instances.size());

		for (Instance inst : instances)
			newDataset.add(inst);

	}
}

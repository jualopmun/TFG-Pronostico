package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import java.util.Scanner;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class WekService {

	// Codigo encontrado en el enlace:
	// https://stackoverflow.com/questions/33556543/how-to-save-model-and-apply-it-on-a-test-dataset-on-java

	// Algoritmo J48
	public static void algoritmoModelJ48() throws Exception {
		//Preparamos las rutas para importar el dataset y exportar el modelo general
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca la ruta de la entrada del archivo arff: ");
		String entrada = sc.nextLine();
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Introduzca la ruta de la salida para el modelo: ");
		String salida = sc2.nextLine();
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(entrada));
		Instances train = new Instances(reader);
		train.setClassIndex(train.numAttributes() - 1);
		reader.close();
		
		//Invocamos que algoritmo sera invocado
		J48 j48 = new J48();
		//Clasificamos el dataset
		j48.buildClassifier(train);
		Evaluation eval = new Evaluation(train);
		eval.crossValidateModel(j48, train, 20, new Random(1));
		
		//Veremos aqui el resultado de cuantos ha sido clasificado y generaremos el .model para ser luego utilizado
		System.out.println(eval.toSummaryString("\n Results \n=====\n", true));
		System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1) + " ");
		weka.core.SerializationHelper.write(salida + "\\" + "j48.model", j48);

	}

	// Algoritmo Randomforest
	public static void algoritmoRandomForest() throws Exception {
		//Preparamos las rutas para importar el dataset y exportar el modelo general
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca la ruta de la entrada del archivo arff: ");
		String entrada= sc.nextLine();
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Introduzca la ruta de la salida para el modelo: ");
		String salida = sc2.nextLine();
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(entrada));
		Instances train = new Instances(reader);
		train.setClassIndex(train.numAttributes() - 1);
		reader.close();
		//Invocamos que algoritmo sera invocado
		RandomForest randomForest = new RandomForest();
		//Clasificamos el dataset
		randomForest.buildClassifier(train);
		Evaluation eval = new Evaluation(train);

		eval.crossValidateModel(randomForest, train, 20, new Random(1));
		//Veremos aqui el resultado de cuantos ha sido clasificado y generaremos el .model para ser luego utilizado
		System.out.println(eval.toSummaryString("\n Results \n=====\n", true));
		System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1) + " ");
		weka.core.SerializationHelper.write(salida + "\\" + "randomForest.model", randomForest);

	}

	// Algoritmo SMO
	public static void algoritmoSMO() throws Exception {
		//Preparamos las rutas para importar el dataset y exportar el modelo general
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca la ruta de la entrada del archivo arff: ");
		String entrada= sc.nextLine();
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Introduzca la ruta de la salida para el modelo: ");
		String salida = sc2.nextLine();
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(entrada));
		Instances train = new Instances(reader);
		train.setClassIndex(train.numAttributes() - 1);
		reader.close();
		//Invocamos que algoritmo sera invocado
		SMO smo = new SMO();
		smo.buildClassifier(train);
		//Clasificamos el dataset
		Evaluation eval = new Evaluation(train);
		//Veremos aqui el resultado de cuantos ha sido clasificado y generaremos el .model para ser luego utilizado
		eval.crossValidateModel(smo, train, 20, new Random(1));

		System.out.println(eval.toSummaryString("\n Results \n=====\n", true));
		System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1) + " ");
		weka.core.SerializationHelper.write(salida + "\\" + "smo.model", smo);

	}

	public static void resultadoFinal() throws Exception {
		//Preparamos las rutas para importar el dataset, el modelo y exportar el resultado final
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca la ruta de la entrada del archivo arff: ");
		String entrada= sc.nextLine();
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Introduzca la ruta de la entrada del modelo: ");
		String entrada2 = sc2.nextLine();
		Scanner sc3 = new Scanner(System.in);
		System.out.println("Introduzca la ruta de la salida del resultado final: ");
		String salida = sc3.nextLine();

		// Cargamos el dataset
		Instances unlabeled = new Instances(new BufferedReader(new FileReader(entrada)));

		// Apuntamos a que etiqueta queremos clasificar
		unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
		// Creamos una copia
		Instances labeled = new Instances(unlabeled);
		// Instaciamos la etiqueta del modelo y clasificamos el resultado final
		Classifier myCls = (Classifier) weka.core.SerializationHelper.read(entrada2);
		for (int i = 0; i < unlabeled.numInstances(); i++) {

			double clsLabel = myCls.classifyInstance(unlabeled.instance(i));

			labeled.instance(i).setClassValue(clsLabel);

		}

		// Guardamos el resultado final
		BufferedWriter writer = new BufferedWriter(new FileWriter(salida + "\\"+"resultadoFinal.arff"));
		writer.write(labeled.toString());
		System.out.println(labeled.toString());
		writer.newLine();
		writer.flush();
		writer.close();
	}

	// Algoritmo para clasificar resultado final

	public static void main(String[] args) throws Exception {
		//algoritmoModelJ48();
		resultadoFinal();
		

	}
}

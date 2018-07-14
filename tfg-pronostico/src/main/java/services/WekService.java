package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

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
	
	//Codigo encontrado en el enlace: https://stackoverflow.com/questions/33556543/how-to-save-model-and-apply-it-on-a-test-dataset-on-java
	
	//Algoritmo J48
	public static void algoritmoModelJ48() throws Exception {
		
		 BufferedReader reader = null;
	      reader=new BufferedReader(new FileReader("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\wekapalabrasGeneral.arff"));
	      Instances train =new Instances (reader);
	      train.setClassIndex(11);     
	      reader.close();

	      J48 j48 = new J48();
	      j48.buildClassifier(train);
	      Evaluation eval = new Evaluation(train);
	      eval.crossValidateModel(j48, train, 20 , new Random(1));

	      System.out.println(eval.toSummaryString("\n Results \n=====\n",true));
	      System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+eval.recall(1)+" ");    
	      weka.core.SerializationHelper.write("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\j48Palabras.model", j48);
		
	}
	
	//Algoritmo Randomforest
	public static void algoritmoRandomForest() throws Exception {
		
		 BufferedReader reader = null;
	      reader=new BufferedReader(new FileReader("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\wekapalabrasGeneral.arff"));
	      Instances train =new Instances (reader);
	      train.setClassIndex(11);     
	      reader.close();

	      RandomForest randomForest = new RandomForest();
	      
	      randomForest.buildClassifier(train);
	      Evaluation eval = new Evaluation(train);
	      
	      eval.crossValidateModel(randomForest, train, 10 , new Random(1));

	      System.out.println(eval.toSummaryString("\n Results \n=====\n",true));
	      System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+eval.recall(1)+" ");    
	      weka.core.SerializationHelper.write("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\randomForestPalabras.model", randomForest);
		
	}
	
	//Algoritmo SMO
	public static void algoritmoSMO() throws Exception {
		
		 BufferedReader reader = null;
	      reader=new BufferedReader(new FileReader("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\wekapalabrasGeneral.arff"));
	      Instances train =new Instances (reader);
	      train.setClassIndex(11);     
	      reader.close();

	      SMO smo = new SMO();
	      smo.buildClassifier(train);
	      Evaluation eval = new Evaluation(train);
	      eval.crossValidateModel(smo, train, 10 , new Random(1));

	      System.out.println(eval.toSummaryString("\n Results \n=====\n",true));
	      System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+eval.recall(1)+" ");    
	      weka.core.SerializationHelper.write("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\smoPalabras.model", smo);
		
	}
	
	public static void resultadoFinal() throws Exception {
		
				// load unlabeled data
				 Instances unlabeled = new Instances(
				                         new BufferedReader(
				                           new FileReader("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\wekapalabras29.arff")));
				 
				 // set class attribute
				 unlabeled.setClassIndex(unlabeled.numAttributes()-1 );
				 // create copy
				 Instances labeled = new Instances(unlabeled);
				 // label instances
				 Classifier myCls = (Classifier) weka.core.SerializationHelper.read("C:\\Users\\\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\smoPalabras.model");
				 for (int i = 0; i <unlabeled.numInstances(); i++) {
					
				   double clsLabel = myCls.classifyInstance(unlabeled.instance(i));
				  
				   labeled.instance(i).setClassValue(clsLabel);
				   
				 }

				 // save labeled data
				 BufferedWriter writer = new BufferedWriter(
				                           new FileWriter("C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\wekapalabrasSMO.arff"));
				 writer.write(labeled.toString());
				 System.out.println(labeled.toString());
				 writer.newLine();
				 writer.flush();
				 writer.close();
			}
		
	
	
	//Algoritmo para clasificar resultado final
	
	
	public static void main(String[] args) throws Exception {
		
		
		algoritmoSMO();
		resultadoFinal();
		
	}
}


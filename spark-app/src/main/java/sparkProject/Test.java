package sparkProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.spark.sql.RowFactory;

public class Test implements Interface {

	public static void printSubsets(ArrayList<Integer> subsets) {
		System.out.print("Reached Here:" + subsets);
		System.out.print("\n");
	}

	public static void makeSub(int[] arr, ArrayList<Integer> subsets, int index) {
		System.out.println("arr.length:" + arr.length);
		System.out.println("arr.length: Start" + index);
		if (index >= arr.length) {
			printSubsets(subsets);
		} else {
			// not including the element at index index
			makeSub(arr, subsets, index + 1);
			System.out.println("I'm Here End:" + index);
			subsets.set(index, arr[index]); // including the element at index index
			makeSub(arr, subsets, index + 1);
		}
	}

	public static void genSubsets(int[] arr, int n) {
		ArrayList<Integer> subsets = new ArrayList<Integer>(n);
		makeSub(arr, subsets, 0);
	}

	public static void main(String[] args) {
		
		Interface ob = new Test();
		Test obt = new Test();
		obt.printString();
		
		
		

		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int arr[] = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = scan.nextInt();
		}
		genSubsets(arr, n);
	}

	@Override
	public String printString() {

		String st = "Im implementing Interface";
		return st;
	}
	
	public String instanceMethod() {

		String st = "Instance Mthod called";
		return st;
	}

}

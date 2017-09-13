package Engine;

import Input.FileWizard;
import Input.loadparam;
import Output.DataWrapper;
import Output.OutputTest;
import Output.ProcRep;

import java.io.*;


/***************************************************************************
 *
 * 	FILE: 			Main.java
 *
 * 	AUTHOR: 		ROCKY LI
 *
 * 	LATEST_EDIT:	2017/9/12
 *
 * 	VER: 			1.2
 *
 * 	Purpose: 		Entry point.
 *
 **************************************************************************/


public class Main {

	public static void main(String[] args) throws IOException {

		// LOAD the parameter file.

		String head = FileWizard.getabspath();

		loadparam data;

		if (args.length == 0){
			data =  new loadparam(head + "/in/params.txt");
		} else {
			data = new loadparam(args[0]);
		}

		// Runs simulation

		Simulation sim = new Simulation(data);
		sim.run();

		// Generate Output

		DataWrapper analyze = new DataWrapper(sim, data);
		analyze.output();

	}

}

package Engine;

import Input.FileWizard;
import Input.loadparam;
import Output.DataWrapper;
import Output.OutputTest;
import Output.ProcRep;

import java.io.*;

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

		// Runs simulation.

		Simulation once = new Simulation(data);
		once.run();

//		OutputTest nv = new OutputTest(once.getCompletesimulation()[0]);
//		nv.textinspect();

		ProcRep rep = new ProcRep(once.getdisdata(), once.getopsdata(), once.getCompletesimulation()[0]);
		rep.run();

		DataWrapper analyze = new DataWrapper(once, data);
		analyze.generate();
		analyze.output();

	}

}

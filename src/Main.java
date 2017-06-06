import Input.loadparam;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		loadparam data;
		if (args.length == 0){
			data =  new loadparam("/home/aperocky/workspace/shadojava/in/params.txt");
		} else {
			data = new loadparam(args[0]);
		}
		
		Simulation runs = new Simulation(data);
		runs.run();
		for (Operator each: runs.operators){
			ProcData proc = new ProcData(each.getQueue().records());
			System.out.println(proc.load());
		}
		
	}
}

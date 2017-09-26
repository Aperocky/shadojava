package Engine;
import Input.loadparam;
import Output.ProcRep;

import java.util.ArrayList;

/***************************************************************************
 *
 * 	FILE: 			Simulation.java
 *
 * 	AUTHOR: 		ROCKY LI
 *
 * 	LATEST EDIT:	2017/9/12
 *
 * 	VER: 			1.1
 *
 * 	Purpose: 		Wraps the simulation, included intra-thread data processing to
 * 	                relieve cost on memory size.
 *
 **************************************************************************/

public class Simulation {

	private loadparam parameters;

    private int[] expiredtaskcount;

    private int[] completedtaskcount;

    private Data[] operatoroutput;

    private Data dispatchoutput;

    private int repnumber;

    public int[] getExpiredtask() {
        return expiredtaskcount;
    }

    public int[] getCompletedtaskcount() {
        return completedtaskcount;
    }

    public Data getOperatoroutput(int i) {
        return operatoroutput[i];
    }

    public Data getDispatchoutput() {
        return dispatchoutput;
    }

    public Data[] getopsdata() { return operatoroutput; }

    public Data getdisdata() { return dispatchoutput; }

    /****************************************************************************
     *
     *	Main Object:	Simulation
     *
     *	Purpose:		Create the simulation Object.
     *
     ****************************************************************************/

    public Simulation(loadparam param) {

        // Get overall parameters

        parameters = param;
        repnumber = param.numReps;

        // Generate overall data field

        operatoroutput = new Data[param.numOps];
        for (int i = 0; i < param.numOps; i++) {
            operatoroutput[i] = new Data(param.numTaskTypes + 1, (int) param.numHours * 6, param.numReps);
        }

        dispatchoutput = new Data(param.numTaskTypes + 1, (int) param.numHours * 6, param.numReps);
//        for (int i = 0; i < param.numDispatch; i++) {
//            dispatchoutput[i] = new Data(param.numTaskTypes + 1, (int) param.numHours * 6, param.numReps);
//        }

        expiredtaskcount = new int[param.numTaskTypes];
        completedtaskcount = new int[param.numTaskTypes];

    }

    /****************************************************************************
     *
     *	Method:			processReplication
     *
     *	Purpose:		process a SINGLE replication, and then remove the reference.
     *
     ****************************************************************************/

    public void processReplication(int repID){

        Replication processed = new Replication(parameters, repID);
        processed.run();
        ProcRep process = new ProcRep(dispatchoutput, operatoroutput, processed);
        process.run();

        for (int i = 0; i < parameters.numTaskTypes; i++) {
            expiredtaskcount[i] += process.getExpired()[i];
            completedtaskcount[i] += process.getCompleted()[i];
        }
    }

    /****************************************************************************
     *
     *	Method:			run
     *
     *	Purpose:		Run Simulation
     *
     ****************************************************************************/

    public void run() {

        for (int i = 0; i < repnumber; i++) {

            processReplication(i);
            if (i%10 == 0){
                System.out.println("we're at " + i + " repetition");
            }
        }


        dispatchoutput.avgdata();
        for (int y = 0; y < (int) parameters.numHours*6 ; y++){
            double sum = 0;
            for (int x = 0; x < parameters.numTaskTypes; x++) {
                sum += dispatchoutput.avgget(x, y);
            }
            sum = Math.round(sum*10000)/10000.0;
            dispatchoutput.avgset(parameters.numTaskTypes, y, sum);
        }


        for (Data each: operatoroutput){
            each.avgdata();
            for (int y = 0; y < (int) parameters.numHours*6 ; y++){
                double sum = 0;
                for (int x = 0; x < parameters.numTaskTypes; x++) {
                    sum += each.avgget(x, y);
                }
                sum = Math.round(sum*10000)/10000.0;
                each.avgset(parameters.numTaskTypes, y, sum);
            }
        }
    }

	
}

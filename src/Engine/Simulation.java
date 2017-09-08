package Engine;
import Input.loadparam;

import java.util.ArrayList;

public class Simulation {

	private loadparam parameters;

    private int[] expiredtaskcount;

    private int[] completedtaskcount;

    private Data[] operatoroutput;

    private Data[] dispatchoutput;

    private int repnumber;

    private int repID;

    private Replication[] completesimulation;

    public Replication[] getCompletesimulation() {
        return completesimulation;
    }

    public Simulation(loadparam param) {

        parameters = param;
        repnumber = param.numReps;
        completesimulation = new Replication[repnumber];
        operatoroutput = new Data[param.numOps];
        for (int i = 0; i < param.numOps; i++) {
            operatoroutput[i] = new Data(param.numTaskTypes, (int) param.numHours * 6, param.numReps);
        }

        dispatchoutput = new Data[param.numDispatch];
        for (int i = 0; i < param.numDispatch; i++) {
            dispatchoutput[i] = new Data(param.numTaskTypes, (int) param.numHours * 6, param.numReps);
        }
        expiredtaskcount = new int[param.numTaskTypes];
        completedtaskcount = new int[param.numTaskTypes];
    }

    public int[] getExpiredtask() {
        return expiredtaskcount;
    }

    public int[] getCompletedtaskcount() {
        return completedtaskcount;
    }

    public int getCompletedtaskinc(int i, int j) {
        return completedtaskcount[i] += j;
    }

    public int getExpiredtaskinc(int i, int j) {
        return expiredtaskcount[i] += j;
    }

    public Data getOperatoroutput(int i) {
        return operatoroutput[i];
    }

    public Data getDispatchoutput(int i) {
        return dispatchoutput[i];
    }

    public void run() {
        for (int i = 0; i < repnumber; i++) {
            repID = i;
            completesimulation[i] = new Replication(parameters);
            completesimulation[i].run();
        }
    }

	
}

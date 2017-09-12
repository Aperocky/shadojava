package Output;

import java.io.*;

import Engine.*;
import Input.FileWizard;
import Input.loadparam;

/***************************************************************************
 *
 * 	FILE: 			DataWrapper.java
 *
 * 	AUTHOR: 		ROCKY LI
 *
 * 	DATE:			2017/6/5
 *
 * 	VER: 			1.0
 *
 * 	Purpose: 		Wrapping the data field for analysis.
 *
 **************************************************************************/

public class DataWrapper {

    public loadparam parameter;

    private Simulation sim;

    private String file_name;

    private String file_head;

    public void setFileHead(){
        file_head = FileWizard.getabspath();
    }

    public DataWrapper(Simulation o, loadparam param) {
        parameter = param;
        sim = o;
    }


    public void generate() throws IOException {

        setFileHead();

        for (int i = 0; i < sim.getCompletesimulation().length; i++) {

            Replication replication = sim.getCompletesimulation()[i];

            Operator[] dispatchers = replication.getDispatch().getDispatch();

            for (TrainSim each : replication.getTrains()) {
                for (Operator dispatcher : dispatchers) {

                    new ProcData(dispatcher.getQueue().records()).store(sim.getCompletesimulation()[i].getTime(), dispatcher,
                            each.getTrainID(), sim, i);

                }

                Operator[] operators = each.operators;


                for (Operator him : operators) {


                    new ProcData(him.getQueue().records()).store(sim.getCompletesimulation()[i].getTime(), him,
                            each.getTrainID(), sim, i);


                }
            }
        }
    }

    public void output() throws IOException {

        for (int i = 0; i < parameter.numDispatch; i++) {
            file_name = file_head + "/out/" + "Dispatcher" + i + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(file_name, false)), true));
            sim.getDispatchoutput(i).outputdata();
        }

        for (int j = 0; j < parameter.numOps; j++) {
            file_name = file_head + "/out/" + parameter.opNames[j] + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(file_name, false)), true));
            sim.getOperatoroutput(j).outputdata();

        }

        for (int k = 0; k < parameter.numTaskTypes; k++) {
            file_name = file_head + "/out/" + "expiredtask" + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(file_name, false)), true));

            System.out.println("expired" + sim.getExpiredtask()[k]);
            System.out.println("completed" + sim.getCompletedtaskcount()[k]);

        }
    }

    }



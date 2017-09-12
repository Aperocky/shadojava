package Engine;

import Engine.Dispatch;
import Engine.Simulation;
import Engine.Task;
import Engine.TrainSim;
import Input.loadparam;

import java.util.ArrayList;

/**
 * Created by erinsong on 6/29/17.
 */

public class Replication {

    public loadparam parameters;

    private int repID;

    private ArrayList<Task> linked;

    private TrainSim[] trains;

    private Dispatch control;

    private double totaltime;

    // Inspectors:

    public TrainSim[] getTrains() {
        return trains;
    }

    public Dispatch getDispatch() {
        return control;
    }

    public int getRepID() {
        return repID;
    }

    public double getTime() {
        return totaltime;
    }

    public Replication(loadparam param, int id) {

        parameters = param;
        totaltime = parameters.numHours * 60;
        this.repID = id;

    }

    public void run() {

        // Initialize control center.

        control = new Dispatch(parameters);
        control.run();
        linked = control.gettasks();

        // Initialize trains.

        trains = new TrainSim[parameters.numTrains];

        for (int i = 0; i < parameters.numTrains; i++) {

            trains[i] = new TrainSim(parameters, i);
            trains[i].genbasis();

        }

        // Add linked tasks to trains.

        for (Task each : linked) {

            int trainid = each.getTrain();
            each = new Task(each.getType(), each.getBeginTime(), parameters, false);
            each.setID(trainid);
//            each.setArrTime(each.getBeginTime());
//            each.setELStime(0);
            if (each.getArrTime() < parameters.numHours*60) {
                trains[trainid].linktask(each);
            }

        }

        // Run each train

        for (TrainSim each : trains) {

            each.run();

        }

    }
}

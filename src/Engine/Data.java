package Engine;

/**
 * Created by erinsong on 6/26/17.
 */
public class Data {

    public double[][][] data;

    public double[][] avg;

    public double[][] std;

    /****************************************************************************
     *
     *	Main Object:	Data
     *
     *	Purpose:		Generate an Data object that has a particular size of i,j,k
     ****************************************************************************/


    public Data(int i, int j, int k) {

        data = new double[i][j][k];
        avg = new double[i][j];
        std = new double[i][j];
    }

    public double dataget(int i, int j, int k) {
        return data[i][j][k];
    }


    public double avgget(int i, int j) {

        return avg[i][j];
    }

    public double stdget(int i, int j) {
        return std[i][j];
    }

    /****************************************************************************
     *
     *	Method:			datainc()
     *
     *	Purpose:		updating the data array by the inc value
     *
     ****************************************************************************/


    public void datainc(int i, int j, int k, double inc) {

        data[i][j][k] += inc;
    }

    /****************************************************************************
     *
     *	Method:			avgdata()
     *
     *	Purpose:		updating the average 2D array, which is the average across simulations
     *
     ****************************************************************************/


    public void avgdata() {

        int N = 0;
        double mean = 0;
        double devSum = 0;
        double delta;

        //calculate mean and std dev across all replications

        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[x].length; y++) {

                N = 0;
                mean = 0;
                devSum = 0;

                for (int z = 0; z < data[x][y].length; z++) {
                    N++;
                    delta = data[x][y][z] - mean;
                    mean += delta / N;
                    devSum += delta * (data[x][y][z] - mean);
                }

                avg[x][y] = mean;
                std[x][y] = Math.sqrt(devSum / (N - 1));
            }
        }


    }

    public void outputdata() {

        for (double[] x : this.avg) {
            for (double y : x) {
                System.out.print(y + ",");
            }
            System.out.println();

        }
    }


}

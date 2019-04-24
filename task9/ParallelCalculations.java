import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
public class ParallelCalculations implements ParallelCalculationsInterface {
   
    PointGeneratorInterface generator;
    int numberOfThreads;
    ArrayList<Thread> threads = new ArrayList<Thread>();
    ArrayList<Calculator> calculators = new ArrayList<Calculator>(); 
    double[] geomCenter = new double[2];
    int[][] histogram = new int[PointInterface.MAX_POSITION + 1][PointInterface.MAX_POSITION + 1];

    public ParallelCalculations() {
    }

    public void setPointGenerator(PointGeneratorInterface generator){
        this.generator = generator;
    }

    public void setNumberOfThreads(int threads) {
        numberOfThreads = threads;
        prepareThreads();
    }

    private void prepareThreads(){
        if(numberOfThreads > threads.size()){
            for(int i=threads.size();i<numberOfThreads;i++){
                calculators.add(new Calculator(generator));
                threads.add(new Thread(calculators.get(i)));
                threads.get(i).setName(String.valueOf(i));
                threads.get(i).start();
                threads.get(i).suspend();
            }
        }

        if(numberOfThreads < threads.size()){
            for(int i=threads.size()-1;i>=numberOfThreads;i--){
                calculators.get(i).setFlag(false);
                try{
                    threads.get(i).resume();
                    threads.get(i).join();    
                }catch(Exception e){}
                calculators.remove(i);
                threads.remove(i);
            }
        }
    }

    public void start(){
        prepareThreads();
        for(int i=0;i<threads.size();i++){
            threads.get(i).resume();
        }
    }

    private void clearHistogram(){
        for(int j=0;j<PointInterface.MAX_POSITION;j++){
            for(int k=0;k<PointInterface.MAX_POSITION;k++){
                histogram[j][k] = 0; 
            }
        }
    }

    public void suspendCalculations(){
        for(int i=0;i<calculators.size();i++){
            threads.get(i).suspend();
        }
        geomCenter[0] = 0;
        geomCenter[1] = 0;
        clearHistogram();

        int counter = 0;

        for(int i=0;i<numberOfThreads;i++){
            geomCenter[0] += calculators.get(i).geomCenter[0];
            geomCenter[1] += calculators.get(i).geomCenter[1];
            counter += calculators.get(i).counter;
            for(int j=0;j<PointInterface.MAX_POSITION;j++){
                for(int k=0;k<PointInterface.MAX_POSITION;k++){
                    histogram[j][k] += calculators.get(i).histogram[j][k];
                }
            }
        }

        if(counter != 0){
            geomCenter[0] /= counter;
            geomCenter[1] /= counter;
        }
    }

    public void continueCalculations() {
        for(int i=0;i<numberOfThreads;i++){
            threads.get(i).resume();
        }
    }

    public double[] getGeometricCenter() {
        return geomCenter;
    }

    public int[][] getHistogram(){
        return histogram;
    }
}



class Calculator extends Thread {
    
    private final AtomicBoolean running = new AtomicBoolean(false);
    PointGeneratorInterface generator;
    double[] geomCenter = new double[2];
    int[][] histogram = new int[PointInterface.MAX_POSITION + 1][PointInterface.MAX_POSITION + 1];
    int counter;
    public volatile int stillRunning;
    
    public Calculator(PointGeneratorInterface generator){
        this.generator = generator;
        running.set(true);
        stillRunning = 0;
    }

    public void setFlag(Boolean flag)
    {
        running.set(flag);
    }
    
    public void run(){
        PointInterface point;
        int[] recv = new int[2];
        if(running.get())
            stillRunning = 1;

        while(running.get()){
            point = generator.getPoint();
            recv = point.getPositions();
            geomCenter[0] += recv[0];
            geomCenter[1] += recv[1];
            histogram[recv[0]][recv[1]]++;
            counter++;
        }
        stillRunning = 0;

    }
}

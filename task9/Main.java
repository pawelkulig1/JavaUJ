public class Main{

    public static void main(String[] argv){
        PointGenerator pg = new PointGenerator();
        ParallelCalculations pc = new ParallelCalculations();
        pc.setPointGenerator(pg);
        pc.setNumberOfThreads(20);
        pc.start();
        try{Thread.sleep(1000);}catch(Exception e){}
        pc.suspendCalculations();

        
        double[] center = new double [2];
        center = pc.getGeometricCenter();
        pc.setNumberOfThreads(2);

        int[][] histogram = new int[PointInterface.MAX_POSITION][PointInterface.MAX_POSITION];
        histogram = pc.getHistogram();

        System.out.println(center[0] + " " + center[1]);
        printHistogram(histogram);
        

        pc.continueCalculations();
        try{Thread.sleep(1000);}catch(Exception e){}
        pc.suspendCalculations();

        center = pc.getGeometricCenter();
        histogram = pc.getHistogram();

        System.out.println(center[0] + " " + center[1]);
        printHistogram(histogram);

        pc.continueCalculations();
        try{Thread.sleep(1000);}catch(Exception e){}
        pc.suspendCalculations();

        pc.setNumberOfThreads(80);
        pc.continueCalculations();

        try{Thread.sleep(1000);}catch(Exception e){}
        pc.suspendCalculations();
        
        center = pc.getGeometricCenter();
        histogram = pc.getHistogram();

        System.out.println(center[0] + " " + center[1]);
        printHistogram(histogram);

    }

    public static void printHistogram(int[][] arr){
        for(int j=0;j<PointInterface.MAX_POSITION;j++){
            for(int k=0;k<PointInterface.MAX_POSITION;k++){
                System.out.print(arr[j][k] + " ");
            }
            System.out.println();
        }
    }
}

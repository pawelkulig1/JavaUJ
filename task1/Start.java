class Start {
    public static int N = 4;
    public static double X_FIRST = 2.5;
    public static double X_LAST = -0.2;
    public static int STEPS = 5;

    private static int factorial(int n){
        if(n == 1)
            return 1;
        return n * factorial(n - 1);
    }

    public static void main(String[] args){
        
        double step = (X_LAST - X_FIRST)/(STEPS+1); //+1 because we want to generate last one, 
        // first one has no dx in this approach
        for(int i=0;i<STEPS + 2;i++) //+2 because first and last 
        {
 
            double x = X_FIRST + i * step;

            double sum = 0;
            for(int n=0;n<N;n++){
                sum += (Math.pow(-1, n) * Math.pow(2, 2*n) * Math.pow(x, (2*n + 1)))
                    /(factorial(2 * n + 1));
            }
            double average = sum;
            double valueSinCos = Math.sin(x)*Math.cos(x);
            double delta = valueSinCos - average;

            String out = String.format( "x=%7.4f sin(x)cos(x)=%8.6f aprox=%8.6f delta=%10.8f", 
                    x,      valueSinCos,     average,  delta );
            
            System.out.println(out);
        }

    }

}

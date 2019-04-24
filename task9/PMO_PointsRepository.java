import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PMO_PointsRepository {

    public static int estimateSize( long time, long delay, int threads ) {
        return (int)( (threads * time ) / delay );
    }

    private static double getProbability( double[] probability, int idx ) {
        if ( idx >= probability.length ) return 0.05;
        return probability[ idx ];
    }

    public static List<PointInterface> getRepository( int size, double[] probability ) {
        List<PointInterface> points = new ArrayList<>();
        Random rnd = ThreadLocalRandom.current();
        int counter = 0;
        int x, y;
        double r;
        do {
            x = rnd.nextInt( PointInterface.MAX_POSITION);
            y = rnd.nextInt( PointInterface.MAX_POSITION);
            r = rnd.nextDouble();
            if ( r < getProbability( probability, x ) * getProbability( probability, y ) ) {
                int[] position = new int[] { x, y };
                points.add(new PointInterface() {
                    @Override
                    public int[] getPositions() {
                        return position;
                    }
                });
                counter++;
            }

        } while ( counter < size );

        return points;
    }
}

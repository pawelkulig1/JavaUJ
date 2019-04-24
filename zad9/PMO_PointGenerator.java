import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class PMO_PointGenerator implements PointGeneratorInterface {

    private final PMO_AtomicCounter parallelUsage;
    private final PMO_AtomicCounter maxUsage;
    private final AtomicBoolean suspendedFlag;
    private final AtomicLong delay;
    private final List<PointInterface> pointsRepository;
    private final AtomicInteger index = new AtomicInteger();
    private final AtomicInteger threadsAllowed;
    private final AtomicBoolean afterSuspensionUsage = new AtomicBoolean(false);
    private final AtomicBoolean numberOfThreadsExceeded = new AtomicBoolean(false);

    {
        maxUsage = PMO_CountersFactory.createCommonMaxStorageCounter();
        parallelUsage = PMO_CountersFactory.createCounterWithMaxStorageSet();
    }

    public PMO_PointGenerator( AtomicBoolean suspendedFlag, AtomicLong delay,
                               AtomicInteger threadsAllowed,
                               List<PointInterface> pointsRepository ) {
        this.suspendedFlag = suspendedFlag;
        this.delay = delay;
        this.pointsRepository = pointsRepository;
        this.threadsAllowed = threadsAllowed;
    }

    @Override
    public PointInterface getPoint() {
        if ( suspendedFlag.get() ) {
            afterSuspensionUsage.set( true );
        }
        parallelUsage.incAndStoreMax();

        int threads = maxUsage.get();
        if ( threads > threadsAllowed.get() ) {
            numberOfThreadsExceeded.set( true );
        }

        try {
            Thread.sleep( delay.get() );
        } catch ( InterruptedException e ) {}

        parallelUsage.dec();
        return pointsRepository.get( index.getAndIncrement() );
    }

    public int getMaxThreads() {
        return maxUsage.get();
    }

    public int[][] getHistogram() {
        int[][] result = new int[ PointInterface.MAX_POSITION + 1 ][ PointInterface.MAX_POSITION + 1 ];

        IntStream.range( 0, index.get() ).forEach( i -> {
            PointInterface point = pointsRepository.get( i );
            result[ point.getPositions()[0]][ point.getPositions()[1]] ++;
        });

        return result;
    }

    public double[] getGeometricCenter() {
        double[] result = new double[2];
        IntStream.range( 0, index.get() ).forEach( i -> {
            PointInterface point = pointsRepository.get( i );
            result[ 0 ] += point.getPositions()[0];
            result[1] += point.getPositions()[1];
        });
        result[0] /= index.get();
        result[1] /= index.get();
        return result;
    }

    public void resetMaxThreadCounter() {
        maxUsage.clear();
    }

    public void test() {
        assertFalse( afterSuspensionUsage.get(), "Oczekiwano, że po wykonaniu suspend punkty nie będą pobierane z generatora");
        assertFalse( numberOfThreadsExceeded.get(), "Przekroczono dozwoloną liczbę wątków");
    }
}

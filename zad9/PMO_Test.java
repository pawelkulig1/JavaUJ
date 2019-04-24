import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class PMO_Test {
    //////////////////////////////////////////////////////////////////////////
    private static final Map<String, Double> tariff = new HashMap<>();

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Tariff {
        double value();
    }

    static {
        tariff.put("parallelWork", 1.0 );
        tariff.put("histogramTest", 1.0 );
        tariff.put("geometricCenterTest",1.0);
        tariff.put("threadsDecrement", 1.0 );
        tariff.put("threadsIncrement", 1.0 );
    }

    public static double getTariff(String testName) {
        return tariff.get(testName);
    }

    //////////////////////////////////////////////////////////////////////////

    private static void showException(Throwable e, String txt) {
        e.printStackTrace();
        fail("W trakcie pracy metody " + txt + " doszło do wyjątku " + e.toString());
    }

    ////////////////////////////////////////////////////////////////////////////////

    private void prepare( long calcTime, long delay, int threads ) {
        prepare( calcTime, delay, threads, new double[] { 0.3, 0.2, 0.1, 0.2, 0.3, 0.1, 0.2, 0,4 });
    }

    private void prepare( long calcTime, long delay, int threads, double[] probabilities ) {
        int size = PMO_PointsRepository.estimateSize( calcTime, delay, threads ) * 2;
        this.delay.set( delay );
        threadsAllowed.set( threads );

        generator = new PMO_PointGenerator( suspendCalculations, this.delay, threadsAllowed,
                PMO_PointsRepository.getRepository(size, probabilities ) );

        try {
            calculations.setPointGenerator( generator );
            setNumberOfThreads( threads );
        } catch ( Exception e ) {
            showException( e, "W trakcie przygotowywania obliczeń doszło do wyjątku");
        }
    }


    private static void sleep( long time ) {
        try {
            Thread.sleep( time );
        } catch ( InterruptedException e ) {}
    }

    private static <T> T tryToExecute(ThrowingSupplier<T> run, String txt, long timeLimit ) {
        try {
            return assertTimeoutPreemptively(Duration.ofMillis(timeLimit), run::get, "Przekroczono limit czasu " + txt);
        } catch (Exception e) {
            showException(e, txt);
        }
        return null;
    }

    private static void tryToExecute( Runnable run, String txt, long timeLimit ) {
        try {
            assertTimeoutPreemptively(Duration.ofMillis(timeLimit), run::run, "Przekroczono limit czasu " + txt);
        } catch (Exception e) {
            showException(e, txt);
        }
    }

    private void suspendCalculations() {
        tryToExecute( calculations::suspendCalculations, "suspendCalculations", 20 );
        suspendCalculations.set(true);
    }

    private void continueCalculations() {
        suspendCalculations.set(false);
        tryToExecute( calculations::continueCalculations, "continueCalculations", 20 );
    }

    private void start() {
        tryToExecute( calculations::start, "start", 20 );
    }

    private void setNumberOfThreads( int threads ) {
        generator.resetMaxThreadCounter();
        threadsAllowed.set( threads );
        tryToExecute( () -> calculations.setNumberOfThreads(threads), "setNumberOfThreads", 20 );
    }

    ////////////////////////////////////////////////////////////////////////////////

    private ParallelCalculationsInterface calculations;
    private AtomicBoolean suspendCalculations;
    private AtomicInteger threadsAllowed;
    private AtomicLong delay;
    private PMO_PointGenerator generator;

    @BeforeEach
    public void creator() {
        calculations = new ParallelCalculations();
        suspendCalculations = new AtomicBoolean();
        threadsAllowed = new AtomicInteger();
        delay = new AtomicLong();
    }

    @DisplayName("Test pracy równoległej")
    @RepeatedTest( 3 )
    public void parallelWork() {
        long calcTime = 2000;
        int threads = 3;
        prepare( calcTime, 20, threads );
        start();
        sleep( calcTime );
        suspendCalculations();
        assertEquals( threads, generator.getMaxThreads(), "Oczekiwano lepszego użycia dostępnych wątków" );
        generator.test();
    }

    @DisplayName("Test poprawności wyliczania histogramu")
    @RepeatedTest( 3 )
    public void histogramTest() {
        long calcTime = 2000;
        int threads = 4;
        prepare( calcTime, 10, threads, new double[] {1.0, 0.1 } );
        start();
        sleep( calcTime );
        suspendCalculations();
        int[][] result = tryToExecute( () -> calculations.getHistogram(), "getHistogram", 5 );
        assertNotNull( result, "Oczekiwano, że getHistogram nie zwróci null");
        int [][] expectedHistogram = generator.getHistogram();

        assertEquals( PointInterface.MAX_POSITION + 1, result.length, "Błędny rozmiar histogramu");
        assertEquals( PointInterface.MAX_POSITION + 1, result[0].length, "Błędny rozmiar histogramu");

        for ( int col = 0; col <= PointInterface.MAX_POSITION; col++ )
            for( int row = 0; row <= PointInterface.MAX_POSITION; row++ ) {
                assertEquals( expectedHistogram[col][row], result[col][row], "Błędna liczba zliczeń w histogramie na " +
                        "pozycji [" + col + "][" + row + "]");
            }
        generator.test();
    }

    @DisplayName("Test poprawności wyliczania środka geometrycznego")
    @RepeatedTest( 3 )
    public void geometricCenterTest() {
        long calcTime = 2000;
        int threads = 5;
        prepare( calcTime, 10, threads );
        start();
        sleep( calcTime );
        suspendCalculations();
        double[] result = tryToExecute( () -> calculations.getGeometricCenter(), "getGeometricCenter", 5 );
        assertNotNull( result, "Oczekiwano, że getGeometricCenter nie zwróci null");
        double [] expected = generator.getGeometricCenter();

        assertEquals( 2, result.length, "Błędny rozmiar tablicy zwracanej przez getGeometricCenter");

        assertArrayEquals( expected, result, 0.001, "Błędna wartość pozycji środka geometrycznego");
        generator.test();
    }

    @DisplayName("Test zmniejszenia liczby watków")
    @RepeatedTest( 3 )
    public void threadsDecrement() {
        long calcTime = 1000;
        int threads = 4;
        prepare( 2*calcTime, 20, threads );
        start();
        sleep( calcTime );
        suspendCalculations();
        assertEquals( threads, generator.getMaxThreads(), "Oczekiwano lepszego użycia dostępnych wątków" );
        setNumberOfThreads( 2 );
        continueCalculations();
        sleep( calcTime );
        suspendCalculations();
        assertEquals( 2, generator.getMaxThreads(), "Oczekiwano innej liczby użytych wątków" );
        generator.test();
    }

    @DisplayName("Test zwiększenia liczby watków")
    @RepeatedTest( 3 )
    public void threadsIncrement() {
        long calcTime = 1000;
        int threads = 4;
        prepare( 2*calcTime, 20, 4 );
        start();
        sleep( calcTime );
        suspendCalculations();
        assertEquals( threads, generator.getMaxThreads(), "Oczekiwano lepszego użycia dostępnych wątków" );
        setNumberOfThreads( 6 );
        continueCalculations();
        sleep( calcTime );
        suspendCalculations();
        assertEquals( 6, generator.getMaxThreads(), "Oczekiwano innej liczby użytych wątków" );
        generator.test();
    }
}

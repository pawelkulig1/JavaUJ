import java.util.Random;
public class PointGenerator implements PointGeneratorInterface {
    Random rand = new Random();
    public PointInterface getPoint(){
        int x = rand.nextInt(PointInterface.MAX_POSITION + 1) + 0;
        int y = rand.nextInt(PointInterface.MAX_POSITION + 1) + 0;
        return new Point(x, y);

    }
}

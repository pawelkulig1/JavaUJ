import java.util.ArrayList;
public class BetterPoint extends AbstractBetterPoint{

    private int dimensions;
    private double[] coordinates;
    private ArrayList<String> passw;

    public BetterPoint(){
        passw = new ArrayList<String>();
    }

    public void setDimensions(int dimensions){
        this.dimensions = dimensions;
        coordinates = new double[dimensions];
    }

    public int lockLevel(){
        return passw.size();
    }

    public int lock(String password){
        if(password == null){
            return lockLevel();
        }
        passw.add(0, password);
        return lockLevel();
    }

    public int unlock(String password){
        if(lockLevel() == 0 || password == null){
            return lockLevel();
        }
        if(!password.equals(passw.get(0)))
        {
            return lockLevel();
        }
        passw.remove(0);
        return lockLevel();
    }

    public boolean move(int dimension, double delta){
        if(delta == 0 || lockLevel() != 0){
            return false;
        }
        coordinates[dimension] += delta;
        return true;
    }

    public boolean set(int dimension, double value){
        if(dimension < 0 || dimension >= this.dimensions){
            return false;
        }
        if(lockLevel() != 0){
            return false;
        }

        coordinates[dimension] = value;
        return true;
    }

    public double get(int dimension){
        return coordinates[dimension];
    }
}

public class Position implements PositionInterface {
    private int x;
    private int y;

    public Position(int xr, int yr){
        x = xr;
        y = yr;
    }

    public int getRow(){
        return this.x;
    }

    public int getCol(){
        return this.y;
    }   

    public String toString(){
        return "Point on: " + String.valueOf(getCol()) + ", " + String.valueOf(getRow());
    }


}

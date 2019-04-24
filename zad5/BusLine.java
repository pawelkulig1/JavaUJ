import java.util.ArrayList;

class BusLine implements BusLineInterface {
    private ArrayList<BusStopInterface> stopContainer;
    
    public BusLine(ArrayList<BusStopInterface> s) {
        stopContainer = s;
    }
    
    @Override
    public int getNumberOfBusStops() {
        return stopContainer.size();
    }
    
    @Override
    public BusStopInterface getBusStop(int number) {
        return stopContainer.get(number);
    }

    public String toString() {
        String temp = "BusLine: Length: " + String.valueOf(getNumberOfBusStops()) + "\n" ;
        for(BusStopInterface bs: stopContainer){
            temp += bs.toString() + "\n";
        }
        return temp;
    }

}

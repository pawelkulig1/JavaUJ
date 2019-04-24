import java.util.ArrayList;

public class PathFinder3 implements PathFinderInterface {
    
    ArrayList<BusLineInterface> busLines = new ArrayList<>();
    ArrayList<BusInterface> buses = new ArrayList<>();
    ArrayList<ArrayList<BusStopInterface>> globalVisited = new ArrayList<ArrayList<BusStopInterface>>();
    ArrayList<BusStopInterface> path = new ArrayList<BusStopInterface>();
    BusLineInterface lastLine;
    @Override
    public void addLine(BusLineInterface line, BusInterface bus) {
        busLines.add(line);
        buses.add(bus);
    }


    ArrayList<BusInterface> getBusesFromStop(BusStopInterface stop){
        ArrayList<BusInterface> ret = new ArrayList<>();
        for(int j=0;j<busLines.size();j++){
            for(int i=0;i<busLines.get(j).getNumberOfBusStops();i++){
                if(busLines.get(j).getBusStop(i) == stop){
                    ret.add(buses.get(j));
                }
            }
        }
        return ret;
    }

    public ArrayList<BusStopInterface> recursiveFinder(BusStopInterface s1, BusStopInterface s2, int depth){
        ArrayList<BusInterface> possibleBuses = getBusesFromStop(s1);
        ArrayList<BusStopInterface> temp;
        ArrayList<BusStopInterface> ret = new ArrayList<>();
        ArrayList<BusStopInterface> possibleStops = new ArrayList<>();
        if(depth < 0){
            return null;
        }

        for(int i=0;i<possibleBuses.size();i++){
            /*if(possibleBuses.get(i) == s2){
                System.out.println("found!");
                return ret;
            }*/
            int lineIndex = buses.indexOf(possibleBuses.get(i)); //getCurrent

            for(int j=0;j<busLines.get(i).getNumberOfBusStops();j++){
                 
            }

 //           if()
            

            if(possibleBuses.get(i) != lastLine){
                temp = recursiveFinder(possibleBuses.get(i), s2, depth - 1);
            }
            else{
                temp = recursiveFinder(possibleBuses.get(i), s2, depth);
            }


            if(temp == null){

            }
            else{
                System.out.println("found2!");
                return ret;
            }
        }
        return ret;
    }

    public void find( BusStopInterface from, BusStopInterface to, int transfers ){
        
    }

    public int getNumerOfSolutions(){
        return 0;
    }

    public int getBusStops( int solution ){
        return 0;
    }

    public BusStopInterface getBusStop( int solution, int busStop ){
        return null;
    }

    public BusInterface getBus( int solution, int busStop ){
        return null;
    }
}

import java.util.ArrayList;


public class PathFinder2 implements PathFinderInterface{
    
    ArrayList<BusLineInterface> busLines = new ArrayList<>();
    ArrayList<BusInterface> buses = new ArrayList<>();
    
    public ArrayList<ArrayList<BusStopInterface>> solutions = new ArrayList<ArrayList<BusStopInterface>>();
    ArrayList<BusStopInterface> path = new ArrayList<>();
    BusInterface currentBus;


    @Override
    public void addLine(BusLineInterface line, BusInterface bus) {
        busLines.add(line);
        buses.add(bus);
    }

    public ArrayList<BusLineInterface> getLinesFromStop(BusStopInterface stop) {
        ArrayList<BusLineInterface> al = new ArrayList<BusLineInterface>();
        for(BusLineInterface bl: busLines) {
            for(int i=0;i<bl.getNumberOfBusStops();i++){
                if(bl.getBusStop(i).getName().equals(stop.getName())) { 
                    al.add(bl);
                }
            }
        }
        return al;
    }
    public ArrayList<BusStopInterface> findNextStops(BusStopInterface here){

        ArrayList<BusStopInterface> neighboursStops = new ArrayList<>();
        ArrayList<BusLineInterface> lines = getLinesFromStop(here);
        for(BusLineInterface line: lines){
            for(int i=0;i<line.getNumberOfBusStops();i++){
                if(line.getBusStop(i) == here){
                    if(i > 0){
                        neighboursStops.add(line.getBusStop(i-1));
                    }
                    if(i<line.getNumberOfBusStops() - 1){
                        neighboursStops.add(line.getBusStop(i+1));
                    }
                }
            }
        }
        return neighboursStops;
    }

    public ArrayList<BusInterface> findBusesConnectingStops(BusStopInterface s1, BusStopInterface s2){
        ArrayList<BusLineInterface> lines1 = getLinesFromStop(s1);
        ArrayList<BusLineInterface> lines2 = getLinesFromStop(s2);
        ArrayList<BusInterface> busesIn = new ArrayList<BusInterface>();
        for(BusLineInterface line: lines1){
            int temp = lines2.indexOf(line);
            if(temp != -1){
                busesIn.add(buses.get(temp));
            }
        }
        return busesIn;
    }

    public ArrayList<BusStopInterface> recursiveFinder(BusStopInterface s1, BusStopInterface s2, int depth){

        if(s1 == s2){
            System.out.println("found1!");
            path.add(s1);
            solutions.add((ArrayList<BusStopInterface>) path.clone());
            path.remove(path.size() - 1);
            return null;           
            //ArrayList<BusStopInterface> x = new ArrayList<>();
        }
        if(depth < 0){
            return null;
        }
        path.add(s1); 
        ArrayList<BusStopInterface> temp;


        ArrayList<BusStopInterface> possibleStops = findNextStops(s1);
        if(currentBus == null){
            currentBus = findBusesConnectingStops(s1, possibleStops.get(0)).get(0);
        }

        System.out.println(currentBus.toString() + s1.toString());
        for(BusStopInterface stop: possibleStops){
            if(path.indexOf(stop) != -1){
                continue;
            }
            if(stop == s1){
                path.add(s1);
                solutions.add((ArrayList<BusStopInterface>) path.clone());
                path.remove(path.size() - 1);
            }
            System.out.print(findBusesConnectingStops(s1, stop));
            if(findBusesConnectingStops(s1, stop).indexOf(currentBus) < 0){
                System.out.println("switch bus!");
                depth = depth-1;
                currentBus = findBusesConnectingStops(s1, stop).get(0);
            }
            temp = recursiveFinder(stop, s2, depth);

            if(temp == null){
                continue;
            }
            else{
                System.out.println("found2!");
                //ArrayList<BusStopInterface> x = new ArrayList<>();
                path.add(s1);
                solutions.add((ArrayList<BusStopInterface>) path.clone());
                path.remove(path.size() - 1);
            }
        }

        return null;
    }

    public void find( BusStopInterface from, BusStopInterface to, int transfers ) {
        
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

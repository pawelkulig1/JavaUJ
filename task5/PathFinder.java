import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class PathFinder implements PathFinderInterface {
    ArrayList<BusLineInterface> busLines = new ArrayList<>();
    ArrayList<BusInterface> buses = new ArrayList<>();

    ArrayList<ArrayList<BusStopInterface>> globalStops = new ArrayList<ArrayList<BusStopInterface>>();

    private ArrayList<ArrayList<BusLineInterface>> globalPaths = new ArrayList<ArrayList<BusLineInterface>>();
    public ArrayList<ArrayList<BusStopInterface>> globalSolutions = new ArrayList<ArrayList<BusStopInterface>>();
    ArrayList<BusLineInterface> path = new ArrayList<BusLineInterface>();

    ArrayList<BusStopInterface> visitedStops = new ArrayList<>();
    ArrayList<BusLineInterface> usedLines = new ArrayList<>();

    public PathFinder() {
        
    }

    @Override
    public void addLine(BusLineInterface line, BusInterface bus) {
        busLines.add(line);
        buses.add(bus);
    }

    //public BusStopInterface 
    
    private BusStopInterface findIntersectingStop(BusLineInterface lin1, BusLineInterface lin2){
        BusStopInterface bs1;
        BusStopInterface bs2;
        for(int i=0;i<lin2.getNumberOfBusStops();i++){
            bs2 = lin2.getBusStop(i);
            for(int j=0;j<lin1.getNumberOfBusStops();j++){
                bs1 = lin1.getBusStop(j);
                if( bs1 == bs2 ){
                    return bs1;
                }
            }
        }

        return null;
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

    public ArrayList<BusLineInterface> getIntersectingLines(BusLineInterface line) {
        ArrayList<BusLineInterface> il = new ArrayList<BusLineInterface>();
        ArrayList<BusLineInterface> temp = new ArrayList<>();

        for(int i=0;i<line.getNumberOfBusStops();i++) {
            temp.addAll(getLinesFromStop(line.getBusStop(i)));
        }

        for(BusLineInterface l: temp) {
            if(l != line){
                il.add(l);
            }
        }
        
        return il;
    }


    public ArrayList<BusStopInterface> fromTo(BusStopInterface from, BusStopInterface to, BusLineInterface line){
        
        ArrayList<BusStopInterface> temp = new ArrayList<BusStopInterface>();
        ArrayList<BusStopInterface> ret = new ArrayList<BusStopInterface>();

        for(int i=0;i<line.getNumberOfBusStops();i++)
        {
            temp.add(line.getBusStop(i));
        }

        int startPoz = temp.indexOf(from);
        int endPoz = temp.indexOf(to);



        if(startPoz <= endPoz){
            ret.addAll(temp.subList(startPoz, endPoz+1));
        }
        else {

            ret.addAll(temp.subList(endPoz, startPoz));
            Collections.reverse(ret);
        }

        return ret;
    }

    public void findStopsFromLines(BusStopInterface from, BusStopInterface to) {
        ArrayList<BusStopInterface> temp = new ArrayList<BusStopInterface>();
        int k = 0;

        for(ArrayList<BusLineInterface> ap : globalPaths){
            globalSolutions.add((ArrayList<BusStopInterface>)temp.clone());
            globalSolutions.get(k).add(from);
            for(int j=0;j<ap.size();j++){
                if(j >= ap.size()-1) {
                    globalSolutions.get(k).addAll(fromTo(globalSolutions.get(k).get(globalSolutions.get(k).size() - 1), to, ap.get(j)));
                    break;
                }
                for(int i=0;i<ap.get(j).getNumberOfBusStops();i++){

                    if(getLinesFromStop(ap.get(j).getBusStop(i)).contains(ap.get(j+1))){
                        
                        globalSolutions.get(k).addAll(fromTo(globalSolutions.get(k).get(globalSolutions.get(k).size() - 1), ap.get(j).getBusStop(i), ap.get(j)));
                    }
                }
            }
            k++;
        }
    }

    @Override
    public void find(BusStopInterface from, BusStopInterface to, int transfers) {
        ArrayList<BusLineInterface> start = getLinesFromStop(from);
        ArrayList<BusLineInterface> stop = getLinesFromStop(to);

        globalPaths.clear();
        globalSolutions.clear();

        //solution on our line

        ArrayList<BusLineInterface> linesFrom = getLinesFromStop(from);
        ArrayList<BusLineInterface> linesTo = getLinesFromStop(to);

        ArrayList<BusStopInterface> solution; 
        ArrayList<BusLineInterface> path = new ArrayList<BusLineInterface>();
        for(int i=0;i<linesFrom.size();i++){
            if(linesTo.indexOf(linesFrom.get(i)) != -1 & transfers == 0){
                solution = fromTo(from, to, linesFrom.get(i));

                globalSolutions.add(solution);
                path.add(linesFrom.get(i));
                globalPaths.add(path);
            }
            
        }
        recursiveFinder(from, to, transfers);
    }
    
    public ArrayList<BusLineInterface> recursiveFinder(BusStopInterface from, BusStopInterface to, int depth){
        ArrayList<BusLineInterface> linesFrom = getLinesFromStop(from);
        ArrayList<BusLineInterface> linesTo = getLinesFromStop(to);
        ArrayList<BusLineInterface> intersectingLines = new ArrayList<BusLineInterface>();

        for(int i=0;i<linesFrom.size();i++){
            intersectingLines.addAll(getIntersectingLines(linesFrom.get(i)));
        }

        //remove duplicates
        HashSet<BusLineInterface> tempSet = new LinkedHashSet<BusLineInterface>();
        tempSet.addAll(intersectingLines);
        intersectingLines.clear();
        intersectingLines.addAll(tempSet);

        ArrayList<BusStopInterface> solution = new ArrayList<BusStopInterface>(); 
        ArrayList<BusLineInterface> path = new ArrayList<BusLineInterface>();
        if(depth == 1){
            for(int i =0;i<intersectingLines.size();i++){
                int index = linesTo.indexOf(intersectingLines.get(i));
                if(index != -1){
                    path.add(getLinesFromStop(from).get(0));
                    path.add(linesTo.get(index));
                    System.out.println(index + " " + i);
                    BusStopInterface intersectingStop = findIntersectingStop(getLinesFromStop(from).get(0), linesTo.get(index));
                    System.out.println(from + " | " + intersectingStop + " | " + getLinesFromStop(from));
                    solution.addAll(fromTo(from, intersectingStop, getLinesFromStop(from).get(0)));
                    ArrayList<BusStopInterface> temp = fromTo(intersectingStop, to, linesTo.get(index));
                    temp.remove(0);
                    solution.addAll(temp);
                    //solution.addAll((fromTo(intersectingStop, to, linesTo.get(index))).remove(0));
                    globalSolutions.add(solution);
                    globalPaths.add(path);
                }
            }
        }

        return null;

    }

    @Override
    public int getNumerOfSolutions() {
        return globalSolutions.size();
    }

    @Override
    public int getBusStops(int solutions) {
        return globalSolutions.get(solutions).size();
    }

    @Override
    public BusStopInterface getBusStop(int solution, int busStop) {
        return globalSolutions.get(solution).get(busStop);
    }

    @Override
    public BusInterface getBus(int solution, int busStop) {
        //BusLineInterface nextStopLine = 
        //System.out.println(globalSolutions);
        //System.out.println(solution + " " + busStop);
        BusStopInterface requestedStop = getBusStop(solution, busStop);
        ArrayList<BusLineInterface> temp = getLinesFromStop(requestedStop);
        BusLineInterface nextLine = globalPaths.get(solution).
        int index = (requestedStop);
        if(index != -1){
            return 
        }
        //System.out.println(temp);
        //System.out.println(globalPaths);
        /*int max = -1;
        for(BusLineInterface bl: temp){
            if(globalPaths.get(solution).indexOf(bl) > max){
                max = globalPaths.get(solution).indexOf(bl);
            }
        }*/
        return buses.get(max);
    }
}


//k - stala anizotropi z kierunkiem
//minima od ktorych zaczynamy
//Ms - magnetyzacja nasycenia
//zorientowanie drugiej wartswy ktora sie nie bedzie ruszac
//wartosz energi sprzezenia miedzy nimi
//wymiary geometryczne, moj program bierze do liczenia tensorow
//zew pole magnetyczne
//


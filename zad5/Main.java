import java.util.ArrayList;

public class Main {
    public static void main(String []args) {
        /*ArrayList<BusStopInterface> l1 = new ArrayList<>();
        ArrayList<BusStopInterface> l2 = new ArrayList<>();
        ArrayList<BusStopInterface> l3 = new ArrayList<>();
        ArrayList<BusStopInterface> l4 = new ArrayList<>();


        for(int i=0;i<5;i++){
            l1.add(new BusStop("l0." + String.valueOf(i)));
        }

        l2.add(new BusStop("l1.0"));
        l2.add(l1.get(1));
        l2.add(new BusStop("l1.2"));
        l2.add(new BusStop("l1.3"));
        l2.add(new BusStop("l1.4"));
        l2.add(new BusStop("l1.5"));

        l3.add(new BusStop("l2.0"));
        l3.add(new BusStop("l2.1"));
        l3.add(new BusStop("l2.2"));
        l3.add(l2.get(5));
        l3.add(new BusStop("l2.4"));
        l3.add(new BusStop("l2.5"));

        l4.add(new BusStop("l3.0"));
        l4.add(l1.get(3));
        l4.add(new BusStop("l3.2"));
        l4.add(new BusStop("l3.3"));
        l4.add(new BusStop("l3.4"));
        l4.add(l3.get(5));
    

        BusLine bl1 = new BusLine(l1);
        BusLine bl2 = new BusLine(l2);
        BusLine bl3 = new BusLine(l3);
        BusLine bl4 = new BusLine(l4);
        
        Bus bus0 = new Bus(0);
        Bus bus1 = new Bus(1);
        Bus bus2 = new Bus(2);
        Bus bus3 = new Bus(3);

        PathFinder pf = new PathFinder();

        pf.addLine(bl1, bus0);
        pf.addLine(bl2, bus1);
        pf.addLine(bl3, bus2);
        pf.addLine(bl4, bus3);

        ArrayList<BusLineInterface> temp = pf.getLinesFromStop(l1.get(1));

        assert temp.get(0) == bl1;
        assert temp.get(1) == bl2;
        assert temp.size() == 2;

        temp = pf.getLinesFromStop(l1.get(3));
        assert temp.size() == 2;
        assert temp.get(0) == bl1;
       
        temp = pf.getIntersectingLines(bl1); 
        assert temp.size() == 2;
        assert temp.get(0) == bl2;
        
        pf.find(l1.get(0), l3.get(0), 2);

        assert pf.getNumerOfSolutions() == 2;
        assert pf.getBusStops(0) == 9;
        assert pf.getBusStop(0, 5) == l2.get(5);

        pf.find(l1.get(4), l2.get(0), 4);
        assert pf.getNumerOfSolutions() == 2;





        //test2
        l1.clear();
        l2.clear();
        l3.clear();
        l4.clear();


        ArrayList<BusStopInterface> l0 = new ArrayList<>();
        ArrayList<BusStopInterface> l5 = new ArrayList<>();
        ArrayList<BusStopInterface> l6 = new ArrayList<>();
        ArrayList<BusStopInterface> l7 = new ArrayList<>();

        for(int i=0;i<6;i++){
            l0.add(new BusStop("l0." + String.valueOf(i)));
        
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l1.add(l0.get(4));
                continue;
            }
            l1.add(new BusStop("l1." + String.valueOf(i)));
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l2.add(l1.get(4));
                continue;
            }
            l2.add(new BusStop("l2." + String.valueOf(i)));
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l3.add(l2.get(4));
                continue;
            }
            l3.add(new BusStop("l3." + String.valueOf(i)));
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l4.add(l3.get(4));
                continue;
            }
            l4.add(new BusStop("l4." + String.valueOf(i)));
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l5.add(l4.get(4));
                continue;
            }
            l5.add(new BusStop("l5." + String.valueOf(i)));
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l6.add(l5.get(4));
                continue;
            }
            l6.add(new BusStop("l6." + String.valueOf(i)));
        }

        for(int i=0;i<6;i++){
            if(i == 1){
                l7.add(l6.get(4));
                continue;
            }
            l7.add(new BusStop("l7." + String.valueOf(i)));
        }

        BusLine bl0 = new BusLine(l0);
        bl1 = new BusLine(l1);
        bl2 = new BusLine(l2);
        bl3 = new BusLine(l3);
        bl4 = new BusLine(l4);
        BusLine bl5 = new BusLine(l5);
        BusLine bl6 = new BusLine(l6);
        BusLine bl7 = new BusLine(l7);

        bus0 = new Bus(0);
        bus1 = new Bus(1);
        bus2 = new Bus(2);
        bus3 = new Bus(3);
        Bus bus4 = new Bus(4);
        Bus bus5 = new Bus(5);
        Bus bus6 = new Bus(6);
        Bus bus7 = new Bus(7);

        PathFinder pf2 = new PathFinder();

        pf2.addLine(bl0, bus0);
        pf2.addLine(bl1, bus1);
        pf2.addLine(bl2, bus2);
        pf2.addLine(bl3, bus3);
        pf2.addLine(bl4, bus4);
        pf2.addLine(bl5, bus5);
        pf2.addLine(bl6, bus6);
        pf2.addLine(bl7, bus7);


        assert pf2.getNumerOfSolutions() == 0;
        pf2.find(l0.get(0), l7.get(5), 7);

        assert pf2.getNumerOfSolutions() == 1;
        assert pf2.getBusStops(0) == 27;
        assert pf2.getBus(0, 1) == bus0;
        assert pf2.getBus(0, 4) == bus1;
        assert pf2.getBus(0, 5) == bus1;
        assert pf2.getBus(0, 7) == bus2;
        assert pf2.getBus(0, 6) == bus1;
        assert pf2.getBus(0, 26) == bus7;

        pf2.find(l0.get(0), l1.get(1), 1);
        assert pf2.getNumerOfSolutions() == 1;

        pf2.find(l0.get(0), l7.get(5), 6);
        assert pf2.getNumerOfSolutions() == 0;
*/

        test2();
    } 

    public static void test2(){
        System.out.println("test2 ===========================================================");
        ArrayList<ArrayList<BusStopInterface>> l = new ArrayList<ArrayList<BusStopInterface>>();
        ArrayList<BusStopInterface> temp = new ArrayList<BusStopInterface>();
        for(int i=0;i<5;i++){
            l.add((ArrayList<BusStopInterface>) temp.clone());
            for(int j =0;j<6;j++){
                l.get(i).add(new BusStop("l" + String.valueOf(i) + "." + String.valueOf(j)));
            }
        }
        
        l.get(1).set(1, l.get(0).get(1));
        l.get(1).set(2, l.get(0).get(2));
        l.get(1).set(3, l.get(0).get(3));
        l.get(2).set(1, l.get(0).get(4));
        l.get(3).set(1, l.get(2).get(4));
        l.get(4).set(1, l.get(3).get(4));
        l.get(4).set(4, l.get(0).get(1));

        ArrayList<BusInterface> buses = new ArrayList<BusInterface>();
        ArrayList<BusLineInterface> lines = new ArrayList<BusLineInterface>();

        PathFinder pf = new PathFinder();
        for(int i=0;i<5;i++){
            buses.add(new Bus(i));
            lines.add(new BusLine(l.get(i)));
            pf.addLine(lines.get(i), buses.get(i));
        }
        pf.recursiveFinder(l.get(0).get(1), l.get(1).get(5), 1);
        System.out.println(pf.globalStops);
    }
}

public class Main{
    public static void main(String a[]){
        int [][] map = new int[5][7];
        for(int i=0;i<7;i++){
            for(int j=0;j<5;j++){
                map[j][i] = 0;
            }
        }

        map[1][1] = 1;
        map[2][1] = 1;
        map[3][1] = 1;
        map[3][2] = 1;
        map[3][3] = 1;
        map[2][3] = 9;
        map[1][3] = 1;
        map[1][2] = 1;

        //map[1][1] = 1;


        PathFinderEnum l = PathFinderEnum.LEFT_HAND_TRAFFIC;
        PathFinderEnum r = PathFinderEnum.RIGHT_HAND_TRAFFIC;

        l.setMap(map);
        r.setMap(map);

        l.printMap();

        Position p1 = new Position(1, 2);
        Position p2 = new Position(3, 2);        

        PositionInterface [] out = l.getShortestRoute(p1, p2);

        System.out.println("OUTPUT left:");
        for(int i=0;i<out.length;i++){
            System.out.println(out[i]);
        }

        out = r.getShortestRoute(p1, p2);

        System.out.println("OUTPUT right:");
        for(int i=0;i<out.length;i++){
            System.out.println(out[i]);
        }

        p1 = new Position(3,2);
        p2 = new Position(2,1);

        out = l.getEasiestRoute(p1, p2);

        System.out.println("OUTPUT left:");
        for(int i=0;i<out.length;i++){
            System.out.println(out[i]);
        }

        out = r.getEasiestRoute(p2, p1);

        System.out.println("OUTPUT right:");
        for(int i=0;i<out.length;i++){
            System.out.println(out[i]);
        }

        p1 = new Position(1,3);
        p2 = new Position(3,3);

        out = l.getFastestRoute(p1, p2);
        System.out.println("OUTPUT left:");
        for(int i=0;i<out.length;i++){
            System.out.println(out[i]);
        }

        out = r.getFastestRoute(p1, p2);

        System.out.println("OUTPUT right:");
        for(int i=0;i<out.length;i++){
            System.out.println(out[i]);
        }

/*      Position prev = new Position(2,2);
        Position now = new Position(2,3);
        Position next = new Position(3,3);

        assert pf.detectDir(prev, now, next) == 1;
        next = new Position(1,3);
        assert pf.detectDir(prev, now, next) == -1;
        next = new Position(2,4);
        assert pf.detectDir(prev, now, next) == 0;
    
        now = new Position(3,2);
        next = new Position(3,3);

        assert pf.detectDir(prev, now, next) == -1;
        next = new Position(3,1);
        assert pf.detectDir(prev, now, next) == 1;
        next = new Position(4,2);
        assert pf.detectDir(prev, now, next) == 0;


        now = new Position(2,1);
        next = new Position(3,1);

        assert pf.detectDir(prev, now, next) == -1;
        next = new Position(1,1);
        assert pf.detectDir(prev, now, next) == 1;
        next = new Position(2,0);
        assert pf.detectDir(prev, now, next) == 0;

        now = new Position(1,2);
        next = new Position(1,1);

        assert pf.detectDir(prev, now, next) == -1;
        next = new Position(1,3);
        assert pf.detectDir(prev, now, next) == 1;
        next = new Position(0,2);
        assert pf.detectDir(prev, now, next) == 0;*/
    }

}

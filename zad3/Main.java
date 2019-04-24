public class Main{
    public static void main(String argv[]){
        int arr[] = new int[5];
        arr[0] = 5;
        arr[1] = 5;
        arr[2] = 4;
        arr[3] = 1;
        arr[4] = 10;
        ShipSizeLimit limit = new ShipSizeLimit(arr);
        Ship.setLimit(limit);
        System.out.println(limit.getLimit(0));
        Ship[] myShips = new Ship[6];
        for(int i=0;i<6;i++){
        
            myShips[i] = Ship.getShip(1);
            System.out.println(myShips[i]);
        }
        myShips[0].shipwreck();
        myShips[5] = Ship.getShip(1);
        System.out.println(myShips[5]);
        System.out.println(Ship.getShip(1));

        Ship[] myShips2 = new Ship[6];
        for(int i=0;i<6;i++){

            myShips2[i] = Ship.getShip(2);
            System.out.println(myShips2[i]);
        }
        myShips2[0].shipwreck();
        myShips2[5] = Ship.getShip(2);
        System.out.println(myShips2[5]);
        System.out.println(Ship.getShip(2));
        
        System.out.println(Ship.getShip(0));
        System.out.println(Ship.getShip(5));
        System.out.println(Ship.getShip(6));
        
        ShipSizeLimit limit2 = new ShipSizeLimit(arr);
        Ship.setLimit(limit2);

        System.out.println(Ship.getShip(1));
        System.out.println(Ship.getShip(1));
        System.out.println(Ship.getShip(1));
        System.out.println(Ship.getShip(1));
        System.out.println(Ship.getShip(1));
        System.out.println(Ship.getShip(1));




    }
}

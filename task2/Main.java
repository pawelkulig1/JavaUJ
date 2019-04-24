class Main{
    public static void main(String[] args){
        SimpleCalculations si = new SimpleCalculations();
        Point p1 = new Point();
        p1.setNumberOfDimensions(4);

        Point p2 = new Point();
        p2.setNumberOfDimensions(4);

        p1.setPosition(0, -1);
        p1.setPosition(1, 7);
        p1.setPosition(2, 5);
        p1.setPosition(3, 1);
        
        p2.setPosition(0, 0);
        p2.setPosition(1, -3);
        p2.setPosition(2, -5);
        p2.setPosition(3, 2);

        Point[] points = new Point[4];
        points = si.equidistantPoints(p1, p2, 4);

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                System.out.print(points[i].getPosition(j) + " ");
            }
            System.out.println();
        }


        System.out.println();
        Point massCenter;
        massCenter = si.geometricCenter(points);
        for(int i=0;i<massCenter.getNumberOfDimensions(); i++){
            System.out.print(massCenter.getPosition(i) + " ");
        }


        System.out.println();

        Point[] pointsEmpty = new Points[0];
        System.out.println("equi " + si.equidistantPoints(null, null, 5));
        System.out.println("geometric: " + si.geometricCenter(null));
        System.out.println("geometric: " + si.geometricCenter(pointsEmpty));

        p1.setPosition(0, 5);
        p1.setPosition(1, 5);
        p1.setPosition(2, 5);
        p1.setPosition(3, 3);
        
        p2.setPosition(0, 5);
        p2.setPosition(1, 5);
        p2.setPosition(2, 2);
        p2.setPosition(3, 3);
        

        Point next = si.next(p1, p2, 5);
        for(int i=0;i<p1.getNumberOfDimensions();i++){
            System.out.print(next.getPosition(i) + " ");
        }
        System.out.println();
    }
}

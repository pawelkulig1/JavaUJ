class SimpleCalculations {
    public Point[] equidistantPoints(Point firstPoint, Point secondPoint, int points) {
        
        if(firstPoint == null || secondPoint == null)
            return null;

        int dims = firstPoint.getNumberOfDimensions();
        double[] d = new double[dims];
        Point[] retPoints = new Point[points];

        for(int i=0;i<dims;i++){
                d[i] = Math.abs(firstPoint.getPosition(i) - 
                        secondPoint.getPosition(i))/(points+1);
        }
        
        double minVal = 0;
        for(int i=0;i<points;i++){
            retPoints[i] = new Point();
            retPoints[i].setNumberOfDimensions(dims);
            for(int dim=0;dim<dims;dim++){
                if(firstPoint.getPosition(dim) <= secondPoint.getPosition(dim)){
                    minVal = firstPoint.getPosition(dim);
                }
                else{
                    minVal = secondPoint.getPosition(dim);
                }
                retPoints[i].setPosition(dim, (i + 1) * d[dim] + minVal);
            }
        }
        return retPoints;
    }

    public Point geometricCenter(Point[] points) {
        if(points == null || points.length == 0)
            return null;
        Point massCenter = new Point();
        int dims = points[0].getNumberOfDimensions();
        massCenter.setNumberOfDimensions(dims);
        double[] mass = new double[dims];

        for(int i=0;i<points.length;i++){
            for(int dim=0;dim<dims;dim++)
            {
                mass[dim] += points[i].getPosition(dim);
            }
        }

        for(int i=0;i<dims;i++){
            massCenter.setPosition(i, mass[i]/points.length);
        }
        
        return massCenter;
    }

    public Point next(Point firstPoint, Point secondPoint, double distance) {
        if(firstPoint == null || secondPoint == null)
            return null;

        int dims = firstPoint.getNumberOfDimensions();
        double dVal = 0;
        Point retPoint = new Point();
        retPoint.setNumberOfDimensions(dims);

        for(int i=0;i<dims;i++){
            retPoint.setPosition(i, firstPoint.getPosition(i));
            if(firstPoint.getPosition(i) != secondPoint.getPosition(i)){
                dVal = firstPoint.getPosition(i) - secondPoint.getPosition(i);

                if(dVal < 0)
                    retPoint.setPosition(i, secondPoint.getPosition(i) + distance);
                else
                    retPoint.setPosition(i, secondPoint.getPosition(i) - distance);
            }
        }
        return retPoint;
    }
}

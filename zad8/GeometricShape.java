import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Optional;


class GeometricShape implements GeometricShapeInterface {
    
    private ArrayList<Point> points = new ArrayList<>();

    public GeometricShape(){

    }
    
    public int getPointDims(Point point){
        int i = 0;
        while(true){
            try{
                point.getPosition(i);
            } catch(Exception e) {
                return i;
            }
            i++;
        }
    }

    public void add(Point point) throws WrongNumberOfDimensionsException {
        if(points.size() != 0){
            int setPoints = getPointDims(points.get(0));
            int recPoints = getPointDims(point);
            if(setPoints != recPoints){
                throw new WrongNumberOfDimensionsException(setPoints, recPoints);
            }
        }
        points.add(point);
    }

    public void remove(Point point) throws WrongArgumentException {
        if(!points.contains(point)){
            throw new WrongArgumentException(point);
        }
        boolean ret = points.remove(point);
    }

    public void addBefore(Point point, Point beforePoint) throws WrongArgumentException,
           WrongNumberOfDimensionsException {

        int setPoints = getPointDims(points.get(0));
        int recPoints = getPointDims(point);
        if(setPoints != recPoints){
            throw new WrongNumberOfDimensionsException(setPoints, recPoints);
        }

        recPoints = getPointDims(beforePoint);
        if(setPoints != recPoints){
            throw new WrongNumberOfDimensionsException(setPoints, recPoints);
        }

        if(!points.contains(beforePoint)){
            throw new WrongArgumentException(beforePoint);
        }


        int index = points.indexOf(beforePoint);
        boolean ret = false;
        if(index != -1){
            ret = true;
            points.add(index, point);
        }
    }

    public void addAfter(Point point, Point afterPoint) throws WrongNumberOfDimensionsException, WrongArgumentException {
        
        int setPoints = getPointDims(points.get(0));
        int recPoints = getPointDims(point);
        if(setPoints != recPoints){
            throw new WrongNumberOfDimensionsException(setPoints, recPoints);
        }

        recPoints = getPointDims(afterPoint);
        if(setPoints != recPoints){
            throw new WrongNumberOfDimensionsException(setPoints, recPoints);
        }

        if(!points.contains(afterPoint)){
            throw new WrongArgumentException(afterPoint);
        }


        int index = points.indexOf(afterPoint);
        for(int i=0;i<points.size();i++){
            if(points.get(i).equals(afterPoint) && index < i){
                index = i;
            }
        }
        boolean ret = false;
        if(index != -1){
            ret = true;
            points.add(index + 1, point);
        }
        //return ret;
    }

    public Point removeBefore(Point beforePoint) throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException {

        if(points.indexOf(beforePoint) == 0){
            throw new NoSuchPointException(beforePoint);
        }

        int setPoints = getPointDims(points.get(0));
        int recPoints = getPointDims(beforePoint);
        if(setPoints != recPoints){
            throw new WrongNumberOfDimensionsException(setPoints, recPoints);
        }

        if(!points.contains(beforePoint)){
            throw new WrongArgumentException(beforePoint);
        }

        

        int index = points.indexOf(beforePoint);
        if(index > 0){
            Point ret = points.remove(index - 1);
            return ret;
        }
        return null;
    }

    public Point removeAfter(Point afterPoint) throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException {
        
        if(points.indexOf(afterPoint) == points.size() - 1){
            throw new NoSuchPointException(afterPoint);
        }

        int setPoints = getPointDims(points.get(0));
        int recPoints = getPointDims(afterPoint);
        if(setPoints != recPoints){
            throw new WrongNumberOfDimensionsException(setPoints, recPoints);
        }

        if(!points.contains(afterPoint)){
            throw new WrongArgumentException(afterPoint);
        }

        int index = points.indexOf(afterPoint);
        for(int i=0;i<points.size();i++){
            if(points.get(i).equals(afterPoint) && index < i){
                index = i;
            }
        }

        if(index != -1 && index < points.size() - 1){
            Point ret = points.remove(index + 1);
            return ret;
        }
        return null;
    }

    public List<Point> get(){
        return (List<Point>)points;
    }

    public Set<Point> getSetOfPoints(){

        Set out = new LinkedHashSet(points);

        /*ArrayList<Point> tempList = (ArrayList<Point>)points.clone();
        System.out.println(tempList.size());
        for(int i=tempList.size() - 1;i>1;i--){
            if(tempList.get(i).equals(tempList.get(i-1))) {
                tempList.remove(i);
            }
        }
        return tempList;*/
        return out;
    }

    public Optional<Point> getByPosition(List<Integer> positions) throws WrongNumberOfDimensionsException {

        int expDims = getPointDims(points.get(0));
        if(positions.size() != expDims){
            throw new WrongNumberOfDimensionsException(expDims, positions.size());
        }

        Optional ret = Optional.empty();
        int counter = 0;
        Point found = null;
        for(Point p: points){
            counter = 0;
            for(int i=0;i<positions.size();i++){
                if(p.getPosition(i) == positions.get(i)){
                    counter++;
                }
                else {
                    break;
                }
            }
            if(counter == positions.size()){
                found = p;
            }
        }
        return ret.ofNullable(found);
    }

    public void clear(){
        points.clear();

    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;


class GeometricShape implements GeometricShapeInterface {
    
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<ArrayList<Point>> memento = new ArrayList<ArrayList<Point>>();
    private ArrayList<Point> redoState = null;
    private boolean canRedo = false;

    public GeometricShape(){

    }
    
    private void saveMemento(){
        memento.add((ArrayList<Point>)points.clone());
        canRedo = false;
    }

    public void add(Point point){
        saveMemento();
        points.add(point);
    }

    public boolean remove(Point point) {
        boolean ret = points.remove(point);
        if(ret == true){
            saveMemento();
        }
        return ret;
    }

    public boolean addBefore(Point point, Point beforePoint){
        int index = points.indexOf(beforePoint);
        boolean ret = false;
        if(index != -1){
            ret = true;
            saveMemento();
            points.add(index, point);
        }
        return ret;
    }

    public boolean addAfter(Point point, Point afterPoint) {
        
        int index = points.indexOf(afterPoint);
        for(int i=0;i<points.size();i++){
            if(points.get(i).equals(afterPoint) && index < i){
                index = i;
            }
        }
        boolean ret = false;
        if(index != -1){
            ret = true;
            saveMemento();
            points.add(index + 1, point);
        }
        return ret;
    }

    public Point removeBefore(Point beforePoint) {
        int index = points.indexOf(beforePoint);
        if(index > 0){
            saveMemento();
            Point ret = points.remove(index - 1);
            return ret;
        }
        return null;
    }

    public Point removeAfter(Point afterPoint) {
        int index = points.indexOf(afterPoint);
        for(int i=0;i<points.size();i++){
            if(points.get(i).equals(afterPoint) && index < i){
                index = i;
            }
        }

        if(index != -1 && index < points.size() - 1){
            saveMemento();
            Point ret = points.remove(index + 1);
            return ret;
        }
        return null;
    }

    public boolean undo() {
        if(memento.size() > 0){
            redoState = points;
            points = memento.get(memento.size() - 1);
            memento.remove(memento.size() - 1);
            canRedo = true;
            return true;
        }
        return false;
    }


    public boolean redo() {
        if(redoState != null && canRedo == true){
            saveMemento();
            points = (ArrayList<Point>)redoState.clone();
            return true;
        }
        return false;
    }

    public List<Point> get(){
        return (List<Point>)points;
    }

    // public List<Point> getUniq(){
    //     Set<Point> tempS = new LinkedHashSet<Point>();
    //     tempS.addAll((List<Point>)points.clone());
    //     List<Point> tempL = new ArrayList<Point>();
    //     tempL.addAll(tempS);
    //     return tempL;
    // }

    public List<Point> getUniq(){
        ArrayList<Point> tempList = (ArrayList<Point>)points.clone();
        System.out.println(tempList.size());
        for(int i=tempList.size() - 1;i>1;i--){
            if(tempList.get(i).equals(tempList.get(i-1))) {
                tempList.remove(i);
            }
        }
        return tempList;
    }

    public Map<Point, Integer> getPointsAsMap(){
        HashMap<Point, Integer> hm = new HashMap<Point, Integer>();
        for(int i=0;i<points.size();i++) { //not working yet
            if(hm.containsKey(points.get(i))){
                hm.put(points.get(i), hm.get(points.get(i)) + 1);
            }
            else{
                hm.put(points.get(i), 1);
            }
        }
        return hm;
    }

    public void clear(){
        points.clear();

    }
}

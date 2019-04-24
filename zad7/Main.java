import java.util.*;
import java.util.ArrayList;

public class Main{

    private static Point clone(Point source) {
        return generate(source.getPosition(0), source.getPosition(1), source.getPosition(2));
    }

    private static Point generate(int p1, int p2, int p3) {
        Point result = new Point();
        result.setPosition(0, p1);
        result.setPosition(1, p2);
        result.setPosition(2, p3);
        return result;
    }

    public static void main(String[] s){
        oramusFailedTests();

    }

    public static void addAll(List<Point> elements, GeometricShape g){
        for(Point p: elements){
            g.add(p);
        }
    }

    public static void oramusFailedTests(){
        GeometricShape g2 = new GeometricShape();
        Point p1 = generate(0, 0, 1);
        Point p2 = generate(0, 0, 2);
        Point p3 = generate(0, 3, 0);
        Point p4 = generate(4, 0, 0);
        Point p5 = generate(5, 0, 1);
        Point p6 = generate(0, 6, 1);
        Point p7 = generate(1, 1, 7);
        Point p1c = clone(p1);
        Point p2c = clone(p2);
        Point p3c = clone(p3);
        Point p4c = clone(p4);
        Point p5c = clone(p5);
        Point p6c = clone(p6);
        Point p7c = clone(p7);

        addAll(List.of(p1, p2, p1, p1, p3, p4, p3, p3, p6), g2);
        System.out.println(g2.getUniq());
        // g2.clear();
        addAll(List.of(p1, p2, p5, p5, p5, p5, p6), g2);
        System.out.println(g2.getUniq());

    }

    public void myTests(){
        GeometricShape g = new GeometricShape();
        Point p1 = new Point();
        ArrayList<Point> points = new ArrayList<Point>();
        for(int i =0;i<10;i++){
            points.add(new Point());
            for(int j=0;j<3;j++){
                points.get(i).setPosition(j, i);
            }
        }
        
        g.add(points.get(0));
        g.addBefore(points.get(1), points.get(0));
        assert g.addBefore(points.get(3), points.get(5)) == false;
        
        g.addAfter(points.get(2), points.get(0));
        assert g.get().size() == 3;
        assert g.addAfter(points.get(3), points.get(5)) == false;
        assert g.get().size() == 3;
        
        assert g.removeBefore(points.get(1)) == null;
        assert g.removeAfter(points.get(2)) == null;

        assert g.get().size() == 3;

        assert g.removeAfter(points.get(0)) == points.get(2);
        assert g.removeBefore(points.get(0)) == points.get(1);

        assert g.get().size() == 1;

        g.undo();
        assert g.get().size() == 2;

        g.undo();
        assert g.get().size() == 3;
        
        g.redo();
        assert g.get().size() == 2;

        g.undo();
        assert g.get().size() == 3;

        
        g.add(points.get(0));
        g.add(points.get(1));
    
        ArrayList<Point> temp = new ArrayList<Point>();

        temp.add(points.get(1));
        temp.add(points.get(0));
        temp.add(points.get(2));
        temp.add(points.get(0));
        temp.add(points.get(1));

        int i = 0;
        for(Point p: g.get()){
            assert p == temp.get(i++);
        }
        temp.clear();

        temp.add(points.get(1));
        temp.add(points.get(0));
        temp.add(points.get(2));

        i = 0;
        for(Point p: g.getUniq()){
            assert p == temp.get(i++);
        }

        System.out.println(g.getPointsAsMap());
        g.undo();
        g.redo();

        i = 0;
        for(Point p: g.getUniq()){
            assert p == temp.get(i++);
        }
        temp.clear();

        temp.add(points.get(1));
        temp.add(points.get(0));
        temp.add(points.get(2));
        temp.add(points.get(0));
        temp.add(points.get(1));

        i=0;
        for(Point p: g.get()){
            assert p == temp.get(i++);
        }

        temp.add(1, points.get(5));
        g.addBefore(points.get(5), points.get(0));

        i=0;
        for(Point p: g.get()){
            assert p == temp.get(i++);
        }

        System.out.println(g.get());
        System.out.println(g.getPointsAsMap());
        System.out.println(g.get().size());


        System.out.println(g.get());
        assert g.get().size() == 6;
        assert g.undo() == true;
        System.out.println(g.get());
        assert g.get().size() == 5;
        assert g.undo() == true;
        System.out.println(g.get());
        assert g.get().size() == 4;
        assert g.undo() == true;
        System.out.println(g.get());
        assert g.get().size() == 3;
        assert g.undo() == true;
        System.out.println(g.get());
        assert g.get().size() == 2;
        assert g.undo() == true;
        System.out.println(g.get());
        assert g.get().size() == 1;
        assert g.undo() == true;
        assert g.undo() == false;
        assert g.redo() == true;
        assert g.get().size() == 1;
        assert g.undo() == true;
        assert g.get().size() == 0;
        System.out.println(g.getPointsAsMap());
        System.out.println(g.getUniq());
        g.add(points.get(6));
        assert g.redo() == false;
        assert g.redo() == false;

    }
}

public class Main{
    public static void main(String args[]){

        BetterPoint p = new BetterPoint();
        p.setDimensions(3);
        assert p.set(0, 0) == true;
        assert p.set(1, 0) == true;
        assert p.set(2, 0) == true;
        assert p.set(3, 0) == false;

        assert p.move(0, 0) == false;
        assert p.move(0, 0.1) == true;

        assert p.lock("A") == 1;
        
        assert p.move(0, 2) == false;
        assert p.get(0) == 0.1;

        assert p.lock("B") == 2;
        assert p.set(0, 0) == false;
        assert p.get(0) == 0.1;

        assert p.unlock("A") == 2;
        assert p.unlock(null) == 2;
        assert p.lock(null) == 2;
        assert p.move(0, 1) == false;
        assert p.get(0) == 0.1;
        assert p.unlock("B") == 1;
        assert p.unlock("A") == 0;
        
        assert p.set(0, 0) == true;
        assert p.get(0) == 0;

        BetterPoint p2 = new BetterPoint();
        p2.setDimensions(2);
        assert p2.set(0, 0) == true;
        assert p2.set(1, 1) == true;
        assert p2.set(2, 2) == false;
        assert p2.lock("a") == 1;
        assert p.set(0, 1) == true; //p!
        assert p.get(0) == 1;
        assert p2.unlock("a") == 0;
        assert p2.move(1, 2) == true;
        assert p2.get(0) == 0;
        assert p2.get(1) == 3;

        assert p2.lock("a") == 1;
        assert p2.lock("a") == 2;
        assert p2.lock("a") == 3;
        assert p2.move(0, 1) == false;
        assert p2.unlock("a") == 2;
        assert p2.move(0, 1) == false;
        assert p2.unlock("a") == 1;
        assert p2.move(0, 1) == false;
        assert p2.unlock("a") == 0;
        
    }
}

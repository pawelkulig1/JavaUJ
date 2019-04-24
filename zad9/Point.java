class Point implements PointInterface {
    private int x;
    private int y;
    
    public Point(int x, int y){
        if(x > MAX_POSITION){
            x = MAX_POSITION;
        } 

        if(y > MAX_POSITION){
            y = MAX_POSITION;
        }
        this.x = x;
        this.y = y;
    }

    public int[] getPositions(){
        int[] temp = new int[2];
        temp[0] = x;
        temp[1] = y;
        return temp;
    } 
}

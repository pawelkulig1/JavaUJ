import java.util.ArrayList;

public enum PathFinderEnum implements PathFinderInterface {
    LEFT_HAND_TRAFFIC(true),
    RIGHT_HAND_TRAFFIC(false);

    private PathFinderEnum(boolean left){
        this.LEFT = left;
    }

    boolean LEFT;
    int [][]used;
    public int [][]map; 
    ArrayList<ArrayList<PositionInterface>> allPaths = new ArrayList<ArrayList<PositionInterface>>();
    ArrayList<PositionInterface> paths = new ArrayList<PositionInterface>();


    public void setMap(int [][] mapR){
        map = new int[mapR[0].length][mapR.length];

        for(int y=0;y<mapR.length;y++){
            for(int x=0;x<mapR[0].length;x++){
                map[x][y] = mapR[y][x];

            }
        }

        used = new int[map.length][map[0].length];
        clear();
        //printMap();
    }

    private void clear(){
        allPaths.clear();
        paths.clear();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                used[i][j] = 0;
            }
        }
    }

    public void printMap(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] getPossibleMoves(int x, int y){
        int [][]ret = new int[4][2];
        int i = 0;

        if(map[x-1][y] != 0 && used[x-1][y] == 0){
            ret[i][0] = x-1;
            ret[i][1] = y;
        }
        else{
            ret[i][0] = -1;
            ret[i][1] = -1;
        }
        i+=1;

        if(map[x+1][y] !=0 && used[x+1][y] == 0){
            ret[i][0] = x+1;
            ret[i][1] = y;
        }
        else{
            ret[i][0] = -1;
            ret[i][1] = -1;
        }
        i+=1;

        if(map[x][y+1] != 0 && used[x][y + 1] == 0){
            ret[i][0] = x;
            ret[i][1] = y + 1;
        }
        else{
            ret[i][0] = -1;
            ret[i][1] = -1;
        }

        i+=1;
        if(map[x][y-1] != 0 && used[x][y - 1] == 0){
            ret[i][0] = x;
            ret[i][1] = y - 1;
        }
        else{
            ret[i][0] = -1;
            ret[i][1] = -1;
        }
        return ret;
    }

    public void print2Darr(int [][]arr){
        for(int i=0;i<arr.length;i++){
            System.out.println("");
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]);
            }
        }
    }

    private PositionInterface[] allPathsFinder(PositionInterface begin, PositionInterface end){
        int x = begin.getRow();
        int y = begin.getCol();

        int [][]moves = new int[4][2];
        moves = getPossibleMoves(x, y);
        PositionInterface[] temp;
        for(int i=0;i<4;i++){
            Position p = new Position(moves[i][0], moves[i][1]);
            if(p.getCol() == end.getCol() & p.getRow() == end.getRow()){
                paths.add(p);
                allPaths.add((ArrayList<PositionInterface>)paths.clone());
                paths.remove(paths.size() - 1);
                used[x][y] = 0;
                return null;
            }
            if(moves[i][0] != -1){
                paths.add(p);
                used[x][y] = 1;
                temp = allPathsFinder(p, end);
                if(temp == null){
                    paths.remove(paths.size() - 1);
                    used[x][y] = 0;
                }
                else{
                    allPaths.add((ArrayList<PositionInterface>)paths.clone());
                    paths.remove(paths.size() - 1);
                    used[x][y] = 0;
                    return null;
                }
            }
        }
        return null;
    }

    public int detectDir(PositionInterface prev, PositionInterface now, PositionInterface next){
        //detect if crossing
        
        int posX = now.getRow();
        int posY = now.getCol();
        int sum = 0;

        if(map[posX][posY + 1] != 0)
            sum += 1; 
        if(map[posX][posY - 1] != 0)
            sum += 1; 
        if(map[posX + 1][posY] != 0)
            sum += 1; 
        if(map[posX - 1][posY] != 0)
            sum += 1;

        if(sum < 3)
            return 0;
        

        int dirx = now.getRow() - prev.getRow();
        int diry = now.getCol() - prev.getCol();

        int movex = next.getRow() - now.getRow();
        int movey = next.getCol() - now.getCol();
        
        if(now.getRow() == prev.getRow() && now.getRow() == next.getRow()){
            return 0;
        }

        if(now.getCol() == prev.getCol() && now.getCol() == next.getCol()){
            return 0;
        }

        int retVal = 0;
        if(LEFT == true)
            retVal = -1;
        else
            retVal = 1;

        if(dirx == 1){
            if(movey == 1)
                return -1 * retVal;
            return 1 * retVal;
        }

        if(dirx == -1){
            if(movey == 1)
                return 1 * retVal;
            return -1 * retVal;
        }

        if(diry == 1){
            if(movex == 1)
                return 1 * retVal;
            return -1 * retVal;
        }

        if(diry == -1){
            if(movex == 1)
                return -1 * retVal;
            return 1 * retVal;
        }

        return 0;
    }

    public PositionInterface[] getShortestRoute(PositionInterface begin, PositionInterface end){
        clear();
        allPathsFinder(begin, end);
        for(int i=0;i<allPaths.size();i++){
            allPaths.get(i).add(0, begin);
        }

        int minSteps = 1000000;

        int minStepsSum = 0;
        int minIndex = 0;
        if(LEFT == true)
            minStepsSum = minSteps;    

        for(int i=0;i<allPaths.size();i++){
            int sum = 0;
            //minSteps = allPaths.get(i).size();//minStepsCost = costSum;
            for(int j=1;j<allPaths.get(i).size()-1;j++){
                sum += detectDir(allPaths.get(i).get(j-1), allPaths.get(i).get(j), allPaths.get(i).get(j+1));
            }
            if(minSteps > allPaths.get(i).size()){
                minSteps = allPaths.get(i).size();
                minStepsSum = sum;
                minIndex = i;
            }
            if(minSteps == allPaths.get(i).size()){
                if(sum < minStepsSum){
                    minIndex = i;
                    minStepsSum = sum;
                }
            }
        }
        if(allPaths.size() > 0){
            PositionInterface[] temp = new PositionInterface[allPaths.get(minIndex).size()];
            temp = allPaths.get(minIndex).toArray(temp);
            return temp;
        }
        PositionInterface[] temp = new PositionInterface[0];
        return temp;
    }


    public PositionInterface[] getEasiestRoute(PositionInterface begin, PositionInterface end) {
        clear();
        allPathsFinder(begin, end);
        for(int i=0;i<allPaths.size();i++){
            allPaths.get(i).add(0, begin);
        }

        int maxIndex = 0;
        int maxPointsSum = 0;
        for(int i=0;i<allPaths.size();i++){
            int pointsSum = 0;
            for(int j=1;j<allPaths.get(i).size()-1;j++){
                pointsSum += detectDir(allPaths.get(i).get(j-1), allPaths.get(i).get(j), allPaths.get(i).get(j+1));
            }
            if(pointsSum < maxPointsSum){
                //minStepsCost = costSum;
                maxIndex = i;
                maxPointsSum = pointsSum;
            }
        }

        if(allPaths.size() > 0){
            PositionInterface[] temp = new PositionInterface[allPaths.get(maxIndex).size()];
            temp = allPaths.get(maxIndex).toArray(temp);
            return temp;
        }
        PositionInterface[] temp = new PositionInterface[0];
        return temp;
    }

    public PositionInterface[] getFastestRoute(PositionInterface begin, PositionInterface end){
        clear();
        allPathsFinder(begin, end);
        
        for(int i=0;i<allPaths.size();i++){
            allPaths.get(i).add(0, begin);
        }

        int minStepsCost = 1000000;
        int bestIndex = 0;
        int maxPointsSum = 0;

        for(int i=0;i<allPaths.size();i++){
            int pointsSum = 0;
            int costSum = 0;
            for(int j=1;j<allPaths.get(i).size()-1;j++){
                pointsSum += detectDir(allPaths.get(i).get(j-1), allPaths.get(i).get(j), allPaths.get(i).get(j+1));
            }

            for(int j=0;j<allPaths.get(i).size();j++){
                costSum += map[allPaths.get(i).get(j).getRow()][allPaths.get(i).get(j).getCol()];
            }
            
            if(costSum < minStepsCost){
                minStepsCost = costSum;
                bestIndex = i;
                maxPointsSum = pointsSum;
            }
            
            
            if(costSum == minStepsCost){
                if(pointsSum < maxPointsSum){
                    minStepsCost = costSum;
                    bestIndex = i;
                    maxPointsSum = pointsSum;
                }
            }
        }
        if(allPaths.size() > 0){
            PositionInterface[] temp = new PositionInterface[allPaths.get(bestIndex).size()];
            temp = allPaths.get(bestIndex).toArray(temp);
            return temp;
        }
        PositionInterface[] temp = new PositionInterface[0];
        return temp;
    }
}

class Position implements PositionInterface {
    private int x;
    private int y;

    public Position(int xr, int yr){
        x = xr;
        y = yr;
    }

    public int getRow(){
        return this.x;
    }

    public int getCol(){
        return this.y;
    }   

/*    public String toString(){
        return "Point on: " + String.valueOf(getCol()) + ", " + String.valueOf(getRow());
    }*/
}

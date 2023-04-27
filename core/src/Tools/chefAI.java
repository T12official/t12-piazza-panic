package Tools;

import Sprites.Chef;
import com.team13.piazzapanic.IdleScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Chef AI contains a path finding script that can tell a chef how to get between 2 points of a game world
 * This is implemented uses an algorithm that takes inspration from Dijkstra's shortest path algorithm.
 * This class could be further expanded into a chef that can actually play the game properly
 */

public class chefAI {


    //TODO make a ai to make burgers and salad

    private IdleScreen game;
    private Chef mychef;
    private int targetx = 4;
    private int targetY = 1;

    private int[][] targetsPoints = {{4,1},{4,8},{9,8},{9,1}}; //This provides a ser of positions for the chef to navigate to
    private int[][] myIntArray =
                    {{19, 18, 18, 18, 18, 18, 18, 18, 18, 20,}, //This is a map of the kitchen and its tiles
                    {16, 21, 21, 21, 21, 21, 21, 21, 21, 17,},
                    {16, 21, 21, 21, 21, 21, 21, 21, 21, 17,},
                    {1, 2, 2, 2, 1, 1, 1, 1, 1, 1,},
                    {1, 10, 10, 10, 10, 10, 10, 10, 10, 1,},
                    {1, 10, 10, 10, 10, 10, 10, 10, 10, 1,},
                    {1, 10, 10, 4, 6, 5, 1, 10, 10, 15,},
                    {1, 10, 10, 1, 1, 8, 7, 10, 10, 15,},
                    {14, 10, 10, 10, 10, 10, 10, 10, 10, 1,},
                    {1, 10, 10, 10, 10, 10, 11, 12, 12, 1,},
                    {1, 1, 13, 1, 1, 1, 3, 9, 9, 1}};
    private int targetVal = 0;

    public chefAI(IdleScreen game) {
        this.game = game;




    }


    public String pickMovementDirection(){
        /**
         * This function uses its current x,y index in the coord grid, and the next x,y index returned by the path finding algorithm
         * to work out which input the chef needs to be provides out of wasd
         */

        int[] myData = convertPosToXY(game.chef1.getNotificationX(), game.chef1.getNotificationY());
        if (myData[0] == targetx && myData[1] == targetY || myData[0] == 0){
            //System.out.println("i arrived at my target node");
            targetx = targetsPoints[targetVal][1];
            targetY = targetsPoints[targetVal][0];
            targetVal ++;
            if (targetVal >= 4){targetVal = 0;}
            return "";
        }

        List<String> targets = defineShortestPath(myData[1], myData[0], targetx, targetY);
        int tarX = Integer.parseInt( targets.get(targets.size() - 1).split(",")[1]);
        int tarY = Integer.parseInt( targets.get(targets.size() - 1).split(",")[0]);
        //System.out.println(String.format("my target was %d %d :::: %d %d ", tarX, tarY, myData[0], myData[1]));
        if (tarX < myData[0]){return "a";}
        if (tarX > myData[0]){return "d";}
        if (tarY > myData[1]){return "s";}
        if (tarY < myData[1]){return  "w";}

        return "w";
    }

    public String returnKeyboardInput() {
        /**
         * This function provides a way for the idle screen to retreive the input that the chef AI wishes to make
         */
        //System.out.println(String.format("chef x: %f chef y: %f ",game.chef1.getX(), game.chef1.getX() )); ;
       // System.out.println(String.format("chef x: %f chef y: %f ",game.chef1.getNotificationX(), game.chef1.getNotificationY() ));
        convertPosToXY(game.chef1.getNotificationX(), game.chef1.getNotificationY());
        //System.out.println("\n\n" + game.map.getLayers().get(0).getObjects(). + "\n\n");
        return pickMovementDirection();
    }

    public List<String> defineShortestPath(int cols, int rows, int endNode, int endCol) {
        /**
         * This is the shortest path algorithm. It will return the optimal shortest path as their is no heuristic
         * @param cols - the current Y location of the chef
         * @param rows - the current X location of the chef
         * @param endNode - the wanted end X position of the chef
         * @param endCol = the wanted end Y position of the chef
         */
        HashMap<String, String> nodes = new HashMap<String, String>();
        HashMap<String, String> visited = new HashMap<String, String>();
        List<String> fringe = new ArrayList<>();
        defineInit(nodes, cols, rows);


        visitNextNodes(cols, rows, fringe, nodes, visited);
        visited.put("2,2", "visited");
        while (fringe.size() > 0) {
            String fringe0 = fringe.get(0);

            String[] args = fringe0.split(",");
            fringe.remove(0);
            visitNextNodes(Integer.parseInt(args[0]), Integer.parseInt(args[1]), fringe, nodes, visited);
            visited.put(fringe0, "visited");
        }

        List<String> targets = printShortestPath(endCol,endNode,nodes);

        return targets;


    }


    private List<String> printShortestPath(int startCol, int startRow, HashMap<String, String> nodes){
        /**
         * This function will go through the hashmap starting from the end node and return  a list of all of the step from the startt to the end node
         */

        List<String> pathToFollow = new ArrayList<>();
        String currentNode = Integer.toString(startCol) + "," + Integer.toString(startRow);
        String endNode = "INIT";
        while (currentNode.equals(endNode) == false){
            System.out.println(currentNode + "apples");
            pathToFollow.add(currentNode);
            String temp = nodes.get(currentNode);
            currentNode = temp.split(",")[2];
            currentNode = currentNode.replace(":", ",");


        }
        pathToFollow.remove(pathToFollow.size() - 1);
        return pathToFollow;

    }


    private void defineInit(HashMap<String, String> nodes, int cols, int rows) {

        nodes.put(Integer.toString(cols) + "," + Integer.toString(rows), "0,C,INIT");
    }

    private void visitNextNodes(int nodeCol, int nodeRow, List<String> fringe, HashMap<String, String> nodes, HashMap<String, String> visited) {
        /**
         * This function will take a node, and attempt to explore all of the adjacent nodes if they are not already explored.
         * @param nodeCol - the Y index of the current node
         * @param nodeRow - the X position of the current node
         * @param fringe - A list of nodes that have been reached by the algorithm, but the algorithm has not yet explored to see where they lead to
         * @param nodes - A collection of nodes that can be explored
         * @param visited - a hashmap that allows the algorithm to store a node, the previous node that comes before it, and other details
         */

        if (visited.containsKey(Integer.toString(nodeCol) + "," + Integer.toString(nodeRow))) {
            return;
        } else {
            visited.put(Integer.toString(nodeCol) + "," + Integer.toString(nodeRow), "visited");
        }

        int temprow;
        int tempCol;
        String tempStr;
        // 0-10
        //0-9

        //Left
        if (nodeRow > 0) {
            temprow = nodeRow - 1;
            tempCol = nodeCol;
            process(fringe, tempCol, temprow,nodes,visited, nodeCol, nodeRow);


        }
        //Right
        if (nodeRow < 9) {
            temprow = nodeRow + 1;
            tempCol = nodeCol;
            process(fringe,tempCol, temprow, nodes,visited, nodeCol, nodeRow);

        }

        //up
        if (nodeCol > 0) {
            temprow = nodeRow;
            tempCol = nodeCol - 1;
            process(fringe,tempCol, temprow, nodes,visited, nodeCol, nodeRow);


        }

        //down
        if (nodeCol < 10) {
            temprow = nodeRow;
            tempCol = nodeCol + 1;
            process(fringe,tempCol, temprow, nodes,visited, nodeCol, nodeRow);

        }

    }



    private int[] convertPosToXY(double x, double y){ //This function is assuming x and y and tiles the player can move to
        /**
         * This function takes the X, Y position of a chef, and works out how it translates into an X,y index of the tile map.
         */

        //bottomY = 0.208184     topY = 1.056333
        //xStart = 0.198333      //xMidle = 0.441956
        double tileWidth = (0.441956 - 0.198333) / 2;
        double[] bottomLeftTileCorner = {0.198333, 0.208184 };

        int tileNumX = (int) ((x - bottomLeftTileCorner[0]) / tileWidth);
        int tileNumY = (int) ((y - bottomLeftTileCorner[1]) / tileWidth);
        System.out.println("x tile is:" + getXconv(tileNumX));
        System.out.println("y tle is " + getYconv(tileNumY));
        return new int[]{getXconv(tileNumX), getYconv(tileNumY)};



    }

    private int getYconv(int y){
        if (y < 4) { return 9 - y;}
        return 10 - y;
    }
    private int getXconv(int x){
        if (x > 4) { return x - 1;}
       return  x + 1;

    }


    private void process(List<String> fringe, int tempCol, int temprow, HashMap<String, String> nodes, HashMap<String, String> visited, int nodeCol, int nodeRow) {
        if (myIntArray[tempCol][temprow] != 10 &&myIntArray[tempCol][temprow] != 11 && myIntArray[tempCol][temprow] != 12){
            return;
        }

        String tempStr;
        tempStr = Integer.toString(tempCol) + "," + Integer.toString(temprow);
        if (fringe.contains(tempStr) && visited.containsKey(tempStr) == false) {

        } else {
            //System.out.println("i added this" + tempStr);
            fringe.add(tempStr);
        }


        String[] myParts = nodes.get(Integer.toString(nodeCol) + "," + Integer.toString(nodeRow)).split(",");
        if (nodes.containsKey(tempStr)) {
            String[] nextParts = nodes.get(tempStr).split(",");


            if (Integer.parseInt(myParts[0]) < Integer.parseInt(nextParts[0])) {
                String newStr = Integer.toString(Integer.parseInt(myParts[0]) + 1) + "," + "U," + Integer.toString(nodeCol) + ":" + Integer.toString(nodeRow);
            }

        } else {
            //System.out.println("running with " + tempStr);

            nodes.put(tempStr, Integer.toString(Integer.parseInt(myParts[0]) + 1) + ",U," + Integer.toString(nodeCol) + ":" + Integer.toString(nodeRow));
            fringe.add(tempStr);
        }
    }
}
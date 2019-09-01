
package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;



/*
 * the structure of the algorithm is from the book
 * Introduction to Algorithms by Thomas H. Cormen, Charles Leiserson, Ronald L. Rivest, Clifford Stein
 * 4. Auflage. ISBN 978-3-486-74861-1, S. 697-704, german version
 *
 * the algorithm has a runtime of O(n^3 * log n)
 */


public class MinPlusMatrixMultiplication extends NavigationService {

    private int numberOfNodes;
    private Graph g;
    private HashMap<Integer, ArrayList<Connection>> links = new HashMap<>();
    private List<List<Integer>> resultMatrix;
    private List<List<Integer>> vorgaenger;
    private int iterator;

    public void calculateShortestWay(Graph g, long startNodeId, long targetNodeId) {
        //int[][] resultMatrix = allPairsShortestPaths(g.getAutobahn());
        // printOutMatrix(resultMatrix);
    }

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {
        this.g = g;
        links = g.getLinks();
        numberOfNodes = g.getAmountOfNodes();
        initArrays(numberOfNodes);
        List<List<Integer>> graphAsMatrix = new ArrayList<List<Integer>>(numberOfNodes);

        for (int i = 0; i < numberOfNodes; i++) {
            List<Integer> distanceOfNodesToOthers = new ArrayList<>(Collections.nCopies(numberOfNodes, INFINITE));
            ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(i);
            for (Connection connectionToNeighbor : allConnectionsOfNode) {
                int aim = connectionToNeighbor.getAim();
                distanceOfNodesToOthers.set(aim, getDistance(connectionToNeighbor));
            }
            distanceOfNodesToOthers.set(i, 0);
            graphAsMatrix.add(distanceOfNodesToOthers);
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (graphAsMatrix.get(i).get(j) != INFINITE) {
                    vorgaenger.get(i).set(j, i);
                }
                if (i == j) {
                    vorgaenger.get(i).set(j, INFINITE);
                }
            }
        }
        resultMatrix = allPairsShortestPaths(graphAsMatrix);
        //printOutMatrix(resultMatrix);
        return output8(g, startNode, targetNode);
    }


    private int[][] extendShortestPaths(int[][] calculatedMatrix, int[][] inputMatrix) {

        int n = inputMatrix.length;
        int[][] shortestDistMatrix = new int[n][n];

        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                shortestDistMatrix[i][j] = INFINITE;
                for (int k = 0; k < n; k++) {
                    shortestDistMatrix[i][j] =
                            getMin(shortestDistMatrix[i][j], calculatedMatrix[i][k], inputMatrix[k][j], i, j, k);
                }
            }
        }
        return shortestDistMatrix;
    }


    private List<List<Integer>> extendShortestPaths(List<List<Integer>> calculatedMatrix, List<List<Integer>> inputMatrix) {
        int n = numberOfNodes;
        //List<List<Integer>> shortestDistMatrix = new ArrayList<List<Integer>>(numberOfNodes);
        List<List<Integer>> shortestDistMatrix = new ArrayList<>(inputMatrix.stream().map(x -> new ArrayList<Integer>(x)).collect(Collectors.toList()));
        /*for (int i = 0; i < n; i++) {
            List<Integer> tempList = new ArrayList<>(numberOfNodes);
            for (int j = 0; j < n; j++) {
                tempList.add(INFINITE);
            }
            shortestDistMatrix.add(tempList);
        }*/

        for (int i = 0; i < n; i++) {
            //List<Integer> tempList = new ArrayList<>();
            //shortestDistMatrix.add(tempList);
            for (int j = 0; j < n; j++) {
                //tempList.add(INFINITE);
                for (int k = 0; k < n; k++) {
                    int oldValue = shortestDistMatrix.get(i).get(j);
                    //System.out.println("old Value " + oldValue);
                    int newWeightPartOne = calculatedMatrix.get(i).get(k);
                    int newWeightPartTwo = inputMatrix.get(k).get(j);
                    shortestDistMatrix.get(i).set(j,
                            getMin(oldValue, newWeightPartOne, newWeightPartTwo, i, j, k));
                    //System.out.println("shortestDis " + shortestDistMatrix.get(i).get(j));
                    //System.out.println(" ");
                }
            }
        }
        //printOutMatrix(shortestDistMatrix);
        return shortestDistMatrix;
    }


    private int[][] fasterAllPairsShortestPaths(int[][] inputMatrix) {
        int n = inputMatrix.length;
        int[][] startMatrix = Arrays.stream(inputMatrix).map(int[]::clone).toArray(int[][]::new);
        List<int[][]> matrices = new ArrayList<>();
        matrices.add(startMatrix);
        int m = 1;
        while (m < n - 1) {
            int[][] curMatrix;
            curMatrix = extendShortestPaths(matrices.get(m - 1), matrices.get(m - 1));
            matrices.add(2 * m - 1, curMatrix);
            m = 2 * m;
        }
        return matrices.get(matrices.size() - 1);
    }


    private List<List<Integer>> allPairsShortestPaths(List<List<Integer>> inputMatrix) {
        int n = numberOfNodes;
        List<List<Integer>> startMatrix = new ArrayList<>(inputMatrix.stream().map(x -> new ArrayList<Integer>(x)).collect(Collectors.toList()));
        List<List<List<Integer>>> matrices = new LinkedList<>();
        matrices.add(startMatrix);

        int m = 1;
        while (m < n - 1) {
            List<List<Integer>> curMatrix;
            int sizeOfList = matrices.size();
            curMatrix = extendShortestPaths(matrices.get(sizeOfList - 1), matrices.get(sizeOfList - 1));
            matrices.add(curMatrix);
            m = 2 * m;
            if (sizeOfList > 3) {
                //matrices.remove(0);
            }
        }
        return matrices.get(matrices.size() - 1);
    }

    //Fall ohne transitiver Abschluss direktVerbindung bearbeiten
    private int getMin(int weightShortestDistanceMatrix, int newWeightPartOne, int newWeightPartTwo, int indexI, int indexJ, int indexK) {
        int newWeight;
        if ((newWeightPartOne != INFINITE) && (newWeightPartTwo != INFINITE)) {
            newWeight = newWeightPartOne + newWeightPartTwo;
            if (weightShortestDistanceMatrix <= newWeight) {
                return weightShortestDistanceMatrix;
            } else {
                //vorgaenger.get(indexI).set(indexK, indexJ);
                vorgaenger.get(indexI).set(indexJ, indexK);
                //predecessor.set(indexJ, indexK);
                return newWeight;
            }
        } else {
            return weightShortestDistanceMatrix;
        }
    }

    private void printOutMatrix(int[][] matrix) {
        int width = 8;
        int numberOfAddedNodes = matrix.length;
        System.out.print("        ");
        for (int i = 0; i < matrix.length; i++)
            System.out.print(i + "        ");

        System.out.println();

        for (int i = 0; i < numberOfAddedNodes; i++) {
            System.out.print(i + "       ");
            for (int j = 0; j < numberOfAddedNodes; j++)
                if (matrix[i][j] != -1)
                    System.out.print((matrix[i][j] + "       ").substring(0, width));
                else
                    System.out.print("        ");
            System.out.println();
        }
    }

    void initArrays(int amountOfNodes) {
        predecessor = new ArrayList<>(amountOfNodes);
        vorgaenger = new ArrayList<List<Integer>>(amountOfNodes);

        /*for (int i = 0; i < amountOfNodes; i++) {
            predecessor.add(INFINITE);
            List<List<Integer>> secondList = new ArrayList<List<Integer>>(amountOfNodes);
            List<Integer> thirdList = new ArrayList<>();
            secondList.add(thirdList);
            vorgaenger.add(secondList);
        }*/
        for (int i = 0; i < amountOfNodes; i++) {
            List<Integer> precursorsOfNode = new ArrayList<>(amountOfNodes);
            for (int j = 0; j < amountOfNodes; j++) {
                precursorsOfNode.add(INFINITE);
            }
            precursorsOfNode.set(i, 0);
            vorgaenger.add(precursorsOfNode);
        }
    }

    private Stack<Integer> output(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }
        int predecessor = targetNode;
        while ((vorgaenger.get(targetNode).get(predecessor) != INFINITE) && (predecessor != startNode) &&
                (predecessor != vorgaenger.get(targetNode).get(predecessor))) {
            predecessor = vorgaenger.get(targetNode).get(predecessor);
            way.push(predecessor);
        }
        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }
        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

    private Stack<Integer> output5(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }

        int iterator = 0;
        List<Stack<Integer>> ways = new ArrayList<Stack<Integer>>();
        ways.add(new Stack<Integer>());
        ways.get(iterator).add(targetNode);
        boolean startNodeNotFound = true;
        while (startNodeNotFound) {
            if (iterator != 0) {
                for (int i = 0; i < ways.get(iterator).size(); i++) {
                    recursiveSearchForWaysToStartNode(startNode, targetNode, ways.get(iterator).get(i), ways);
                    if (ways.get(iterator).get(ways.get(iterator).size() - 1) == startNode) {
                        //way=ways.get(iterator).get(ways.get(iterator).size()-1);
                        startNodeNotFound = false;

                    }
                }
            } else {
                recursiveSearchForWaysToStartNode(startNode, targetNode, targetNode, ways);
            }
            iterator++;
        }


        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }
        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }


    private Stack<Integer> output6(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }

        iterator = 0;
        List<Stack<Integer>> ways = new ArrayList<Stack<Integer>>();
        ways.add(new Stack<Integer>());
        ways.get(iterator).add(targetNode);

        boolean change = true;
        boolean startNodeNotFound = true;
        while (change && startNodeNotFound) {
            if (iterator != 0) {
                boolean changeInThisRound = false;
                int newWaysCheckedTill = ways.size();
                for (int i = iterator; i < newWaysCheckedTill; i++) {
                    if (recursiveSearchForWaysToStartNode(startNode, targetNode, ways.get(i).peek(), ways)) {
                        changeInThisRound = true;
                    }
                }
                for (int j = newWaysCheckedTill; j < ways.size(); j++){
                    if (ways.get(j).peek() == startNode) {
                        way = ways.get(j);
                        startNodeNotFound = false;
                    }
                }
                iterator = newWaysCheckedTill;
                change = changeInThisRound;
            } else {
                change = recursiveSearchForWaysToStartNode(targetNode, startNode, startNode, ways);
                iterator++;
            }
        }

        if (g.getNodeById(targetNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }
        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

    private Stack<Integer> output7(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(startNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }

        iterator = 0;
        List<Stack<Integer>> ways = new ArrayList<Stack<Integer>>();
        ways.add(new Stack<Integer>());
        ways.get(iterator).add(targetNode);

        boolean change = true;
        boolean startNodeNotFound = true;
        while (change && startNodeNotFound) {
            if (iterator != 0) {
                boolean changeInThisRound = false;
                int newWaysCheckedTill = ways.size();
                for (int i = iterator; i < newWaysCheckedTill; i++) {
                    if (recursiveSearchForWaysToStartNode(targetNode, startNode, ways.get(i).peek(), ways)) {
                        changeInThisRound = true;
                    }
                }
                for (int j = newWaysCheckedTill; j < ways.size(); j++){
                    if (ways.get(j).peek() == targetNode) {
                        way = ways.get(j);
                        startNodeNotFound = false;
                    }
                }
                iterator = newWaysCheckedTill;
                change = changeInThisRound;
            } else {
                change = recursiveSearchForWaysToStartNode(startNode, targetNode, targetNode, ways);
                iterator++;
            }
        }

        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }
        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

    private Stack<Integer> output2(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }
        int precursor = targetNode;
        while ((vorgaenger.get(targetNode).get(precursor) != INFINITE) && (precursor != startNode)) {
            precursor = vorgaenger.get(targetNode).get(precursor);
            way.push(precursor);
        }
        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }

        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

    private Stack<Integer> output3(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }
        int precursor = targetNode;
        while ((vorgaenger.get(targetNode).get(precursor) != INFINITE) && (precursor != startNode)) {
            precursor = vorgaenger.get(targetNode).get(precursor);
            way.push(precursor);
        }
        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }

        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

    private Stack<Integer> output4(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }

        int iterator = 0;
        boolean stop = false;
        List<List<Integer>> openWays = new ArrayList<List<Integer>>();
        openWays.add(new ArrayList<Integer>());
        openWays.get(iterator).add(targetNode);
        boolean startNodeNotFound = true;
        int erg;
        while (startNodeNotFound) {
            if (iterator != 0) {
                for (int i = 0; i < openWays.get(iterator).size(); i++) {
                    // recursiveSearchForWaysToStartNode(targetNode, openWays.get(iterator).get(i), openWays.get(iterator));
                    if (openWays.get(iterator).get(i) == startNode) {
                        erg = i;
                        startNodeNotFound = false;
                    }
                }
            } else {
                // recursiveSearchForWaysToStartNode(targetNode, targetNode, openWays.get(iterator));
            }
            iterator++;
        }
        for (int j = iterator; j >= 0; j--) {
            //way.push(openWays.get(iterator).get(erg));
        }

        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }

        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

    private boolean recursiveSearchForWaysToStartNode(int startNode, int targetNode, int successor, List<Stack<Integer>> ways) {
        boolean stackAdded = false;
        for (int i = 0; i < numberOfNodes; i++) {
            if (vorgaenger.get(targetNode).get(i) == successor) {
                Stack<Integer> newWay = new Stack<>();
                newWay.addAll(ways.get(iterator));
                newWay.add(i);
                ways.add(newWay);
                stackAdded = true;
            }
        }
        return stackAdded;
    }


    private Stack<Integer> output8(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }
        int precursor = targetNode;
        while ((vorgaenger.get(startNode).get(precursor) != INFINITE) && (precursor != startNode)) {
            precursor = vorgaenger.get(startNode).get(precursor);
            way.push(precursor);
        }
        if (g.getNodeById(startNode).getName().equals("HelperNode") && !way.empty()) {
            way.pop();
        }

        if (way.empty() || way.size() == 1) {
            return null;
        } else {
            return way;
        }
    }

}


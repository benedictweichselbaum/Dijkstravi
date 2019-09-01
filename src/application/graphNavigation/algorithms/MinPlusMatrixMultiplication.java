
package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;



/*
 * the structure of the algorithm is from the book
 * Introduction to Algorithms by Thomas H. Cormen, Charles Leiserson, Ronald L. Rivest, Clifford Stein
 * 4. Auflage. ISBN 978-3-486-74861-1, S. 697-704, german version
 */


public class MinPlusMatrixMultiplication extends AbstractAlgorithm {

    private int numberOfNodes;
    private List<List<Integer>> precursors;

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {
        numberOfNodes = g.getAmountOfNodes();
        initArrays(numberOfNodes);
        List<List<Integer>> graphAsMatrix = new ArrayList<>(numberOfNodes);
        double progressUnit = (1.0/numberOfNodes)/2.0;
        for (int i = 0; i < numberOfNodes; i++) {
            List<Integer> distanceOfNodesToOthers = new ArrayList<>(numberOfNodes);
            for(int j= 0; j<numberOfNodes; j++){
                distanceOfNodesToOthers.add(INFINITE);
            }
            ArrayList<Connection> allConnectionsOfNode = g.getAllConnectionsOfNode(i);
            for (Connection connectionToNeighbor : allConnectionsOfNode) {
                int aim = connectionToNeighbor.getAim();
                distanceOfNodesToOthers.set(aim, getDistance(connectionToNeighbor));
            }
            graphAsMatrix.add(distanceOfNodesToOthers);
            progress += progressUnit;
        }
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (graphAsMatrix.get(i).get(j) != INFINITE) {
                    precursors.get(i).set(j, i);
                }
                if (i == j) {
                    precursors.get(i).set(j, INFINITE);
                }
            }
            progress += progressUnit;
        }
        progress = 1.0;
        List<List<Integer>> resultMatrix = allPairsShortestPaths(graphAsMatrix);
        return output(g, startNode, targetNode);
    }

    private List<List<Integer>> extendShortestPaths(List<List<Integer>> calculatedMatrix, List<List<Integer>> inputMatrix) {
        List<List<Integer>> shortestDistMatrix = new ArrayList<>(inputMatrix.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
        for (int i = 0; i < numberOfNodes; i++) {
            List<Integer> tempList = new ArrayList<>(numberOfNodes);
            for (int j = 0; j < numberOfNodes; j++) {
                tempList.add(INFINITE);
            }
            shortestDistMatrix.add(tempList);
        }

        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                for (int k = 0; k < numberOfNodes; k++) {
                    int oldValue = shortestDistMatrix.get(i).get(j);
                    int newWeightPartOne = calculatedMatrix.get(i).get(k);
                    int newWeightPartTwo = inputMatrix.get(k).get(j);

                    if(newWeightPartOne < INFINITE && newWeightPartTwo < INFINITE && newWeightPartOne + newWeightPartTwo <= oldValue){
                        shortestDistMatrix.get(i).set(j, newWeightPartOne + newWeightPartTwo);
                        precursors.get(i).set(j, k);
                    }
                }
            }
        }
        return shortestDistMatrix;
    }


    private List<List<Integer>> fasterAllPairsShortestPaths(List<List<Integer>> inputMatrix) {
        List<List<Integer>> startMatrix = new ArrayList<>(inputMatrix.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
        List<List<List<Integer>>> matrices = new ArrayList<>(numberOfNodes);
        matrices.add(startMatrix);
        int m = 1;
        while (m < numberOfNodes) {
            List<List<Integer>> curMatrix = extendShortestPaths(matrices.get(m - 1), matrices.get(m - 1));
            int indexInMatrices=2 * m - 1;
            matrices.add(indexInMatrices, curMatrix);
            m = 2 * m;
        }
        return matrices.get(matrices.size() - 1);
    }

    private List<List<Integer>> allPairsShortestPaths(List<List<Integer>> inputMatrix) {
        List<List<Integer>> startMatrix = new ArrayList<>(inputMatrix.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
        List<List<List<Integer>>> matrices = new LinkedList<>();
        matrices.add(startMatrix);

        int m = 1;
        while (m < numberOfNodes - 1) {
            List<List<Integer>> curMatrix;
            int sizeOfList = matrices.size();
            curMatrix = extendShortestPaths(matrices.get(sizeOfList - 1), matrices.get(sizeOfList - 1));
            matrices.add(curMatrix);
            m = 2 * m;
            if (sizeOfList > 3) {
                matrices.remove(0);
            }
        }
        return matrices.get(matrices.size() - 1);
    }

    /*private void printOutMatrix(int[][] matrix) {
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
    }*/

    void initArrays(int amountOfNodes) {
        precursors = new ArrayList<>(amountOfNodes);

        for (int i = 0; i < amountOfNodes; i++) {
            List<Integer> precursorsOfNode = new ArrayList<>(amountOfNodes);
            for (int j = 0; j < amountOfNodes; j++) {
                precursorsOfNode.add(INFINITE);
            }
            precursorsOfNode.set(i, 0);
            precursors.add(precursorsOfNode);
        }
    }

    private Stack<Integer> output(Graph g, int startNode, int targetNode) {
        Stack<Integer> way = new Stack<>();
        if (!g.getNodeById(targetNode).getName().equals("HelperNode")) {
            way.push(targetNode);
        }
        int predecessor = targetNode;
        while ((precursors.get(startNode).get(predecessor) != INFINITE) && (predecessor != startNode)) {
            predecessor = precursors.get(startNode).get(predecessor);
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
}


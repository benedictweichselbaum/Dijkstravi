
package application.graphNavigation.algorithms;

import application.graphNavigation.graph.Connection;
import application.graphNavigation.graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/*
 * the structure of the algorithm is from the book
 * Introduction to Algorithms by Thomas H. Cormen, Charles Leiserson, Ronald L. Rivest, Clifford Stein
 * 4. Auflage. ISBN 978-3-486-74861-1, S. 697-704, german version
 *
 * The result matrix with all shortest distances is returned by the allPairsShortestPaths method.
 * It is not used here, but characterises the algorithm.
 * Other essential methods are extendShortestPaths and output
 * For each Node the way to each other Node is saved as a list in the precursors list.
 */

public class MinPlusMatrixMultiplication extends AbstractAlgorithm {

    private int numberOfNodes;
    private List<List<Integer>> precursors;

    @Override
    public Stack<Integer> calculateShortestWay(Graph g, int startNode, int targetNode) {
        numberOfNodes = g.getAmountOfNodes();
        initPrecursorArray(numberOfNodes);
        List<List<Integer>> graphAsMatrix = convertGraphAsAdjacencyListToAdjacencyMatrix(g);
        fillPrecursorsArrayWithDirectConnections(graphAsMatrix);

        List<List<Integer>> resultMatrix = allPairsShortestPaths(graphAsMatrix);
        return output(g, startNode, targetNode);
    }

    private List<List<Integer>> allPairsShortestPaths(List<List<Integer>> inputMatrix) {
        List<List<Integer>> startMatrix = new ArrayList<>(inputMatrix.stream().map(ArrayList::new).collect(Collectors.toList()));
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

    //runtime improvement that is sadly not running at the moment
    private List<List<Integer>> fasterAllPairsShortestPaths(List<List<Integer>> inputMatrix) {
        List<List<Integer>> startMatrix = new ArrayList<>(inputMatrix.stream().map(ArrayList::new).collect(Collectors.toList()));
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

    private List<List<Integer>> extendShortestPaths(List<List<Integer>> calculatedMatrix, List<List<Integer>> inputMatrix) {
        List<List<Integer>> shortestDistMatrix = initializeShortestDistMatrix(inputMatrix);

        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                for (int k = 0; k < numberOfNodes; k++) {
                    int oldValue = shortestDistMatrix.get(i).get(j);
                    int newWeightPartOne = calculatedMatrix.get(i).get(k);
                    int newWeightPartTwo = inputMatrix.get(k).get(j);

                    if(newWeightPartOne < INFINITE && newWeightPartTwo < INFINITE
                            && newWeightPartOne + newWeightPartTwo <= oldValue){
                        shortestDistMatrix.get(i).set(j, newWeightPartOne + newWeightPartTwo);
                        precursors.get(i).set(j, k);
                    }
                }
            }
        }
        return shortestDistMatrix;
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

    private void fillPrecursorsArrayWithDirectConnections(List<List<Integer>> graphAsMatrix) {
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                if (graphAsMatrix.get(i).get(j) != INFINITE) {
                    precursors.get(i).set(j, i);
                }
                if (i == j) {
                    precursors.get(i).set(j, INFINITE);
                }
            }
        }
    }

    private List<List<Integer>> convertGraphAsAdjacencyListToAdjacencyMatrix(Graph g) {
        List<List<Integer>> graphAsMatrix = new ArrayList<>(numberOfNodes);
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
        }
        return graphAsMatrix;
    }

    private List<List<Integer>> initializeShortestDistMatrix(List<List<Integer>> inputMatrix) {
        List<List<Integer>> shortestDistMatrix = new ArrayList<>(inputMatrix.stream().map(ArrayList::new).collect(Collectors.toList()));
        for (int i = 0; i < numberOfNodes; i++) {
            List<Integer> tempList = new ArrayList<>(numberOfNodes);
            for (int j = 0; j < numberOfNodes; j++) {
                tempList.add(INFINITE);
            }
            shortestDistMatrix.add(tempList);
        }
        return shortestDistMatrix;
    }

    private void initPrecursorArray(int amountOfNodes) {
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

}


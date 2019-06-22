package application.graphNavigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//o(n^3 logn) :)
public class MinPlusMatrixMultiplication implements Navigator {

    public void calculateShortestWay(Graph g, long startNodeId, long targetNodeId) {

        int[][] resultMatrix = fasterAllPairsShortestPaths(g.getAutobahn());
        printOutMatrix(resultMatrix);
    }

    private int[][] extendShortestPaths(int[][] calculatedMatrix, int[][] inputMatrix) {
        int n = inputMatrix.length;
        int[][] shortestDistMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                shortestDistMatrix[i][j] = INFINITE;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //shortestDistMatrix[i][j] = INFINITE;
                for (int k = 0; k < n; k++) {
                    System.out.println("shortestDis "+calculatedMatrix[i][j]);
                    shortestDistMatrix[i][j] =
                            getMin(shortestDistMatrix[i][j], (calculatedMatrix[i][k] + inputMatrix[k][j]));
                }
            }
        }
        printOutMatrix(shortestDistMatrix);
        return shortestDistMatrix;
    }

    /*private int[][] fasterAllPairsShortestPaths(int[][] inputMatrix){
        int n=inputMatrix.length;
        int[][] startMatrix= Arrays.stream(inputMatrix).map(int[]::clone).toArray(int[][]::new);
        List<int[][]> matrices=new ArrayList<>();
        matrices.add(startMatrix);
        int m=1;
        while(m<n-1){
            int[][] curMatrix;
            curMatrix=extendShortestPaths(matrices.get(m-1),matrices.get(m-1));
            matrices.add(2*m-1,curMatrix);
            m=2*m;
        }

        return matrices.get(matrices.size()-1);
    }*/

    private int[][] fasterAllPairsShortestPaths(int[][] inputMatrix) {
        int n = inputMatrix.length;
        int[][] startMatrix = Arrays.stream(inputMatrix).map(int[]::clone).toArray(int[][]::new);
        List<int[][]> matrices = new LinkedList<>();
        matrices.add(startMatrix);
        int m = 1;
        while (m < n - 1) {
            int[][] curMatrix;
            int sizeOfList=matrices.size();
            curMatrix = extendShortestPaths(matrices.get(sizeOfList-1), matrices.get(sizeOfList-1));
            matrices.add(curMatrix);
            m = 2 * m;
        }

        return matrices.get(matrices.size() - 1);
    }

    private int getMin(int weightShortestDistanceMatrix, int newWeight) {
        if (weightShortestDistanceMatrix < newWeight) {
            return weightShortestDistanceMatrix;
        } else {
            //System.out.println("Min is "+newWeight);
            return newWeight;


        }
    }

    private void printOutMatrix(int[][] matrix) {
        int width = 4;
        int numberOfAddedNodes = matrix.length;
        System.out.print("    ");
        for (int i = 0; i < matrix.length; i++)
            System.out.print(i + "   ");

        System.out.println();

        for (int i = 0; i < numberOfAddedNodes; i++) {
            System.out.print(i + "   ");
            for (int j = 0; j < numberOfAddedNodes; j++)
                if (matrix[i][j] != -1)
                    System.out.print((matrix[i][j] + "   ").substring(0, width));
                else
                    System.out.print("    ");
            System.out.println();
        }


        System.out.println("getMinTest, should be 5: "+ getMin(5,10));
        System.out.println("getMinTest, should be -1: "+ getMin(5,-1));
        System.out.println("getMinTest, should be 5: "+ getMin(5,5));
    }
}

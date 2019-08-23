/*
package application.graphNavigation.algorithms.;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



/*
 * the structure of the algorithm is from the book
 * Introduction to Algorithms by Thomas H. Cormen, Charles Leiserson, Ronald L. Rivest, Clifford Stein
 * 4. Auflage. ISBN 978-3-486-74861-1, S. 697-704, german version
 *
 * the algorithm has a runtime of O(n^3 * log n)
 * */

/*
class MinPlusMatrixMultiplication implements NavigationService {

    public void calculateShortestWay(Graph g, long startNodeId, long targetNodeId) {
        int[][] resultMatrix = allPairsShortestPaths(g.getAutobahn());
        printOutMatrix(resultMatrix);
    }

    private int[][] extendShortestPaths(int[][] calculatedMatrix, int[][] inputMatrix) {

        int n = inputMatrix.length;
        int[][] shortestDistMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                shortestDistMatrix[i][j] = INFINITE;
                for (int k = 0; k < n; k++) {
                    shortestDistMatrix[i][j] =
                            getMin(shortestDistMatrix[i][j], calculatedMatrix[i][k], inputMatrix[k][j]);
                }
            }
        }
        return shortestDistMatrix;
    }


private int[][] extendShortestPaths(int[][] calculatedMatrix, int[][] inputMatrix) {
        int n = inputMatrix.length;
        int[][] shortestDistMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                shortestDistMatrix[i][j] = INFINITE;
                for (int k = 0; k < n; k++) {

                    System.out.println("Value to compare consisting of calculatedMatrix " + calculatedMatrix[i][k] + " and inputMatrix " + inputMatrix[k][j]);
                    int newValue = shortestDistMatrix[i][j];
                    System.out.println("old Value " + newValue);
                    shortestDistMatrix[i][j] =
                            getMin(newValue, calculatedMatrix[i][k], inputMatrix[k][j]);
                    System.out.println("shortestDis " + shortestDistMatrix[i][j]);
                    System.out.println(" ");
                }
            }
        }
        printOutMatrix(shortestDistMatrix);
        return shortestDistMatrix;
    }



private int[][] fasterAllPairsShortestPaths(int[][] inputMatrix){
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
    }


    private int[][] allPairsShortestPaths(int[][] inputMatrix) {
        int n = inputMatrix.length;
        int[][] startMatrix = Arrays.stream(inputMatrix).map(int[]::clone).toArray(int[][]::new);
        List<int[][]> matrices = new LinkedList<>();
        matrices.add(startMatrix);

        int m = 1;
        while (m < n - 1) {
            int[][] curMatrix;
            int sizeOfList = matrices.size();
            curMatrix = extendShortestPaths(matrices.get(sizeOfList - 1), matrices.get(sizeOfList - 1));
            matrices.add(curMatrix);
            m = 2 * m;
        }

        return matrices.get(matrices.size() - 1);
    }

    private int getMin(int weightShortestDistanceMatrix, int newWeightPartOne, int newWeightPartTwo) {
        int newWeight;
        if ((newWeightPartOne != INFINITE) && (newWeightPartTwo != INFINITE)) {
            newWeight = newWeightPartOne + newWeightPartTwo;
        } else {
            newWeight = INFINITE;
        }
        if (weightShortestDistanceMatrix < newWeight) {
            return weightShortestDistanceMatrix;
        } else {
            return newWeight;


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
}

*/
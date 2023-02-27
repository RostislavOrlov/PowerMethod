package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Double[][] myMatrix = {{2.0, 1.0, 1.0, 1.0}, {1.0, 2.0, 1.0, 1.0}, {1.0, 1.0, 2.0, 1.0}, {1.0, 1.0, 1.0, 2.0}};
//        Matrix m1 = new Matrix(myMatrix);
//        double myDelta = 0.0000000001;
//        double myEpsilon = 0.00000001;
//        double eigenvalue = 0.0;
//        Vector eigenvector;
//        Eigen eigen = m1.calculateByPowerMethod(myDelta, myEpsilon);
//        eigenvalue = eigen.getEigenvalue();
//        eigenvector = eigen.getEigenVector().getNormalizedVectorWithInfinityNorm();
//        System.out.println("______________");
//        System.out.println("Cобственное значение = " + eigen.getEigenvalue());
//        System.out.println("__________________");
//        System.out.println("Координаты собственного вектора: ");
//        System.out.println();
//        for (int i = 0; i < eigenvector.getCoordinates().length; ++i) {
//            System.out.println(eigenvector.getCoordinates()[i]);
//        }

        File input = new File("/Users/rostislavorlov/Desktop/примеры ЧМ 0/Примеры ЧМ 0.txt");
        Scanner scanner = new Scanner(input);
        int cur_example = 0;
        while(scanner.hasNext()) {

            System.out.println("Пример " + cur_example);
            double myDelta = scanner.nextDouble();
            double myEpsilon = scanner.nextDouble();
            int dimension = scanner.nextInt();
            Double[][] myMatrix = new Double[dimension][dimension];
            for (int i = 0; i < dimension; ++i) {
                for (int j = 0; j < dimension; ++j) {
                    myMatrix[i][j] = scanner.nextDouble();
                }
             }
            Matrix matrix = new Matrix(myMatrix);
            Eigen eigen = matrix.calculateByPowerMethod(myDelta, myEpsilon);

            double eigenvalue = 0.0;
            Vector eigenvector;

            eigenvalue = eigen.getEigenvalue();
            eigenvector = eigen.getEigenVector().getNormalizedVectorWithInfinityNorm();
            System.out.println("______________");
            System.out.println("Cобственное значение = " + eigen.getEigenvalue());
            System.out.println("__________________");
            System.out.println("Координаты собственного вектора: ");
            System.out.println();
            for (int i = 0; i < eigenvector.getCoordinates().length; ++i) {
                System.out.println(eigenvector.getCoordinates()[i]);
            }
            System.out.println("______________________________________________________________________________________________________________________________________");
            ++cur_example;
        }
    }
}
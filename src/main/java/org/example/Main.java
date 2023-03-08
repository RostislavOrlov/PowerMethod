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

    public static boolean isDegreeOfTwo(int number) {

        while(number % 2 == 0) {
            number = number / 2;
        }

        if(number != 1)
            return false;

        else
            return true;
    }

    public static int maxDegreeOfTwoLessThanNumber(int number) {

        if(Main.isDegreeOfTwo(number))
            return number;

        int max = 0;

        for(int i = 1; i < number; ++i) {
            if(Main.isDegreeOfTwo(i) && (i > max))
                max = i;
        }
        return max;
    }

//    public static Matrix product(Double[][] m1, Double[][] m2, int number) {
//
//        if(number == 1)
//            return new Matrix(m1);
//
//
//        Double[][] result = new Double[m1.length][m1.length];
//
//        for (int k = 1; k < number; ++k) {
//            for (int i = 0; i < m1.length; ++i) {
//                for (int j = 0; j < m1.length; ++j) {
//                    result[i][j] = 0.0;
//                }
//            }
//
//            for (int i = 0; i < m1.length; ++i) {
//                for (int j = 0; j < m1.length; ++j) {
//                    for (int r = 0; r < m1.length; ++r) {
//                        result[i][j] += m1[i][r] * m2[r][j];
//                    }
//                }
//            }
////            System.out.println("^^^^^^^^^^^^^^^^^^");
////            System.out.println("Функция product");
////            for (int i = 0; i < m1.length; ++i) {
////                for (int j = 0; j < m1.length; ++j) {
////                    System.out.println(result[i][j]);
////                }
////            }
////            System.out.println("^^^^^^^^^^^^^^^^^^");
//            for (int q1 = 0; q1 < m1.length; ++q1)
//                for (int q2 = 0; q2 < m1.length; ++q2)
//                    m2[q1][q2] = result[q1][q2];
//        }
//
//        Matrix result_object_matrix = new Matrix(result);
//        return result_object_matrix;
//    }

    public static Double[][] product(Double[][] m1, int number) {

        if(number == 1)
            return m1;

        Double[][] curr = new Double[m1.length][m1.length];
        for (int i = 0; i < m1.length; ++i) {
            for (int j = 0; j < m1.length; ++j) {
                curr[i][j] = m1[i][j];
            }
        }
        Double[][] result = new Double[m1.length][m1.length];

        for (int k = 1; k < number; ++k) {
            for (int i = 0; i < m1.length; ++i) {
                for (int j = 0; j < m1.length; ++j) {
                    result[i][j] = 0.0;
                }
            }

            for (int i = 0; i < m1.length; ++i) {
                for (int j = 0; j < m1.length; ++j) {
                    for (int r = 0; r < m1.length; ++r) {
                        result[i][j] += m1[i][r] * curr[r][j];
                    }
                }
            }

            for (int q1 = 0; q1 < m1.length; ++q1)
                for (int q2 = 0; q2 < m1[q1].length; ++q2)
                    curr[q1][q2] = result[q1][q2];
        }

        return result;
    }
}
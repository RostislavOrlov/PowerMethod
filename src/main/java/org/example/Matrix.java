package org.example;

import java.util.ArrayList;

import static java.lang.Math.abs;
public class Matrix {
    private int n;
    private Double[][] matrix;
    public Matrix(Double[][] matrix) {
        this.n = matrix.length;
        this.matrix = matrix;
    }
    public int getDimensionOfMatrix() {
        return n;
    }
    public Double[][] getMatrix() {
        return matrix;
    }
    public Vector productOfMatrixAndVector(Vector vector) {
//        Vector normalizedVector = vector.getNormalizedVectorWithInfinityNorm();
        Double[] resultCoordinates = new Double[this.getDimensionOfMatrix()];
        for(int i = 0; i < resultCoordinates.length; ++i)
            resultCoordinates[i] = 0.0;
        for (int i = 0; i < vector.getCoordinates().length; ++i)
            for (int j = 0; j < vector.getCoordinates().length; ++j) {
                resultCoordinates[i] += matrix[i][j] * vector.getCoordinates()[j];
            }
        System.out.println("productOfMatrixAndVector: ");
        for(int i = 0; i < resultCoordinates.length; ++i)
            System.out.println(resultCoordinates[i]);

        Vector resultVector = new Vector(resultCoordinates);
        return resultVector;
    }

    public Eigen calculateByPowerMethod(double myDelta, double myEpsilon) {

        double delta = myDelta, epsilon = myEpsilon;
        Vector prevVector;
        ArrayList<Vector> vectors = new ArrayList<>();
        //задаём начальное приближение
        Double[] initialApproximation = new Double[this.getDimensionOfMatrix()];
        initializationWithRandomValues(initialApproximation);
        Vector curVector = new Vector(initialApproximation);
        System.out.println("Начальный вектор: ");
        for (int i = 0; i < curVector.getCoordinates().length; ++i)
            System.out.println(curVector.getCoordinates()[i]);
        //объявляем массивы текущих итераций, предыдущих итераций и модуля разности между элементами этих массивов
        Double[] difference = new Double[this.getDimensionOfMatrix()];
        Double[] prevRatios = new Double[this.getDimensionOfMatrix()];
        Double[] curRatios = new Double[this.getDimensionOfMatrix()];

//        initializationWithRandomValues(prevRatios);
//        initializationWithRandomValues(curRatios);

        //максимальное количество итераций
        int maxCountOfIterations = 10000;

        //начальное заполнение элементов массивов предыдущих и текущих итераций
        for (int i = 0; i < prevRatios.length; i++) {
            prevRatios[i] = 2.0;
            curRatios[i] = 4.0;
        } //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //инициализация собственного вектора, собственного значения
        Double[] eigenvectorCoordinates = new Double[this.getDimensionOfMatrix()];
        for (int i = 0; i < eigenvectorCoordinates.length; i++) {
            eigenvectorCoordinates[i] = 0.0;
        }
        Vector eigenvector = new Vector(eigenvectorCoordinates);
        double eigenvalue = 0.0;
        int count = 0;
        double sum = 0.0;

        //булевый массив, хранящий информацию о выведенных компонентах собственного значения
        boolean[] isOut = new boolean[this.getDimensionOfMatrix()];
        for (int i = 0; i < isOut.length; ++i) {
            isOut[i] = false;
        }

        //
        for(int i = 0; i < this.getDimensionOfMatrix(); ++i) {
            difference[i] = abs(curRatios[i] - prevRatios[i]);
        }

        //текущая итерация
        int k = 0;

        Double[][] m1 = new Double[this.getDimensionOfMatrix()][this.getDimensionOfMatrix()];
        for(int p = 0; p < m1.length; ++p)
            for(int q = 0; q < m1.length; ++q)
                m1[p][q] = this.matrix[p][q];
        Matrix m1_matrix = new Matrix(m1);

        Double[][] m2 = new Double[this.getDimensionOfMatrix()][this.getDimensionOfMatrix()];
        for(int p = 0; p < m2.length; ++p)
            for(int q = 0; q < m2.length; ++q)
                m2[p][q] = this.matrix[p][q];
        Matrix m2_matrix = new Matrix(m2);
        //основной цикл
        do {
            sum = 0;
            count = 0;
            k++;

            System.out.println("Итерация: " + k);

            //инициализируем предыдущий вектор текущим, нормируя его
            prevVector = curVector;
//                    .getNormalizedVectorWithInfinityNorm();
            vectors.add(prevVector);
            System.out.println("В массив добавился вектор: ");
            for(int i = 0; i < prevVector.getCoordinates().length; ++i)
                System.out.println(prevVector.getCoordinates()[i]);
            System.out.println();
            System.out.println("Координаты предыдущего вектора: ");
            prevVector.printCoordinates();

            System.out.println();

            int a = Main.maxDegreeOfTwoLessThanNumber(k);
            int m = k - a;// number == k - m на данной итерации, где m
            //такое, что k - m является максимальной степенью двойки, не превосходящей k
            if(k == 4)
                System.out.println(a);

            //текущий вектор получаем путём произведения матрицы на вектор
//            curVector = this.productOfMatrixAndVector(curVector);

            Double[][] currMatrix = Main.product(m1, a);
            Matrix currMatrixObject = new Matrix(currMatrix);
            System.out.println("*************************");
            System.out.println("currMatrix: ");
            for (int i = 0; i < currMatrixObject.getDimensionOfMatrix(); ++i)
                for (int j = 0; j < currMatrixObject.getDimensionOfMatrix(); ++j)
                    System.out.println(currMatrixObject.getMatrix()[i][j]);
            System.out.println("*************************");
            System.out.println("Вектор[m]: ");
            for(int i = 0; i < vectors.get(m).getCoordinates().length; ++i)
                System.out.println(vectors.get(m).getCoordinates()[i]);
            System.out.println("Он же " + (m) + "ый");
            curVector = currMatrixObject.productOfMatrixAndVector(vectors.get(m));

            System.out.println("Координаты текущего вектора: ");
            curVector.printCoordinates();

            System.out.println();

            //Пересчитываем отношения координат предыдущего и текущего векторов
            System.out.println("Значения из массива текущих отношений координат: ");
            for(int i = 0; i < curRatios.length; ++i) {

                prevRatios[i] = curRatios[i];

                //Если компонента предыдущего вектора меньше по модулю наперёд заданного дельта - выводим
                if((abs(prevVector.getCoordinates()[i]) <= delta) && (isOut[i] == false))
                    isOut[i] = true;

                //Если нет - пересчитываем элемент массива текущих отношений
                if((abs(prevVector.getCoordinates()[i]) > delta) && (isOut[i] == false)) {
                    curRatios[i] = curVector.getCoordinates()[i] / prevVector.getCoordinates()[i];
                    System.out.println(curRatios[i]);
                    sum += curRatios[i];
                    ++count;
                }

                difference[i] = abs(curRatios[i] - prevRatios[i]);

                eigenvector = curVector;
            }

            eigenvalue = sum / count;

            System.out.println();
        } while((maximum(difference) > epsilon) && (k <= maxCountOfIterations)); // || (k <= maxCountOfIterations)

        //возвращаем собственное значение и собственный вектор через класс
        Eigen eigen = new Eigen(eigenvalue, eigenvector);

        return eigen;
    }

    public double maximum(Double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; ++i) {
            if(array[i] > max)
                max = array[i];
        }
        return max;
    }

    public Double[] initializationWithRandomValues(Double[] array) {
        for(int i = 0; i < this.getDimensionOfMatrix(); ++i) {
            array[i] = Math.random();
        }
        return array;
    }





//    public Matrix productOfMatrixAndMatrix(int number) {
//
//        Double[][] result = new Double[this.getDimensionOfMatrix()][this.getDimensionOfMatrix()];
//
//
//        for (int i = 0; i < this.getDimensionOfMatrix(); ++i) {
//            for (int j = 0; j < this.getDimensionOfMatrix(); ++j) {
//                result[i][j] = 0.0;
//            }
//        }
//
//        Double[][] currMatrix = this.matrix;
//
//        for(int k = 0; k < number; ++k) {
//            for (int i = 0; i < this.getDimensionOfMatrix(); ++i) {
//                for (int j = 0; j < this.getDimensionOfMatrix(); ++j) {
//                    for (int r = 0; r < this.getDimensionOfMatrix(); ++r) {
//                        result[i][j] += currMatrix[i][r] * currMatrix[r][j];
//                    }
//                }
//            }
//            currMatrix = result;
//        }
//
//        Matrix resultMatrix = new Matrix(result);
//        return resultMatrix;
//    }
}
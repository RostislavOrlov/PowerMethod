package org.example;

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
        Vector normalizedVector = vector.getNormalizedVectorWithInfinityNorm();
        Double[] resultCoordinates = new Double[this.getDimensionOfMatrix()];
        for(int i = 0; i < resultCoordinates.length; ++i)
            resultCoordinates[i] = 0.0;
        for (int j = 0; j < vector.getCoordinates().length; ++j)
            for (int i = 0; i < vector.getCoordinates().length; ++i) {
                resultCoordinates[j] += matrix[j][i] * normalizedVector.getCoordinates()[i];
            }

        Vector resultVector = new Vector(resultCoordinates);
        return resultVector;
    }

    public Eigen calculateByPowerMethod(double myDelta, double myEpsilon) {

        double delta = myDelta, epsilon = myEpsilon;
        Vector prevVector;

        //задаём начальное приближение
        Double[] initialApproximation = new Double[this.getDimensionOfMatrix()];
        initializationWithRandomValues(initialApproximation);
        Vector curVector = new Vector(initialApproximation);

        //объявляем массивы текущих итераций, предыдущих итераций и модуля разности между элементами этих массивов
        Double[] difference = new Double[this.getDimensionOfMatrix()];
        Double[] prevRatios = new Double[this.getDimensionOfMatrix()];
        Double[] curRatios = new Double[this.getDimensionOfMatrix()];

//        initializationWithRandomValues(prevRatios);
//        initializationWithRandomValues(curRatios);

        //максимальное количество итераций
        int maxCountOfIterations = 1000;

        //начальное заполнение элементов массивов предыдущих и текущих итераций
        for (int i = 0; i < prevRatios.length; i++) {
            prevRatios[i] = 2.0;
            curRatios[i] = 4.0;
        }

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

        //
        for(int i = 0; i < this.getDimensionOfMatrix(); ++i) {
            difference[i] = abs(curRatios[i] - prevRatios[i]);
        }

        //текущая итерация
        int k = 0;

        //основной цикл
        do {
            sum = 0;
            count = 0;
            k++;

            System.out.println("Итерация: " + k);

            //инициализируем предыдущий вектор текущим, нормируя его
            prevVector = curVector.getNormalizedVectorWithInfinityNorm();
            System.out.println("Координаты предыдущего вектора: ");
            prevVector.printCoordinates();

            System.out.println();

            //текущий вектор получаем путём произведения матрицы на вектор
            curVector = this.productOfMatrixAndVector(curVector);
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
        } while((maximum(difference) > epsilon) || (k <= maxCountOfIterations));

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
}
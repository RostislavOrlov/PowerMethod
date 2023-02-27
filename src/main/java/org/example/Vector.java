package org.example;

import static java.lang.Math.abs;

public class Vector {
    private Double[] coordinates;
    public Vector(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public double getMaxCoordinate() {
        double max = coordinates[0];
        for (int i = 1; i < coordinates.length; ++i) {
            if (coordinates[i] > max)
                max = coordinates[i];
        }
        return max;
    }

    public Vector getNormalizedVectorWithInfinityNorm() {
        Double[] absCoordinates = new Double[coordinates.length];
        for (int i = 0; i < coordinates.length; ++i) {
            absCoordinates[i] = abs(coordinates[i]);
        }

        Vector temp = new Vector(absCoordinates);

        Double[] newCoordinates = new Double[coordinates.length];
        double norm = temp.getMaxCoordinate();

        for (int i = 0; i < coordinates.length; ++i) {
            newCoordinates[i] = coordinates[i] / norm;
        }

        Vector newVector = new Vector(newCoordinates);

        return newVector;
    }

    public double calculatingRatioOfCoordinates(double coordinate_first_vector, double coordinate_second_vector) {
        return coordinate_first_vector / coordinate_second_vector;
    }

    public void printCoordinates() {
        for(int i = 0; i < this.coordinates.length; ++i)
            System.out.println(this.coordinates[i]);
    }
}
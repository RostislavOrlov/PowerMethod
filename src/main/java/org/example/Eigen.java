package org.example;

public class Eigen {
    private double eigenvalue;
    private Vector eigenVector;

    public Eigen(double eigenvalue, Vector eigenVector) {
        this.eigenvalue = eigenvalue;
        this.eigenVector = eigenVector;
    }

    public double getEigenvalue() {
        return eigenvalue;
    }

    public Vector getEigenVector() {
        return eigenVector;
    }
}

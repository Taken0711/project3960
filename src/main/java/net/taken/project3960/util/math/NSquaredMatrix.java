package net.taken.project3960.util.math;

import javafx.geometry.Point3D;
import net.taken.project3960.util.geometry.AdvancedPoint;

import java.util.Arrays;
import java.util.function.DoubleFunction;
import java.util.regex.Pattern;

// TODO: extends collection and stuff ?
public class NSquaredMatrix {

    public final int n;

    protected double[][] matrix;


    public NSquaredMatrix(int n) {
        this.n = n;
        matrix = new double[n][n];
    }

    public NSquaredMatrix(int n, double[][] matrix) {
        this.n = n;
        this.matrix = matrix;
    }

    public NSquaredMatrix(NSquaredMatrix m) {
        this(m.n, m.matrix);
    }

    public int getN() {
        return n;
    }

    public double get(int x, int y) {
        return matrix[y][x];
    }

    public void set(int x, int y, double value) {
        matrix[y][x] = value;
    }

    public void setRow(int i, double... vector) {
        checkVectorSize(vector);
        matrix[i] = vector;
    }

    public void setRow(int i, Point3D vector) {
        setRow(i, vector.getX(), vector.getY(), vector.getZ());
    }

    public void setColumn(int i, double... vector) {
        checkVectorSize(vector);
        for (int j = 0; j < n; j++) {
            matrix[j][i] = vector[j];
        }
    }

    public void setColumn(int i, Point3D vector) {
        setColumn(i, vector.getX(), vector.getY(), vector.getZ());
    }

    public void add(NSquaredMatrix m) {
        checkMatrixSize(m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                matrix[j][i] += m.matrix[j][i];
        }
    }

    public void substract(NSquaredMatrix m) {
        checkMatrixSize(m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                matrix[j][i] -= m.matrix[j][i];
        }
    }

    public void multiplyScalar(double factor) {
        iterateOverMatrix(e -> factor * e);
    }

    public double[] multiplyVector(double... vector) {
        checkVectorSize(vector);
        double[] res = new double[vector.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                res[i] += vector[j] * matrix[i][j];
            }
        }
        return res;
    }

    public void transpose() {
        double[][] tmp = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tmp[j][i] = matrix[i][j];
        matrix = tmp;
    }

    public NSquaredMatrix copy() {
        return new NSquaredMatrix(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NSquaredMatrix that = (NSquaredMatrix) o;

        if (n != that.n) return false;
        return Arrays.deepEquals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        int result = n;
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

    protected void iterateOverMatrix(DoubleFunction<Double> settingFunction) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                matrix[j][i] = settingFunction.apply(matrix[j][i]);
        }
    }

    protected void checkMatrixSize(NSquaredMatrix m) {
        if (m.n != n)
            throw new IllegalArgumentException("Matrix must have the same size");
    }

    protected void checkVectorSize(double... vector) {
        if (vector.length != n)
            throw new IllegalArgumentException("The vector must match the matrix size");
    }

}

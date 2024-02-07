import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private final Picture pic;

    public SeamCarver(Picture pic) {
        if (pic == null) {
            throw new IllegalArgumentException("Picture is null");
        }
        this.pic = new Picture(pic);
    }

    public Picture picture() { // current pic
        return new Picture(pic);
    }

    public int width() {  // width of current pic
        return pic.width();
    }

    public int height() { // height of current pic
        return pic.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= pic.width() || y < 0 || y >= pic.height()) {
            throw new IllegalArgumentException("Invalid x or y");
        }
        int xp1 = (x == pic.width() - 1) ? 0 : x + 1;
        int xm1 = (x == 0) ? pic.width() - 1 : x - 1;
        int yp1 = (y == pic.height() - 1) ? 0 : y + 1;
        int ym1 = (y == 0) ? pic.height() - 1 : y - 1;

        double xGradient = calculateGradient(pic.get(xp1, y), pic.get(xm1, y));
        double yGradient = calculateGradient(pic.get(x, yp1), pic.get(x, ym1));

        return xGradient + yGradient;
    }

    private double calculateGradient(Color c1, Color c2) {
        double diffR = c1.getRed() - c2.getRed();
        double diffG = c1.getGreen() - c2.getGreen();
        double diffB = c1.getBlue() - c2.getBlue();
        return diffR * diffR + diffG * diffG + diffB * diffB;
    }


    public int[] findHorizontalSeam() {  // sequence of indices for horizontal seam
        //        Transpose the image and call findVerticalSeam
        Picture transposed = new Picture(pic.height(), pic.width());
        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                transposed.set(j, i, pic.get(i, j));
            }
        }
        SeamCarver sc = new SeamCarver(transposed);
        return sc.findVerticalSeam();
    }

    public int[] findVerticalSeam() {  // sequence of indices for vertical seam
        //        e(i,j) - energy cost of pixel at location (i, j)
        double[][] cost = new double[pic.width()][pic.height()];
        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                cost[i][j] = energy(i, j);
            }
        }
        //        M(i,j) - cost of minimum cost path ending at (i, j)
        double[][] path = new double[pic.width()][pic.height()];
        for (int i = 0; i < pic.width(); i++) {
            path[i][0] = cost[i][0];
        }

//        M(i,j)=e(i,j)+min(M(i−1,j−1),M(i,j−1),M(i+1,j−1))
        if (pic.width() == 1) {
            int [] seam = new int[pic.height()];
            for (int j = 0; j < pic.height(); j++) {
                seam[j] = 0;
            }
            return seam;
        } else if (pic.height() == 1) {
            int [] seam = new int[1];
            seam[0] = 0;
            return seam;
        }

        for (int j = 1; j < pic.height(); j++) {
            for (int i = 0; i < pic.width(); i++) {
                path[i][j] = cost[i][j] + getMinCost(i, j, path);
            }
        }

        int[] seam = new int[pic.height()];

        double min = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < pic.width(); i++) {
            if (path[i][pic.height() - 1] < min) {
                min = path[i][pic.height() - 1];
                minIndex = i;
            }
        }
        seam[pic.height() - 1] = minIndex;

        for (int j = pic.height() - 2; j >= 0; j--) {
            minIndex = getMinNeighbor(minIndex, path, j);
            seam[j] = minIndex;
        }

        return seam;
    }

    private int getMinNeighbor(int minIndex, double[][] path, int j) {
        int[] neighbors = {minIndex, minIndex - 1, minIndex + 1};
        // Start with the first neighbor
        int minNeighbor = neighbors[0];
        // Initialize minPathValue with the value of the first neighbor
        double minPathValue = path[minNeighbor][j];

        // Find the index of the minimum value among the neighbors
        for (int neighbor : neighbors) {
            if (neighbor >= 0 && neighbor < pic.width() && path[neighbor][j] < minPathValue) {
                minPathValue = path[neighbor][j];
                minNeighbor = neighbor;
            }
        }
        return minNeighbor;
    }


    private double getMinCost(int i, int j, double[][] path) {
        if (i < 0 || i >= pic.width() || j < 0 || j >= pic.height()) {
            String message = String.format("Invalid i or j: i=%d, j=%d", i, j);
            throw new IllegalArgumentException("message");
        }
        if (i == 0) {
            return Math.min(path[i][j - 1], path[i + 1][j - 1]);
        } else if (i == pic.width() - 1) {
            return Math.min(path[i - 1][j - 1], path[i][j - 1]);
        } else {
            return Math.min(path[i - 1][j - 1], Math.min(path[i][j - 1], path[i + 1][j - 1]));
        }
    }

    public void removeHorizontalSeam(int[] seam) { // remove horizontal seam from pic

    }

    public void removeVerticalSeam(int[] seam) { // remove vertical seam from pic

    }
}

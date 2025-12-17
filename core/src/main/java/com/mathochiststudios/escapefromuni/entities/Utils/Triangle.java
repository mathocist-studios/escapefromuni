package com.mathochiststudios.escapefromuni.entities.Utils;

public class Triangle {

    public float[] vertices;

    public Triangle(float[] vertices) {
        this.vertices = vertices;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] pickPointInTriangle() {
        float r1 = (float) Math.random();
        float r2 = (float) Math.random();

        // Barycentric coordinates
        float sqrtR1 = (float) Math.sqrt(r1);
        float u = 1 - sqrtR1;
        float v = r2 * sqrtR1;

        float x = u * vertices[0] + v * vertices[2] + (1 - u - v) * vertices[4];
        float y = u * vertices[1] + v * vertices[3] + (1 - u - v) * vertices[5];

        return new float[]{x, y};
    }

    public double getArea() {
        float x1 = vertices[0];
        float y1 = vertices[1];
        float x2 = vertices[2];
        float y2 = vertices[3];
        float x3 = vertices[4];
        float y3 = vertices[5];

        return Math.abs((x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1 - y2)) / 2.0);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=(" + vertices[0] + ", " + vertices[1] + "), (" +
                vertices[2] + ", " + vertices[3] + "), (" +
                vertices[4] + ", " + vertices[5] + "), " +
                "area=" + getArea() + '}';
    }

}

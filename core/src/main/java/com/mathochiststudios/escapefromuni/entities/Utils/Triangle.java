package com.mathochiststudios.escapefromuni.entities.Utils;

public record Triangle(float[] vertices) {

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

        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    @Override
    public String toString() {
        return "Triangle{" +
            "vertices=(" + vertices[0] + ", " + vertices[1] + "), (" +
            vertices[2] + ", " + vertices[3] + "), (" +
            vertices[4] + ", " + vertices[5] + "), " +
            "area=" + getArea() + '}';
    }

    public boolean equals(Triangle other) {
        float[] v1 = new float[] {this.vertices[0], this.vertices[1]};
        float[] v2 = new float[] {this.vertices[2], this.vertices[3]};
        float[] v3 = new float[] {this.vertices[4], this.vertices[5]};

        float[] ov1 = new float[] {other.vertices[0], other.vertices[1]};
        float[] ov2 = new float[] {other.vertices[2], other.vertices[3]};
        float[] ov3 = new float[] {other.vertices[4], other.vertices[5]};

        // Check if all vertices match in any order
        if (!((v1[0] == ov1[0] && v1[1] == ov1[1]) ||
              (v1[0] == ov2[0] && v1[1] == ov2[1]) ||
              (v1[0] == ov3[0] && v1[1] == ov3[1]))) {
            return false;
        }
        if (!((v2[0] == ov1[0] && v2[1] == ov1[1]) ||
              (v2[0] == ov2[0] && v2[1] == ov2[1]) ||
              (v2[0] == ov3[0] && v2[1] == ov3[1]))) {
            return false;
        }
        if (!((v3[0] == ov1[0] && v3[1] == ov1[1]) ||
              (v3[0] == ov2[0] && v3[1] == ov2[1]) ||
              (v3[0] == ov3[0] && v3[1] == ov3[1]))) {
            return false;
        }

        return true;
    }

}

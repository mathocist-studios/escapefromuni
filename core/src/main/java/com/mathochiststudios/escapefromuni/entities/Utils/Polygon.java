package com.mathochiststudios.escapefromuni.entities.Utils;

import java.util.ArrayList;


/**
 * A simple polygon represented by an array of vertices.
 * Primarily used for triangulation and point picking for the Duck AI.
 *
 * @param vertices An array of float arrays, where each float array represents a vertex (x, y).
 */
public record Polygon(float[][] vertices) {

    public Polygon {
        if (vertices.length < 3) {
            throw new IllegalArgumentException("A polygon must have at least 3 vertices (6 float values).");
        }

    }

    /**
     * Check if the angle formed by three points is convex, taking polygon winding into account.
     *
     * @param prev The previous vertex.
     * @param curr The current vertex.
     * @param next The next vertex.
     * @param orientationSign Sign of polygon orientation: +1 for CCW, -1 for CW.
     * @return True if the angle is convex, false otherwise.
     */
    public static boolean isConvex(float[] prev, float[] curr, float[] next, float orientationSign) {
        // cross product to determine if angle is convex
        // pretty sure this is right
        float crossProduct = (curr[0] - prev[0]) * (next[1] - curr[1]) - (curr[1] - prev[1]) * (next[0] - curr[0]);
        return crossProduct * orientationSign > 0f;
    }

    /**
     * Check if a point is inside a triangle formed by three vertices.
     *
     * <br>
     * CREDITS: <a href="https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle">StackOverflow</a
     *
     * @param pt The point to check.
     * @param v1 The first vertex of the triangle.
     * @param v2 The second vertex of the triangle.
     * @param v3 The third vertex of the triangle.
     * @return True if the point is inside the triangle, false otherwise.
     */
    public static boolean pointInTriangle(float[] pt, float[] v1, float[] v2, float[] v3) {
        // only lord knows
        float dX = pt[0] - v3[0];
        float dY = pt[1] - v3[1];
        float dX21 = v3[0] - v2[0];
        float dY12 = v2[1] - v3[1];
        float D = dY12 * (v1[0] - v3[0]) + dX21 * (v1[1] - v3[1]);
        float s = dY12 * dX + dX21 * dY;
        float t = (v3[1] - v1[1]) * dX + (v1[0] - v3[0]) * dY;
        if (D < 0) return s <= 0 && t <= 0 && s + t >= D;
        return s >= 0 && t >= 0 && s + t <= D;
    }

    /**
     * Compute signed area (positive for counter-clockwise winding).
     *
     * @param verts The vertices of the polygon.
     * @return The signed area of the polygon.
     */
    public static float signedArea(float[][] verts) {
        int n = verts.length;
        float sum = 0f;
        for (int i = 0; i < n; i++) {
            float[] a = verts[i];
            float[] b = verts[(i + 1) % n];
            sum += a[0] * b[1] - b[0] * a[1];
        }
        return sum / 2f;
    }

    public float signedArea() {
        return signedArea(this.vertices);
    }

    public static float area(float[][] verts) {
        return Math.abs(signedArea(verts));
    }

    public float area() {
        return area(this.vertices);
    }

    /**
     * Triangulate the polygon using the ear clipping method.<br>
     * Note: This method assumes the polygon is simple (no self-intersections).
     * <br>
     * UPDATED: Now handles both clockwise and counter-clockwise polygons by determining orientation.
     * <br><br>
     * CREDITS: <a href="https://www.google.com/search?q=ear+clipping+algorithm">Google</a>
     *
     * @return Array of triangles that make up the polygon.
     */
    public Triangle[] triangulate() {
        ArrayList<Triangle> triangles = new ArrayList<>();
        Polygon poly = this;

        while (poly.vertices().length > 3) {
            int n = poly.vertices().length; // number of vertices
            boolean earFound = false;

            // compute orientation sign for current polygon (+1 for CCW, -1 for CW)
            float orient = signedArea(poly.vertices());
            float orientationSign = orient >= 0 ? 1f : -1f;

            // find an ear
            for (int i = 0; i < n; i++) {
                float[] prev = poly.vertices()[(i - 1 + n) % n];
                float[] curr = poly.vertices()[i];
                float[] next = poly.vertices()[(i + 1) % n];

                if (isConvex(prev, curr, next, orientationSign)) {
                    boolean isEar = true;

                    // check if any other vertex is inside the triangle
                    for (int j = 0; j < n; j++) {
                        if (j != (i - 1 + n) % n && j != i && j != (i + 1) % n) {
                            if (pointInTriangle(poly.vertices()[j], prev, curr, next)) {
                                isEar = false;
                                break;
                            }
                        }
                    }

                    if (!isEar) {
                        continue;
                    }

                    triangles.add(new Triangle(new float[]{
                        prev[0], prev[1],
                        curr[0], curr[1],
                        next[0], next[1]
                    }));

                    // remove ear vertex from polygon
                    float[][] newVertices = new float[n - 1][2];
                    for (int k = 0, idx = 0; k < n; k++) {
                        if (k != i) {
                            newVertices[idx++] = poly.vertices()[k];
                        }
                    }
                    poly = new Polygon(newVertices);
                    earFound = true;
                    break;
                }
            }

            if (!earFound) {
                throw new IllegalArgumentException("Polygon is not simple or has inconsistent winding and cannot be triangulated.");
            }
        }

        // final triangle
        triangles.add(new Triangle(new float[]{
            poly.vertices()[0][0], poly.vertices()[0][1],
            poly.vertices()[1][0], poly.vertices()[1][1],
            poly.vertices()[2][0], poly.vertices()[2][1]
        }));

        return triangles.toArray(new Triangle[0]);
    }

    /**
     * Pick a random point inside the polygon.
     * <br><br>
     * UPDATED: Now uses area-weighted random selection of triangles for uniform distribution.
     *
     * @return A float array representing the (x, y) coordinates of the point.
     */
    public float[] pickPointInPolygon() {
        Triangle[] triangles = triangulate();
        double[] areas = new double[triangles.length];
        double totalArea = 0.0;

        // calculate areas of triangles and total area
        for (int i = 0; i < triangles.length; i++) {
            areas[i] = triangles[i].getArea();
            totalArea += areas[i];
        }

        // pick a triangle based on area weights
        double rand = Math.random() * totalArea;
        double cumulativeArea = 0.0;
        for (int i = 0; i < triangles.length; i++) {
            cumulativeArea += areas[i];
            if (rand <= cumulativeArea) {
                // pick a point in this triangle
                return triangles[i].pickPointInTriangle();
            }
        }

        return triangles[triangles.length - 1].pickPointInTriangle();
    }

    /**
     * Check if a point is inside the polygon.
     *
     * @param point A float array representing the (x, y) coordinates of the point.
     * @return True if the point is inside the polygon, false otherwise.
     */
    public boolean isPointInPolygon(float[] point) {
        Triangle[] triangles = triangulate();
        for (Triangle triangle : triangles) {
            if (pointInTriangle(point,
                new float[]{triangle.vertices()[0], triangle.vertices()[1]},
                new float[]{triangle.vertices()[2], triangle.vertices()[3]},
                new float[]{triangle.vertices()[4], triangle.vertices()[5]})) {
                return true;
            }
        }
        return false;
    }

}

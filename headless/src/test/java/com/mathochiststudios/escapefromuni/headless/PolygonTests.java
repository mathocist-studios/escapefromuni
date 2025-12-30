package com.mathochiststudios.escapefromuni.headless;

import com.mathochiststudios.escapefromuni.entities.Utils.Polygon;
import com.mathochiststudios.escapefromuni.entities.Utils.Triangle;
import org.junit.jupiter.api.Test;

public class PolygonTests {

    @Test
    public void testMinimumVertices() {
        try {
            new com.mathochiststudios.escapefromuni.entities.Utils.Polygon(new float[][]{
                {0f, 0f},
                {1f, 0f}
            });
            assert false : "Expected IllegalArgumentException for less than 3 vertices";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("A polygon must have at least 3 vertices (6 float values).");
        }
    }

    @Test
    public void testIsConvex() {
        float[] prev = {1f, 1f};
        float[] curr = {0f, 0f};
        float[] next = {2f, 0f};
        boolean result = Polygon.isConvex(prev, curr, next, 1f);
        assert result : "Expected angle to be convex";
    }

    @Test
    public void testPointInTriangle() {
        float[] pt = {1f, 0.5f};
        float[] v1 = {0f, 0f};
        float[] v2 = {2f, 0f};
        float[] v3 = {1f, 1f};
        boolean result = Polygon.pointInTriangle(pt, v1, v2, v3);
        assert result : "Expected point to be inside the triangle";
    }

    @Test
    public void testPointOutsideTriangle() {
        float[] pt = {3f, 3f};
        float[] v1 = {0f, 0f};
        float[] v2 = {2f, 0f};
        float[] v3 = {1f, 1f};
        boolean result = Polygon.pointInTriangle(pt, v1, v2, v3);
        assert !result : "Expected point to be outside the triangle";
    }

    @Test
    public void testPolygonCreation() {
        float[][] vertices = {
            {0f, 0f},
            {1f, 0f},
            {1f, 1f},
            {0f, 1f}
        };
        Polygon polygon = new Polygon(vertices);
        assert polygon.vertices().length == 4 : "Expected polygon to have 4 vertices";
    }

    @Test
    public void testSignedArea() {
        float[][] vertices = {
            {0f, 0f},
            {4f, 0f},
            {4f, 3f},
            {0f, 3f}
        };
        float area = Polygon.signedArea(vertices);
        assert area == 12f : "Expected signed area to be 12";
    }

    @Test
    public void testSignedAreaNegative() {
        float[][] vertices = {
            {0f, 0f},
            {0f, 3f},
            {4f, 3f},
            {4f, 0f}
        };
        float area = Polygon.signedArea(vertices);
        assert area == -12f : "Expected signed area to be -12";
    }

    @Test
    public void testTriangulation() {
        float[][] vertices = {
            {0f, 0f},
            {4f, 0f},
            {4f, 3f},
            {0f, 3f}
        };
        Polygon polygon = new Polygon(vertices);
        Triangle[] triangles = polygon.triangulate();
        assert triangles.length == 2 : "Expected polygon to be triangulated into 2 triangles";

        Triangle expected_triangle1 = new Triangle(
            new float[] {
                0f, 0f,
                4f, 0f,
                0f, 3f
            }
        );

        Triangle expected_triangle2 = new Triangle(
            new float[] {
                4f, 0f,
                4f, 3f,
                0f, 3f
            }
        );

        assert triangles[0].equals(expected_triangle1) || triangles[0].equals(expected_triangle2) : "First triangle does not match expected";
        assert triangles[1].equals(expected_triangle1) || triangles[1].equals(expected_triangle2) : "Second triangle does not match expected";

    }

    @Test
    public void testConcaveTriangulation() {
        float[][] vertices = {
            {0f, 0f},
            {4f, 0f},
            {4f, 3f},
            {2f, 1.5f},
            {0f, 3f}
        };
        Polygon polygon = new Polygon(vertices);
        Triangle[] triangles = polygon.triangulate();
        assert triangles.length == 3 : "Expected concave polygon to be triangulated into 3 triangles";

        Triangle expected_triangle1 = new Triangle(
            new float[] {
                0f, 0f,
                4f, 0f,
                2f, 1.5f
            }
        );

        Triangle expected_triangle2 = new Triangle(
            new float[] {
                4f, 0f,
                4f, 3f,
                2f, 1.5f
            }
        );

        Triangle expected_triangle3 = new Triangle(
            new float[] {
                0f, 0f,
                0f, 3f,
                2f, 1.5f
            }
        );

        assert (triangles[0].equals(expected_triangle1) || triangles[0].equals(expected_triangle2) || triangles[0].equals(expected_triangle3)) : "First triangle does not match expected";
        assert (triangles[1].equals(expected_triangle1) || triangles[1].equals(expected_triangle2) || triangles[1].equals(expected_triangle3)) : "Second triangle does not match expected";
        assert (triangles[2].equals(expected_triangle1) || triangles[2].equals(expected_triangle2) || triangles[2].equals(expected_triangle3)) : "Third triangle does not match expected";
    }

    @Test
    public void testPickPointInPolygon() {
        float[][] vertices = {
            {0f, 0f},
            {4f, 0f},
            {4f, 3f},
            {0f, 3f}
        };
        Polygon polygon = new Polygon(vertices);
        float[] point = polygon.pickPointInPolygon();

        assert polygon.isPointInPolygon(point) : "Expected picked point to be inside the polygon";
    }

    @Test
    public void testIsPointInPolygon() {
        float[][] vertices = {
            {0f, 0f},
            {4f, 0f},
            {4f, 3f},
            {0f, 3f}
        };
        Polygon polygon = new Polygon(vertices);
        float[] pointInside = {2f, 1f};
        float[] pointOutside = {5f, 5f};

        assert polygon.isPointInPolygon(pointInside) : "Expected point to be inside the polygon";
        assert !polygon.isPointInPolygon(pointOutside) : "Expected point to be outside the polygon";
    }

    @Test
    public void testPickPointInTriangle() {
        Triangle triangle = new Triangle(
            new float[] {
                0f, 0f,
                4f, 0f,
                2f, 3f
            }
        );
        float[] point = triangle.pickPointInTriangle();

        assert Polygon.pointInTriangle(point,
            new float[] {0f, 0f},
            new float[] {4f, 0f},
            new float[] {2f, 3f}
        ) : "Expected picked point to be inside the triangle";
    }

    @Test
    public void testTriangleArea() {
        Triangle triangle = new Triangle(
            new float[] {
                0f, 0f,
                4f, 0f,
                2f, 3f
            }
        );
        double area = triangle.getArea();
        assert area == 6.0 : "Expected triangle area to be 6.0";
    }

}

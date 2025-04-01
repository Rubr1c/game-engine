package dev.rubr1c.core.utils;

import dev.rubr1c.core.Camera;
import dev.rubr1c.core.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    public static Matrix4f createTransformationMatrix(Entity entity) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(entity.getPos())
                .rotateX((float) Math.toRadians(entity.getRotation().x))
                .rotateY((float) Math.toRadians(entity.getRotation().y))
                .rotateZ((float) Math.toRadians(entity.getRotation().z))
                .scale(entity.getScale());

        return matrix;
    }

    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f pos = camera.getPosition();
        Vector3f rot = camera.getRotation();
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(-pos.x, -pos.y, -pos.z)
                .rotateY((float) Math.toRadians(rot.y))
                .rotateX((float) Math.toRadians(rot.x));
        return matrix;
    }
}

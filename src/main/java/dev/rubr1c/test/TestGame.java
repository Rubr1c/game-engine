package dev.rubr1c.test;

import dev.rubr1c.core.*;
import dev.rubr1c.core.entity.Entity;
import dev.rubr1c.core.entity.Model;
import dev.rubr1c.core.entity.Texture;
import dev.rubr1c.core.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;
    private Camera camera;

    private Vector3f cameraInc;

    public TestGame() {
        renderer = new RenderManager();
        window = Main.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
                // Front face
                -0.5f, 1.0f, 0.5f, // top-left
                -0.5f, -0.5f, 0.5f, // bottom-left
                0.5f, -0.5f, 0.5f, // bottom-right
                0.5f, 1.0f, 0.5f, // top-right
                // Back face
                -0.5f, 1.0f, -0.5f, // top-left
                -0.5f, -0.5f, -0.5f, // bottom-left
                0.5f, -0.5f, -0.5f, // bottom-right
                0.5f, 1.0f, -0.5f, // top-right
                // Top face
                -0.5f, 1.0f, -0.5f, // top-left
                0.5f, 1.0f, -0.5f, // top-right
                -0.5f, 1.0f, 0.5f, // bottom-left
                0.5f, 1.0f, 0.5f, // bottom-right
                // Bottom face
                -0.5f, -0.5f, -0.5f, // top-left
                0.5f, -0.5f, -0.5f, // top-right
                -0.5f, -0.5f, 0.5f, // bottom-left
                0.5f, -0.5f, 0.5f, // bottom-right
                // Right face
                0.5f, 1.0f, 0.5f, // top-left
                0.5f, -0.5f, 0.5f, // bottom-left
                0.5f, 1.0f, -0.5f, // top-right
                0.5f, -0.5f, -0.5f, // bottom-right
                // Left face
                -0.5f, 1.0f, 0.5f, // top-left
                -0.5f, -0.5f, 0.5f, // bottom-left
                -0.5f, 1.0f, -0.5f, // top-right
                -0.5f, -0.5f, -0.5f, // bottom-right
        };

        float[] textCoords = {
                // Front face
                0.0f, 0.0f, // top-left
                0.0f, 1.0f, // bottom-left
                1.0f, 1.0f, // bottom-right
                1.0f, 0.0f, // top-right
                // Back face
                0.0f, 0.0f, // top-left
                0.0f, 1.0f, // bottom-left
                1.0f, 1.0f, // bottom-right
                1.0f, 0.0f, // top-right
                // Top face
                0.0f, 0.0f, // top-left
                1.0f, 0.0f, // top-right
                0.0f, 1.0f, // bottom-left
                1.0f, 1.0f, // bottom-right
                // Bottom face
                0.0f, 0.0f, // top-left
                1.0f, 0.0f, // top-right
                0.0f, 1.0f, // bottom-left
                1.0f, 1.0f, // bottom-right
                // Right face
                0.0f, 0.0f, // top-left
                0.0f, 1.0f, // bottom-left
                1.0f, 0.0f, // top-right
                1.0f, 1.0f, // bottom-right
                // Left face
                1.0f, 0.0f, // top-right
                1.0f, 1.0f, // bottom-right
                0.0f, 0.0f, // top-left
                0.0f, 1.0f, // bottom-left
        };
        int[] indices = {
                // Front face
                0, 1, 2, 2, 3, 0,
                // Back face
                4, 5, 6, 6, 7, 4,
                // Top face
                8, 9, 10, 10, 11, 9,
                // Bottom face
                12, 13, 14, 14, 15, 13,
                // Right face
                16, 17, 18, 18, 19, 17,
                // Left face
                20, 21, 22, 22, 23, 21
        };

        Model model = loader.loadModel(vertices, textCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("textures/dirt.png")));
        entity = new Entity(model, new Vector3f(0, 0, -2), new Vector3f(0, 0, 0), 1);
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
        if (window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;
        if (window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if (window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
            cameraInc.y = 1;
        if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
            cameraInc.y = -1;
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * Constants.CAMERA_STEP, cameraInc.y * Constants.CAMERA_STEP,
                cameraInc.z * Constants.CAMERA_STEP);

        if (mouseInput.isRightButtonPress()) {
            Vector2f rotVec = mouseInput.getDisplayVec();
            camera.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
        }

    }

    @Override
    public void render() {
        if (window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.render(entity, camera);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}

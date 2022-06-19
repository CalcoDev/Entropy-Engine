package Entropy.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width;
    private int height;
    private String title;

    private long windowId;
    private int errorCode;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        this.windowId = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);
        if (this.windowId == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwMakeContextCurrent(this.windowId);
        GLFW.glfwSwapInterval(0);
        GLFW.glfwShowWindow(this.windowId);

        GLFWErrorCallback.createPrint(System.err).set();

        GL.createCapabilities();

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnable(GL11.GL_VERTEX_ARRAY);
    }

    public void close() {
        GLFW.glfwDestroyWindow(this.windowId);
        GLFW.glfwTerminate();

        //noinspection ConstantConditions
        GLFWErrorCallback.createPrint(null).free();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.windowId);
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public void render() {
        GLFW.glfwSwapBuffers(this.windowId);
    }

    public boolean errorOccurred() {
        this.errorCode = GL11.glGetError();
        return this.errorCode != GL11.GL_NO_ERROR;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindowId() {
        return windowId;
    }
}

package warpedrealities.core.core;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import warpedrealities.bloodgun.scenes.Platformer;
import warpedrealities.core.font.FontSupport;
import warpedrealities.core.gui.SharedGUIResources;
import warpedrealities.core.input.Keyboard;
import warpedrealities.core.input.MouseHook;
import warpedrealities.core.shared.Scene;
import warpedrealities.core.shared.SceneBase;
import warpedrealities.core.shared.SceneManager;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Game implements SceneManager {

	private GLFWErrorCallback errorCallback;

	private long openGLWindow;
	public static SceneManager sceneManager;
	double lastFrame;
	private int vShader, fShader, vShadowShader, fShadowShader, tintVar0, viewVar0, objVar0;

	int viewVar1, objVar1;
	static public int pShader, pShadowShader;
	Scene currentScene;
	GameManager gManager;

	Config graphicsConfiguration;
	MouseHook mouseInput;

	// public static float m_scale;

	public Config getConfig() {
		return graphicsConfiguration;
	}

	public Game() {

		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		sceneManager = this;
		graphicsConfiguration = new Config();

		/*
		 * try { System.setErr(new PrintStream(new
		 * FileOutputStream(System.getProperty("user.dir")+"/error.log"))); }
		 * catch (FileNotFoundException ex) { ex.printStackTrace(); }
		 */

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		// Configure our window
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_RED_BITS, 8);
		glfwWindowHint(GLFW_GREEN_BITS, 8);
		glfwWindowHint(GLFW_BLUE_BITS, 8);
		glfwWindowHint(GLFW_ALPHA_BITS, 8);
		glfwWindowHint(GLFW_DEPTH_BITS, 0);
		glfwWindowHint(GLFW_STENCIL_BITS, 8);

		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
		glfwWindowHint(GLFW_SAMPLES, 8);

		int WIDTH = (int) (1280 * graphicsConfiguration.getScale());
		int HEIGHT = (int) (1024 * graphicsConfiguration.getScale());

		// Create the window
		openGLWindow = glfwCreateWindow(WIDTH, HEIGHT, "Blood Gun", NULL, NULL);
		if (openGLWindow == NULL) {
			System.err.println("Failed to create the GLFW window");
			System.err.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
			System.err.println("OpenGL shader version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
			System.exit(-1);
		}
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(openGLWindow, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

		// Make the OpenGL context current
		glfwMakeContextCurrent(openGLWindow);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(openGLWindow);

		Keyboard.setWindow(openGLWindow);
		GL.createCapabilities();

		FontSupport.getInstance();

		LoadShaders();

		int[] var = new int[5];
		var[0] = tintVar0;
		var[1] = viewVar0;
		var[2] = objVar0;
		var[3] = viewVar1;
		var[4] = objVar1;

		SceneBase.setVariables(var);

		currentScene = new Platformer();

		mouseInput = new MouseHook(openGLWindow);
		glfwSetCursorPosCallback(openGLWindow, mouseInput);
		GL11.glDepthFunc(GL_LEQUAL);
		currentScene.start(mouseInput);

		// load font
		lastFrame = GLFW.glfwGetTime();
		glViewport(0, 0, WIDTH, HEIGHT);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glfwSetCharCallback(openGLWindow, warpedrealities.core.input.Keyboard.getInstance());

		SharedGUIResources.getInstance().initialize();
	}

	private int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}

		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile shader.");
			System.err.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
			System.err.println("OpenGL shader version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
			System.exit(-1);
		}

		return shaderID;
	}

	public void LoadShaders() {
		vShader = this.loadShader("assets/shaders/shaderv.txt", GL20.GL_VERTEX_SHADER);
		fShader = this.loadShader("assets/shaders/shaderf.txt", GL20.GL_FRAGMENT_SHADER);
		pShader = GL20.glCreateProgram();
		System.err.println("system debug primary shader program id is " + pShader);
		GL20.glAttachShader(pShader, vShader);
		GL20.glAttachShader(pShader, fShader);

		GL20.glBindAttribLocation(pShader, 0, "in_Position");
		GL20.glBindAttribLocation(pShader, 1, "in_TextureCoord");
		GL20.glLinkProgram(pShader);
		GL20.glValidateProgram(pShader);

		tintVar0 = GL20.glGetUniformLocation(pShader, "in_color");
		viewVar0 = GL20.glGetUniformLocation(pShader, "projMatrix");
		objVar0 = GL20.glGetUniformLocation(pShader, "modelMatrix");

		vShadowShader = this.loadShader("assets/shaders/shadowshaderv.txt", GL20.GL_VERTEX_SHADER);
		fShadowShader = this.loadShader("assets/shaders/shadowshaderf.txt", GL20.GL_FRAGMENT_SHADER);
		pShadowShader = GL20.glCreateProgram();
		System.err.println("system debug shadow shader program id is " + pShadowShader);
		GL20.glAttachShader(pShadowShader, vShadowShader);
		GL20.glAttachShader(pShadowShader, fShadowShader);

		GL20.glBindAttribLocation(pShadowShader, 0, "in_Position");
		GL20.glBindAttribLocation(pShadowShader, 1, "in_TextureCoord");
		GL20.glBindAttribLocation(pShadowShader, 2, "in_Light");
		GL20.glLinkProgram(pShadowShader);
		GL20.glValidateProgram(pShadowShader);
		viewVar1 = GL20.glGetUniformLocation(pShadowShader, "projMatrix");
		objVar1 = GL20.glGetUniformLocation(pShadowShader, "modelMatrix");

	}

	public void End() {

		Callbacks.glfwFreeCallbacks(openGLWindow);
		// delete shader
		GL20.glUseProgram(0);
		GL20.glDetachShader(pShader, vShader);
		GL20.glDetachShader(pShader, fShader);
		GL20.glDeleteShader(vShader);
		GL20.glDeleteShader(fShader);
		GL20.glDeleteProgram(pShader);

		GL20.glUseProgram(0);
		GL20.glDetachShader(pShadowShader, vShadowShader);
		GL20.glDetachShader(pShadowShader, fShadowShader);
		GL20.glDeleteShader(vShadowShader);
		GL20.glDeleteShader(fShadowShader);
		GL20.glDeleteProgram(pShadowShader);
		// clean up
		// m_mouse.Discard();
		currentScene.end();
		glfwDestroyWindow(openGLWindow);

		glfwTerminate();
		errorCallback.close();
		FontSupport.discard();
		SharedGUIResources.getInstance().discard();
	}

	public void Run() {

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		while (!glfwWindowShouldClose(openGLWindow)) {

			double t = GLFW.glfwGetTime();
			double dt = t - lastFrame;
			if (dt > 0.016F) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the
																	// framebuffer
				update(dt);
				lastFrame = t;
				glfwSwapBuffers(openGLWindow); // swap the color buffers
			} else {
				Thread.yield();
			}

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}

	}

	public void update(double dt) {
		// update
		currentScene.update((float) dt);

		// m_gman.Update((float)dt);

		glfwPollEvents();
		// render
		mouseInput.update();
		Draw();
	}

	void Draw() {

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL20.glUseProgram(pShader);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		currentScene.draw();

		GL20.glUseProgram(0);

	}

	@Override
	public void SwapScene(Scene scene) {
		// TODO Auto-generated method stub
		currentScene.end();
		currentScene = scene;
		mouseInput.Clean();
		currentScene.start(mouseInput);
	}

}

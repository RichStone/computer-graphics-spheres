import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL45;
import org.lwjgl.util.glu.GLU;

import edu.berlin.htw.ds.cg.helper.GLDrawHelper;
import edu.berlin.htw.ds.cg.helper.InteractiveItem;
import static org.lwjgl.opengl.GL11.*;
public class Main implements InteractiveItem {

	
	
	private int len = 70;
	private int wid = 30; 
	
	//display size
	static int width = 1200;
	static int height = 800;
	
	private boolean appEnd = false;
	
	//configure action keys
	private boolean keyPressed = false;
	
	//float[] cameraPos = new float[]{0, 0, 1500};
	
	Camera cam;
	
	Fork fork;
	
	public static void main(String[] args) {
		Main app = new Main();
		app.run();
	}
	
	public void run() {
		setup();
		while(!appEnd) {
			update();
			render();
		}
		finish();
	}

	@Override
	public void setup() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setInitialBackground(0.5f, 0.5f, 0.5f);
			Display.create();
		}
		catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		cam = new Camera(70, (float)Display.getWidth() / (float)Display.getHeight(), 0.3f, 1000);
//		GL11.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
//		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		GL11.glLoadIdentity();
//		GLU.gluPerspective(75.f, (float)width/height, 2.1f, 6000.f);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
//		fork = new Fork(40,70,len,wid, //main fork
//				new Fork(40,70,len,wid, //left fork with two balls
//						new Fork(), new Fork()),
//				new Fork(40,70,len,wid, //right fork with two forks
//				new Fork(40,70,len*0.75f,wid*0.75f, //left ball fork
//						new Fork(), new Fork()), 
//				new Fork(40,70,len*0.75f,wid*0.75f, //right ball fork
//						new Fork(), new Fork())));
		
		fork = new Fork(40, 70, len, wid, new Fork(), new Fork());
	}

	
	
	@Override
	public void update() {
		appEnd = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) | Display.isCloseRequested();
	}

	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		cam.useView();
		
		//GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2], 0, 0, 0, 0, 1, 0);
		
		fork.render();
		
		Display.update();
		Display.sync(60);
	}

	@Override
	public void finish() {
		Display.destroy();
	}

}

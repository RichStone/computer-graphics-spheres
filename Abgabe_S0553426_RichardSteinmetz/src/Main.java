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

public class Main implements InteractiveItem {

	
	
	private int len = 70;
	private int wid = 30; 
	
	//display size
	static int width = 1200;
	static int height = 800;
	
	private boolean appEnd = false;
	
	//configure action keys
	private boolean keyPressed = false;
	
	float[] cameraPos = new float[]{0, 0, 1500};
	
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
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(75.f, (float)width/height, 2.1f, 6000.f);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		fork = new Fork(40,70,len,wid,
				new Fork(40,70,len,wid,new Fork(), new Fork()),
				new Fork(40,70,len,wid,
				new Fork(40,70,len*0.75f,wid*0.75f,new Fork(), new Fork()), 
				new Fork(40,70,len*0.75f,wid*0.75f,new Fork(), new Fork())));
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
		GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2], 0, 0, 0, 0, 1, 0);
		
		//strippgen: render fork (the whole mobile) here ( i.e. fork.render(); )
		
		Display.update();
		Display.sync(60);
	}

	@Override
	public void finish() {
		
	}

}

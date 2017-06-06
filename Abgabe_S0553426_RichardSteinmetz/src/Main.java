import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;

import edu.berlin.htw.ds.cg.helper.InteractiveItem;

public class Main implements InteractiveItem {

	private int len = 70;
	private int wid = 30; 
	private boolean end = false;
	
	//display size
	static int width = 1200;
	static int height = 800;
	
	//configure action keys
	private boolean keyPressed = false;
	
	
	
	public static void main(String[] args) {
		Main app = new Main();
		app.run();
	}
	
	public void run() {
		setup();
		while(!end) {
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
	}

	
	
	@Override
	public void update() {
		end = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) | Display.isCloseRequested();
	}

	@Override
	public void render() {
		
	}

	@Override
	public void finish() {
		
	}

}

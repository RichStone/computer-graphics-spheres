import org.lwjgl.opengl.GL11;

import edu.berlin.htw.ds.cg.helper.GLDrawHelper;
import edu.berlin.htw.ds.cg.helper.InteractiveItem;

public class Fork implements InteractiveItem {
	//constructor fork
	private float len;
	private float wid; 
	private int pitch;
	private int yaw;
	private InteractiveItem leftFork;
	private InteractiveItem rightFork;
	boolean isSphere;
	
	public Fork(int pitch, int yaw, float len, float wid, Fork fork2, Fork fork3) {
		isSphere = false;
		this.pitch = pitch;
		this.yaw = yaw;
		this.len = len;
		this.wid = wid;
		leftFork = fork2;
		rightFork = fork3;
	}

	public Fork() {
		isSphere = true;
	}

	@Override
	public void setup() {
		//not needed
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		if(isSphere) {
//			GL11.glColor3f(0.8f, 0.9f, 0.1f);
//			GLDrawHelper.drawSphere(100, 10, 10);
//			GL11.glTranslated(100, 100, 100);
		}
		else {
			//--> render leftFork & rightFork
			GL11.glColor3f(0.2f, 0.9f, 0.1f);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex3f(0, 1000, 0);
			GL11.glVertex3f(500, 100, 0);
			GL11.glEnd();
			
			leftFork.render();
			rightFork.render();
		}
	}

	@Override
	public void finish() {
		
	}
}

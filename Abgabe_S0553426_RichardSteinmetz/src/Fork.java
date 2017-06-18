import static org.lwjgl.opengl.GL11.GL_QUADS;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.*;

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
			//render this fork
			glPushMatrix();
			{
				GL11.glColor3f(0.2f, 0.9f, 0.1f);
				glTranslatef(0,0,-10);
				glRotatef(0,1,0.2f,0);
				
				GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex3f(1, 1, 1);
				GL11.glVertex3f(-1, -1, 1);
				GL11.glEnd();
			}
			glPopMatrix();
			
			//render adjacent forks
			leftFork.render();
			rightFork.render();
		}
	}

	@Override
	public void finish() {
		
	}
}

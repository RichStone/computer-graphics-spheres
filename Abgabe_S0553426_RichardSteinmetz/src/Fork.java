import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.Random;

import org.lwjgl.opengl.Display;
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
	
	private float movement = 0.4f;
	private float actualPitch;
	private int maxPitch = 40;
	private String pitchDirection = "NORMAL";
	private float actualYaw;
	private int maxYaw = 180;
	private String yawDirection = "NORMAL";
	
	private Random rnd = new Random();
	
	private Integer textureID;
	
	public Fork(int pitch, int yaw, float len, float wid, Fork fork2, Fork fork3) 
	{
		isSphere = false;
		int randomMovementStart = rnd.nextInt(40);
		this.pitch = randomMovementStart;
		randomMovementStart = rnd.nextInt(180);
		this.yaw = yaw + randomMovementStart;
		this.len = len;
		this.wid = wid;
		leftFork = fork2;
		rightFork = fork3;
	}

	public Fork() 
	{
		isSphere = true;
		int randomTextureNumber = rnd.nextInt(Main.textureIDs.size());
		System.out.println(Main.textureIDs.get(randomTextureNumber));
        textureID = Main.textureIDs.get(randomTextureNumber);
	}

	@Override
	public void render() 
	{
		glPushMatrix(); 
		{
			if(isSphere) {
				//enable textures & bind texture with texture id (only for spheres)
	            GL11.glEnable(GL11.GL_TEXTURE_2D);
	            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
				GLDrawHelper.drawSphere(18, 10, 10);
	            //clear texture
	            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
			}
			else {
				actualYaw = getActualYaw();
				actualPitch = getActualPitch();
				GL11.glColor3f(0.9f, 0.9f, 0.9f);
				GL11.glLineWidth(8f);
				//vertical line
				{
					GL11.glBegin(GL11.GL_LINES);
					glVertex3f(0, 0, 0);
					glVertex3f(0, -len, 0);
					GL11.glEnd();
				}
				//horizontal line
				glTranslatef(0, -len, 0);
				glRotatef(actualYaw, 0, 1, 0);
				{
					glBegin(GL11.GL_LINES);
					glVertex3f(-wid, actualPitch, 0);
					glVertex3f(wid, -actualPitch, 0);
					glEnd();
				}
				//render adjacent forks
				glTranslatef(wid, -actualPitch, 0);
				leftFork.render();
				glTranslatef(-2 * wid, 2 * actualPitch, 0);
				rightFork.render();
			}
		}
		glPopMatrix();
	}
	
	private float getActualYaw() 
	{
		if(actualYaw > maxYaw) {
			yawDirection = "INVERSE";
		}
		else if(actualYaw < 0) {
			yawDirection = "NORMAL";
		}
		
		if(yawDirection.equals("NORMAL")) {
			actualYaw += movement;
		}
		else if(yawDirection.equals("INVERSE")) {
			actualYaw -= movement;
		}
		
		return actualYaw;
	}
	
	private float getActualPitch() {
		if(actualPitch > maxPitch) {
			pitchDirection = "INVERSE";
		}
		else if(actualPitch < 0) {
			pitchDirection = "NORMAL";
		}
		
		if(pitchDirection.equals("NORMAL")) {
			actualPitch += movement;
		}
		else if(pitchDirection.equals("INVERSE")) {
			actualPitch -= movement;
		}
		return actualPitch;
	}

	@Override
	public void setup() 
	{
		// TODO not needed
	}
	
	@Override
	public void update() 
	{
		//not needed
	}

	@Override
	public void finish() 
	{
		Display.destroy();
	}
}
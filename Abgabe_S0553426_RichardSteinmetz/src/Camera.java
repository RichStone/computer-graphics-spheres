import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Camera {
	
	private float xPosition;
	private float yPosition;
	private float zPosition;
	
	private float rotationX;
	private float rotationY;
	private float rotationZ;
	
	//how much we will see 
	private float fieldOfView;
	private float aspectRatio;
	private float nearClippingPlain;
	//prevent seeing infinity
	private float farClippingPlain;
	
	public Camera(float fieldOfView, float aspectRatio, float nearClippingPlain, float farClippingPlain) {
		xPosition = 0;
		yPosition = 0;
		zPosition = 0;
		
		rotationX = 0;
		rotationY = 0;
		rotationZ = 0;
		
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.nearClippingPlain = nearClippingPlain;
		this.farClippingPlain = farClippingPlain;
		
		initProjection();
	}
	
	private void initProjection() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fieldOfView, aspectRatio, nearClippingPlain, farClippingPlain);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void useView() {
		GL11.glRotatef(rotationX, 1, 0, 0);
		GL11.glRotatef(rotationY, 0, 1, 0);
		GL11.glRotatef(rotationZ, 0, 0, 1);
		GL11.glTranslatef(xPosition, yPosition, zPosition);
	}
	
	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getyPosition() {
		return yPosition;
	}

	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	public float getzPosition() {
		return zPosition;
	}

	public void setzPosition(float zPosition) {
		this.zPosition = zPosition;
	}

	public float getRotationX() {
		return rotationX;
	}

	public void setRotationX(float rotationX) {
		this.rotationX = rotationX;
	}

	public float getRotationY() {
		return rotationY;
	}

	public void setRotationY(float rotationY) {
		this.rotationY = rotationY;
	}

	public float getRotationZ() {
		return rotationZ;
	}

	public void setRotationZ(float rotationZ) {
		this.rotationZ = rotationZ;
	}
}

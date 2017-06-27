import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import edu.berlin.htw.ds.cg.helper.GLDrawHelper;
import edu.berlin.htw.ds.cg.helper.InteractiveItem;
import edu.berlin.htw.ds.cg.helper.TextureReader;
import edu.berlin.htw.ds.cg.helper.TextureReader.Texture;


public class Main implements InteractiveItem 
{
	private String texturesPath = "/Users/Rich/Dropbox/Studium/3. Semester/Computergrafik/uebung/CGSS15Ex3MobileDS/dataEx3/Textures";
    public static List<Integer> textureIDs = new ArrayList<Integer>();
	
	//display size
	static int width = 1200;
	static int height = 800;
	
	private boolean appEnd = false;
	
	//configure action keys
	private boolean keyPressed = false;
	
	float[] cameraPos = new float[]{0, 0, 500};
	
	private Camera cam;
	private float movementSpeed = 10f;
	private float rotationSpeed = 5f;
	
	private Fork fork;
	private int len = 70;
	private int wid = 60;
	
	//light buffers
	private FloatBuffer noAmbient = GLDrawHelper.directFloatBuffer(new float[] {.5f, .5f, .5f, 1f});
    private FloatBuffer whiteDiffuse = GLDrawHelper.directFloatBuffer(new float[] {.5f, .5f, .5f, 1f});
    private FloatBuffer positionOfLight = GLDrawHelper.directFloatBuffer(new float[] {0f, 100f, 0f, 1f});
	
	public static void main(String[] args) 
	{
		Main app = new Main();
		app.run();
	}
	
	public void run() 
	{
		setup();
		while(!appEnd) {
			update();
			render();
		}
		finish();
	}

	@Override
	public void setup() 
	{
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setInitialBackground(0.5f, 0.5f, 0.5f);
			Display.create();
			Mouse.create();
		}
		catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		cam = new Camera(70, (float)Display.getWidth() / (float)Display.getHeight(), 0.3f, 1000);
		
		setTextureList();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		fork = new Fork(40,70,len,wid, //main fork
				new Fork(40,70,len,wid, //left fork with two balls
						new Fork(), new Fork()),
				new Fork(40,70,len,wid, //right fork with two forks
				new Fork(40,70,len*0.75f,wid*0.75f, //left ball fork
						new Fork(), new Fork()), 
				new Fork(40,70,len*0.75f,wid*0.75f, //right ball fork
						new Fork(), new Fork())));		
		
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, noAmbient);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, whiteDiffuse);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE) ;
        GL11.glEnable(GL11.GL_COLOR_MATERIAL) ;
	}

	
	
	@Override
	public void update() 
	{
		appEnd = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) | Display.isCloseRequested();
		//cam movement
		boolean forward = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean backward = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
		int horizontalRotation = Mouse.getX();
		int verticalRotation = Mouse.getY();
		
		if(forward)
			cam.moveBackForth(movementSpeed);
		if(backward)
			cam.moveBackForth(-movementSpeed);
		if(left)
			cam.moveLeftRight(movementSpeed);	
		if(right)
			cam.moveLeftRight(-movementSpeed);

		cam.setRotationY(verticalRotation);
		
		cam.setRotationX(horizontalRotation);
	}

	@Override
	public void render() 
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		cam.useView();
		GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2], 0, 0, 0, 0, 1, 0);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, positionOfLight);
		
		fork.render();
		
		Display.update();
		Display.sync(60);
	}

	@Override
	public void finish() 
	{
		Display.destroy();
	}
	
	public void setTextureList() 
	{
        File[] textures = new File(texturesPath).listFiles();

        for (File curTexture : textures) {
        	try {
                Texture texture = TextureReader.readTexture(curTexture.getAbsolutePath());
                
                textureIDs.add(GL11.glGenTextures());
                
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIDs.get(textureIDs.size() - 1));

                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, texture.getWidth(), 
                		texture.getHeight(), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, texture.getPixels());

                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private long getSysTime()
    {
        return (Sys.getTime() * 1000 / Sys.getTimerResolution());
    }
}

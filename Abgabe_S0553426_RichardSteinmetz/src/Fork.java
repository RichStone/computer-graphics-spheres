import edu.berlin.htw.ds.cg.helper.InteractiveItem;

public class Fork implements InteractiveItem {
	//constructor fork
	private float len;
	private float wid; 
	private int pitch;
	private int yaw;
	private InteractiveItem leftFork;
	private InteractiveItem rightFork;
	boolean isSphere = false;
	
	public Fork(int pitch, int yaw, float len, float wid, Fork fork2, Fork fork3) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.len = len;
		this.wid = wid;
		leftFork = fork2;
		rightFork = fork3;
	}

	@Override
	public void setup() {
		leftFork.setup();
		rightFork.setup();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		if(isSphere) {
			
		}
		else {
			
		}
	}

	@Override
	public void finish() {
		
	}
}

package camera;


public class CameraFactory {
	static public Camera parseCommand(String cmd) {
		String name = cmd.split(" ")[0];
		if(name.equals("perspective") || name.equals("default")) {
			return PerspectiveCamera.create(cmd.substring(name.length()+1));
		}
		return null;
	}
}

package primitive;

public class EntityFactory {
	static public Entity parseCommand(String cmd) {
		String pri = cmd.split(" ")[0];
		if(pri.equals("sphere")) {
			return Sphere.create(cmd.substring(7));
		}
		if(pri.equals("mesh")) {
			return Mesh.create(cmd.substring(5));
		}
		if(pri.equals("aarectangle")) {
			return AxisAlignedRectangle.create(cmd.substring(12));
		}
		return null;
	}
}

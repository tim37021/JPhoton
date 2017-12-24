package primitive;

public class EntityFactory {
	static public Entity parseCommand(String cmd) {
		String pri = cmd.split(" ")[0];
		if(pri.equals("sphere")) {
			return Sphere.CreateSphere(cmd.substring(7));
		}
		return null;
	}
}

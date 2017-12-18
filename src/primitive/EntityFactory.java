package primitive;

public class EntityFactory {
	public Entity parseCommand(String cmd) {
		String s[] = cmd.split(" ");
		if(s[0].equals("sphere")) {
			return Sphere.CreateSphere(cmd);
		}
		return null;
	}
}

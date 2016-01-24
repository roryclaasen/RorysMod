package net.roryclaasen.rorysmod.util;

public class Arguments {

	private Arguments() {}

	public static boolean isExperiment() {
		String var = System.getProperty("rorysModExperiment");
		if (var == null) return false;
		try {
			if (Boolean.parseBoolean(var)) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}

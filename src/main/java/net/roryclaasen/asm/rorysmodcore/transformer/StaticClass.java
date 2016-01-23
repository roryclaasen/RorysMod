package net.roryclaasen.asm.rorysmodcore.transformer;

import net.roryclaasen.rorysmod.Settings;

public class StaticClass {

	private StaticClass() {}

	public static boolean shouldWakeUp() {
		return !Settings.enableStayInBed;
	}
}

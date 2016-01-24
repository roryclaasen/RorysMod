package net.roryclaasen.asm.rorysmodcore.transformer;

import java.util.Iterator;

import net.minecraft.launchwrapper.IClassTransformer;
import net.roryclaasen.rorysmod.util.RMLog;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class WorldServerTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		try {
			if (arg0.equals("js")) {
				RMLog.info("About to patch WorldServer [js]", true);
				return patchClassASM(arg0, arg2, true);
			}

			if (arg0.equals("net.minecraft.world.WorldServer")) {
				RMLog.info("About to patch WorldServer [net.minecraft.world.WorldServer]", true);
				return patchClassASM(arg0, arg2, false);
			}
		} catch (Exception e) {
			RMLog.warn("Patch failed!", true);
			e.printStackTrace();
		}

		return arg2;
	}

	public byte[] patchClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";

		// Our target method
		if (obfuscated == true) targetMethodName = "d";
		else targetMethodName = "wakeAllPlayers";

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		// Now we loop over all of the methods declared inside the World server class until we get to the targetMethodName "doExplosionB"

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode method = methods.next();
			int invok_index = -1;
			if ((method.name.equals(targetMethodName) && method.desc.equals("()V"))) {
				AbstractInsnNode currentNode = null;
				@SuppressWarnings("unused")
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = method.instructions.iterator();

				int index = -1;

				while (iter.hasNext()) {
					index++;
					currentNode = iter.next();

					if (currentNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
						RMLog.info("Found index [opcode=" + currentNode.getOpcode() + "]", true);
						targetNode = currentNode;
						invok_index = index;
						break;
					}
				}

				AbstractInsnNode p1;
				p1 = method.instructions.get(invok_index);
				MethodInsnNode p2 = new MethodInsnNode(Opcodes.INVOKESTATIC, "net/roryclaasen/asm/rorysmodcore/transformer/StaticClass", "shouldWakeUp", "()Z", false);

				method.instructions.set(p1, p2);
				RMLog.info("Finnished Patching!", true);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		return writer.toByteArray();
	}
}
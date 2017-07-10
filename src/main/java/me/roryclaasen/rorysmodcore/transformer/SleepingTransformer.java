/*
 * Copyright 2016-2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmodcore.transformer;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import me.roryclaasen.rorysmodcore.asm.ASMHelper;
import me.roryclaasen.rorysmodcore.asm.ASMNames;
import net.minecraft.launchwrapper.IClassTransformer;

public class SleepingTransformer implements IClassTransformer {
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		if ("net.minecraft.entity.player.EntityPlayer".equals(transformedName)) {
			return transformPlayer(bytes);
		}

		if ("net.minecraft.world.WorldServer".equals(transformedName)) {
			return transformWorldServer(bytes);
		}

		return bytes;
	}

	private byte[] transformPlayer(byte[] bytes) {
		ClassNode clazz = ASMHelper.createClassNode(bytes);

		MethodNode method = ASMHelper.findMethod(clazz, ASMNames.MD_PLAYER_UPDATE);

		InsnList needle = new InsnList();
		needle.add(new VarInsnNode(Opcodes.ALOAD, 0));
		needle.add(ASMHelper.getFieldInsnNode(Opcodes.GETFIELD, ASMNames.FD_PLAYER_WORLD_OBJ));
		needle.add(ASMHelper.getMethodInsnNode(Opcodes.INVOKEVIRTUAL, ASMNames.MD_WORLD_IS_DAY, false));
		LabelNode l2 = new LabelNode();
		needle.add(new JumpInsnNode(Opcodes.IFEQ, l2));

		AbstractInsnNode insertPoint = ASMHelper.findFirstNodeFromNeedle(method.instructions, needle);

		method.instructions.remove(insertPoint.getNext().getNext());
		method.instructions.set(insertPoint.getNext(), ASMHelper.getMethodInsnNode(Opcodes.INVOKESTATIC, ASMNames.MD_RM_HELPER_SLEEP_PLAEYR, false));

		return ASMHelper.createBytes(clazz, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
	}

	private static byte[] transformWorldServer(byte[] bytes) {
		ClassNode clazz = ASMHelper.createClassNode(bytes);
		MethodNode method = ASMHelper.findMethod(clazz, ASMNames.MD_WORLD_TICK);

		InsnList needle = new InsnList();
		needle.add(new VarInsnNode(Opcodes.ALOAD, 0));
		needle.add(ASMHelper.getMethodInsnNode(Opcodes.INVOKEVIRTUAL, ASMNames.MD_WAKE_ALL_PLAYERS, false));

		AbstractInsnNode aload = ASMHelper.findFirstNodeFromNeedle(method.instructions, needle);
		AbstractInsnNode call = ASMHelper.findLastNodeFromNeedle(method.instructions, needle);

		method.instructions.remove(aload);
		method.instructions.remove(call);

		return ASMHelper.createBytes(clazz, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
	}
}
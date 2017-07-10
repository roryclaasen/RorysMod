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
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
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

		// onUpdate

		{
			MethodNode method = ASMHelper.findMethod(clazz, ASMNames.MD_PLAYER_UPDATE);

			InsnList needleForJump = new InsnList();
			// needleForJump.add(new LabelNode());
			// needleForJump.add(new LineNumberNode(327, new LabelNode()));
			needleForJump.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			needleForJump.add(new VarInsnNode(Opcodes.ALOAD, 0));
			needleForJump.add(ASMHelper.getMethodInsnNode(Opcodes.INVOKESPECIAL, ASMNames.MD_ENTITY_LIVING_UPDATE, false));
			needleForJump.add(new LabelNode());

			AbstractInsnNode jumpPoint = ASMHelper.findFirstNodeFromNeedle(method.instructions, needleForJump);

			InsnList needle = new InsnList();
			needle.add(new VarInsnNode(Opcodes.ALOAD, 0));
			needle.add(ASMHelper.getFieldInsnNode(Opcodes.GETFIELD, ASMNames.FD_PLAYER_WORLD_OBJ));
			needle.add(ASMHelper.getMethodInsnNode(Opcodes.INVOKEVIRTUAL, ASMNames.MD_WORLD_IS_DAY, false));
			LabelNode l2 = new LabelNode();
			needle.add(new JumpInsnNode(Opcodes.IFEQ, l2));

			AbstractInsnNode insertPoint = ASMHelper.findLastNodeFromNeedle(method.instructions, needle);

			InsnList injectList = new InsnList();
			injectList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injectList.add(ASMHelper.getMethodInsnNode(Opcodes.INVOKESTATIC, ASMNames.MD_RM_HELPER_SLEEP_PLAEYR, false));
			injectList.add(new JumpInsnNode(Opcodes.IFEQ, (LabelNode) jumpPoint));
			method.instructions.insert(insertPoint, injectList);
		}

		// sleepInBedAt

		{
			MethodNode method = ASMHelper.findMethod(clazz, ASMNames.MD_PLAYER_SLEEP_IN_BED);

			InsnList needle = new InsnList();
			needle.add(ASMHelper.getFieldInsnNode(Opcodes.GETSTATIC, ASMNames.FD_PLAYER_ENUM_NOT_POSSIBLE));
			needle.add(new InsnNode(Opcodes.ARETURN));

			AbstractInsnNode enumNode = ASMHelper.findFirstNodeFromNeedle(method.instructions, needle);
			AbstractInsnNode returnNode = ASMHelper.findLastNodeFromNeedle(method.instructions, needle);
			method.instructions.remove(enumNode);
			method.instructions.remove(returnNode);
		}

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
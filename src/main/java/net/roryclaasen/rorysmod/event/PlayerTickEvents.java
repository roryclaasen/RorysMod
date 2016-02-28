/*
Copyright 2016 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package net.roryclaasen.rorysmod.event;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.util.UsefulFunctions;
import net.roryclaasen.rorysmod.util.VersionChecker;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerTickEvents {

	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (UsefulFunctions.isPlayer(event.entity) && event.world.isRemote) {
			if (!VersionChecker.haveWarnedVersionOutOfDate && !RorysMod.instance.checker.isLatestVersion()) {
				ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "http://gogo98901.github.io/RorysMod/");
				ChatStyle clickableChatStyle = new ChatStyle().setChatClickEvent(versionCheckChatClickEvent);
				ChatComponentText versionWarningChatComponent = new ChatComponentText(EnumChatFormatting.GOLD + "[Rory's Mod]" + EnumChatFormatting.WHITE + " There is a new version avalible!" + EnumChatFormatting.GREEN + "  Click here to update.");
				versionWarningChatComponent.setChatStyle(clickableChatStyle);
				UsefulFunctions.getPlayerFromEntity(event.entity).addChatMessage(versionWarningChatComponent);
				VersionChecker.haveWarnedVersionOutOfDate = true;
			}
		}
	}
}

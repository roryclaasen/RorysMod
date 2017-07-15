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
package me.roryclaasen.rorysmod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.Version;
import me.roryclaasen.rorysmod.util.UsefulFunctions;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class PlayerTickEvents {

	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (UsefulFunctions.isPlayer(event.entity) && event.world.isRemote) {
			
			Version version = RorysMod.instance.versionCheker;
			if (!version.haveWarnedVersionOutOfDate && !version.isLatestVersion()) {
				ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "http://rorysmod.rtfd.io");
				ChatStyle clickableChatStyle = new ChatStyle().setChatClickEvent(versionCheckChatClickEvent);
				ChatComponentText versionWarningChatComponent = new ChatComponentText(EnumChatFormatting.GOLD + "[" + RorysMod.NAME + "]" + EnumChatFormatting.WHITE + " There is a new version avalible!" + EnumChatFormatting.GREEN + " Click here to update.");
				versionWarningChatComponent.setChatStyle(clickableChatStyle);
				UsefulFunctions.getPlayerFromEntity(event.entity).addChatMessage(versionWarningChatComponent);
				version.haveWarnedVersionOutOfDate = true;
			}
		}
	}
}

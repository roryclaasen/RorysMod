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
package me.roryclaasen.rorysmod.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import me.roryclaasen.rorysmod.util.UsefulFunctions;

public class PlayerTickEvents {

	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (UsefulFunctions.isPlayer(event.entity) && event.world.isRemote) {
			// if (!VersionChecker.haveWarnedVersionOutOfDate && !RorysMod.instance.checker.isLatestVersion()) {
			// ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "http://gogo98901.github.io/RorysMod/");
			// ChatStyle clickableChatStyle = new ChatStyle().setChatClickEvent(versionCheckChatClickEvent);
			// ChatComponentText versionWarningChatComponent = new ChatComponentText(EnumChatFormatting.GOLD + "[Rory's Mod]" + EnumChatFormatting.WHITE + " There is a new version avalible!" + EnumChatFormatting.GREEN + " Click here to update.");
			// versionWarningChatComponent.setChatStyle(clickableChatStyle);
			// UsefulFunctions.getPlayerFromEntity(event.entity).addChatMessage(versionWarningChatComponent);
			// VersionChecker.haveWarnedVersionOutOfDate = true;
			// }
		}
	}
}

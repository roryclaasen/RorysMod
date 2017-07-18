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
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class PlayerTickEvents {

	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (UsefulFunctions.isPlayer(event.entity) && event.world.isRemote) {
			Version version = RorysMod.instance.versionCheker;
			if (!version.haveWarnedVersionOutOfDate && !version.isLatestVersion()) {
				ChatComponentText localChatComponentText = new ChatComponentText("");
				ChatStyle localChatStyle;
				IChatComponent modVersion = new ChatComponentText(EnumChatFormatting.AQUA + StatCollector.translateToLocal("message.rorysmod.version.current") + ": " + RorysMod.VERSION);

				IChatComponent modName = new ChatComponentText(EnumChatFormatting.GOLD + "[" + RorysMod.NAME + "]" + EnumChatFormatting.WHITE);
				localChatStyle = modName.getChatStyle();
				localChatStyle.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, modVersion));
				localChatStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://rorysmod.rtfd.io"));
				localChatComponentText.appendSibling(modName);

				IChatComponent newVersion = new ChatComponentText(" " + StatCollector.translateToLocal("message.rorysmod.version.new"));
				newVersion.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.WHITE + version.getLatest().getName())));
				localChatComponentText.appendSibling(newVersion);
				UsefulFunctions.getPlayerFromEntity(event.entity).addChatMessage(localChatComponentText);
				localChatComponentText = new ChatComponentText("");

				IChatComponent download = new ChatComponentText(EnumChatFormatting.GREEN + StatCollector.translateToLocal("message.rorysmod.download"));
				download.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, version.getLatest().getHtmlUrl())).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("message.rorysmod.version.download"))));

				IChatComponent update = new ChatComponentText("[");
				update.appendSibling(download);
				update.appendText(EnumChatFormatting.WHITE + "] " + EnumChatFormatting.GRAY + version.getLatest().getName());
				update.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, version.getLatest().getHtmlUrl()));
				localChatComponentText.appendSibling(update);
				UsefulFunctions.getPlayerFromEntity(event.entity).addChatMessage(localChatComponentText);
				version.haveWarnedVersionOutOfDate = true;
			}
		}
	}
}

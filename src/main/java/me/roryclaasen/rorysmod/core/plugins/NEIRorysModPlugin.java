package me.roryclaasen.rorysmod.core.plugins;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import me.roryclaasen.rorysmod.core.RorysMod;
import net.minecraft.item.ItemStack;

public class NEIRorysModPlugin implements IConfigureNEI {
	@Override
	public void loadConfig() {
		API.hideItem(new ItemStack(RorysMod.items.laserBolt));
	}

	@Override
	public String getName() {
		return RorysMod.NAME;
	}

	@Override
	public String getVersion() {
		return "";
	}
}

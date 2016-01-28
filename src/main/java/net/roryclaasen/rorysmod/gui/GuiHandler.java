package net.roryclaasen.rorysmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.container.ContainerRifleTable;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (ID == RorysMod.GUIS.RILE_TABLE.getId()) {
			if (tileEntity instanceof TileEntityRifleTable) {
				return new ContainerRifleTable(player.inventory, (TileEntityRifleTable) tileEntity);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (ID == RorysMod.GUIS.RILE_TABLE.getId()) {
			if (tileEntity instanceof TileEntityRifleTable) {
				return new GuiRileTable(player.inventory, (TileEntityRifleTable) tileEntity);
			}
		}
		return null;
	}
}

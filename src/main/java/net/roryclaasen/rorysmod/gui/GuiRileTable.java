package net.roryclaasen.rorysmod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.container.ContainerRifleTable;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;

import org.lwjgl.opengl.GL11;

public class GuiRileTable extends GuiContainer {

	private TileEntityRifleTable tileEntity;

	private ResourceLocation texture = new ResourceLocation(RorysMod.MODID, "textures/gui/table.png");

	public GuiRileTable(InventoryPlayer inventoryPlayer, TileEntityRifleTable tileEntity) {
		super(new ContainerRifleTable(inventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}

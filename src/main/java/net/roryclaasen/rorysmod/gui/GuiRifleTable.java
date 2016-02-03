package net.roryclaasen.rorysmod.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.roryclaasen.rorysmod.RorysMod;
import net.roryclaasen.rorysmod.container.ContainerRifleTable;
import net.roryclaasen.rorysmod.core.Settings;
import net.roryclaasen.rorysmod.entity.tile.TileEntityRifleTable;
import net.roryclaasen.rorysmod.util.ColorUtils;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.config.GuiSlider;

public class GuiRifleTable extends GuiContainer {

	private TileEntityRifleTable tileEntity;

	private ResourceLocation empty = new ResourceLocation(RorysMod.MODID, "textures/gui/table-empty.png");
	private ResourceLocation table = new ResourceLocation(RorysMod.MODID, "textures/gui/table.png");

	private GuiSlider colorR, colorG, colorB;

	public GuiRifleTable(InventoryPlayer inventoryPlayer, TileEntityRifleTable tileEntity) {
		super(new ContainerRifleTable(inventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
		this.tileEntity.setGuiRifleTable(this);

		colorR = new GuiSlider(1, -100, 0, 100, 20, "Red: ", "", 0, 255, 255, true, true);
		colorG = new GuiSlider(2, -100, 30, 100, 20, "Green: ", "", 0, 255, 0, true, true);
		colorB = new GuiSlider(3, -100, 60, 100, 20, "Blue: ", "", 0, 255, 0, true, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(colorR);
		buttonList.add(colorG);
		buttonList.add(colorB);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		if (slidersEnabled()) fontRendererObj.drawString("Laser Color", -colorR.width + 10, 10, ColorUtils.getIntFromColor(getColorFromSlider()));
		// else fontRendererObj.drawString("Laser Color", -colorR.width + 10, 10, ColorUtils.getIntFromColor(Color.GRAY));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(empty);
		if (tileEntity.hasLaser()) this.mc.renderEngine.bindTexture(table);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (Settings.showColorBox && slidersEnabled()) {
			int id = ColorUtils.loadTextureFromColour(getColorFromSlider(), 64, 64);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			this.drawTexturedModalRect(colorB.xPosition + ((colorB.width - 32) / 2), colorB.yPosition + colorB.height + 10, 0, 0, 32, 32);
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		colorR.xPosition = x - (colorR.width + 10);
		colorG.xPosition = x - (colorG.width + 10);
		colorB.xPosition = x - (colorB.width + 10);

		colorR.yPosition = y + ((ySize + (colorR.height * 3)) / 5) - 20;
		colorG.yPosition = colorR.yPosition + colorR.height + 10;
		colorB.yPosition = colorG.yPosition + colorG.height + 10;

		setSlidersEnabled(tileEntity.hasLens());
		super.updateScreen();
	}

	public void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 1 :
				tileEntity.setColor(getColorFromSlider());
			case 2 :
				tileEntity.setColor(getColorFromSlider());
			case 3 :
				tileEntity.setColor(getColorFromSlider());
				break;
			default :
				break;
		}
	}

	public boolean slidersEnabled() {
		return colorR.enabled && colorG.enabled && colorB.enabled;
	}

	public void setSlidersEnabled(boolean enable) {
		colorR.enabled = enable;
		colorG.enabled = enable;
		colorB.enabled = enable;

		colorR.visible = enable;
		colorG.visible = enable;
		colorB.visible = enable;
	}

	public Color getColorFromSlider() {
		return new Color(colorR.getValueInt(), colorG.getValueInt(), colorB.getValueInt());
	}

	public void setColorSlider(Color color) {
		colorR.setValue(color.getRed());
		colorG.setValue(color.getGreen());
		colorB.setValue(color.getBlue());
		colorR.displayString = colorR.dispString + colorR.getValueInt();
		colorG.displayString = colorG.dispString + colorG.getValueInt();
		colorB.displayString = colorB.dispString + colorB.getValueInt();
	}
}

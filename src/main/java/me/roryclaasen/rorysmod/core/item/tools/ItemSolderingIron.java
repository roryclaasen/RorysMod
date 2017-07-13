package me.roryclaasen.rorysmod.core.item.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IBoxable;
import me.roryclaasen.rorysmod.core.item.base.ItemBaseEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSolderingIron extends ItemBaseEnergyContainer implements IBoxable {

	public ItemSolderingIron(String unlocalizedName) {
		super(unlocalizedName, 100, 2);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);

		this.canRepair = false;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound.setInteger("Energy", 0);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		int energy = 0;
		if (stack.stackTagCompound.hasKey("Energy")) energy = stack.stackTagCompound.getInteger("Energy");
		tooltip.add(StatCollector.translateToLocal("message.rorysmod.charge") + ": " + energy + " / " + this.capacity + " RF");
	}

	@Override
	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}

	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemstack) {
		return false;
	}

	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	public ItemStack getContainerItem(ItemStack itemstack) {
		ItemStack stack = itemstack.copy();

		((ItemBaseEnergyContainer) stack.getItem()).use(stack, 1, false);
		return stack;
	}
}

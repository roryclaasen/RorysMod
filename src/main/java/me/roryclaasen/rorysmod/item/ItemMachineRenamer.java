package me.roryclaasen.rorysmod.item;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class ItemMachineRenamer extends ItemBlock {

	private EnergyStorage energy;
	private String renamingName;

	public ItemMachineRenamer(Block block) {
		super(block);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(new ChatComponentText("Energy Stored : " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored() + " RF"));
		list.add(new ChatComponentText("Custom Name : " + renamingName));
	}
}

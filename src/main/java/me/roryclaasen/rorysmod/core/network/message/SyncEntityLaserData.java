package me.roryclaasen.rorysmod.core.network.message;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import me.roryclaasen.rorysmod.core.network.AbstractMessage.AbstractClientMessage;
import me.roryclaasen.rorysmod.entity.EntityLaser;
import me.roryclaasen.rorysmod.util.NBTLaser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class SyncEntityLaserData extends AbstractClientMessage<SyncEntityLaserData> {

	private NBTLaser laserData;

	private int entityId;

	public SyncEntityLaserData() {}

	public SyncEntityLaserData(NBTLaser laserData, int entityId) {
		this.laserData = laserData;
		this.entityId = entityId;
		this.laserData.getTag().setInteger("entity", entityId);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		laserData = new NBTLaser(buffer.readNBTTagCompoundFromBuffer());
		entityId = laserData.getTag().getInteger("entity");
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeNBTTagCompoundToBuffer(laserData.getTag());
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		Entity entity = player.worldObj.getEntityByID(entityId);
		if (entity instanceof EntityLaser) {
			((EntityLaser) entity).setLaserData(laserData);
		}
	}
}

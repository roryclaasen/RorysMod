package me.roryclaasen.rorysmod.core.network.message;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import me.roryclaasen.rorysmod.block.tile.TileEntityRenamer;
import me.roryclaasen.rorysmod.core.network.AbstractMessage.AbstractServerMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class SyncRenamerInfo extends AbstractServerMessage<SyncRenamerInfo> {

	private int x, y, z, ticks;
	private boolean active;

	public SyncRenamerInfo() {}

	public SyncRenamerInfo(int x, int y, int z, boolean active, int ticks) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.active = active;
		this.ticks = ticks;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		active = buffer.readBoolean();
		ticks = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeBoolean(active);
		buffer.writeInt(ticks);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity entity = player.worldObj.getTileEntity(x, y, z);
		if (entity != null) {
			if (entity instanceof TileEntityRenamer) {
				TileEntityRenamer tileEntity = (TileEntityRenamer) entity;
				tileEntity.setActive(active);
				tileEntity.setTicksProcessed(ticks);
			}
		}
	}
}

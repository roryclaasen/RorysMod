package net.roryclaasen.rorysmod.block;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.BlockBed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.roryclaasen.rorysmod.Settings;

public class BlockBedAdvanced extends BlockBed {

	private final Random random = new Random();

	private final String[] NOT_POSSIBLE_NOW = new String[]{"Getting in to bed right now is pointless", "Time wont wait for no one", "Sorry but you have to wait for night"};
	private final String[] NOT_SAFE = new String[]{"There are monsters near by!"};

	public BlockBedAdvanced() {
		super();
		setHardness(0.2F);
		setBlockName("bed");
		setBlockTextureName("bed");
		disableStats();
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (p_149727_1_.isRemote) {
			return true;
		} else {
			int i1 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);

			if (!isBlockHeadOfBed(i1)) {
				int j1 = getDirection(i1);
				p_149727_2_ += field_149981_a[j1][0];
				p_149727_4_ += field_149981_a[j1][1];

				if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) != this) {
					return true;
				}

				i1 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
			}

			if (p_149727_1_.provider.canRespawnHere() && p_149727_1_.getBiomeGenForCoords(p_149727_2_, p_149727_4_) != BiomeGenBase.hell) {
				if (func_149976_c(i1)) {
					EntityPlayer entityplayer1 = null;
					Iterator iterator = p_149727_1_.playerEntities.iterator();

					while (iterator.hasNext()) {
						EntityPlayer entityplayer2 = (EntityPlayer) iterator.next();

						if (entityplayer2.isPlayerSleeping()) {
							ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

							if (chunkcoordinates.posX == p_149727_2_ && chunkcoordinates.posY == p_149727_3_ && chunkcoordinates.posZ == p_149727_4_) {
								entityplayer1 = entityplayer2;
							}
						}
					}

					if (entityplayer1 != null) {
						p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
						return true;
					}

					func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, false);
				}

				EntityPlayer.EnumStatus enumstatus = p_149727_5_.sleepInBedAt(p_149727_2_, p_149727_3_, p_149727_4_);

				if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
					if (Settings.isEnableSleepInDay()) {
						p_149727_5_.addChatMessage(new ChatComponentText(getMessage(EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)));
					} else {
						p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
						return true;
					}
				} else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
					if (Settings.isEnableMobsNearByCheck()) {
						p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
						return true;
					} else p_149727_5_.addChatMessage(new ChatComponentText(getMessage(EntityPlayer.EnumStatus.NOT_SAFE)));
				}
				func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, true);
				return true;
			} else {
				double d2 = (double) p_149727_2_ + 0.5D;
				double d0 = (double) p_149727_3_ + 0.5D;
				double d1 = (double) p_149727_4_ + 0.5D;
				p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
				int k1 = getDirection(i1);
				p_149727_2_ += field_149981_a[k1][0];
				p_149727_4_ += field_149981_a[k1][1];

				if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) == this) {
					p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
					d2 = (d2 + (double) p_149727_2_ + 0.5D) / 2.0D;
					d0 = (d0 + (double) p_149727_3_ + 0.5D) / 2.0D;
					d1 = (d1 + (double) p_149727_4_ + 0.5D) / 2.0D;
				}

				p_149727_1_.newExplosion((Entity) null, (double) ((float) p_149727_2_ + 0.5F), (double) ((float) p_149727_3_ + 0.5F), (double) ((float) p_149727_4_ + 0.5F), 5.0F, true, true);
				return true;
			}
		}
	}

	private String getMessage(EntityPlayer.EnumStatus status) {
		if (status == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
			int msg = random.nextInt(NOT_POSSIBLE_NOW.length);
			return NOT_POSSIBLE_NOW[msg];
		}
		if (status == EntityPlayer.EnumStatus.NOT_SAFE) {
			int msg = random.nextInt(NOT_SAFE.length);
			return NOT_SAFE[msg];
		}
		return "";
	}

}

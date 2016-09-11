package com.creepgaming.usefulcompass.items;

import javax.annotation.Nullable;

import com.creepgaming.usefulcompass.UsefulCompass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCompass extends Item {

	public ItemCompass() {

		this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			double rotation;
			@SideOnly(Side.CLIENT)
			double rota;
			@SideOnly(Side.CLIENT)
			long lastUpdateTick;

			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn == null && !stack.isOnItemFrame()) {
					return 0.0F;
				} else {
					boolean flag = entityIn != null;
					Entity entity = (Entity) (flag ? entityIn : stack.getItemFrame());

					if (worldIn == null) {
						worldIn = entity.worldObj;
					}

					double d0;

					if (worldIn.provider.isSurfaceWorld() && entityIn != null) {

						double d1 = flag ? (double) entity.rotationYaw
								: this.getFrameRotation((EntityItemFrame) entity);
						d1 = d1 % 360.0D;

						double d2 = this.getLocationToAngle(worldIn, entity, stack);
						d0 = Math.PI - ((d1 - 90.0D) * 0.01745329238474369D - d2);

					} else if (worldIn.provider.isSurfaceWorld()) {
						double d1 = flag ? (double) entity.rotationYaw
								: this.getFrameRotation((EntityItemFrame) entity);
						d1 = d1 % 360.0D;

						double d2 = this.getSpawnToAngle(worldIn, entity);
						d0 = Math.PI - ((d1 - 90.0D) * 0.01745329238474369D - d2);
					} else {
						d0 = Math.random() * (Math.PI * 2D);
					}

					if (flag) {
						d0 = this.wobble(worldIn, d0);
					}

					float f = (float) (d0 / (Math.PI * 2D));
					return MathHelper.positiveModulo(f, 1.0F);
				}
			}

			@SideOnly(Side.CLIENT)
			private double wobble(World p_185093_1_, double p_185093_2_) {
				if (p_185093_1_.getTotalWorldTime() != this.lastUpdateTick) {
					this.lastUpdateTick = p_185093_1_.getTotalWorldTime();
					double d0 = p_185093_2_ - this.rotation;
					d0 = d0 % (Math.PI * 2D);
					d0 = MathHelper.clamp_double(d0, -1.0D, 1.0D);
					this.rota += d0 * 0.1D;
					this.rota *= 0.8D;
					this.rotation += this.rota;
				}

				return rotation;
			}

			@SideOnly(Side.CLIENT)
			private double getFrameRotation(EntityItemFrame p_185094_1_) {
				return (double) MathHelper.clampAngle(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
			}

			@SideOnly(Side.CLIENT)
			private double getSpawnToAngle(World world, Entity entity) {
				BlockPos blockpos = world.getSpawnPoint();
				return Math.atan2((double) blockpos.getZ() - entity.posZ, (double) blockpos.getX() - entity.posX);

			}

			/*
			 * This method calculates the angle the compass needs to be pointing
			 * at so it leads to the desired location. It first checks if the
			 * stack has a tag compound at all, and then checks if the required
			 * (x & z) are present. If the first check is not passed, it
			 * defaults to the spawn location to avoid a crash.
			 */

			@SideOnly(Side.CLIENT)
			private double getLocationToAngle(World world, Entity entity, ItemStack stack) {
				if (stack.hasTagCompound() == true && stack.getTagCompound().hasKey("x")
						&& stack.getTagCompound().hasKey("z")) {

					int x = stack.getTagCompound().getInteger("x");
					int z = stack.getTagCompound().getInteger("z");

					return Math.atan2((double) z - entity.posZ, (double) x - entity.posX);

				} else {

					BlockPos blockpos = world.getSpawnPoint();

					return Math.atan2((double) blockpos.getZ() - entity.posZ, (double) blockpos.getX() - entity.posX);
				}
			}
		});
	}

	/*
	 * Pre-Rework NBT based target finding // method to write z&x coords on
	 * right click
	 * 
	 * @Override public ActionResult<ItemStack> onItemRightClick(ItemStack
	 * itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
	 *
	 * 
	 * debug code if (worldIn.isRemote && !playerIn.isSneaking()){
	 * 
	 * Boolean hasUX = itemStackIn.getTagCompound().hasUniqueId("x"); Boolean
	 * hasUZ = itemStackIn.getTagCompound().hasUniqueId("z"); Boolean hasX =
	 * itemStackIn.getTagCompound().hasKey("x"); Boolean hasZ =
	 * itemStackIn.getTagCompound().hasKey("z");
	 * 
	 * playerIn.addChatComponentMessage(new TextComponentString(
	 * "It has the following" + hasUX + hasUZ + hasX + hasZ)); return new
	 * ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	 * 
	 * }
	 * 
	 *
	 * 
	 * // remove coords if they already exist before applying new ones if
	 * (worldIn.isRemote && playerIn.isSneaking()) {
	 * 
	 * if (itemStackIn.hasTagCompound() == true &&
	 * itemStackIn.getTagCompound().hasKey("x") &&
	 * itemStackIn.getTagCompound().hasKey("z")) {
	 * itemStackIn.getTagCompound().removeTag("x");
	 * itemStackIn.getTagCompound().removeTag("z"); }
	 * 
	 * // create new NBT compound and attatch z &x coords NBTTagCompound nbtTag
	 * = new NBTTagCompound(); nbtTag.setInteger("x",
	 * playerIn.getPosition().getX()); nbtTag.setInteger("z",
	 * playerIn.getPosition().getZ());
	 * 
	 * itemStackIn.setTagCompound(nbtTag); playerIn.addChatComponentMessage(new
	 * TextComponentString("Location Stored!")); return new
	 * ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn); } return
	 * new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	 * 
	 * }
	 * 
	 */

}

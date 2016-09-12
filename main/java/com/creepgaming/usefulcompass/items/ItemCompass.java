package com.creepgaming.usefulcompass.items;

import javax.annotation.Nullable;
import com.creepgaming.usefulcompass.UsefulCompass;
import com.creepgaming.usefulcompass.helper.NbtTagHelper;
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
			 * Uses the NbtTagHelper class to get the currently selected
			 * location
			 */

			@SideOnly(Side.CLIENT)
			private double getLocationToAngle(World world, Entity entity, ItemStack stack) {
				if (NbtTagHelper.hasRequiredTags(stack)) {

					return Math.atan2((double) NbtTagHelper.getLocationZ(stack) - entity.posZ,
							(double) NbtTagHelper.getLocationX(stack) - entity.posX);

				} else {

					BlockPos blockpos = world.getSpawnPoint();

					return Math.atan2((double) blockpos.getZ() - entity.posZ, (double) blockpos.getX() - entity.posX);
				}
			}
		});
	}

	/*
	 * NBT Magic Happens here
	 * 
	 * 
	 */

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {

		if(worldIn.isRemote && playerIn.isSneaking()){
			
			playerIn.openGui(UsefulCompass.instance, 0, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
			
		}
		
		
		if (!worldIn.isRemote && !playerIn.isSneaking()) {
			NbtTagHelper.iterateSelector(itemStackIn, playerIn);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack) {

		if (entity.getEntityWorld().isRemote && entity.isSneaking()) {

		((EntityPlayer) entity).openGui(UsefulCompass.instance, 1, entity.worldObj, entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ());

		}
		

		return super.onEntitySwing(entity, stack);
	}

}
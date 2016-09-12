package com.creepgaming.usefulcompass.network.packets;

import com.creepgaming.usefulcompass.helper.ChatHelper;
import com.creepgaming.usefulcompass.helper.NbtTagHelper;
import com.creepgaming.usefulcompass.items.ItemRegister;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketInt implements IMessage {

	// A default constructor is always required
	public PacketInt() {
	}

	private int toSend;

	public PacketInt(int toSend) {
		this.toSend = toSend;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// Writes the int into the buf
	    buf.writeInt(toSend);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// Reads the int back from the buf. Note that if you have multiple
		// values, you must read in the same order you wrote.
	    toSend = buf.readInt();
	}

	public static class PacketIntHandler implements IMessageHandler<PacketInt, IMessage> {
		// Do note that the default constructor is required, but implicitly
		// defined in this case

		@Override
		public IMessage onMessage(PacketInt message, MessageContext ctx) {

			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketInt message, MessageContext ctx) {

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
			// The value that was sent
			int deleteWaypoint = message.toSend;

			if (deleteWaypoint == 1){
			if (serverPlayer.getHeldItemMainhand().getItem() != null
					&& serverPlayer.getHeldItemMainhand().getItem() == ItemRegister.itemCompass) {
				
				String locName = serverPlayer.getHeldItemMainhand().getTagCompound().getString("name" + serverPlayer.getHeldItemMainhand().getTagCompound().getInteger("Selector"));
				
				if(serverPlayer.getHeldItemMainhand().getTagCompound().hasKey("Selector")){
				
				ChatHelper.notifyLocationDelete(serverPlayer, locName);
				}
				NbtTagHelper.deleteLocation(serverPlayer.getHeldItemMainhand());


			} else if (serverPlayer.getHeldItemOffhand().getItem() != null
					&& serverPlayer.getHeldItemOffhand().getItem() == ItemRegister.itemCompass) {
				
				NbtTagHelper.deleteLocation(serverPlayer.getHeldItemOffhand());

			}
			// No response packet
		}
	}
	}
}

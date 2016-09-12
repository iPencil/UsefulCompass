package com.creepgaming.usefulcompass.network.packets;

import com.creepgaming.usefulcompass.helper.NbtTagHelper;
import com.creepgaming.usefulcompass.items.ItemRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketString implements IMessage {

	// A default constructor is always required
	public PacketString() {
	}

	private String toSend;

	public PacketString(String toSend) {
		this.toSend = toSend;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// Writes the int into the buf
		ByteBufUtils.writeUTF8String(buf, toSend);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// Reads the int back from the buf. Note that if you have multiple
		// values, you must read in the same order you wrote.
		toSend = ByteBufUtils.readUTF8String(buf);
	}
	
	public static class PacketStringHandler implements IMessageHandler<PacketString, IMessage> {
		// Do note that the default constructor is required, but implicitly
		// defined in this case

		@Override
		public IMessage onMessage(PacketString message, MessageContext ctx) {

			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));

			

			
			
			
			return null;
		}

		private void handle(PacketString message, MessageContext ctx) {

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
			// The value that was sent
			String locName = message.toSend;
			

			if (serverPlayer.getHeldItemMainhand().getItem() != null
					&& serverPlayer.getHeldItemMainhand().getItem() == ItemRegister.itemCompass) {
				NbtTagHelper.saveLocationToNbt(serverPlayer.getHeldItemMainhand(), serverPlayer.getPosition().getX(),
						serverPlayer.getPosition().getZ(), serverPlayer, locName);

			} else if (serverPlayer.getHeldItemOffhand().getItem() != null
					&& serverPlayer.getHeldItemOffhand().getItem() == ItemRegister.itemCompass) {

				NbtTagHelper.saveLocationToNbt(serverPlayer.getHeldItemOffhand(), serverPlayer.getPosition().getX(),
						serverPlayer.getPosition().getZ(), serverPlayer, locName);
			}
			// No response packet
		}
	}

}

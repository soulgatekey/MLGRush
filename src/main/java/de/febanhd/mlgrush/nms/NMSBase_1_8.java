package de.febanhd.mlgrush.nms;

import com.mojang.authlib.GameProfile;
import de.febanhd.mlgrush.game.lobby.LobbyHandler;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBanner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public class NMSBase_1_8 implements NMSBase {

    @Override
    public Entity spawnQueueEntity(EntityType entityType, Location location) {
        Entity bukkitEntity = location.getWorld().spawnEntity(location, entityType);
        net.minecraft.server.v1_8_R3.Entity entity = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity)bukkitEntity).getHandle();
        entity.setCustomNameVisible(true);
        bukkitEntity.setCustomName(LobbyHandler.queueEntityName);
        NBTTagCompound nbtTagCompound = entity.getNBTTag();
        if(nbtTagCompound == null) nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("Invulnerable", true);
        nbtTagCompound.setBoolean("Silent", true);
        nbtTagCompound.setBoolean("NoAI", true);
        return bukkitEntity;
    }

    @Override
    public void sendActionbar(Player player, String string) {
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(new ChatComponentText(string), (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    @Override
    public GameProfile getGameProfile(Player player) {
        return ((CraftPlayer)player).getProfile();
    }

    @Override
    public void setBlockData(Block block, byte data) {
        ((CraftBlock)block).setData(data, false);
    }

    @Override
    public void setUnbreakable(ItemStack stack, ItemMeta meta) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound nbt = itemStack.getTag();
        if(nbt == null) {
            nbt = new NBTTagCompound();
        }
        nbt.setBoolean("Unbreakable", true);
        itemStack.setTag(nbt);
    }
}
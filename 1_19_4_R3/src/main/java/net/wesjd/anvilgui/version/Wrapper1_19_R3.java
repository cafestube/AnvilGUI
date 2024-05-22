package net.wesjd.anvilgui.version;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.*;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class Wrapper1_19_R3 implements VersionWrapper {
    private int getRealNextContainerId(Player player) {
        return toNMS(player).nextContainerCounter();
    }

    /**
     * Turns a {@link Player} into an NMS one
     *
     * @param player The player to be converted
     * @return the NMS EntityPlayer
     */
    private ServerPlayer toNMS(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    @Override
    public int getNextContainerId(Player player, AnvilContainerWrapper container) {
        return ((AnvilContainer) container).getContainerId();
    }

    @Override
    public void handleInventoryCloseEvent(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(toNMS(player), InventoryCloseEvent.Reason.PLUGIN);
        toNMS(player).doCloseContainer();
    }

    @Override
    public void sendPacketOpenWindow(Player player, int containerId, Component inventoryTitle) {
        toNMS(player).connection.send(new ClientboundOpenScreenPacket(containerId, MenuType.ANVIL, PaperAdventure.asVanilla(inventoryTitle)));
    }

    @Override
    public void sendPacketCloseWindow(Player player, int containerId) {
        toNMS(player).connection.send(new ClientboundContainerClosePacket(containerId));
    }

    @Override
    public void setActiveContainerDefault(Player player) {
        toNMS(player).containerMenu = toNMS(player).inventoryMenu;
    }

    @Override
    public void setActiveContainer(Player player, AnvilContainerWrapper container) {
        toNMS(player).containerMenu = (AbstractContainerMenu) container;
    }

    @Override
    public void setActiveContainerId(AnvilContainerWrapper container, int containerId) {}

    @Override
    public void addActiveContainerSlotListener(AnvilContainerWrapper container, Player player) {
        toNMS(player).initMenu((AbstractContainerMenu) container);
    }

    @Override
    public AnvilContainerWrapper newContainerAnvil(Player player, Component title) {
        return new AnvilContainer(player, getRealNextContainerId(player), title);
    }

    private static class AnvilContainer extends AnvilMenu implements AnvilContainerWrapper {
        public AnvilContainer(Player player, int containerId, Component guiTitle) {
            super(
                    containerId,
                    ((CraftPlayer) player).getHandle().getInventory(),
                    ContainerLevelAccess.create(((CraftWorld) player.getWorld()).getHandle(), new BlockPos(0, 0, 0)));
            this.checkReachable = false;
            setTitle(PaperAdventure.asVanilla(guiTitle));
        }

        @Override
        public void createResult() {
            // If the output is empty copy the left input into the output
            Slot output = this.getSlot(2);
            if (!output.hasItem()) {
                output.set(this.getSlot(0).getItem().copy());
            }

            this.cost.set(0);

            // Sync to the client
            this.sendAllDataToRemote();
            this.broadcastChanges();
        }

        @Override
        public void removed(net.minecraft.world.entity.player.@NotNull Player player) {}

        @Override
        protected void clearContainer(net.minecraft.world.entity.player.@NotNull Player player, @NotNull Container inventory) {}

        public int getContainerId() {
            return this.containerId;
        }

        @Override
        public String getRenameText() {
            return this.itemName;
        }

        @Override
        public void setRenameText(String text) {
            // If an item is present in the left input slot change its hover name to the literal text.
            Slot inputLeft = this.getSlot(0);
            if (inputLeft.hasItem()) {
                inputLeft.getItem().setHoverName(net.minecraft.network.chat.Component.literal(text));
            }
        }

        @Override
        public Inventory getBukkitInventory() {
            return getBukkitView().getTopInventory();
        }
    }
}

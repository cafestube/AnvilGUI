package net.wesjd.anvilgui.version;


import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class Wrapper1_19_R2 implements VersionWrapper {
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
    public int getNextContainerId(Player player, Object container) {
        return ((AnvilContainer) container).getContainerId();
    }

    @Override
    public void handleInventoryCloseEvent(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(toNMS(player), InventoryCloseEvent.Reason.PLUGIN);
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
    public void setActiveContainer(Player player, Object container) {
        toNMS(player).containerMenu = (AbstractContainerMenu) container;
    }

    @Override
    public void setActiveContainerId(Object container, int containerId) {}

    @Override
    public void addActiveContainerSlotListener(Object container, Player player) {
        toNMS(player).initMenu((AbstractContainerMenu) container);
    }

    @Override
    public Inventory toBukkitInventory(Object container) {
        return ((AbstractContainerMenu) container).getBukkitView().getTopInventory();
    }

    @Override
    public Object newContainerAnvil(Player player, Component title) {
        return new AnvilContainer(player, getRealNextContainerId(player), title);
    }

    private static class AnvilContainer extends AnvilMenu {
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
            super.createResult();
            this.cost.set(0);

        }

        @Override
        public void removed(net.minecraft.world.entity.player.@NotNull Player player) {}

        @Override
        protected void clearContainer(net.minecraft.world.entity.player.@NotNull Player player, @NotNull Container inventory) {}

        public int getContainerId() {
            return this.containerId;
        }
    }
}

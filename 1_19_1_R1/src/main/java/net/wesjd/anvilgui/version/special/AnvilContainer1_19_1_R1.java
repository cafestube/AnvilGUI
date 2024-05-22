package net.wesjd.anvilgui.version.special;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.wesjd.anvilgui.version.VersionWrapper;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class AnvilContainer1_19_1_R1 extends AnvilMenu implements VersionWrapper.AnvilContainerWrapper {
    public AnvilContainer1_19_1_R1(Player player, int containerId, Component guiTitle) {
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

    public int getContainerId() {
        return this.containerId;
    }

    @Override
    public Inventory getBukkitInventory() {
        return getBukkitView().getTopInventory();
    }
}
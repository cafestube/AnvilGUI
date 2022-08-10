package net.wesjd.anvilgui.version.special;



import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnvilContainer1_19_1_R1 extends AnvilMenu {
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
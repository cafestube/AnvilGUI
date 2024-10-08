package net.wesjd.anvilgui.version;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Wraps versions to be able to easily use different NMS server versions
 *
 * @author Wesley Smith
 * @since 1.0
 */
public interface VersionWrapper {

    /**
     * Gets the next available NMS container id for the player
     *
     * @param player    The player to get the next container id of
     * @param container The container that a new id is being generated for
     * @return The next available NMS container id
     */
    int getNextContainerId(Player player, AnvilContainerWrapper container);

    /**
     * Closes the current inventory for the player
     *
     * @param player The player that needs their current inventory closed
     */
    void handleInventoryCloseEvent(Player player);

    /**
     * Sends PacketPlayOutOpenWindow to the player with the container id and window title
     *
     * @param player         The player to send the packet to
     * @param containerId    The container id to open
     * @param inventoryTitle The title of the inventory to be opened (only works in Minecraft 1.14 and above)
     */
    void sendPacketOpenWindow(Player player, int containerId, Component inventoryTitle);

    /**
     * Sends PacketPlayOutCloseWindow to the player with the container id
     *
     * @param player      The player to send the packet to
     * @param containerId The container id to close
     */
    void sendPacketCloseWindow(Player player, int containerId);

    /**
     * Sends PacketPlayOutExperience to the player with the experience level
     *
     * @param player          The player to send the packet to
     * @param experienceLevel The experience level to set
     */
    void sendPacketExperienceChange(Player player, int experienceLevel);

    /**
     * Sets the NMS player's active container to the default one
     *
     * @param player The player to set the active container of
     */
    void setActiveContainerDefault(Player player);

    /**
     * Sets the NMS player's active container to the one supplied
     *
     * @param player    The player to set the active container of
     * @param container The container to set as active
     */
    void setActiveContainer(Player player, AnvilContainerWrapper container);

    /**
     * Sets the supplied windowId of the supplied Container
     *
     * @param container   The container to set the windowId of
     * @param containerId The new windowId
     */
    void setActiveContainerId(AnvilContainerWrapper container, int containerId);

    /**
     * Adds a slot listener to the supplied container for the player
     *
     * @param container The container to add the slot listener to
     * @param player    The player to have as a listener
     */
    void addActiveContainerSlotListener(AnvilContainerWrapper container, Player player);

    /**
     * Creates a new ContainerAnvil
     *
     * @param player The player to get the container of
     * @param title  The title of the anvil inventory
     * @return The Container instance
     */
    AnvilContainerWrapper newContainerAnvil(Player player, Component title);

    /**
     * Interface implemented by the custom NMS AnvilContainer used to interact with it directly
     */
    interface AnvilContainerWrapper {

        /**
         * Retrieves the raw text that has been entered into the Anvil at the moment
         * <br><br>
         * This field is marked as public in the Minecraft AnvilContainer only from Minecraft 1.11 and upwards
         *
         * @return The raw text in the rename field
         */
        default String getRenameText() {
            return null;
        }

        /**
         * Sets the provided text as the literal hovername of the item in the left input slot
         *
         * @param text The text to set
         */
        default void setRenameText(String text) {}

        /**
         * Gets the {@link Inventory} wrapper of the NMS container
         *
         * @return The inventory of the NMS container
         */
        Inventory getBukkitInventory();
    }


}

package it.chiarchiaooo.commandblocker.services.messages;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

/**
 * The MessageBuilder class is responsible for building messages in Minecraft.
 * It provides methods to create single-line and multiple-line messages.
 */
public class MessageBuilder {

    private String singleLineMessage;
    private List<String> multipleLineMessage;

    /**
     * Constructs a Message with a single-line.
     * @param text The text of the single-line message.
     */
    public MessageBuilder(String text) {
        this.singleLineMessage = text;

        addPlaceHolder("%prefix%", CfgMessage.getPrefix());
        color();
    }


    /**
     * Constructs a Message with a single-line from config.
     * @param message The config key mapped to the message.
     */
    public MessageBuilder(CfgMessage message) {
        this(Objects.toString(message,""));
    }

    /**
     * Constructs an empty message with a single-line.
     */
    public MessageBuilder() {
        this("");
    }


    /**
     * Constructs a Message with multiple-line.
     * @param lines The text of the multi-line message.
     */
    public MessageBuilder(List<String> lines) {
        this.multipleLineMessage = lines;

        addPlaceHolder("%prefix%", CfgMessage.getPrefix());
        color();
    }

    public MessageBuilder(String... lines) {
        this(List.of(lines));
    }

    // Getters

    public List<String> getTextList() {
        return (multipleLineMessage != null) ? multipleLineMessage : new ArrayList<>();
    }

    public String getText() {
        return (multipleLineMessage != null) ? singleLineMessage : "";
    }

    // Check

    /**
     * Checks if the current message has a single line or multiple
     * @return If the message is multi-line or not
     */
    public boolean isMultipleLine() {
        return singleLineMessage == null && multipleLineMessage != null;
    }

    // Formatting

    /**
     * Replaces placeholders in the message with actual value.
     *
     * @param value Actual value to replace
     * @param placeholders list of aliases for a placeholder
     * @return Returns the MessageBuilder object for method chaining.
     */
    public MessageBuilder addPlaceHolder(Object value, String... placeholders) {
        if (isMultipleLine()) {
            List<String> copy = new ArrayList<>(multipleLineMessage);
            copy.replaceAll(s -> s.replaceAll(String.join("|",placeholders), Objects.toString(value)));
            multipleLineMessage = copy;

        } else singleLineMessage = singleLineMessage.replaceAll(String.join("|",placeholders), Objects.toString(value));
        return this;
    }

    /**
     * Sets the color of the message using the more common color code &.
     *
     * @return Returns the MessageBuilder object for method chaining.
     */
    public MessageBuilder color() {
        if (isMultipleLine()) {
            List<String> copy = new ArrayList<>(multipleLineMessage);
            copy.replaceAll(line -> ChatColor.translateAlternateColorCodes('&',line));
            multipleLineMessage = copy;

        } else ChatColor.translateAlternateColorCodes('&', singleLineMessage);
        return this;
    }


    // Sending

    /**
     * Sends a message to the specified player.
     *
     * @param player The player to send the message to.
     */
    public void send(Player player) {
        if (player == null) return;

        PlaceholderAPI.setPlaceholders(player,getText());
        PlaceholderAPI.setPlaceholders(player,getTextList());

        if (isMultipleLine()) multipleLineMessage.forEach(player::sendMessage);
        else player.sendMessage(singleLineMessage);
    }

    /**
     * Sends a message to the specified entity.
     *
     * @param sender The entity to send the message to.
     */
    public void send(CommandSender sender) {
        if (sender instanceof Player) send(sender);
        else if (isMultipleLine()) multipleLineMessage.forEach(sender::sendMessage);
        else sender.sendMessage(singleLineMessage);
    }

    /**
     * Sends a message to the player with the specified UUID.
     *
     * @param playerUUID The UUID of the player to send the message to.
     */
    public void send(UUID playerUUID) {
        if (playerUUID == null) return;

        OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(playerUUID);
        if (oPlayer.isOnline()) send(oPlayer.getPlayer());
    }

    public void sendConsole(Level level) {
        if (level == null) sendConsole();
        else if (isMultipleLine())
            multipleLineMessage.forEach(line -> Bukkit.getLogger().log(level,line));
        else Bukkit.getLogger().log(level,singleLineMessage);
    }

    public void sendConsole() {
        sendConsole(Level.INFO);
    }

    @Override
    public String toString() {
        return (isMultipleLine()) ? getText() : String.join("\n", getTextList());
    }
}

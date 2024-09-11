package it.chiarchiaooo.commandblocker.services.messages;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public enum CfgMessage {

    PLAYER_NOT_FOUND,
    GENERAL_ERROR,
    CANNOT_EXECUTE_ON_SELF,
    CMD_COOLDOWN_FAILED,
    INVALID_OPTION,

    BLOCKED_CMD_MESSAGE,
    CMD_ARG_NOT_FOUND_MESSAGE,
    RESET_CONFIRM_MESSAGE;

    // Metodi
    private static final CommandBlocker PLUGIN = CommandBlocker.getInstance();
    private static final ConfigurationSection CFG_ROOT = PLUGIN.getConfig().getConfigurationSection("messages");

    @Getter private static final String CMD_GROUP_PERMISSION = "cmdblock.bypass.group.<group-name>";
    @Getter private static final String CMD_GENERAL_BYPASS_PERMISSION = "cmdblock.bypass.*";
    @Getter private static final String HELP_MESSAGE = "§6&lCommandBlocker §8- Help:\n" +
        "§6Version: §f" + PLUGIN.getDescription().getVersion() + "\nAvailable commands:\n" +
        "§8» §6§o/cmdblock help §8- &fShows this list\n" +
        "§8» §6§o/cmdblock reload §8- &fReload plugin configuration.\n" +
        "§8» §6§o/cmdblock reset §8- &fResets plugin configuration.\n";


    private static String getCmdGroupPermission(String name) {
        if (name == null || name.isBlank()) return CMD_GROUP_PERMISSION;
        else return CMD_GROUP_PERMISSION.replace("<group-name>",name.toLowerCase());
    }


    private final String NOT_FOUND_MESSAGE = "&cError: Cannot find message &7"+formatName()+"&r";

    public static String getPrefix() {return PLUGIN.getConfig().getString("prefix", "Prefix >");}

    public String formatName() { return name().toLowerCase().replace("_", "-"); }

    public ConfigurationSection getSection() {
        return (CFG_ROOT != null) ? CFG_ROOT.getConfigurationSection(formatName()) : null;
    }

    public List<String> getList() {
        return (CFG_ROOT != null) ? CFG_ROOT.getStringList(formatName()) : List.of(NOT_FOUND_MESSAGE);
    }

    @Override
    public String toString() {
        return (CFG_ROOT != null) ? CFG_ROOT.getString(formatName(), NOT_FOUND_MESSAGE) : NOT_FOUND_MESSAGE;
    }
}

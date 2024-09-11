package it.chiarchiaooo.commandblocker.listeners;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.services.messages.CfgMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TabSuggestListener extends Listener implements TabCompleter {

    public TabSuggestListener(CommandBlocker main) {
        super(main);
    }

    // Tab completer for the /cmdblock command
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("help", "reload", "reset");
        return new ArrayList<>();
    }

    /**
     * Commands suggestions check on player join (rejoin after plugin reload to apply changes)
     *
     * @param event Event fired when an MC client asks the server for the list of available commands after joining the server
     */
    @EventHandler(ignoreCancelled = true)
    public void onCommandSend(final PlayerCommandSendEvent event) {
        Player p = event.getPlayer();

        if (p.hasPermission(CfgMessage.getCMD_GENERAL_BYPASS_PERMISSION())) return;


        // Removes every command suggestion
        event.getCommands().clear();

        // New command suggestion list based on the general whitelist
        List<String> allowedCommands = varService.getCmdWhitelist();

        // Adds all permission-based command to the new suggestion list
        if (varService.isPermBasedCommandEnabled())
            allowedCommands.addAll(setupSingleCmds(p));

        // Adds all the player's group commands to the new suggestion list
        if (varService.isCommandGroupsEnabled())
            allowedCommands.addAll(setGroupCmds(p));

        // Deletes every / in the commands, since they are not required in the suggestions
        allowedCommands.replaceAll(s -> s.replace("/", ""));

        // Adds back the new list
        event.getCommands().addAll(allowedCommands);
    }

    /**
     * Adds all the player's command group commands to player's suggestion list
     *
     * @param p The player to check
     */
    private List<String> setGroupCmds(Player p) {
        List<String> l = new ArrayList<>();

        for (Map.Entry<String, List<String>> groups : varService.getCmdGroupCommands().entrySet())
            if (p.hasPermission(groups.getKey())) l.addAll(groups.getValue());

        return l;
    }

    /**
     * Adds all commands the player has access to his suggestion list
     *
     * @param p The player to check
     */
    private List<String> setupSingleCmds(Player p) {
        List<String> l = new ArrayList<>(varService.getSingleCmdWhitelist());
        l.removeIf(command -> !p.hasPermission("cmdblock.bypass." + command));

        return l;
    }
}
package it.chiarchiaooo.commandblocker.listeners;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.services.messages.CfgMessage;
import it.chiarchiaooo.commandblocker.services.messages.MessageBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandListener extends Listener {

    public CommandListener(CommandBlocker main) {
        super(main);
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void CmdEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage().split(" ")[0];

        if (p.hasPermission(CfgMessage.getCMD_GENERAL_BYPASS_PERMISSION()) || varService.getCmdWhitelist().contains(cmd))
            return;

        else if (canPlayerExecute(p, cmd) || (varService.isCommandGroupsEnabled() && canPlayerExecuteGroup(p, cmd))) return;

        e.setCancelled(true);
        new MessageBuilder(CfgMessage.BLOCKED_CMD_MESSAGE).send(p);
    }

    /**
     * Get the player command group by checking if he has the group permissison
     *
     * @param p The player to check
     * @return The command group
     */

    private List<String> getGroupCmds(Player p) {
        for (Map.Entry<String, List<String>> m : varService.getCmdGroupCommands().entrySet())
            if (p.hasPermission(m.getKey())) return m.getValue();
        
        return new ArrayList<>();
    }

    /**
     * Checks to see if the player has the permission to execute that command
     *
     * @param p   The player to check
     * @param cmd The cmd the player is trying to execute
     * @return Whether the Cmd is blocked or not
     */

    private boolean canPlayerExecute(Player p, String cmd) {
        return p.hasPermission("cmdblock.bypass." + cmd);
    }

    /**
     * Checks to see if a cmd is blocked using permission groups and regex patterns
     *
     * @param p   The player trying to execute the command
     * @param cmd The cmd the player is trying to execute
     * @return Whether the Cmd is blocked or not
     */

    private boolean canPlayerExecuteGroup(Player p, String cmd) {
        if (p == null || cmd == null || cmd.isBlank()) return false;

        List<String> cmdList = getGroupCmds(p);
        if (cmd.isEmpty()) return false;

        String permList = String.join("|", cmdList);
        Matcher regexMatcher = Pattern.compile(permList).matcher(cmd);

        return regexMatcher.find();
    }
}
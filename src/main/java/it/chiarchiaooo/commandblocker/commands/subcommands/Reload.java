package it.chiarchiaooo.commandblocker.commands.subcommands;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.commands.Command;
import it.chiarchiaooo.commandblocker.services.messages.MessageBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Reload extends Command {

    public Reload(CommandBlocker main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {

        long timestampStart = System.currentTimeMillis();
        main.getLogger().info("Reloading configs...");

        main.getConfigService().reload();

        int processingTime = (int) (System.currentTimeMillis() - timestampStart);
        if (sender instanceof Player) main.getLogger().info("Config reloaded (in " + processingTime + " ms)");
        new MessageBuilder("§aConfig reloaded successfully (in " + processingTime + " ms)").send(sender);

        return false;
    }
}
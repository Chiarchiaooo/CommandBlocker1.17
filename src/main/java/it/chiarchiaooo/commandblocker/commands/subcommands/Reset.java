package it.chiarchiaooo.commandblocker.commands.subcommands;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.commands.Command;
import it.chiarchiaooo.commandblocker.services.messages.CfgMessage;
import it.chiarchiaooo.commandblocker.services.messages.MessageBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.logging.Level;

public class Reset extends Command {


    public Reset(CommandBlocker main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (args.length < 1 || !args[0].equals("CONFIRM")) {
            new MessageBuilder(CfgMessage.RESET_CONFIRM_MESSAGE).send(sender);
            return true;
        }

        long timestampStart = System.currentTimeMillis();
        main.getLogger().info("Resetting configs...");

        if (!new File(main.getDataFolder(), "config.yml").delete()) {
            new MessageBuilder("§cAn error occred while resetting the configs: Cannot delete plugin folder").sendConsole(Level.SEVERE);
            return true;
        }

        main.getConfigService().reload();
        int processingTime = (int) (System.currentTimeMillis() - timestampStart);

        if (sender instanceof Player) new MessageBuilder("Config reloaded (in " + processingTime + " ms)").sendConsole();
        new MessageBuilder("§aConfig resetted successfully (in " + processingTime + " ms)").send(sender);
        return true;
    }
}

package it.chiarchiaooo.commandblocker.commands;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.commands.subcommands.Help;
import it.chiarchiaooo.commandblocker.commands.subcommands.Reload;
import it.chiarchiaooo.commandblocker.commands.subcommands.Reset;
import it.chiarchiaooo.commandblocker.services.messages.CfgMessage;
import it.chiarchiaooo.commandblocker.services.messages.MessageBuilder;
import org.bukkit.command.CommandSender;

import java.util.Arrays;


//The class will implement CommandExecutor.
public class MainCommand extends Command {

    public MainCommand(CommandBlocker main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
        MessageBuilder message = new MessageBuilder();

        if (args.length == 0)
            message = new MessageBuilder("&6CommandBlocker &7&l- &ePlugin made by &6&lChiarchiaooo, type &6\"/cmdblocker help\" for help");

        else if (!checkPermission(sender, args[0]))
            message = new MessageBuilder(CfgMessage.BLOCKED_CMD_MESSAGE);

        else runCommand(sender, cmd, commandLabel, args);

        message.send(sender);
        return true;
    }


    private boolean checkPermission(CommandSender sender, String cmd) {
        return sender.hasPermission("cmdblock.command."+cmd) || cmd.equals("help");
    }

    private void runCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
        Command aCmd;

        switch (args[0]) {
            default -> {
                new MessageBuilder(CfgMessage.CMD_ARG_NOT_FOUND_MESSAGE).send(sender);
                return;
            }

            case "help" -> aCmd = new Help(main);
            case "reload" -> aCmd = new Reload(main);
            case "reset" -> aCmd = new Reset(main);
        }

        aCmd.onCommand(sender, cmd, commandLabel, Arrays.copyOfRange(args, 1, args.length));
    }
}

package it.chiarchiaooo.commandblocker.commands.subcommands;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.commands.Command;
import it.chiarchiaooo.commandblocker.services.messages.CfgMessage;
import it.chiarchiaooo.commandblocker.services.messages.MessageBuilder;
import org.bukkit.command.CommandSender;

public class Help extends Command {

    public Help(CommandBlocker main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
        new MessageBuilder(CfgMessage.getHELP_MESSAGE()).send(sender);
        return false;
    }
}
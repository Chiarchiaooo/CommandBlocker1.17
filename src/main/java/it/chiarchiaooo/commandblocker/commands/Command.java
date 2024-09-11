package it.chiarchiaooo.commandblocker.commands;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.services.ConfigService;
import it.chiarchiaooo.commandblocker.services.VarService;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public abstract class Command implements CommandExecutor {

    protected CommandBlocker main;
    protected VarService varService;
    protected ConfigService configService;

    public Command(CommandBlocker main) {
        this.main = main;
        this.varService = main.getVarService();
        this.configService = main.getConfigService();
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        return false;
    }
}

package it.chiarchiaooo.commandblocker.listeners;

import it.chiarchiaooo.commandblocker.CommandBlocker;
import it.chiarchiaooo.commandblocker.services.VarService;

public abstract class Listener implements org.bukkit.event.Listener {

    protected VarService varService;

    public Listener(CommandBlocker main) {
        this.varService = main.getVarService();
    }
}

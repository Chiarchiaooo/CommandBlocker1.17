package it.chiarchiaooo.commandblocker;

import it.chiarchiaooo.commandblocker.commands.MainCommand;
import it.chiarchiaooo.commandblocker.commands.subcommands.Help;
import it.chiarchiaooo.commandblocker.commands.subcommands.Reload;
import it.chiarchiaooo.commandblocker.commands.subcommands.Reset;
import it.chiarchiaooo.commandblocker.listeners.CommandListener;
import it.chiarchiaooo.commandblocker.listeners.TabSuggestListener;
import it.chiarchiaooo.commandblocker.services.ConfigService;
import it.chiarchiaooo.commandblocker.services.VarService;
import it.chiarchiaooo.commandblocker.services.messages.MessageBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

@Getter
public final class CommandBlocker extends JavaPlugin {

    private final ConsoleCommandSender CONSOLE = Bukkit.getConsoleSender();

    @Getter
    private static CommandBlocker instance;

    private ConfigService configService;
    private VarService varService;


    public void onEnable() {
        long timestampStart = System.currentTimeMillis();
        instance = this;

        new MessageBuilder(
                "",
                "§6§lCommandBlocker",
                "",
                "§eMade by §6Chiarchiaooo§7",
                "-".repeat(40),
                ""
        ).sendConsole();

        setupServices();
        CompletableFuture.supplyAsync(this::setupCommands);
        CompletableFuture.supplyAsync(this::setupEvents);


        int processingTime = (int) (System.currentTimeMillis() - timestampStart);
        new MessageBuilder(
                "",
                "§aPlugin successfully enabled (in " + processingTime + " ms)",
                "§6Remember to rate this plugin on spigotmc.org"
        ).sendConsole();
    }

    private void setupServices() {
        varService = new VarService();
        configService = new ConfigService(this);

        CONSOLE.sendMessage("Services loaded");
    }


    public boolean setupCommands() {
        getCommand("cmdblock").setExecutor(new MainCommand(this));
        getCommand("help").setExecutor(new Help(this));
        getCommand("reload").setExecutor(new Reload(this));
        getCommand("reset").setExecutor(new Reset(this));

        CONSOLE.sendMessage("Commands registered");
        return true;
    }

    public boolean setupEvents() {
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        if (varService.isTabBlockingEnabled()) // Makes sure tab blocker is enabled from config
            getServer().getPluginManager().registerEvents(new TabSuggestListener(this), this);

        CONSOLE.sendMessage("Events registered");
        return true;
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
        CONSOLE.sendMessage("§cPlugin successfully Disabled");
    }
}

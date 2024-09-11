package it.chiarchiaooo.commandblocker.services;

import it.chiarchiaooo.commandblocker.CommandBlocker;

import java.util.Objects;
import java.util.Set;

public class ConfigService {

    private final CommandBlocker main;
    private final VarService varService;


    public ConfigService(CommandBlocker main) {
        this.main = main;
        this.varService = main.getVarService();

        reload();
    }

    public void reload() {
        main.reloadConfig();
        makeConfigs();
        setBools();
        setLists();
    }

    public void makeConfigs() {
        main.getConfig().options().copyDefaults(false);
        main.saveDefaultConfig();
    }

    public void setBools() {
        varService.setTabBlockingEnabled(main.getConfig().getBoolean("toggles.tab-blocker"));
        varService.setCommandGroupsEnabled(main.getConfig().getBoolean("toggles.command-groups"));
        varService.setPermBasedCommandEnabled(main.getConfig().getBoolean("toggles.perm-based-commands"));
    }

    public void setLists() {
        varService.setSingleCmdWhitelist(main.getConfig().getStringList("single-allowed-cmds"));
        varService.setCmdWhitelist(main.getConfig().getStringList("allowed-cmds"));

        Set<String> commandGroups = Objects.requireNonNull(main.getConfig().getConfigurationSection("group")).getKeys(false);
        commandGroups.forEach(
                (group) -> varService.getCmdGroupCommands().put(main.getConfig().getString("groups." + group + ".permission"), main.getConfig().getStringList("groups." + group + ".commands"))
        );
    }
}


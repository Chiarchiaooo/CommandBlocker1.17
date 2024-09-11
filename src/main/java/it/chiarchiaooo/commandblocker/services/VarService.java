package it.chiarchiaooo.commandblocker.services;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class VarService {

    private boolean permBasedCommandEnabled;
    private boolean commandGroupsEnabled;
    private boolean tabBlockingEnabled;
    private boolean papiPresent;

    private List<String> SingleCmdWhitelist;
    private List<String> cmdWhitelist;

    // Key: group permission, value: group cmds
    private final Map<String, List<String>> CmdGroupCommands = new HashMap<>();
}

package com.minegusta.mgessentials;

import com.minegusta.mgessentials.command.*;
import org.bukkit.command.CommandExecutor;

public enum Commands {
    C1("explode", new ExplodeCommand()),
    C4("pop", new PopCommand()),
    C5("roll", new RollCommand()),
    C6("rape", new RapeCommand()),
    C7("nuke", new NukeCommand()),
    C9("fart", new FartCommand()),
    C10("disco", new DiscoCommand()),
    C11("nukearrow", new NukeArrowCommand()),
    C12("speedy", new PotionCommands()),
    C13("regen", new PotionCommands()),
    C14("haste", new PotionCommands()),
    C15("strength", new PotionCommands()),
    C16("fireresistance", new PotionCommands()),
    C17("invisibility", new PotionCommands()),
    C18("jump", new PotionCommands()),
    C19("nightvision", new PotionCommands()),
    C20("hug", new HugCommand()),
    C21("highfive", new HighFiveCommand()),
    C22("kiss", new KissCommand()),
    C23("swag", new SwagCommand()),
    C25("desc", new RenameCommand()),
    C26("rename", new RenameCommand()),
    C29("particle", new EffectCommand()),
    C30("poke", new PokeCommand()),
    C31("votepoints", new VotePointsCommand()),
    C32("vote", new VoteRedeemCommand()),
    C33("color", new ColorCommand()),
    C54("joinsound", new JoinSoundCommand()),
    C55("massmute", new MassMuteCommand()),
    C35("ghostify", new GhostCommand()),
    C36("spook", new SpookCommand()),
    C38("giveall", new GiveAllCommand()),
    STAFF("staff", new StaffCommand()),
    WARN("warn", new WarnCommand()),
    REK("rek", new RekCommand()),
    PET("pet", new KennonCommands()),
    CRAFT("craft", new CraftCommand()),
    TIME("mytime", new TimeCommand()),
    LICK("lick", new KennonCommands()),
    ENTITIES("entities", new EntitiesCommand()),
    WORLD("world", new WorldCommand()),
    HAT("hat", new HatCommand()),
    RANDOMPSPAWN("spawnme", new RandomSpawnCommand()),
    REMOVEITEMFRAMES("removeframes", new RemoveFrameCommand()),
    C34("hail", new HailCommand());


    private String commandName;
    private CommandExecutor executor;

    private Commands(String commandName, CommandExecutor executor) {
        this.commandName = commandName;
        this.executor = executor;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public String getCommandName() {
        return commandName;
    }
}

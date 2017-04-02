package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@CondAnnotation.Condition(
        name = "PlayerIsPlayingTrack",
        title = "Player is playing Track",
        desc = "Check if a player is playing a track",
        syntax = "bot %string% is playing audio",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$reset\\\":\\n" +
                "\\t\\tif bot \\\"Rawr\\\" is playing audio:\\n" +
                "\\t\\t\\treset player \\\"Rawr\\\"\\n" +
                "\\t\\t\\treply with \\\"You have successfully reset the player.\\\"")

public class CondBotIsPlayingTrack extends Condition{
    Expression<String> vBot;
    @Override
    public boolean check(Event e) {
        AudioPlayer player = EffLogin.audioPlayers.get(vBot.getSingle(e));
        if(player.getPlayingTrack()!=null){
            return true;
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        return true;
    }
}

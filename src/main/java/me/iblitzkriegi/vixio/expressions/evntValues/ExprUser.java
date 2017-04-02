package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.*;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(
        name = "eventuser",
        title = "event-user",
        desc = "Get the User out of various Vixio events",
        syntax = "[event-]user",
        returntype = User.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprUser extends SimpleExpression<User>{
    @Override
    protected User[] get(Event e) {
        return new User[]{getUser(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)
                |ScriptLoader.isCurrentEvent(EvntPrivateMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntUserStatusChange.class)
                | ScriptLoader.isCurrentEvent(EvntUserJoinVc.class)
                | ScriptLoader.isCurrentEvent(EvntUserAvatarUpdate.class)
                | ScriptLoader.isCurrentEvent(EvntGuildBan.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMessageBotSend.class)
                | ScriptLoader.isCurrentEvent(EvntUserStartStreaming.class)
                | ScriptLoader.isCurrentEvent(EvntMessageAddReaction.class)
                ){
            return true;
        }
        Skript.warning("Cannot use 'event-user' outside of discord events!");
        return false;
    }
    private static User getUser(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getEvntUser();
        }else if(e instanceof EvntPrivateMessageReceive){
            return ((EvntPrivateMessageReceive)e).getEvntUser();
        }else if(e instanceof EvntGuildMemberJoin){
            return ((EvntGuildMemberJoin)e).getEvntUser();
        }else if (e instanceof EvntGuildMemberLeave) {
            return ((EvntGuildMemberLeave) e).getEvntUser();
        }else if (e instanceof EvntUserStatusChange) {
            return ((EvntUserStatusChange) e).getEvntUser();
        }else if (e instanceof EvntUserJoinVc) {
            return ((EvntUserJoinVc) e).getEvntUser();
        }else if (e instanceof EvntUserLeaveVc) {
            return ((EvntUserLeaveVc) e).getEvntUser();
        }else if (e instanceof EvntUserAvatarUpdate) {
            return ((EvntUserAvatarUpdate) e).getEvntUser();
        }else if (e instanceof EvntGuildBan) {
            return ((EvntGuildBan) e).getEvntUser();
        }else if (e instanceof EvntGuildMessageBotSend) {
            return ((EvntGuildMessageBotSend) e).getEvntUser();
        }else if (e instanceof EvntUserStartStreaming) {
            return ((EvntUserStartStreaming) e).getEvntUser();
        }else if (e instanceof EvntMessageAddReaction) {
            return ((EvntMessageAddReaction) e).getEvntUser();
        }
        return null;
    }

}

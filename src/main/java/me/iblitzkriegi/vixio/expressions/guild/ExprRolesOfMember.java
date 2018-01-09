package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExprRolesOfMember extends SimpleExpression<Role> {
    static {
        Vixio.getInstance().registerExpression(ExprRolesOfMember.class, Role.class, ExpressionType.SIMPLE,
                "role[s] of %members% [as %bot/string%]")
                .setName("Roles of Member")
                .setDesc("Get the roles that a Member has in a Guild")
                .setExample("Coming Soon!");
    }
    Expression<Member> member;
    Expression<Object> bot;
    @Override
    protected Role[] get(Event e) {
        if(member.getAll(e) == null){
            Skript.error("You, must input a Member, to get the roles of..");
            return null;
        }
        List<Role> roles = new ArrayList<>();
        Arrays.stream(member.getAll(e))
                .filter(Objects::nonNull)
                .forEach(member -> roles.addAll(member.getRoles()));
        return roles.toArray(new Role[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "roles of " + member.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        member = (Expression<Member>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD) && member.isSingle()) {
            return new Class[]{Role[].class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        Role role = (Role) delta[0];
        if(bot.getSingle(e) == null){
            Skript.error("You must input a bot to modify roles.");
            return;
        }
        Object object = this.bot.getSingle(e);
        Member member = this.member.getSingle(e);
        if(member == null){
            Skript.error("You must input a Member to modify their roles.");
            return;
        }
        Guild guild = role.getGuild();
        try {
            switch (mode) {
                case REMOVE:
                case ADD:
                    Bot bot = Util.botFrom(object);
                    if(bot == null){
                        Skript.error("Could not parse provided bot. Please input either a %bot% or the string name you gave to your bot with the login effect!");
                        return;
                    }

                    if(delta.length == 1){
                        if(Util.botIsConnected(bot, guild.getJDA())) {
                            guild.getController().addSingleRoleToMember(member, (Role) delta[0]).queue();
                            return;
                        }
                        bot.getJDA().getGuildById(guild.getId()).getController().addSingleRoleToMember(member, (Role) delta[0]).queue();
                        return;
                    }
                    ArrayList<Role> roles = new ArrayList<>();
                    for(int i = 0; i < delta.length; i++){
                        roles.add((Role) delta[i]);
                    }
                    if(Util.botIsConnected(bot, guild.getJDA())) {
                        guild.getController().addRolesToMember(member, roles).queue();
                        return;
                    }
                    bot.getJDA().getGuildById(guild.getId()).getController().addRolesToMember(member, roles).queue();
            }
        }catch (PermissionException x){
            Skript.error("Provided bot does not have enough permission to execute the requested action.");
        }
    }
}

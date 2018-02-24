package me.iblitzkriegi.vixio.events.base;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SimpleVixioEvent<T extends net.dv8tion.jda.core.events.Event> extends Event {

    private HandlerList handlerList = new HandlerList();
    private T JDAEvent;

    public HandlerList getHandlers() {
        return handlerList;
    }

    public T getJDAEvent() {
        return JDAEvent;
    }

    public void setJDAEvent(T JDAEvent) {
        this.JDAEvent = JDAEvent;
    }

}

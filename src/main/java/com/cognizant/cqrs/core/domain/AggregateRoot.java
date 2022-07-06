package com.cognizant.cqrs.core.domain;

import com.cognizant.cqrs.core.events.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AggregateRoot {
    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());
    protected String id;
    private int version = -1;

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    protected void applyChanges(final BaseEvent event, final Boolean isNewEvent) {
        try {
            final var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (final NoSuchMethodException e) {
            logger.log(Level.WARNING, MessageFormat.format("the apply method was not found in the aggregate for {0}", event.getClass().getName()));
        } catch (final Exception e) {
            logger.log(Level.SEVERE, "error applying event to aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(final BaseEvent event) {
        applyChanges(event, true);
    }

    public void replayEvents(final Iterable<BaseEvent> events) {
        events.forEach(event -> applyChanges(event, false));
    }
}

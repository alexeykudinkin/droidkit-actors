package com.droidkit.actors;

import com.droidkit.actors.mailbox.Mailbox;

import java.util.UUID;

/**
 * INTERNAL API
 * <p/>
 * Actor Scope contains states of actor, UUID, Path, Props and Actor (if created).
 *
 * @author Stepan Ex3NDR Korshakov (me@ex3ndr.com)
 */
public class ActorScope {

    public static final int STATE_STARTING = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_SHUTDOWN = 2;

    private UUID uuid;
    private String path;
    private Props props;

    private ActorRef actorRef;
    private Mailbox mailbox;

    private int state;

    private Actor actor;

    public ActorScope(UUID uuid, String path, Props props) {
        this.uuid = uuid;
        this.path = path;
        this.props = props;
        this.state = STATE_STARTING;
    }

    public void init(Mailbox mailbox, ActorRef actorRef) {
        this.mailbox = mailbox;
        this.actorRef = actorRef;
    }

    public int getState() {
        return state;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPath() {
        return path;
    }

    public Props getProps() {
        return props;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public Actor getActor() {
        return actor;
    }

    /**
     * Create actor
     *
     * @throws Exception
     */
    public void createActor() throws Exception {
        if (state == STATE_STARTING) {
            actor = props.create();
        } else if (state == STATE_RUNNING) {
            throw new RuntimeException("Actor already created");
        } else if (state == STATE_SHUTDOWN) {
            throw new RuntimeException("Actor shutdown");
        } else {
            throw new RuntimeException("Unknown ActorScope state");
        }
    }

    /**
     * Shutdown actor
     *
     * @throws Exception
     */
    public void shutdownActor() throws Exception {
        if (state == STATE_STARTING || state == STATE_RUNNING ||
                state == STATE_SHUTDOWN) {
            actor = null;
        } else {
            throw new RuntimeException("Unknown ActorScope state");
        }
    }
}

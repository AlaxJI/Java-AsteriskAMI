/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.event;

/**
 *
 * @author alaxji
 */
public class Event implements java.io.Serializable {

    /* Modifier constants */

    /**
     * Любое событие
     */
    public static final int ANY = 0;

    /**
     * Событие при соединении с сокетом
     */
    public static final int CONNECT = 100;

    /**
     * Событие при отключении от сокета или потери связи с сокетом
     */
    public static final int DISCONNECT = 200;

    /**
     * Событие при выполнении комманды
     */
    public static final int ACTION = 300;

    /**
     * Событие при ответе на команду
     */
    public static final int RESPONSE = 400;

    /**
     * События AMI
     */
    public static final int EVENT = 500;

    /**
     * События при внутренних ошибках
     */
    public static final int ERROR = 99900;

    /**
     * The target component. This indicates the component over which the event
     * occurred or with which the event is associated. This object has been
     * replaced by AWTEvent.getSource()
     *
     * @serial
     * @see java.awt.AWTEvent#getSource()
     */
    public Object target;

    /**
     * The time stamp. Replaced by InputEvent.getWhen().
     *
     * @serial
     * @see java.awt.event.InputEvent#getWhen()
     */
    public long when;

    /**
     * Indicates which type of event the event is, and which other
     * <code>Event</code> variables are relevant for the event. This has been
     * replaced by AWTEvent.getID()
     *
     * @serial
     * @see java.awt.AWTEvent#getID()
     */
    public int id;

    /**
     * An arbitrary argument of the event. The value of this field
     * depends on the type of event.
     * <code>arg</code> has been replaced by event specific property.
     *
     * @serial
     */
    public Object arg;


    /**
     * The next event. This field is set when putting events into a linked list.
     * This has been replaced by EventQueue.
     *
     * @serial
     * @see java.awt.EventQueue
     */
    public Event evt;

    /**
     *
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is available
     * only for backwards compatibility. It has been replaced by the
     * <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Creates an instance of <code>Event</code> with the specified target
     * component, time stamp, event type, <i>x</i> and <i>y</i>
     * coordinates, keyboard key, state of the modifier keys, and argument.
     *
     * @param target the target component.
     * @param when the time stamp.
     * @param id the event type.
     */
    public Event(Object target, long when, int id, Object arg) {
        this.target = target;
        this.when = when;
        this.id = id;
        this.arg = arg;
    }

    /**
     *
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is available
     * only for backwards compatibility. It has been replaced by the
     * <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Creates an instance of <code>Event</code> with the specified target
     * component, time stamp, event type, <i>x</i> and <i>y</i>
     * coordinates, keyboard key, state of the modifier keys, and argument.
     *
     * @param target the target component.
     * @param when the time stamp.
     * @param id the event type.
     */
    public Event(Object target, int id) {
        this(target, 0, id, null);
    }

    public Event(Object target, int id, Object arg) {
        this(target, 0, id, arg);
    }

    public final static int[] getAllEvents(){
        int[] result = {ANY, CONNECT, DISCONNECT, ACTION, RESPONSE, EVENT, ERROR};
        return result;
    }
}

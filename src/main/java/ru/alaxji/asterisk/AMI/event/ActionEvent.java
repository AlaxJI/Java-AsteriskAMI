/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.event;

import java.util.EventObject;

/**
 *
 * @author alaxji
 */
public class ActionEvent extends EventObject {

    /**
     * The event's id.
     *
     * @serial
     * @see #getID()
     * @see #AWTEvent
     */
    protected int id;

    /**
     * An arbitrary argument of the event. The value of this field depends on
     * the type of event. <code>arg</code> has been replaced by event specific
     * property.
     *
     * @serial
     */
    public Object arg;

    public ActionEvent(Event event) {
        this(event.target, event.id, event.arg);
    }

    public ActionEvent(Object source, int id, Object arg) {
        super(source);
        this.id = id;
        this.arg = arg;
    }

    public ActionEvent(Object source, int id) {
        this(source, id, null);
    }

    public int getEventId() {
        return id;
    }

    public Object getArg() {
        return arg;
    }


}

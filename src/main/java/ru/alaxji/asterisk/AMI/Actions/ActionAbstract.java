/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.alaxji.asterisk.AMI.AMI;
import ru.alaxji.asterisk.AMI.AMIData;
import ru.alaxji.asterisk.AMI.Interfaces.AMIActionListener;
import ru.alaxji.asterisk.AMI.Response;
import ru.alaxji.asterisk.AMI.Interfaces.ActionInterface;
import ru.alaxji.asterisk.AMI.event.ActionEvent;
import ru.alaxji.asterisk.AMI.event.Event;

/**
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public abstract class ActionAbstract implements ActionInterface {

    private AMI ami;

    private AMIActionListener actionListener;

    protected final String actionName = "AbstactAction";

    public AMIData action;

    public AMIData response;

    private final static AtomicLong _nextActionId = new AtomicLong();

    private final String _actionId = "" + _nextActionId.incrementAndGet();

    private String command = "";

    protected int timeOut = 3000;

    @Override
    public String getActionID() {
        return this._actionId;
    }

    public ActionAbstract(AMI ami, AMIData action) {
        this.ami = ami;
        this.action = action;
        action.put("Action", getActionName());
        action.put("ActionID", getActionID());
    }

    public synchronized final String executeAction() {
        this.execute();
        return this.getActionID();
    }

    public abstract String getActionName();

    public String buildCommand() throws Exception {
        if (getActionName().equals("AbstactAction")) {
            throw new Exception(this.getClass() + ": Команда не преопределена");
        }
        this.command = "" + action;
        return this.command;
    }

    private final void write() throws IOException, Exception {
        PrintWriter writer = this.ami.getWriter();
        if (writer == null) {
            throw new Exception(this.getClass() + ": Объект вывода не найден");
        }
        else {
            writer.write(buildCommand());
            writer.flush();
        }
    }

    /*
    if (authType == null) {
            actionsPerformed(new Event(this, Event.ERROR, new Exception("Challenge: Аутентификация не задана")));
        }
        try {
            Challenge ch = new Challenge(connection, this.authType);
            //Responde r = ch.execute();
            //System.err.println("!@!"+r.getAMIData());
            ch.executeAction();
            actionsPerformed(new Event(this, Event.ACTION, ch.buildCommand()));
        }
        catch (Exception ex) {
            actionsPerformed(new Event(this, Event.ERROR, ex));
            // Logger.getLogger(AMI.class.getName()).log(Level.SEVERE, null, ex);
        }
     */
    //public
    @Override
    public final synchronized Response execute() {
        AMIActionListener l = ( e ) -> {
            AMIData amiData = (AMIData) e.getArg();
            if (amiData.get("ActionID").equals(this.getActionID())) {
                this.response = amiData.clone();
            }
        };
        ami.addActionListener(Event.RESPONSE, l);
        int code = 0;
        Exception exeption = null;
        try {
            this.write();
        }
        catch (IOException ex) {
            code = 1;
            exeption = (IOException) ex;
        }
        catch (Exception ex) {
            code = 2;
            exeption = ex;
        }
        int time = 0;
        while (this.response == null && time < this.timeOut) {
            time += 500;
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(ActionAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Response response;
        if (this.response == null && time >= this.timeOut) {
            response = new Response(code, null, new Exception("Время ожидания ответа истекло"));
        }
        else {
            response = new Response(code, this.response.clone(), exeption);
        }
        if (code == 0) {
            ami.actionsPerformed(new Event(this, Event.ACTION, response));
        }
        else {
            ami.actionsPerformed(new Event(this, Event.ERROR, response));
        }
        ami.removeActionListener(l);
        return response;
    }

}

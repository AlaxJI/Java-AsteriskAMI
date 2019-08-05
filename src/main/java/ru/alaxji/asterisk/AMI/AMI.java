/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.alaxji.asterisk.AMI.Actions.*;
import ru.alaxji.asterisk.AMI.Interfaces.AMIActionListener;
import ru.alaxji.asterisk.AMI.event.ActionEvent;
import ru.alaxji.asterisk.AMI.event.Event;

/**
 *
 * @author alaxji
 */
public class AMI {

    private LinkedHashMap<Integer, LinkedList<AMIActionListener>> listenerMap = new LinkedHashMap<>();

    private Connection connection;

    private String host;
    private int port;

    private boolean processing;

    private CompletableFuture runReader;

    public AMI() {
        this("localhost", 5038);
    }

    public AMI(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Добавляет наблюдателя для собыйтий AMI без учёта типа событий.
     *
     * @param l наблюдатель
     */
    public void addActionListener(AMIActionListener l, boolean runAsync) {
        this.addActionListener(0, l, runAsync);
    }

    /**
     * Добавляет наблюдателя для собыйтий AMI без учёта типа событий.
     *
     * @param l наблюдатель
     */
    public void addActionListener(AMIActionListener l) {
        this.addActionListener(0, l);
    }

    /**
     * Добавляет наблюдателя для собыйтий AMI с учётом типа события
     *
     * @param eventId тип события
     * @param l наблюдатель
     * @see ru.alaxji.asterisk.AMI.event.Event
     */
    public void addActionListener(int eventId, AMIActionListener l, boolean runAsync) {
        if (runAsync) {
            this.addActionListener(eventId + 1, l);
        }
        else {
            this.addActionListener(eventId, l);
        }
    }

    /**
     * Добавляет наблюдателя для собыйтий AMI с учётом типа события
     *
     * @param eventId тип события
     * @param l наблюдатель
     * @see ru.alaxji.asterisk.AMI.event.Event
     */
    public void addActionListener(int eventId, AMIActionListener l) {
        if (listenerMap.get(eventId) != null) {
            listenerMap.get(eventId).add(l);
        }
        else {
            LinkedList<AMIActionListener> listenerList = new LinkedList<>();
            listenerList.add(l);
            listenerMap.put(eventId, listenerList);
        }
    }

    public boolean removeActionListener(AMIActionListener l) {
        for (int eventID : Event.getAllEvents()) {
            if (listenerMap.get(eventID) != null) {
                for (AMIActionListener listener : listenerMap.get(eventID)) {
                    if (listener == l) {
                        listenerMap.get(eventID).remove(l);
                        return true;
                    }
                }
            }
            if (listenerMap.get(eventID + 1) != null) {
                for (AMIActionListener listener : listenerMap.get(eventID + 1)) {
                    if (listener == l) {
                        listenerMap.get(eventID + 1).remove(l);
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void actionsPerformed(Event e) {
        LinkedList<CompletableFuture<Void>> futures = new LinkedList<>();
        CompletableFuture<Void> future;
        if (listenerMap.get(1) != null) {
            for (AMIActionListener listener : listenerMap.get(1)) {
                //future =
                CompletableFuture.runAsync(() -> listener.actionPerformed(new ActionEvent(e)));
                //listener.actionPerformed(new ActionEvent(e));
            }
        }
        if (listenerMap.get(e.id + 1) != null) {
            for (AMIActionListener listener : listenerMap.get(e.id + 1)) {
                //future =
                CompletableFuture.runAsync(() -> listener.actionPerformed(new ActionEvent(e)));
                //listener.actionPerformed(new ActionEvent(e));
            }
        }
        if (listenerMap.get(0) != null) {
            for (AMIActionListener listener : listenerMap.get(0)) {
                //future =
                //CompletableFuture.runAsync(() -> listener.actionPerformed(new ActionEvent(e)));
                listener.actionPerformed(new ActionEvent(e));
            }
        }
        if (listenerMap.get(e.id) != null) {
            for (AMIActionListener listener : listenerMap.get(e.id)) {
                //future =
                //CompletableFuture.runAsync(() -> listener.actionPerformed(new ActionEvent(e)));
                listener.actionPerformed(new ActionEvent(e));
            }
        }

    }

    public void connect() throws IOException, Exception {
        this.connection = new Connection(host, port);
        actionsPerformed(new Event(this, Event.CONNECT, "Соединение с " + host + ":" + port));
    }

    public void connectAndRunReader() {
        try {
            this.connect();
        }
        catch (IOException ex) {
            this.actionsPerformed(new Event(this, Event.ERROR, ex));
            Logger.getLogger(AMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            this.actionsPerformed(new Event(this, Event.ERROR, ex));
            Logger.getLogger(AMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.connection.isConnected() && !this.connection.isClosed()) {
            try {
                runReader();
            }
            catch (Exception ex) {
                this.actionsPerformed(new Event(this, Event.ERROR, ex));
                Logger.getLogger(AMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void runReader() throws Exception {
        if (this.processing) {
            throw new Exception("Повторная попытка запустить чтение. Отклонено.");
        }
        this.processing = true;
        runReader = CompletableFuture.runAsync(
                () -> {
                    try {
                        readSocket();
                    }
                    catch (IOException ex) {
                        if (this.processing) {
                            actionsPerformed(new Event(this, Event.ERROR, ex));
                            Logger.getLogger(AMI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (this.connection.isClosed()) {
                            actionsPerformed(new Event(this, Event.DISCONNECT));
                        }
                    }
                });
    }

    public void close() throws IOException {
        this.processing = false;
        connection.close();
        actionsPerformed(new Event(this, Event.DISCONNECT,true));
    }

    private synchronized void readSocket() throws IOException {
        AMIData amiData = null;

        String line;
        String key;
        String value;
        boolean isResponse;
        boolean isEvent;

        int delimPosition = -1;

        BufferedReader reader = this.connection.getReader();

        do {
            amiData = new AMIData();
            isResponse = false;
            isEvent = false;
            // NOTE: !!!! Блокировка потока ожиданием строки в сокете
            while (!( line = reader.readLine() ).isEmpty()) {
                delimPosition = line.indexOf(": ");
                if (delimPosition == -1) {
                    key = line.trim();
                    value = "";
                }
                else {
                    key = line.substring(0, delimPosition).trim();
                    value = line.substring(delimPosition + 2).trim();
                }
                if (key.equals("Response")) {
                    isResponse = true;
                }
                if (key.equals("Event")) {
                    isEvent = true;
                }
                amiData.put(key, value);
            }
            if (isResponse) {
                actionsPerformed(new Event(this, Event.RESPONSE, amiData));
            }
            if (isEvent) {
                actionsPerformed(new Event(this, Event.EVENT, amiData));
            }
        }
        while (this.processing && connection.isConnected() && !connection.isClosed());
    }

    /**
     * Отдаёт ссылку на объект для записи в поток текущего соединения с AMI.
     *
     *
     * @return ссылка на объект для записи в поток текущего соединения с AMI.
     * Если соединение не установленно вернёт null
     * @see Connection
     * @see PrintWriter
     */
    public PrintWriter getWriter() {
        if (connection == null) {
            return null;
        }
        return connection.getWriter();
    }
}

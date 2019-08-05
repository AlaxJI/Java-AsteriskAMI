/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public class Connection {

    private static Socket socket = null;
    private static BufferedReader in = null;
    private static PrintWriter out = null;

    private final String amiVersionString;
    private final String amiVersion;

    private static String host = "localhost";
    private static int port = 5038;

    public Connection() throws IOException, Exception {
        this(host, port);
    }

    public Connection(String host, int port) throws IOException, Exception {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        this.amiVersionString = in.readLine();
        if (!this.amiVersionString.startsWith("Asterisk")) {
            throw new Exception(this.amiVersionString);
        }
        else {
            String[] verionStrings = this.amiVersionString.split("/");
            this.amiVersion = verionStrings[1];
        }
    }

    public void close() throws IOException {
        socket.close();
    }

    /**
     * Copy from Socket manifest:<br>
     * Returns the closed state of the socket.
     *
     * @return true if the socket has been closed
     * @since 1.4
     * @see #close
     */
    public boolean isClosed() {
        return socket.isClosed();
    }

    /**
     *
     * Скопировано из манифеста Socket:<br>
     * Возвращает состояние соединения сокета.
     * <p>
     * Примечание. Закрытие сокета не очищает его состояние соединения,
     * это означает, что этот метод вернет {@code true} для закрытого сокета
     * (см. {@Link #isClosed ()}), если он был успешно подключен до закрытия.
     *
     * @return true если сокет был успешно подключён к серверу
     * @since 1.4
     */
    public boolean isConnected() {
        return socket.isConnected();
    }

    public String getAMIVersion() {
        return this.amiVersion;
    }

    /**
     * Отдаёт ссылку на объект для чтения данных из потока соединения с AMI.
     *
     * @return ссылка на объект для чтения из потока.
     * @see BufferedReader
     */
    public BufferedReader getReader() {
        return this.in;
    }

    /**
     * Отдаёт ссылку на объект для записи в поток соединения с AMI.
     *
     * @return ссылка на объект для записи в поток.
     * @see PrintWriter
     */
    public PrintWriter getWriter() {
        return this.out;
    }

}

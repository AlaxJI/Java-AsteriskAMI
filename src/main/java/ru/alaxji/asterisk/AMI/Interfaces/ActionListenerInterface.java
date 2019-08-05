/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Interfaces;

import ru.alaxji.asterisk.AMI.event.Event;
import java.awt.Checkbox;

/**
 * Слушатель событий AMI
 * @author alaxji
 */
public interface ActionListenerInterface {

    /**
     * Добавляет слушателя для получения событий от  Adds the specified action listener to receive action events from
     * this button. Action events occur when a user presses or releases
     * the mouse over this button.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param         l the action listener
     * @see           #removeActionListener
     * @see           #getActionListeners
     * @see           java.awt.event.ActionListener
     * @since         JDK1.1
     */
    public void addActionListener(AMIActionListener l);
    public void addActionListener(AMIActionListener l, boolean runAsync);
    public void addActionListener(int eventId, AMIActionListener l, boolean runAsync);
    public void actionsPerformed(Event e);

}

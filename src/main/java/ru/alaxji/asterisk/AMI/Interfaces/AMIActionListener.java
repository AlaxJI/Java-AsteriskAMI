/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Interfaces;

import java.util.EventListener;
import ru.alaxji.asterisk.AMI.event.ActionEvent;
import java.awt.Button;
import ru.alaxji.asterisk.AMI.event.Event;

/**
 *
 * @author alaxji
 */
@FunctionalInterface
public interface AMIActionListener extends EventListener {

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e);

}

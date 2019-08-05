/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Interfaces;

import ru.alaxji.asterisk.AMI.Response;

/**
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public interface ActionInterface {

    public String getActionID();

    public Response execute();

}

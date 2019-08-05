/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Actions;

/**
 *
 * @author alaxji
 */
public class EventsMask {

    public final EventsMask agent     = new EventsMask(0b0000000000000001);
    public final EventsMask call      = new EventsMask(0b0000000000000010);
    public final EventsMask command   = new EventsMask(0b0000000000000100);
    public final EventsMask dtmf      = new EventsMask(0b0000000000001000);
    public final EventsMask log       = new EventsMask(0b0000000000010000);
    public final EventsMask reporting = new EventsMask(0b0000000000100000);
    public final EventsMask system    = new EventsMask(0b0000000001000000);
    public final EventsMask verbose   = new EventsMask(0b0000000010000000);
    public final EventsMask user      = new EventsMask(0b0000000100000000);
    public final EventsMask any       = new EventsMask(0b1111111111111111);


    private final int value;

    public EventsMask(int em) {
        this.value = em;
    }

}

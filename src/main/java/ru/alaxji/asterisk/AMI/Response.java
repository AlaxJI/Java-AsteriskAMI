/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI;

import ru.alaxji.asterisk.AMI.AMIData;
import java.io.IOException;

/**
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public class Response {

    private int code;
    private AMIData amiData;
    private Exception exception;

    public Response( int code, AMIData amiData )
    {
        this( code, amiData, null );
    }

    public Response( int code, AMIData amiData, Exception exception )
    {
        this.code = code;
        this.amiData = amiData;
        this.exception = exception;
    }

    public int getCode()
    {
        return this.code;
    }

    public AMIData getAMIData()
    {
        return this.amiData;
    }

    public Exception getException()
    {
        return this.exception;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Actions;

import java.awt.Color;
import ru.alaxji.asterisk.AMI.AMI;
import ru.alaxji.asterisk.AMI.AMIData;

/**
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public class Challenge extends ActionAbstract {

    private final String actionName = "Challenge";

    /**
     * Тип шифрования
     * @see AuthTypes
     */
    private AuthTypes authType = AuthTypes.none;

    public Challenge(AMI ami, AMIData action) {
        super(ami, action);
    }

    public Challenge(AMI ami) {
        this(ami, new AMIData());
    }

    public Challenge(AMI ami, AuthTypes authType) {
        this(ami);
        this.setAuthType(authType);
    }

    @Override
    public final String getActionName() {
        return this.actionName;
    }

    /**
     *
     * @param authType
     */
    public void setAuthType(AuthTypes authType) {
        if (authType == null) {
            this.action.remove("AuthType");
        }
        else {
            this.authType = authType;
            if (this.authType.equals(AuthTypes.MD5)) {
                this.action.put("AuthType", "MD5");
            }
            else {
                this.action.remove("AuthType");
            }
        }
    }

    Color color;
}

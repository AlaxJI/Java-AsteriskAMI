/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Actions;

import com.mkyong.hashing.PasswordMD5;
import ru.alaxji.asterisk.AMI.AMI;
import ru.alaxji.asterisk.AMI.Connection;
import ru.alaxji.asterisk.AMI.AMIData;

/**
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public class Login extends ActionAbstract {

    private final String actionName = "Login";

    private AuthTypes authType;

    private String user;
    private String secret;
    private String challenge;

    public Login(AMI ami, AMIData action) {
        super(ami, action);
    }

    public Login(AMI ami) {
        this(ami, new AMIData());
    }

    public Login(AMI ami, String user, String secret) {
        this(ami, user, secret, null, null);
    }

    public Login(AMI ami, String user, String secret, AuthTypes authType, String challenge) {
        this(ami);
        this.setAuthType(authType);
        this.setUser(user);
        this.setSecret(secret);
        this.setChallenge(challenge);
    }

    @Override
    public final String getActionName() {
        return this.actionName;
    }

    public final void setAuthType(AuthTypes authType) {
        if (authType == null || authType.equals(AuthTypes.none)) {
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

    public final void setUser(String user) {
        this.user = user;
        this.action.put("Username", user);
    }

    public final void setSecret(String secret) {
        this.secret = secret;
    }

    public final void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    @Override
    public final String buildCommand() throws Exception {
        if (authType != null && authType.equals(AuthTypes.MD5)) {
            if (this.challenge == null) {
                throw new Exception(this.getClass() + ": AuthType: MD5 - для данного типа аутентификации недостаточно данных");
            }
            else {
                String md5 = PasswordMD5.MD5(this.challenge + this.secret);
                this.action.remove("Secret");
                this.action.put("AuthType", "MD5");
                this.action.put("Key", md5);
            }
        }
        else {
            this.action.remove("AuthType");
            this.action.remove("Key");
            this.action.put("Secret", this.secret);
        }
        super.buildCommand();
        return "" + action;
    }

}

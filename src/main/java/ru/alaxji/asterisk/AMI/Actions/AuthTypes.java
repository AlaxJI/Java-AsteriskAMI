/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI.Actions;

/**
 * Digest algorithm to use in the challenge.
 *
 * Алгоритм дайджеста для использования в приветствии.
 *
 * @author alaxji
 */
public class AuthTypes {

    /**
     * Don`t use digest
     *
     * Не использовать дайджест
     */
    public static final AuthTypes none = new AuthTypes(0b0000000000000000);

    /**
     * Digest algorithm MD5
     *
     * Алгоритм дайджеста MD5
     */
    public static final AuthTypes MD5 = new AuthTypes(0b0000000000000001);


    int value;

    private final int[] getTypes(){
        int[] result = {none.value, MD5.value};
        return result;
    }

    private AuthTypes(int value) {
        this.value = value;
    }

    public boolean equals(AuthTypes authType){
        return (this.value == authType.value);
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AuthTypes){
            return (this.value == ((AuthTypes) obj).value);
        } else if (obj instanceof int[]) {
            return this.value == (int) obj;
        }
        return false;
    }
}

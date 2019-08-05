/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alaxji.asterisk.AMI;

import java.util.HashMap;

/**
 * Данные AMI храняться как {@literal <ключ> = <знавение>}<br>
 * <strong>ключ</strong> строка соответсвующая параметру команды<br>
 * <strong>значение</strong> строка соответсвующая значению параметра команды
 *
 * @version 0.1-SNAPSHOT
 * @author alaxji
 */
public class AMIData extends HashMap<String, String> {

    /**
     * Устанавливает параметр комманды или события
     *
     * @param paramName
     * @param paramValue
     */
    protected void setParam(String paramName, String paramValue) {
        this.put(paramName, paramValue);
    }

    /**
     * Прочитать значения параметра paramName
     *
     * @param paramName имя параметра
     * @return значение параметра
     */
    public String getParamValue(String paramName) {
        return this.get(paramName);
    }

    /**
     * Удалить параметр и его значение
     *
     * @param paramName имя параметра
     */
    protected void removeParam(String paramName) {
        this.remove(paramName);
    }

    @Override
    public String toString() {
        final String[] logString
                       = {
                    ""
                };
        this.forEach(( key, value ) -> logString[0] += key + ": " + value + "\r\n");
        logString[0] += "\r\n";

        return logString[0];
    }

    @Override
    public AMIData clone() {
        AMIData amiData;
        amiData = new AMIData();
        this.forEach(( key, value ) -> {
            amiData.put(key, value);
        });
        return amiData;
    }

}

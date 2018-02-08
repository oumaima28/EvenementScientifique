/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class SearchUtil {

    public static Date convert(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static java.sql.Date convertToSqlDate(java.util.Date date) {

        return new java.sql.Date(date.getTime());
    }
    public static java.sql.Date convertdate(java.util.Date date) {

        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp convertToSqlTimeStamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public static String addConstraint(String beanAbrev, String atributeName, String operator, Object value) {
        if (value != null) {
            return " AND " + beanAbrev + "." + atributeName + " " + operator + " '" + value + "'";
        }
        return "";
    }

    public static String addConstraintMinMax(String beanAbrev, String atributeName, Object valueMin, Object valueMax) {
        String requette = "";
        if (valueMin != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " >= '" + valueMin + "'";
        }
        if (valueMax != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " <= '" + valueMax + "'";
        }
        return requette;
    }

    public static String addConstraintDate(String beanAbrev, String atributeName, String operator, Date value) {
        return addConstraint(beanAbrev, atributeName, operator, convertToSqlDate(value));
    }

    public static String addConstraintMinMaxDate(String beanAbrev, String atributeName, Date valueMin, Date valueMax) {
        return addConstraintMinMax(beanAbrev, atributeName, convertdate(valueMin), convertdate(valueMax));
    }

    public static String supprimerCleEtranger(String beanAbrev, String atributeName, String condition, Object value) {
        String requet = "";
        if (value != null) {
            requet = "UPDATE " + beanAbrev + " SET " + atributeName + " = NULL WHERE " + condition + " = " + value;
        }
        return requet;
    }

}

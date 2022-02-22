package com.inovationware.generalmodule;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DW {

    public static String buildSelectString(String t_, ArrayList<String> select_params, ArrayList<String> where_keys, ArrayList<Object> _values, String OrderByField, InternalTypes.OrderBy order_by) {
        String v = "SELECT ";

        if (select_params != null) {
            for (int i = 0; i <= select_params.size() - 1; i++) {
                v += select_params.get(i);
                if (select_params.size() > 1 & i != select_params.size() - 1)
                    v += ", ";
            }
        } else
            v += " *";
        v += " FROM " + t_;

        if (where_keys != null) {
            if (where_keys.size() > 0) {
                v += " WHERE (";
                for (int j = 0; j <= where_keys.size() - 1; j++) {
                    v += where_keys.get(j) + "=" + toType(_values.get(j));
                    if (where_keys.size() > 1 & j != where_keys.size() - 1)
                        v += " AND ";
                }
            }
            v += ")";
        }
        if (OrderByField != null)
            v += " ORDER BY " + OrderByField;
        if (OrderByField != null & order_by != null)
            v += " " + order_by.toString();
        return v;
    }

    public static String buildInsertString(String t_, ArrayList<String> insert_keys) {
        String v = "INSERT INTO " + t_ + " (";

        for (int i = 0; i <= insert_keys.size() - 1; i++) {
            v += insert_keys.get(i);
            if (insert_keys.size() > 1 & i != insert_keys.size() - 1)
                v += ", ";
        }

        v += ") VALUES (";

        for (int j = 0; j <= insert_keys.size() - 1; j++) {
            v += "?"; // + insert_keys.get(j);
            if (insert_keys.size() > 1 & j != insert_keys.size() - 1)
                v += ", ";
        }

        v += ")";
        return v;
    }

    private static Object toType(Object value){
        if(((Object) value).getClass().getSimpleName() == "String"){
            return "''" + value.toString() + "''";
        }
        else{
            return value;
        }
    }

//    public static String buildUpdateString(String t_, ArrayList<String> update_keys, ArrayList<String> where_keys)
//    {
//        String v = "UPDATE " + t_ + " SET ";
//
//        for (int j = 0; j <= update_keys.size() - 1; j++)
//        {
//            v += update_keys.get(j) + "=@" + update_keys.get(j);
//            if (update_keys.size() > 1 & j != update_keys.size() - 1)
//                v += ", ";
//        }
//
//        if (where_keys != null)
//        {
//            if (where_keys.size() > 0)
//            {
//                v += " WHERE (";
//
//                for (int k = 0; k <= where_keys.size() - 1; k++)
//                {
//                    v += where_keys.get(k) + "=@" + where_keys.get(k);
//                    if (where_keys.size() > 1 & k != where_keys.size() - 1)
//                        v += " AND ";
//                }
//                v += ")";
//            }
//        }
//
//        return v;
//    }

    public static String buildUpdateString(String t_, ArrayList<String> update_keys, ArrayList<Object> update_values, ArrayList<String> where_keys, ArrayList<Object> where_values)
    {
        String v = "UPDATE " + t_ + " SET ";

        for (int j = 0; j <= update_keys.size() - 1; j++)
        {
            v += update_keys.get(j) + "=" + toType(update_values.get(j));
            if (update_keys.size() > 1 & j != update_keys.size() - 1)
                v += ", ";
        }

        if (where_keys != null)
        {
            if (where_keys.size() > 0)
            {
                v += " WHERE (";

                for (int k = 0; k <= where_keys.size() - 1; k++)
                {
                    v += where_keys.get(k) + "=" +toType(where_values.get(k));
                    if (where_keys.size() > 1 & k != where_keys.size() - 1)
                        v += " AND ";
                }
                v += ")";
            }
        }

        return v;
    }

}

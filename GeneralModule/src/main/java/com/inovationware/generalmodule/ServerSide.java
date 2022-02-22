package com.inovationware.generalmodule;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.inovationware.generalmodule.DW.buildInsertString;
import static com.inovationware.generalmodule.DW.buildSelectString;
import static com.inovationware.generalmodule.DW.buildUpdateString;
import static com.inovationware.generalmodule.NFunctions.joinTextFromSplits;
import static com.inovationware.generalmodule.NFunctions.splitTextInTwo;
import static com.inovationware.generalmodule.NFunctions.transformText;

public class ServerSide {
    private static String username;
    private static String password;
    private static String database;
    private static String server;

    public ServerSide(String server, String database, String username, String password) {
        this.server = server;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @SuppressLint("NewApi")
    public static Connection con() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + server + ";"
                    + "databaseName=" + database + ";user=" + username + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            //Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            //Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            //Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
//del
//    public void writeValue(String file, String text, String app_name, InternalTypes.InformationIsStored store) {
//
//        try {
//            String file_ = file;
//            app_name = GetName(app_name);
//
//            String col = GetName(file_);
//            String table = GetName(app_name) + "_" + col;
//
//            ArrayList<String> insert_keys = new ArrayList<String>();
//            insert_keys.add(col);
//
//            ArrayList<Object> kv = new ArrayList<Object>();
//            kv.add(col);
//            kv.add(text);
//
//            String query = buildInsertString(table, insert_keys);
//
//            PrepareTable(table, col);
//
//            if (rowsExist(table)) {
//                if (store == InternalTypes.InformationIsStored.OneOff) {
//                    // 'update
//                    query = buildUpdateString(table, insert_keys, null);
//                    commitSequel(query, kv);
//                } else {
//                    commitSequel(query, kv);
//                }
//            } else{
//                 commitSequel(query, kv);
//            }
//
//        } catch (Exception ex) {
//        }
//
//    }

    public Object readValue(String file_, String app_name) {
        try {
            file_ = file_;
            app_name = GetName(app_name);

            String col = GetName(file_);
            String table = GetName(app_name) + "_" + col;
            ArrayList<String> select_params = new ArrayList<String>();
            select_params.add(col);
            String query = buildSelectString(table, select_params, null, null, InternalTypes.OrderBy.ASC);
            return qData(query);
        } catch (Exception ex) {
            return false;
        }
    }


//    private void writeValue(String value) {
//        try {
//            Connection connect = ServerSide.con();
//
//            String queryStmt = "UPDATE Windowsapplication999_Settings SET Settings='" + value + "'";
//
//            PreparedStatement preparedStatement = connect
//                    .prepareStatement(queryStmt);
//
//            preparedStatement.executeUpdate();
//
//            preparedStatement.close();
//
//        } catch (SQLException e) {
//            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
//        } catch (Exception e) {
//            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
//        }
//
//    }


    private static String GetName(String str__) {
        String file__ = str__.replace("_", " ");
        ArrayList<String> str = new NFunctions().splitTextInSplits(file__, " ");
        ArrayList<String> l = new ArrayList<String>();
        for (int i = 0; i <= str.size() - 1; i++)
            l.add(transformText(str.get(i), InternalTypes.TextCase.Capitalize, " "));

        return joinTextFromSplits(l, "");
    }

//    public boolean tableExists(String table) {
//        boolean exists = false;
//        ArrayList<String> names = new ArrayList<String>();
//        try {
//            Connection connect = ServerSide.con();
//
//            ArrayList<String> select_params = new ArrayList<String>();
//            select_params.add("name");
//            String queryStmt = buildSelectString(table, select_params, null, "name", InternalTypes.OrderBy.ASC);
//
//            Statement statement = connect.createStatement();
//            ResultSet rows = statement.executeQuery(queryStmt);
//
//            if (rows != null) {
//                while (rows.next()) {
//                    names.add(rows.getString("name").toLowerCase());
//                }
//            }
//            statement.close();
//        } catch (SQLException e) {
//            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
//        } catch (Exception e) {
//            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
//        }
//
//        if (names.contains(table.toLowerCase())) {
//            exists = true;
//        }
//        return exists;
//    }

    private boolean tableHasData_WORKING(String table) {
        boolean hasData = false;
        try {
            Connection connect = ServerSide.con();

            String queryStmt = "SELECT * FROM " + table;

            Statement statement = connect.createStatement();
            ResultSet rows = statement.executeQuery(queryStmt);

            int size = 0;
            if (rows != null) {
                hasData = true;
            }

            statement.close();
        } catch (SQLException e) {
            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
        }
        return hasData;
    }

    public boolean rowsExist(String table) {
        boolean hasData = false;
        try {
            Connection connect = ServerSide.con();

            String queryStmt = "SELECT * FROM " + table;

            Statement statement = connect.createStatement();
            ResultSet rows = statement.executeQuery(queryStmt);

            int size = 0;
            if (rows != null) {
                hasData = true;
            }

            statement.close();
        } catch (SQLException e) {
            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
        }
        return hasData;
    }

    public boolean tableExists(String table) {
        return rowsExist(table);
    }

    private void PrepareTable(String table, String col) {
        try {
            if (rowsExist(table) == false) {
                // 'create table
                String sql = "CREATE TABLE [" + database + "].[dbo].[" + table + "_" + col + "]([RecordSerial] [int] IDENTITY(1,1) NOT NULL, [" + col + "] [nvarchar](max) NULL)";

                Connection connect = ServerSide.con();

                String queryStmt = sql;

                PreparedStatement preparedStatement = connect
                        .prepareStatement(queryStmt);

                preparedStatement.executeUpdate();

                preparedStatement.close();
            }
        } catch (Exception ex) {
        }
    }


    public String commitSequel(String query) {
        String result = "false";

        try {
            Connection connect = ServerSide.con();

            Statement statement = connect.createStatement();
            int rows = statement.executeUpdate(query);

            if (rows > 0) {
                result = "true";
            }

            statement.close();
            connect.close(); //new

        } catch (SQLException e) {
            result=("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            result=("Exception\n\n" + e.getMessage().toString());
        }
        return result;
    }

    public String qData(String query) {
        String result = null;
        try {
            Connection connect = ServerSide.con();

            Statement statement = connect.createStatement();
            ResultSet rows = statement.executeQuery(query);

            if (rows != null) {
                while (rows.next()) {
                    result = rows.getString(col_from_query(query));
                }
            }
            statement.close();
            connect.close(); //new
        } catch (SQLException e) {
            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
        }

        return result;

    }

    private String col_from_query(String query) {
        String right = splitTextInTwo(query, " ", InternalTypes.SideToReturn.Right).trim();
        String first = splitTextInTwo(right, " ", InternalTypes.SideToReturn.Left).trim();
        return first;
    }

    //not working
//    public String commitSequel(String query, ArrayList<Object> _values) {
//        String result = "false";
//
//        try {
//            Connection connect = ServerSide.con();
//
//            PreparedStatement statement = connect.prepareStatement(query);
//
//            if (_values.size() > 0) {
//                for (int i = 0; i < _values.size(); i++) {
//                    statement.setObject(i + 1, _values.get(i));
//                }
//            }
//
//            int rows = statement.executeUpdate();
//
//            if (rows > 0) {
//                result = "true";
//            }
//
//            statement.close();
//            connect.close(); //new
//
//        } catch (SQLException e) {
//            result=("SQLException\n\n" + e.getMessage().toString());
//        } catch (Exception e) {
//            result=("Exception\n\n" + e.getMessage().toString());
//        }
//        return result;
//    }


    public static String buildSelectString(String t_, ArrayList<String> select_params, ArrayList<String> where_keys, String OrderByField, InternalTypes.OrderBy order_by) {
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
                    v += where_keys.get(j) + "=@" + where_keys.get(j);
                    if (where_keys.size() > 1 & j != where_keys.size() - 1)
                        v += " AND ";
                }
            }
            v += ")";
        }
        if (OrderByField != null)
            v += " ORDER BY " + OrderByField;
        if (OrderByField != null)
            v += " " + order_by.toString();
        return v;
    }


    public static String buildUpdateString(String t_, ArrayList<String> update_keys, ArrayList<String> where_keys) {
        String v = "UPDATE " + t_ + " SET ";

        for (int j = 0; j <= update_keys.size() - 1; j++) {
            v += update_keys.get(j) + "=@" + update_keys.get(j);
            if (update_keys.size() > 1 & j != update_keys.size() - 1)
                v += ", ";
        }

        if (where_keys != null) {
            if (where_keys.size() > 0) {
                v += " WHERE (";

                for (int k = 0; k <= where_keys.size() - 1; k++) {
                    v += where_keys.get(k) + "=@" + where_keys.get(k);
                    if (where_keys.size() > 1 & k != where_keys.size() - 1)
                        v += " AND ";
                }
                v += ")";
            }
        }

        return v;
    }

}

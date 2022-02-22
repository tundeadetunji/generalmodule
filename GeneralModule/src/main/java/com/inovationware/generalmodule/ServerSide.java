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

    public Object readValue(String file_, String app_name) {
        String reply = "false";
        try {
            //file_ = GetName(file_);
            app_name = GetName(app_name);

            String col = GetName(file_);
            String table = GetName(app_name) + "_" + col;
            ArrayList<String> select_params = new ArrayList<String>();
            select_params.add(col);
            String query = new DW().buildSelectString(table, select_params, null, null, null, null);
            reply= (String) qData(query);
        } catch (Exception ex) {
            reply = ex.getMessage();
        }
        return reply;
    }


    public String writeValue(String file_, String txt_, String app_name, InternalTypes.InformationIsStored store) {
        String reply = "false";
        try {
            app_name = GetName(app_name);
            String col = GetName(file_);
            String table = GetName(app_name) + "_" + col;
            ArrayList<String> insert_keys = new ArrayList<>();
            insert_keys.add(col);
            ArrayList<Object> insert_values = new ArrayList<>();
            insert_values.add(txt_);
            String query = new DW().buildInsertString(table, insert_keys);

            PrepareTable(table, col);

                if (store == InternalTypes.InformationIsStored.OneOff) {
                    // 'update
                    query = new DW().buildUpdateString(table, insert_keys, null, null);

                    //w reply = commitSequel(query, insert_values);
                    commitSequel(query, insert_values);
                    reply = "true";
                } else
                    // 'create

                    //wreply = commitSequel(query, insert_values);
                    commitSequel(query, insert_values);
                    reply = "true";

        } catch (Exception ex) {
            reply= ex.getMessage();
        }
        return reply;
    }


    //working
//    public Object readValue(String file_, String app_name) {
//        try {
//            file_ = file_;
//            app_name = GetName(app_name);
//
//            String col = GetName(file_);
//            String table = GetName(app_name) + "_" + col;
//            ArrayList<String> select_params = new ArrayList<String>();
//            select_params.add(col);
//            String query = buildSelectString(table, select_params, null, null, InternalTypes.OrderBy.ASC);
//            return qData(query);
//        } catch (Exception ex) {
//            return false;
//        }
//    }


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

//    private boolean tableHasData_WORKING(String table) {
//        boolean hasData = false;
//        try {
//            Connection connect = ServerSide.con();
//
//            String queryStmt = "SELECT * FROM " + table;
//
//            Statement statement = connect.createStatement();
//            ResultSet rows = statement.executeQuery(queryStmt);
//
//            int size = 0;
//            if (rows != null) {
//                hasData = true;
//            }
//
//            statement.close();
//        } catch (SQLException e) {
//            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
//        } catch (Exception e) {
//            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
//        }
//        return hasData;
//    }

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
                connect.close();
            }
        } catch (Exception ex) {
        }
    }

    public Object qData(String query) {
        Object result = null;

        try {
            Connection connect = ServerSide.con();

            Statement statement = connect.createStatement();

            ResultSet rows = statement.executeQuery(query);

            if (rows != null) {
                while (rows.next()) {
                    result = rows.getObject(col_from_query(query));
                }
            }
            statement.close();
            connect.close(); //new
        } catch (SQLException e) {
            //result = ("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //result=("Exception\n\n" + e.getMessage().toString());
        }

        return result;

    }

    private String col_from_query(String query) {
        String right = splitTextInTwo(query, " ", InternalTypes.SideToReturn.Right).trim();
        String first = splitTextInTwo(right, " ", InternalTypes.SideToReturn.Left).trim();
        return first;
    }

    public boolean commitSequel(String query, ArrayList<Object> insert_or_update_values) {
        boolean result = false;
        ArrayList<Object> _values = insert_or_update_values;
        try {
            Connection connect = ServerSide.con();

            PreparedStatement statement = connect.prepareStatement(query);

            if (_values != null) {
                if (_values.size() > 0) {
                    for (int i = 0; i < _values.size(); i++) {
                        statement.setObject(i + 1, _values.get(i));
                    }
                }
            }

            int rows = statement.executeUpdate();

            if (rows > 0) {
                result = true;
            }

            statement.close();
            connect.close(); //new

        } catch (SQLException e) {
            //result=("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //result=("Exception\n\n" + e.getMessage().toString());
        }
        return result;
    }


}

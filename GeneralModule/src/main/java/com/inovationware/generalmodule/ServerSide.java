package com.inovationware.generalmodule;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerSide {
    private static String username;
    private static String password;
    private static String database;
    private static String server;

    public ServerSide(String server, String database, String username, String password) {
        this.server = server;
        this.database = database;
        this.username = username;
        this.password=password;
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

    public void writeValue(String value){
        try {
//            ServerSide serverSideConnect = new ServerSide(getString(R.string._user), getString(R.string._pass), getString(R.string._DB), getString(R.string._server));
            Connection connect = ServerSide.con();

            String queryStmt = "UPDATE Windowsapplication999_Settings SET Settings='" + value + "'";

            PreparedStatement preparedStatement = connect
                    .prepareStatement(queryStmt);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
        }

    }

    public Object readValue(){
        Object settings = null;
        try {
            Connection connect = ServerSide.con();

            String queryStmt = "SELECT Settings FROM Windowsapplication999_Settings ORDER BY RecordSerial ASC";

            Statement statement = connect.createStatement();
            ResultSet rows = statement.executeQuery(queryStmt);

            while(rows.next()){
                settings = rows.getString("Settings");
            }
            statement.close();
        }
        catch (SQLException e) {
            //textDetails.setText("SQLException\n\n" + e.getMessage().toString());
        } catch (Exception e) {
            //textDetails.setText("Exception\n\n" + e.getMessage().toString());
        }

        return settings;

    }

}

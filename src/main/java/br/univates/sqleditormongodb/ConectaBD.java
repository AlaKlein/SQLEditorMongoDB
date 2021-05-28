/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.sqleditormongodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;

/**
 *
 * @author ala.klein
 */
public class ConectaBD {

    ResultSet resultadoQ = null;

    public long consulta() {
        long start = System.nanoTime();

        try {

            ConnectionString connString = new ConnectionString(
                    "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
            );
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .retryWrites(true)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("parlamentares");
            System.out.println("Database = " + database);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Connection con = null;
//        String driver = "org.mariadb.jdbc.Driver";
//        String url = "jdbc:mariadb://" + ConectaSSH.rhost + ":" + ConectaSSH.lport + "/";
//        //String db = "classicmodels";
//        String db = "parlamentares";
//        String dbUser = "admindb";
//        String dbPasswd = "2021A";
//        try {
//            Class.forName(driver);
//            con = DriverManager.getConnection(url + db, dbUser, dbPasswd);
//            try {
//                Statement st = con.createStatement();
//                resultadoQ = st.executeQuery(query);
//
//                new TableModel().resultSetToTableModel(resultadoQ, tabela);
//                con.close();
//
//            } catch (SQLException s) {
//                JOptionPane.showMessageDialog(null, "SQL statement is not executed!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        long end = System.nanoTime();

        long time = (end - start) / 1000000;

        return time;
    }
}

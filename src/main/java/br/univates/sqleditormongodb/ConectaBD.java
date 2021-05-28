/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.sqleditormongodb;

import com.mongodb.BasicDBObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.mongodb.ConnectionString;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.Document;

/**
 *
 * @author ala.klein
 */
public class ConectaBD {

    ResultSet resultadoQ = null;

    public void consulta() {
        long start = System.nanoTime();

        ConnectionString connString = new ConnectionString(
                "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("parlamentares");
        MongoCollection<Document> collection = database.getCollection("fornecedores");
        //Retrieving the documents
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

            long end = System.nanoTime();

            long time = (end - start) / 1000000;

        }
    }
}

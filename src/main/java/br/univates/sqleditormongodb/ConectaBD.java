/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.sqleditormongodb;

import java.sql.ResultSet;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;

/**
 *
 * @author ala.klein
 */
public class ConectaBD {

    ResultSet resultadoQ = null;
    Document d = null;

    public Document consulta(String query, String colecao) {
        long start = System.nanoTime();

        //db.nomes_parlamentares.find({“estado”: “RS”},{“nome”: 1, “estado”: 1, “partido”: 1}).sort({“nome”: 1})
        ConnectionString connString = new ConnectionString(
                "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("parlamentares");
        MongoCollection<Document> collection = database.getCollection(colecao);

        //MongoCollection<Document> collection = database.runCommand( Document.parse(json) );
        //Retrieving the documents
        FindIterable<Document> iterDoc = collection.find();
        Iterator<Document> it = iterDoc.iterator();
        while (it.hasNext()) {

            d = it.next();

            print(d);

            long end = System.nanoTime();

            long time = (end - start) / 1000000;

        }
        return d;
    }

    public void print(Document d) {
        System.out.println("{");
        for (Entry<String, Object> e : d.entrySet()) {
            System.out.println("\t" + e.getKey() + ":" + e.getValue().toString());
        }
        System.out.println("}");
    }
}

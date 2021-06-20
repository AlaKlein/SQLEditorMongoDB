/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.sqleditormongodb;

import com.mongodb.BasicDBObject;
import java.sql.ResultSet;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTextArea;

import org.bson.Document;

/**
 *
 * @author ala.klein
 */
public class ConectaBD {

    ResultSet resultadoQ = null;
    long start;
    long end;
    long time;

    public long consulta(String query, String colecao, JTextArea area) {
        start = System.nanoTime();

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

        //Retrieving the documents
        //FindIterable<Document> iterDoc = collection.find(gt("estado", "RS"));
        
        FindIterable<Document> iterDoc = collection.find();
        Iterator<Document> it = iterDoc.iterator();
        area.setText("");
        
        while (it.hasNext()) {

            Document d = it.next();

            print(d, area);

        }
        end = System.nanoTime();
        time = (end - start) / 1000000;
        return time;
    }

    public void print(Document d, JTextArea area) {
        area.append("{\n");

        for (Entry<String, Object> e : d.entrySet()) {
            area.append("\t" + e.getKey() + ":" + e.getValue().toString() + "\n");
        }
        area.append("}\n");
    }
}

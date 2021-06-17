/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.sqleditormongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

/**
 *
 * @author Klein
 */
public class main {

    public static void main(String[] args) {
        try {
            ConectaSSH.go();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String connectionString = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase db = mongoClient.getDatabase("parlamentares");
            MongoCollection<Document> nomes_parlamentares = db.getCollection("nomes_parlamentares");
            threeMostPopulatedCitiesInTexas(nomes_parlamentares);
        }
    }

//db.nomes_parlamentares.aggregate([ 
//{$match: {estado : { $in: ['SP','RS']}}}, 
//{$project: {nome: 1, estado: 1, partido: 1}}, 
//{$sort: {estado: 1, nome: 1}} 
//]);
    /**
     * find the 3 most densely populated cities in Texas.
     *
     * @param zips sample_training.zips collection from the MongoDB Sample
     * Dataset in MongoDB Atlas.
     */
    private static void threeMostPopulatedCitiesInTexas(MongoCollection<Document> parlamentares) {
        Bson match = match(eq("estado", "RS"));
//        //Bson group = group("$city", sum("totalPop", "$pop"));
        Bson project = project(fields(excludeId(), include("totalPop"), computed("city", "$_id")));
//       Bson project = project(fields(excludeId(), include("totalPop"), computed("city", "$_id")));
//        Bson sort = sort(descending("estado: 1, nome: 1"));
//        Bson limit = limit(3);

        List<Document> results = parlamentares.aggregate(Arrays.asList(match, project))
                .into(new ArrayList<>());
        System.out.println("==> 3 most densely populated cities in Texas");
        results.forEach(printDocuments());
    }

    private static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }
}

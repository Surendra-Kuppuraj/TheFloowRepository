package com.thefloow.presistance;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @author surendrakuppuraj
 *
 */
public class WordCountDAOImpl implements WordCountDAO<Map<String, Integer>> {

	private static final String DATABASENAME = "THEFLOOWDB";
	private static final String TABLE_NAME = "WordCount";
	private final String hostName;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public WordCountDAOImpl(String hostName) {
		this.hostName = hostName;
	}

	@Override
	public Map<String, Integer> getAll() {
		MongoClient mongoClient = null;
		Map<String, Integer> queriedResult = null;
		try {
			mongoClient = getDBConnection();
			queriedResult = new TreeMap<>();
			MongoCursor<Document> dbObject = collection.find().iterator();
			while (dbObject.hasNext()) {
				Document doc = dbObject.next();
				queriedResult.put(doc.getString("word"), doc.getInteger("count"));
			}
		} catch (UnknownHostException ex) {
			System.err.println("Database Connnection cannot be established: " + ex);
		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
		return queriedResult;
	}

	@Override
	public void create(Map<String, Integer> words) {
		MongoClient mongoClient = null;
		try {
			mongoClient = getDBConnection();
			collection.drop();
			words.forEach((k, v) -> {
				collection.insertOne(new Document().append("word", k).append("count", v));
			});
		} catch (UnknownHostException ex) {
			System.err.println("Database Connnection cannot be established: " + ex);
		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	private MongoClient getDBConnection() throws UnknownHostException {
		mongoClient = new MongoClient(hostName);
		database = mongoClient.getDatabase(DATABASENAME);
		collection = database.getCollection(TABLE_NAME);
		return mongoClient;

	}
}

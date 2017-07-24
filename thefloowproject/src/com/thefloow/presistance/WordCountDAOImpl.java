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
	private static final String WORD_COLUMN_NAME = "word";
	private static final String COUNT_COLUMN_NAME = "count";

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
		final Map<String, Integer> queriedResult = new TreeMap<>();
		try {
			mongoClient = getDBConnection();
			MongoCursor<Document> dbObject = collection.find().iterator();
			dbObject.forEachRemaining(document -> { 
				queriedResult.put(document.getString(WORD_COLUMN_NAME), document.getInteger(COUNT_COLUMN_NAME));});
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
			words.forEach((k, v) -> {
				collection.insertOne(new Document().append(WORD_COLUMN_NAME, k).append(COUNT_COLUMN_NAME, v));
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

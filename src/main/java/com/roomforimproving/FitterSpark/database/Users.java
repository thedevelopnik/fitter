package com.roomforimproving.FitterSpark.database;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

/*
 * Created by davidsudia on 5/4/16.
 */
public class Users {
    // create new mongoClient
    public static MongoClient mongoClient = new MongoClient();

    // create access to database
    public static MongoDatabase db = mongoClient.getDatabase("fitter");

    // adds one user to the db
    public void insertUser(User newUser) {

        // set user's apiKey
        newUser.setApiKey(UUID.randomUUID().toString());

        // hash password for secure storage
        String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(10));

        String email = newUser.getEmail();
        String firstname= newUser.getFirstname();
        String lastname = newUser.getLastname();
        String apiKey = newUser.getApiKey();

        // insert user into database
        db.getCollection("users").insertOne(
                new Document()
                        .append("email", email)
                        .append("firstname", firstname)
                        .append("lastname", lastname)
                        .append("password", hashed)
                        .append("apiKey", apiKey)
        );
    }

    // finds a user in db by email and returns details
    public User findUser(String email) {
        // retrieve iterable of documents
        FindIterable<Document> iterable = db.getCollection("users").find(
                new Document("email", email));

        // establish memory pointer for desired document
        final Document[] user = new Document[1];

        // set document at memory pointer
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                user[0] = document;
            }
        });

        // set user info from document
        String userEmail = user[0].getString("email");
        String userFirstName = user[0].getString("firstname");
        String userLastName = user[0].getString("lastname");
        String userApiKey = user[0].getString("apiKey");
        String hashedPass = user[0].getString("password");

        User foundUser = new User(null, userEmail, userFirstName, userLastName, hashedPass, userApiKey);
        return foundUser;
    }

    // finds a user in db by email and returns details
    public static Boolean findValidApiKey(String apiKey) {
        // retrieve iterable of documents
        FindIterable<Document> iterable = db.getCollection("users").find(
                new Document("apiKey", apiKey));

        // establish memory pointer for desired document
        final Document[] user = new Document[1];

        // set document at memory pointer
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                user[0] = document;
            }
        });

        return (user[0] != null);
    }


}

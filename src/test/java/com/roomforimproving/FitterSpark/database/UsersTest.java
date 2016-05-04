package com.roomforimproving.FitterSpark.database;

import org.bson.Document;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/*
 * Created by davidsudia on 5/4/16.
 */
public class UsersTest {

    private Users users = new Users();

    @BeforeMethod
    public void setUp() throws Exception {
        users.db.getCollection("fitter").deleteMany(new Document());
        users.db.getCollection("users").insertOne(
                new Document()
                        .append("email", "dsudia@gmail.com")
                        .append("firstname", "David")
                        .append("lastname", "Sudia")
                        .append("password", "abc123")
                        .append("apiKey", UUID.randomUUID().toString())
        );
    }

    @Test
    public void findUserFindsCorrectUser() throws Exception {
        User foundUser = users.findUser("dsudia@gmail.com");

        assertEquals("email is the same", "dsudia@gmail.com", foundUser.getEmail());
        assertEquals("firstname is the same", "David", foundUser.getFirstname());
        assertEquals("lastname is the same", "Sudia", foundUser.getLastname());
    }

    @Test
    public void insertUserWorksCorrectly() throws Exception {
        User newUser = new User(null, "ben@gmail.com", "Ben", "Hernandez", "abc123", null);

        users.insertUser(newUser);

        User foundUser = users.findUser("ben@gmail.com");

        assertEquals("email is the same", "ben@gmail.com", foundUser.getEmail());
        assertEquals("firstname is the same", "Ben", foundUser.getFirstname());
        assertEquals("lastname is the same", "Hernandez", foundUser.getLastname());
    }

}
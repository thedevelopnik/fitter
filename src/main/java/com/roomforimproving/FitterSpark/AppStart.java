package com.roomforimproving.FitterSpark;

import com.roomforimproving.FitterSpark.database.User;
import com.roomforimproving.FitterSpark.database.Users;
import com.roomforimproving.FitterSpark.websocket.EchoWebSocket;
import org.mindrot.jbcrypt.BCrypt;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/*
 * Created by davidsudia on 5/2/16.
 */
public class AppStart {
    public static void main(String[] args) {
        Users users = new Users();

        ThymeleafTemplateEngine thymeleafTemplateEngine = new ThymeleafTemplateEngine();

        webSocket("/subscribe", EchoWebSocket.class);

        get("/", (req, res) -> {
            final Map indexMap = new HashMap();
            indexMap.put("title", "index");
            ModelAndView modelAndView = new ModelAndView(indexMap, "index");
            res.status(200);
            return thymeleafTemplateEngine.render(modelAndView);
        });

        get("/login", (req, res) -> {
            final Map indexMap = new HashMap();
            indexMap.put("title", "login");
            ModelAndView modelAndView = new ModelAndView(indexMap, "login");
            res.status(200);
            return thymeleafTemplateEngine.render(modelAndView);
        });

        get("/docs", (req, res) -> {
            final Map indexMap = new HashMap();
            indexMap.put("title", "docs");
            ModelAndView modelAndView = new ModelAndView(indexMap, "docs");
            res.status(200);
            return thymeleafTemplateEngine.render(modelAndView);
        });

        get("/signup", (req, res) -> {
            final Map indexMap = new HashMap();
            indexMap.put("title", "signup");
            ModelAndView modelAndView = new ModelAndView(indexMap, "signup");
            res.status(200);
            return thymeleafTemplateEngine.render(modelAndView);
        });

        post("/signup", (req, res) -> {

            // parse req.body for form values and save values in memory
            String[] userDataArray = req.body().split("&");

            String[] emailPair = userDataArray[0].split("=");
            String[] fnamePair = userDataArray[1].split("=");
            String[] lnamePair = userDataArray[2].split("=");
            String[] passwordPair = userDataArray[3].split("=");
            String[] cpassPair = userDataArray[4].split("=");

            String email = URLDecoder.decode(emailPair[1], "UTF-8");
            String firstname  = URLDecoder.decode(fnamePair[1], "UTF-8");
            String lastname = URLDecoder.decode(lnamePair[1], "UTF-8");
            String password = URLDecoder.decode(passwordPair[1], "UTF-8");
            String confirmpassword = URLDecoder.decode(cpassPair[1], "UTF-8");

            if (password.equals(confirmpassword)) {
                User newUser = new User(null, email, firstname, lastname, password, null);
                users.insertUser(newUser);
                res.redirect("/login");
                return "submitted";
            } else {
                res.redirect("/signup?error=true");
                return "passwords must match";
            }
        });

        post("/login", (req, res) -> {
            String[] userDataArray = req.body().split("&");
            String[] emailPair = userDataArray[0].split("=");
            String[] passwordPair = userDataArray[1].split("=");
            String email = URLDecoder.decode(emailPair[1], "UTF-8");
            String password = URLDecoder.decode(passwordPair[1], "UTF-8");

            User foundUser = users.findUser(email);

            String hashedPass = foundUser.getPassword();

            if (BCrypt.checkpw(password, hashedPass)) {
                res.redirect("/docs");
                System.out.println("Success!");
                return "Success!";
            } else {
                res.redirect("/login?error=true");
                System.out.println("Login credentials are incorrect.");
                return "Login credentials are incorrect.";
            }
        });
    }
}

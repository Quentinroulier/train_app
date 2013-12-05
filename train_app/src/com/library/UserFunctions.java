package com.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://domaine-berge-de-sainte-rose.com/train-app/index.php";
    private static String registerURL = "http://domaine-berge-de-sainte-rose.com/train-app/index.php";
    private static String informationURL = "http://domaine-berge-de-sainte-rose.com/train-app/index.php";
    private static String uploadsongURL = "http://domaine-berge-de-sainte-rose.com/train-app/index.php";
    
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String Information_tag = "information";
    private static String upload_tag = "upload";
    private static String download_tag = "download";
    
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
         
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }
    public JSONObject userInformation(String email){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Information_tag));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(informationURL, params);
        // return json
        // Log.e("JSON", email);
        return json;
    }
    public JSONObject uploadSongUser(String answer, String name_video, String email){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", upload_tag));
        params.add(new BasicNameValuePair("answer", answer));
        params.add(new BasicNameValuePair("name_video", name_video));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(uploadsongURL, params);
        // return json
        // Log.e("JSON", email);
        return json;
    }
    public JSONObject downloadSongUser(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", download_tag));
        JSONObject json = jsonParser.getJSONFromUrl(uploadsongURL, params);
        // return json
        // Log.e("JSON", email);
        return json;
    }
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
    public String userEmail(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            return db.getUserDetails().get("email").toString();
        }
		return null;
    }
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}
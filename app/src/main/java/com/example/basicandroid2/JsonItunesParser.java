package com.example.basicandroid2;

import com.example.basicandroid2.Model.ITunesStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonItunesParser {


    public static ITunesStuff getItunesStuff(String url)throws JSONException{

        ITunesStuff iTunesStuff=new ITunesStuff();

        JSONObject iTunesStuffJsonObject=new JSONObject(url);

        JSONArray resultsJsonArray=iTunesStuffJsonObject.getJSONArray("results");
        JSONObject artistObject=resultsJsonArray.getJSONObject(0);
        iTunesStuff.setType(getString("wrapperType",artistObject));
        iTunesStuff.setArtistName(getString("artistName",artistObject));
         iTunesStuff.setKind(getString("kind",artistObject));
          iTunesStuff.setCollectionName(getString("collectionName",artistObject));
          iTunesStuff.setArtistViewURL(getString("artworkUrl100",artistObject));
           iTunesStuff.setTrackName(getString("trackName",artistObject));

           return iTunesStuff;
    }

    private static JSONObject getJsonObject(String tagName,JSONObject jsonObject)throws JSONException{

        return jsonObject.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jsonObject)throws JSONException {

        return jsonObject.getString(tagName);
    }

    private static int getInt(String tagName,JSONObject jsonObject)throws JSONException{

        return jsonObject.getInt(tagName);
    }

    private static boolean getBoolean(String tagName,JSONObject jsonObject)throws JSONException{

        return jsonObject.getBoolean(tagName);
    }

    private static float getfloat(String tagName,JSONObject jsonObject)throws JSONException{

        return (float) jsonObject.getDouble(tagName);
    }
}

package com.example.rickandmortyapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConectApiRest {
    private static String baseURL="https://rickandmortyapi.com/api";
    public static String getRequest(String strUrl ){
        HttpURLConnection http = null;
        String content = null;
        try {
            URL url = new URL( baseURL + strUrl );
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if( http != null ) http.disconnect();
        }
        return content;
    }
}

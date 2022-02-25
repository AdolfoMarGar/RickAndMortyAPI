package com.example.rickandmortyapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConectApiRest {
    //url base para las consultas de la api rest
    private static String baseURL="https://rickandmortyapi.com/api";

    //Metodo que realiza una peticion get a la api rest, se le pide un String que es el final de la url
    public static String getRequest(String strUrl ){
        HttpURLConnection http = null;
        String content = null;
        try {
            URL url = new URL( baseURL + strUrl );
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            //obtenemos el resultado de la petición, aceptando solo json ya que es el recurso que devuelve y nos interesa.
            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                //devolvemos el json obtenido en forma de string
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

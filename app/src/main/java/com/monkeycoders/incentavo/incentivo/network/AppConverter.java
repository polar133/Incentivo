package com.monkeycoders.incentavo.incentivo.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;


public class AppConverter implements Converter {
    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String text = null;
        Object objeto = null;
        try {
            text = fromStream(body.in());
            if (text.startsWith("[")) {
                objeto = new JSONArray(text);
            } else {
                objeto = new JSONObject(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objeto;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
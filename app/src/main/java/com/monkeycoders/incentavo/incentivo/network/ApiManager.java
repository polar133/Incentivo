package com.monkeycoders.incentavo.incentivo.network;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class ApiManager {
    private static final String API_URL = "http://192.168.27.125:3000";
    private static final String API_BANCO = "http://192.168.27.2:9001/BChHackatonAPI/webrest";
    private static final String API_SOCKET = "http://192.168.27.79:7777";

    public static final String apiID = "18_MCS";

    private static AppApi appApi;
    private static AppApi appApBank;
    private static AppApi appApSocket;

    public static AppApi geApiSocket() {
        // Create our Converter
        JacksonConverter converter = new JacksonConverter();

        if (appApSocket == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_SOCKET)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setConverter(converter)
                    .build();

            appApSocket = restAdapter.create(AppApi.class);
        }

        return appApSocket;
    }

    public static AppApi geApiBank() {
        // Create our Converter
        JacksonConverter converter = new JacksonConverter();

        if (appApBank == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_BANCO)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setConverter(converter)
                    .build();

            appApBank = restAdapter.create(AppApi.class);
        }

        return appApBank;
    }

    public static AppApi geApiClient() {
        // Create our Converter
        JacksonConverter converter = new JacksonConverter();

        if (appApi == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setConverter(converter)
                    .build();

            appApi = restAdapter.create(AppApi.class);
        }

        return appApi;
    }


}

package com.monkeycoders.incentavo.incentivo.services;


        import android.content.Context;
        import android.os.AsyncTask;

        import org.xml.sax.InputSource;

        import java.net.URL;
        import java.util.HashMap;

        import javax.xml.parsers.SAXParser;
        import javax.xml.parsers.SAXParserFactory;

        import retrofit.RetrofitError;

public class openDashboardService {
    private static openDashboardService instance;

    private openDashboardService() {
    }

    public static openDashboardService getInstance() {
        if (instance == null) {
            instance = new openDashboardService();
        }
        return instance;
    }

    public HashMap<String, Object> openDashboar(Context context) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            //response = ApiManager.geApiSocket().openDashboard();
            RetrieveFeedTask retrieveFeedTask = new RetrieveFeedTask();
            retrieveFeedTask.execute();
            return null;

        } catch (RetrofitError e) {
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url= new URL("http://192.168.27.79:7777");
                SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                InputSource is=new InputSource(url.openStream());
            } catch (Exception e) {

            }
            return null;
        }
    }
}

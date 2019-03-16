package org.appducegep.forexMax;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PageForex extends AppCompatActivity {

    private TextView libelleTitre;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_meteo:
//                    libelleTitre.setText(R.string.titre_accueil);
//                    return true;
//                case R.id.navigation_meteo_detail:
//                    libelleTitre.setText(R.string.titre_meteo_detail);
//                    return true;
//                case R.id.navigation_notifications:
//                    libelleTitre.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_meteo);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        libelleTitre = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String CLE = "0ee79cbc173226487f997b7aa2c4867d";
        String xml = "";

        try {
            URL url = new URL("http://data.fixer.io/api/latest?access_key="+CLE+"&base=EUR&symbols=USD");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                xml = stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        System.out.println(xml);

        try {
//            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = null;
//            docBuilder = builderFactory.newDocumentBuilder();
//            Document doc = null;
//            doc = docBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));


//            Element elementHumidite = (Element)doc.getElementsByTagName("humidity").item(0);
//            String humidite = elementHumidite.getTextContent();
//            Element elementVentForce = (Element)doc.getElementsByTagName("wind_kph").item(0);
//            String ventForce = elementVentForce.getTextContent();
//            Element elementVentDirection = (Element)doc.getElementsByTagName("wind_dir").item(0);
//            String ventDirection = elementVentDirection.getTextContent();
//            Element elementCondition = (Element)doc.getElementsByTagName("condition").item(0);
//            Element elementSoleilOuNuage = (Element)elementCondition.getElementsByTagName("text").item(0);
//            String soleilOuNuage = elementSoleilOuNuage.getTextContent();
//            if(soleilOuNuage.compareTo("Sunny") == 0) soleilOuNuage = "Ensoleillé";
//            else soleilOuNuage = "Nuageux";
//            Element elementLieu = (Element)doc.getElementsByTagName("location").item(0);
//            Element elementVille =(Element)elementLieu.getElementsByTagName("name").item(0);
//            Element elementTemperature =(Element)doc.getElementsByTagName("temp_c").item(0);
//            String ville = elementVille.getTextContent();
//            String vent = ventDirection +" "+ ventForce;
//            float temperature = Float.parseFloat(elementTemperature.getTextContent());
            String convert = xml.toString();
            convert = convert.replaceAll("[\\{\\}\"]", "");
            System.out.println(convert+" CONVERT");
            Map<String,String> myMap = new HashMap<>();
            String[] pairs = convert.split(",");
            for (int i=0;i<pairs.length;i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split(":");
                if(keyValue.length==3){
                    myMap.put("value",keyValue[2]);
                }
                myMap.put(keyValue[0], keyValue[1]);
            }
            System.out.println(myMap);
            String elementPaire1 = myMap.get("base");
            String elementPaire2 = myMap.get("rates");
            String rate = myMap.get("value");
            System.out.println("elementPaire2");
            String paire = elementPaire1+"/"+elementPaire2;
            float valeur = Float.parseFloat(rate);


            System.out.println("\n//////////////////////");
            System.out.println("Paire = " + paire);
            System.out.println("Valeur = " + valeur);
            System.out.println("//////////////////////");

            TextView affichageTitre = (TextView) this.findViewById(R.id.titre_page_meteo);
            String titre = "Valeur de " + paire;
            affichageTitre.setText(titre);

            TextView affichageForex = (TextView)this.findViewById(R.id.meteo);
            affichageForex.append("\n\n\n\n\n");
            affichageForex.append("Coté à : " + valeur + "\n");


            ForexDAO forexDAO = new ForexDAO(getApplicationContext());
            forexDAO.ajouterForex(paire,valeur);


        } catch (PatternSyntaxException e) {
            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//         catch (ParserConfigurationException e) {
//            e.printStackTrace();
        }

    }

}

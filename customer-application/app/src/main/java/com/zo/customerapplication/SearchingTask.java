//package com.zo.customerapplication;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.ref.WeakReference;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class SearchingTask extends AsyncTask<String, Void, Void> {
//    private WeakReference<Context> taskContext;
//
//    public SearchingTask(Context context) {
//        taskContext = new WeakReference<>(context);
//    }
//
//    @Override
//    protected Void doInBackground(String... strings) {
//        /*
//            Connects to backend API providing search results for phrase passed in @strings argument.
//         */
//        if (strings.length != 5) {
//            return null;
//        }
//
//        HttpURLConnection connectionToServer = null;
//        try {
//
//            URL serverUrl = new URL("192.168.0.115:8000" + "/api/products/?name=" + strings[0]);
//            connectionToServer = (HttpURLConnection) serverUrl.openConnection();
//            Log.println(Log.INFO, "API", "Connected to API");
//
////            JSONObject jsonToBeSearched = new JSONObject();
////            jsonToBeSearched.put("id_user", strings[0]);
////            jsonToBeSearched.put("subject", strings[1]);
////            jsonToBeSearched.put("category", strings[2]);
////            jsonToBeSearched.put("min_price", strings[3]);
////            jsonToBeSearched.put("max_price", strings[4]);
//
//            connectionToServer.setRequestMethod("GET");
////            byte[] postDataBytes = jsonToBeSearched.toString().getBytes("UTF-8");
////            connectionToServer.setRequestMethod("POST");
//            connectionToServer.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//            connectionToServer.setDoOutput(true);
//            connectionToServer.setDoInput(true);
//            connectionToServer.getOutputStream().write(postDataBytes);
//
//            int HttpResult = connectionToServer.getResponseCode();
//            System.out.println("RESPONSE " + HttpResult);
//
//            connectionToServer.disconnect();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        /* ----odbieranie danych z POSTa---- */
//        InputStream inputStream = null;
//        try {
//            inputStream = connectionToServer.getInputStream();
//            InputStreamReader is = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader br = new BufferedReader(is);
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//
//            System.out.println("POST RESPONSE:");
//            System.out.println(response.toString());
//
//            /* ------zapisywanie searcha do pliku ------ */
//            File searchesFile = new File(taskContext.get().getFilesDir(), "searches.json");
//            String fileContents = "";
//            if (searchesFile.exists()) {
//                fileContents += ",";
//            }
//            JSONObject responseJson = new JSONObject(response.toString());
//            String filename = "searches.json";
//
//            fileContents += "{\n\"id_search\": " + responseJson.get("id_search") + ",\n\"name_search\": \"" + responseJson.get("subject") + "\"\n}";
//            System.out.println("STRING TO BE WRITTEN");
//            System.out.println(fileContents);
//            FileOutputStream fos = taskContext.get().openFileOutput(filename, Context.MODE_APPEND);
//            fos.write(fileContents.getBytes());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//    }
//}
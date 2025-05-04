package com.example.redditClone.mongo_api;

import android.os.AsyncTask;

public class MongoAPITask extends AsyncTask<Void, Void, String> {
    private String action;
    private String body;
    private MongoAPIListener listener;

    public MongoAPITask(String action, String body, MongoAPIListener listener) {
        this.action = action;
        this.body = body;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            return MongoAPI.makeRequest(action, body);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onResult(result);
        }
    }

}

package com.sahilssoft.p1_sportsocial.Methods;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.activities.ChooseAuthActivity;
import com.sahilssoft.p1_sportsocial.callback.ArrayCallback;
import com.sahilssoft.p1_sportsocial.callback.BaseCallback;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.jsonhelper.JsonParser;
import com.sahilssoft.p1_sportsocial.utils.Constants;
import com.sahilssoft.p1_sportsocial.utils.SharedPrefClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class APIRequest {

    private RequestQueue requestQueue;
    private boolean cached = true;
    private Context context;
    public static APIRequest _this;
    private String BASE_URL = "https://demo.appcrates.net/sp-ssw/public/api/";

    public APIRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.context = context;

    }

    public static APIRequest getInstance(Context context) {
        if (_this == null) {
            _this = new APIRequest(context);
        }
        return _this;
    }

    public <T> void postPutRequest(String url, final Object object, final ObjectCallback<T> callback, int req) {
        Log.e("postPutRequestUrl", BASE_URL + url + "");
        Log.e("postPutObject => " + object.getClass().getName(), new Gson().toJson(object) + "");
        try {
            JSONObject jsonObject = null;

            if (req != Request.Method.GET) {
                jsonObject = JsonParser.toJSON(object);
            }

            JsonObjectRequest request = new JsonObjectRequest(req, BASE_URL + url, jsonObject, response -> {
                Log.e("Response", response + "");
                if (callback != null) {
                    try {
                        if (response.getInt("status") == 401) {
                            logout(callback);
                        } else {
                            if (response.getInt("status") == 200) {
                                Log.e("onresponsestatus", true + "");
                                callback.onData((T) new Gson().fromJson(response.toString(), object.getClass()));

                            } else if (response.getInt("status") == 401) {

                                logout(callback);
                                callback.onError(response.getString("message"));
                            } else {
                                callback.onError(response.getString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, error -> {
                Log.e("ResponseERROR", error.getMessage() + "");
                Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();

                if (callback != null) {
                    setErrorMsg(error, callback, new Gson().toJson(object), BASE_URL + url, req);

//                    callback.onError(error.toString());
//                    setError(error, callback);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Log.e("headercall", BASE_URL + url);

                    return getHeader();
                }
            };
            //store post
            //description on post
            addRequest(request);
        } catch (JSONException e) {
            Log.e("getMessage", e.getMessage() + "");

            e.printStackTrace();
            if (callback != null) {
                callback.onError(e.getMessage());
            }
        }
    }


    public <T> void getObjectRequest(String url, final Class clazz, final ObjectCallback<T> callback) {
        Log.e("getObjectRequestUrl", BASE_URL + url + "");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + url, null, response -> {
            Log.e("Response", response + "");
            if (callback != null) {
                try {
//                    if (response.getInt("code") == 401) {
//                        logout(callback);
//                    } else {
                    if (response.getInt("status") == 200) {
                        callback.onData((T) new Gson().fromJson(response.toString(), clazz));

                    } else if (response.getInt("status") == 401) {

                        logout(callback);
                        callback.onError(response.getString("message"));
                    } else {
                        callback.onError(response.getString("message"));

                    }
//                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.e("ResponseERROR", error.getMessage() + "");

            if (callback != null) {
                setErrorMsg(error, callback, null, BASE_URL + url, Request.Method.GET);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeader();
            }
        };
        addRequest(request);
    }


    public <T> void getArrayRequest(String url, final Class clazz, final ArrayCallback<T> callback) {
        Log.e("getArrayRequestUrl: ", BASE_URL + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, BASE_URL + url, null, response -> {
            if (callback != null) {
                Log.e("Response", "" + response.toString());
                callback.onData(JsonParser.toList(response.toString(), clazz));
            }
        }, error -> {

            if (callback != null) {
                setErrorMsg(error, callback, null, BASE_URL + url, Request.Method.GET);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeader();

            }
        };
        addRequest(request);
    }


    @SuppressLint("HardwareIds")
    private Map<String, String> getHeader() {
        Map<String, String> map = new HashMap<>();
        Log.e("Header", SharedPrefClass.getInstance(context).getData(Constants.TOKEN) + "");
        map.put("Authorization", "Bearer " + SharedPrefClass.getInstance(context).getData(Constants.TOKEN));
        map.put("Accept", "application/json");
        //        Log.e("DeviceId", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + "");
//        map.put("DeviceId", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));

        return map;


    }

    private void setErrorMsg(VolleyError error, BaseCallback callback, String data, String url, int request) {
        NetworkResponse response = error.networkResponse;
        if (response != null) {
            String errorMsg = new String(error.networkResponse.data).replaceAll("^\"|\"$", "");
            String codeMsg = new String(response.data) + "";

            switch (response.statusCode) {
                case 401:
                    logout(callback);
                case 413:
                    callback.onError(errorMsg);
//                    refreshToken(msg);
//                Utils.showLogoutDialog(context);

                    break;
                case 500:
                case 404:
                case 403:
                    callback.onError(errorMsg);
                    break;
                case 444:
                    callback.onError(errorMsg);

//                Utils.refreshTokenDialog(context);
                    break;
                case 422:
                    callback.onError(errorMsg);
                    break;
                default:
                    callback.onError(errorMsg + "");
                    break;
            }
        } else {
            callback.onError("Something Went Wrong...");
        }

    }

    private void logout(BaseCallback callback) {
        callback.onError("Unauthorized plz Login again.");
        SharedPrefClass.getInstance(context).clearPref();
        Intent i = new Intent(context, ChooseAuthActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
        ((Activity) context).finishAffinity();
    }

    private void addRequest(Request request) {

        long timeout = TimeUnit.SECONDS.toMillis(10);
        int maxRetries = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
        float backoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        RetryPolicy retryPolicy = new DefaultRetryPolicy((int) timeout, maxRetries, backoffMultiplier);
        request.setRetryPolicy(retryPolicy);

        request.setShouldCache(cached);
        requestQueue.add(request);
    }

}

package com.cit.d2dnotification2203;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cit.d2dnotification2203.databinding.ActivityMainBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    final static String FCM_KEY ="key=AAAA2ZSyECI:APA91bEw-RocUCiAUC2XBMb-3WdYp9Z1aL1WB7RArOWgMVj2EV87UHixqQ_ii6ilSnYR24lFk6MxB_Ucpms3ovgwwJBEOAnSFk_VWDbdRpSq2DplRFbAut3gZzyFEVjsMVRaFTRfmBxW";
    final static String url = "https://fcm.googleapis.com/fcm/send";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermission();

        requestQueue = Volley.newRequestQueue(this);

        binding.send.setOnClickListener( view -> {
            D2DNotification();
        });
    }

    private void checkPermission() {
        Dexter.withContext(this)
                .withPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        //D2DNotification();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();


    }

    private void D2DNotification() {

        JSONObject object = new JSONObject();

        try {
            object.put("to", "/topics/"+"jobAlert");

                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("title","App Developer");
                  jsonObject.put("body","Description");

            object.put("notification", jsonObject);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> notificationMap = new HashMap<>();
                    notificationMap.put("content-type","application/json");
                    notificationMap.put("authorization", FCM_KEY);

                    return notificationMap;
                }
            };

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
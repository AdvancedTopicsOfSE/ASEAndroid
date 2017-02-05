package com.example.arvindkalyankumar.ase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pixplicity.sharp.Sharp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    private Button qrcode,bonus,logout;
    private ImageView qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent intent = this.getIntent();
        final String message = intent.getStringExtra("session");
        final String user = intent.getStringExtra("userid");
      //  Toast.makeText(this,"Response 2xxxxxx " +message,Toast.LENGTH_LONG).show();
        // Toast.makeText(this,"Response userxxxx " +user,Toast.LENGTH_LONG).show();
        qrImage = (ImageView)findViewById(R.id.qrView);
        qrcode = (Button)findViewById(R.id.qrbtn);
        qrcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                generateQRCode(message);
            }
        });

        bonus = (Button)findViewById(R.id.bonusBtn);
        bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestBonus(user);
            }
        });

        logout = (Button)findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    protected void generateQRCode(String token){
        try {
            //URL url = new URL("https://shkspr.mobi/blog/wp-content/uploads/2011/12/uRmAhs.qrcode.png");
            URL url = new URL("http://aatserver.appspot.com/qr_code/"+token);
            Log.d("Load imagexxxx","http://aatserver.appspot.com/qr_code/"+token);
            qrImage.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            Sharp.loadInputStream(url.openConnection().getInputStream()).into(qrImage);
            //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            //qrImage.setImageBitmap(bmp);
        }
        catch (IOException e){

        }
  }

    public void requestBonus(final String uname){
        try {
                    String url = "http://aatserver.appspot.com/bonus/"+uname;

                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");


                    System.out.println("\nSending 'GET' request to URL : " + url);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Log.d("Bonussss",response.toString());
                    Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show();
                }
                catch(IOException e){
                }
            }

        public void logoutUser(){
            Context context=getApplicationContext();
            Intent intnt = new Intent(context, MainActivity.class);
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intnt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intnt);
    }
}

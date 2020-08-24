package com.example.basicandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basicandroid2.Model.ITunesStuff;

public class MasterJsonActivity extends AppCompatActivity {

    private TextView txtType,txtArtistName,txtCollectionName,txtTrackName,txtKind;
    private ImageView imgArtist;
    private Button btnGetData;
    private String imgURL;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_json);

        txtType=findViewById(R.id.txtType);
        txtArtistName=findViewById(R.id.txtArtistName);
        txtCollectionName=findViewById(R.id.txtCollectionName);
        txtTrackName=findViewById(R.id.txtTrackName);
        txtKind=findViewById(R.id.txtKind);

        imgArtist=findViewById(R.id.imgArtist);
        btnGetData=findViewById(R.id.btnGetData);


        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonItunesStuffStack jsonItunesStuffStack=new JsonItunesStuffStack(MasterJsonActivity.this);
                jsonItunesStuffStack.execute();
            }
        });

    }

    private class JsonItunesStuffStack extends AsyncTask<String,Void, ITunesStuff> {

        Context context;
        ProgressDialog progressDialog;

        public JsonItunesStuffStack(Context context){

            this.context=context;
            progressDialog=new ProgressDialog(context);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Downloading Info from Itunes....Please Wait");
             progressDialog.show();
        }

        @Override
        protected ITunesStuff doInBackground(String... params) {

            ITunesStuff iTunesStuff=new ITunesStuff();
            ItunesHTTPClient itunesHTTPClient=new ItunesHTTPClient();

            String data=(itunesHTTPClient.getItunesStuffData());

            try {

                iTunesStuff=JsonItunesParser.getItunesStuff(data);
                imgURL=iTunesStuff.getArtistViewURL();
                bitmap=(itunesHTTPClient.getBitmapFromURL(imgURL));


            }catch (Throwable t){
                t.printStackTrace();
            }

            return iTunesStuff;
        }

        @Override
        protected void onPostExecute(ITunesStuff iTunesStuff) {
            super.onPostExecute(iTunesStuff);

            txtArtistName.setText(iTunesStuff.getArtistName());
            txtType.setText(iTunesStuff.getType());
            txtKind.setText(iTunesStuff.getKind());
            txtCollectionName.setText(iTunesStuff.getCollectionName());
            txtTrackName.setText(iTunesStuff.getTrackName());
            imgArtist.setImageBitmap(bitmap);

            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
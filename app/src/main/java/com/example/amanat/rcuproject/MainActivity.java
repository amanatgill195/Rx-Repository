package com.example.amanat.rcuproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amanat.rcuproject.Retrofit.MyApi;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mahdi.mzip.zip.ZipArchive;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Worldpopulation> worldpopulationArrayList;
    private Adapter adapter;
    private Button button;
    private Boolean permissionsBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Content();

        contacts();

    }

    private void contacts() {
        button = findViewById(R.id.btGetContacts);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getVcardString();
                //Asking for runtime permissions
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    permissionsBoolean = true;
                    doBackgroundTasks();

                } else {

                    permissionsBoolean = false;
                    // permission denied
                    Toast.makeText(MainActivity.this, "Permissions are Denied", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void doBackgroundTasks() {
        //Observable

        Observable<String> myObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {

                        //getting out contacts in a cursor
                        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                        Log.d("CURSOR COUNT", String.valueOf(cursor.getCount()));

                        List<String[]> data = new ArrayList<String[]>();

                        String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().concat("/contacts.csv");
                        CSVWriter writer = null;
                        try {
                            writer = new CSVWriter(new FileWriter(csv));
                        } catch (IOException e) {
                            Log.d("NULL CSV WRITER", e.getMessage());
                            e.printStackTrace();
                        }

                        //Storing id, name , number in List "data"
                        while (cursor.moveToNext()) {

                            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            data.add(new String[]{id, name, phoneNumber});

                        }

                        //Writing List data to CSV
                        if (writer != null) {
                            writer.writeAll(data);
                        } else {
                        }

                        //Close cursor and writer
                        cursor.close();
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ZipArchive zipArchive = new ZipArchive();
                        zipArchive.zip(android.os.Environment.getExternalStorageDirectory().getAbsolutePath().concat("/contacts.csv")
                                , android.os.Environment.getExternalStorageDirectory().getAbsolutePath().concat("/contacts.zip"), "");


                        // completion of the event
                        subscriber.onCompleted();
                    }
                });


        //Subscribing observer to the observable
        myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ".Zip has been saved to the Root Directory of Your Phone", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("New Error:", e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
    }

    private void Content() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadJSON();

    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.androidbegin.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApi myApi = retrofit.create(MyApi.class);
        Call<JSONResponse> call = myApi.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                worldpopulationArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getWorldpopulation()));
                adapter = new Adapter(worldpopulationArrayList, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went Wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

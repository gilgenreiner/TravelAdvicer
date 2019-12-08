package com.example.traveladvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.traveladvisor.bll.Location;

public class LocationDetailActivity extends AppCompatActivity {

    private TextView textView_beschreibung;
    private TextView textView_bezeichnung;
    private TextView textView_branchen;
    private TextView textView_punkte;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        location = (Location) getIntent().getExtras().get("selectedLocation");

        textView_beschreibung = findViewById(R.id.textview_beschreibung);
        textView_bezeichnung = findViewById(R.id.textview_bezeichung);
        textView_branchen = findViewById(R.id.textview_branchen);
        textView_punkte = findViewById(R.id.textview_punkte);

        textView_bezeichnung.setText(location.getBezeichnung());
        textView_beschreibung.setText(location.getBeschreibung());
        //textView_branchen.setText(location.getBranchen().toString());
        textView_punkte.setText(String.valueOf(location.getPunkte()));

    }
}

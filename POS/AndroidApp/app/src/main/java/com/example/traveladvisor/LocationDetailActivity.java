package com.example.traveladvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.traveladvisor.bll.Location;
import com.google.android.material.textfield.TextInputEditText;

public class LocationDetailActivity extends AppCompatActivity {
    private TextInputEditText etxtBeschreibung;
    private TextInputEditText etxtBezeichnung;
    private TextInputEditText etxtBranchen;
    private TextInputEditText etxtPunkte;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        location = (Location) getIntent().getExtras().get("selectedLocation");

        etxtBezeichnung = findViewById(R.id.etxtBezeichnung);
        etxtBeschreibung = findViewById(R.id.etxtBeschreibung);
        etxtBranchen = findViewById(R.id.etxtBranchen);
        etxtPunkte = findViewById(R.id.etxtPunkte);

        etxtBezeichnung.setEnabled(false);
        etxtBeschreibung.setEnabled(false);
        etxtBranchen.setEnabled(false);
        etxtPunkte.setEnabled(false);

        etxtBeschreibung.setText(location.getBeschreibung());
        etxtBezeichnung.setText(location.getBezeichnung());
        etxtBranchen.setText(location.getBranchenAsString());
        etxtPunkte.setText(String.valueOf(location.getPunkte()));

    }
}

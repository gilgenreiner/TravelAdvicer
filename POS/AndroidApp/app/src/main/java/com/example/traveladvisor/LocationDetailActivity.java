package com.example.traveladvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traveladvisor.bll.Location;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class LocationDetailActivity extends AppCompatActivity {
    private TextInputEditText etxtBeschreibung;
    private TextInputEditText etxtBezeichnung;
    private TextInputEditText etxtBranchen;
    private TextInputEditText etxtPunkte;
    private ImageView imageView;

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
        imageView = findViewById(R.id.imageView);

        etxtBezeichnung.setEnabled(false);
        etxtBeschreibung.setEnabled(false);
        etxtBranchen.setEnabled(false);
        etxtPunkte.setEnabled(false);

        etxtBeschreibung.setText(location.getBeschreibung());
        etxtBezeichnung.setText(location.getBezeichnung());
        etxtBranchen.setText(location.getBranchenAsString());
        etxtPunkte.setText(String.valueOf(location.getPunkte()));

        Picasso.get().load("https://picsum.photos/510/300?random").into(imageView);
    }
}

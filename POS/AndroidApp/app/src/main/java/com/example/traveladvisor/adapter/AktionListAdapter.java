package com.example.traveladvisor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traveladvisor.R;
import com.example.traveladvisor.bll.Aktion;

import java.util.ArrayList;

public class AktionListAdapter extends ArrayAdapter<Aktion> {

    public AktionListAdapter(Context context, ArrayList<Aktion> aktionen) {
        super(context, 0, aktionen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aktion aktion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.aktion_list_items, parent, false);
        }

        TextView tvBezeichnung = (TextView) convertView.findViewById(R.id.tvBezeichnung);
        TextView tvPunkte = (TextView) convertView.findViewById(R.id.tvPunkte);
        Button btnEinloesen = convertView.findViewById((R.id.button_Aktion_Einsloesen));

        tvBezeichnung.setText(aktion.getBezeichnung());
        tvPunkte.setText(""+aktion.getPunkte());

        btnEinloesen.setTag(aktion);
        btnEinloesen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aktion aktion = (Aktion) view.getTag();
                Toast.makeText(getContext() ,"Einlösen",Toast.LENGTH_SHORT).show();

                //einlösen Route noch nicht vorhanden
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}

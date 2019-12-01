package com.example.traveladvisor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveladvisor.MapActivity;
import com.example.traveladvisor.R;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

import java.util.List;

import static android.media.MediaDrm.PROPERTY_DESCRIPTION;

public class MapRecyclerViewAdapter extends
        RecyclerView.Adapter<MapRecyclerViewAdapter.MyViewHolder> {

    private static final String PROPERTY_SELECTED = "selected";
    private static final String PROPERTY_LOADING = "loading";
    private static final String PROPERTY_LOADING_PROGRESS = "loading_progress";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_FAVOURITE = "favourite";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static final String PROPERTY_POI = "poi";
    private static final String PROPERTY_STYLE = "style";

    private List<Feature> featureCollection;
    private MapActivity activity;

    public MapRecyclerViewAdapter(MapActivity activity, FeatureCollection featureCollection) {
        this.activity = activity;
        this.featureCollection = featureCollection.features();
    }

    @Override
    public MapRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_symbol_layer, parent, false);
        return new MapRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MapRecyclerViewAdapter.MyViewHolder holder, int position) {
        Feature feature = featureCollection.get(position);
        holder.title.setText(feature.getStringProperty(PROPERTY_TITLE));
        holder.description.setText(feature.getStringProperty(PROPERTY_DESCRIPTION));
        holder.poi.setText(feature.getStringProperty(PROPERTY_POI));
        holder.style.setText(feature.getStringProperty(PROPERTY_STYLE));
        holder.setClickListener(new MapActivity.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (activity != null) {
                    activity.toggleFavourite(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return featureCollection.size();
    }

    /**
     * ViewHolder for RecyclerView.
     */
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView poi;
        TextView style;
        TextView description;
        CardView singleCard;
        MapActivity.ItemClickListener clickListener;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textview_title);
            poi = view.findViewById(R.id.textview_poi);
            style = view.findViewById(R.id.textview_style);
            description = view.findViewById(R.id.textview_description);
            singleCard = view.findViewById(R.id.single_location_cardview);
            singleCard.setOnClickListener(this);
        }

        void setClickListener(MapActivity.ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getLayoutPosition());
        }
    }
}

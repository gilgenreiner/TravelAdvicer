package com.example.traveladvisor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traveladvisor.adapter.MapRecyclerViewAdapter;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.style.sources.TileSet;
import com.mapbox.mapboxsdk.style.sources.VectorSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener,
        MapboxMap.OnMapClickListener {
    private static final String SOURCE_ID = "mapbox.poi";
    private static final String MAKI_LAYER_ID = "mapbox.poi.maki";
    private static final String LOADING_LAYER_ID = "mapbox.poi.loading";
    private static final String CALLOUT_LAYER_ID = "mapbox.poi.callout";

    private static final String PROPERTY_SELECTED = "selected";
    private static final String PROPERTY_LOADING = "loading";
    private static final String PROPERTY_LOADING_PROGRESS = "loading_progress";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_FAVOURITE = "favourite";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static final String PROPERTY_POI = "poi";
    private static final String PROPERTY_STYLE = "style";

    private static final long CAMERA_ANIMATION_TIME = 1950;
    private static final float LOADING_CIRCLE_RADIUS = 60;
    private static final int LOADING_PROGRESS_STEPS = 25; //number of steps in a progress animation
    private static final int LOADING_STEP_DURATION = 50; //duration between each step

    private MapView mapView;
    private MapboxMap mapboxMap;
    private RecyclerView recyclerView;

    private GeoJsonSource source;
    private FeatureCollection featureCollection;
    private HashMap<String, View> viewMap;
    private AnimatorSet animatorSet;

    private PermissionsManager permissionsManager;


    @ActivityStep
    private int currentStep;

    public MapActivity() {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef( {STEP_INITIAL, STEP_LOADING, STEP_READY})
    public @interface ActivityStep {
    }

    private static final int STEP_INITIAL = 0;
    private static final int STEP_LOADING = 1;
    private static final int STEP_READY = 2;

    private static final Map<Integer, Double> stepZoomMap = new HashMap<>();

    static {
        stepZoomMap.put(STEP_INITIAL, 11.0);
        stepZoomMap.put(STEP_LOADING, 13.5);
        stepZoomMap.put(STEP_READY, 18.0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.access_token));

        setContentView(R.layout.activity_map);

        recyclerView = findViewById(R.id.rv_on_top_of_map);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MapActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/streets-v11")
                , new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                        mapboxMap.getUiSettings().setCompassEnabled(false);
                        mapboxMap.getUiSettings().setLogoEnabled(false);
                        mapboxMap.getUiSettings().setAttributionEnabled(false);
                        new LoadPoiDataTask(MapActivity.this).execute();
                        mapboxMap.addOnMapClickListener(MapActivity.this);
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this,"Gejz", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "Geht ned", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);
        List<Feature> features = mapboxMap.queryRenderedFeatures(screenPoint, CALLOUT_LAYER_ID);
        if (!features.isEmpty()) {
// we received a click event on the callout layer
            Feature feature = features.get(0);
            PointF symbolScreenPoint = mapboxMap.getProjection().toScreenLocation(convertToLatLng(feature));
            handleClickCallout(feature, screenPoint, symbolScreenPoint);
        } else {
// we didn't find a click event on callout layer, try clicking maki layer
            return handleClickIcon(screenPoint);
        }
        return true;
    }

    public void setupData(final FeatureCollection collection) {
        if (mapboxMap == null) {
            return;
        }
        featureCollection = collection;
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                setupSource(style);
                setupMakiLayer(style);
                setupLoadingLayer(style);
                setupCalloutLayer(style);
                setupRecyclerView();
                hideLabelLayers(style);
            }
        });
    }

    private void setupSource(@NonNull Style loadedMapStyle) {
        source = new GeoJsonSource(SOURCE_ID, featureCollection);
        loadedMapStyle.addSource(source);
    }

    private void refreshSource() {
        if (source != null && featureCollection != null) {
            source.setGeoJson(featureCollection);
        }
    }

    /**
     * Setup a layer with maki icons, eg. restaurant.
     */
    private void setupMakiLayer(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_location_24dp, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        mapboxMap.getStyle().addImage("my.image", mBitmap);

        loadedMapStyle.addLayer(new SymbolLayer(MAKI_LAYER_ID, SOURCE_ID)
                .withProperties(
                        /* show maki icon based on the value of poi feature property
                         * https://www.mapbox.com/maki-icons/
                         */
                        iconImage("my.image"),
                        /* allows show all icons */
                        iconAllowOverlap(true),

                        /* when feature is in selected state, grow icon */
                        iconSize(match(Expression.toString(get(PROPERTY_SELECTED)), literal(1.0f),
                                stop("true", 1.5f))))
        );
    }

    /**
     * Setup layer indicating that there is an ongoing progress.
     */
    private void setupLoadingLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayerBelow(new CircleLayer(LOADING_LAYER_ID, SOURCE_ID)
                .withProperties(
                        circleRadius(interpolate(exponential(1), get(PROPERTY_LOADING_PROGRESS), getLoadingAnimationStops())),
                        circleColor(Color.GRAY),
                        circleOpacity(0.6f)
                )
                .withFilter(eq(get(PROPERTY_LOADING), literal(true))), MAKI_LAYER_ID);
    }

    private Expression.Stop[] getLoadingAnimationStops() {
        List<Expression.Stop> stops = new ArrayList<>();
        for (int i = 0; i < LOADING_PROGRESS_STEPS; i++) {
            stops.add(stop(i, LOADING_CIRCLE_RADIUS * i / LOADING_PROGRESS_STEPS));
        }

        return stops.toArray(new Expression.Stop[LOADING_PROGRESS_STEPS]);
    }

    /**
     * Setup a layer with Android SDK call-outs
     * <p>
     * title of the feature is used as key for the iconImage
     * </p>
     */
    private void setupCalloutLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer(CALLOUT_LAYER_ID, SOURCE_ID)
                .withProperties(
                        /* show image with id title based on the value of the title feature property */
                        iconImage("{title}"),

                        /* set anchor of icon to bottom-left */
                        iconAnchor(Property.ICON_ANCHOR_BOTTOM_LEFT),

                        /* offset icon slightly to match bubble layout */
                        iconOffset(new Float[] {-20.0f, -10.0f})
                )

/* add a filter to show only when selected feature property is true */
                .withFilter(eq((get(PROPERTY_SELECTED)), literal(true))));
    }

    private void setupRecyclerView() {
        RecyclerView.Adapter adapter = new MapRecyclerViewAdapter(this, featureCollection);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    int index = layoutManager.findFirstVisibleItemPosition();
                    setSelected(index, false);
                }
            }
        });
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    private void hideLabelLayers(@NonNull Style style) {
        String id;
        for (Layer layer : style.getLayers()) {
            id = layer.getId();
            if (id.startsWith("place") || id.startsWith("poi") || id.startsWith("marine") || id.startsWith("road-label")) {
                layer.setProperties(visibility(Property.NONE));
            }
        }
    }

    /**
     * This method handles click events for callout symbols.
     * <p>
     * It creates a hit rectangle based on the the textView, offsets that rectangle to the location
     * of the symbol on screen and hit tests that with the screen point.
     * </p>
     *
     * @param feature           the feature that was clicked
     * @param screenPoint       the point on screen clicked
     * @param symbolScreenPoint the point of the symbol on screen
     */
    private void handleClickCallout(Feature feature, PointF screenPoint, PointF symbolScreenPoint) {
        View view = viewMap.get(feature.getStringProperty(PROPERTY_TITLE));
       // View textContainer = view.findViewById(R.id.text_container);

// create hitbox for textView
        Rect hitRectText = new Rect();
        //textContainer.getHitRect(hitRectText);

// move hitbox to location of symbol
        hitRectText.offset((int) symbolScreenPoint.x, (int) symbolScreenPoint.y);

// offset vertically to match anchor behaviour
        hitRectText.offset(0, -view.getMeasuredHeight());

// hit test if clicked point is in textview hitbox
        if (hitRectText.contains((int) screenPoint.x, (int) screenPoint.y)) {
// user clicked on text
            String callout = feature.getStringProperty("call-out");
            Toast.makeText(this, callout, Toast.LENGTH_LONG).show();
        } else {
// user clicked on icon
            List<Feature> featureList = featureCollection.features();
            for (int i = 0; i < featureList.size(); i++) {
                if (featureList.get(i).getStringProperty(PROPERTY_TITLE).equals(feature.getStringProperty(PROPERTY_TITLE))) {
                    toggleFavourite(i);
                }
            }
        }
    }

    /**
     * This method handles click events for maki symbols.
     * <p>
     * When a maki symbol is clicked, we moved that feature to the selected state.
     * </p>
     *
     * @param screenPoint the point on screen clicked
     */
    private boolean handleClickIcon(PointF screenPoint) {
        List<Feature> features = mapboxMap.queryRenderedFeatures(screenPoint, MAKI_LAYER_ID);
        if (!features.isEmpty()) {
            String title = features.get(0).getStringProperty(PROPERTY_TITLE);
            List<Feature> featureList = featureCollection.features();
            for (int i = 0; i < featureList.size(); i++) {
                if (featureList.get(i).getStringProperty(PROPERTY_TITLE).equals(title)) {
                    setSelected(i, true);
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Set a feature selected state with the ability to scroll the RecycleViewer to the provided index.
     *
     * @param index      the index of selected feature
     * @param withScroll indicates if the recyclerView position should be updated
     */
    private void setSelected(int index, boolean withScroll) {
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }

        deselectAll(false);

        Feature feature = featureCollection.features().get(index);
        selectFeature(feature);
        animateCameraToSelection(feature);
        refreshSource();

        if (withScroll) {
            recyclerView.scrollToPosition(index);
        }
    }

    /**
     * Deselects the state of all the features
     */
    private void deselectAll(boolean hideRecycler) {
        for (Feature feature : featureCollection.features()) {
            feature.properties().addProperty(PROPERTY_SELECTED, false);
        }

        if (hideRecycler) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * Selects the state of a feature
     *
     * @param feature the feature to be selected.
     */
    private void selectFeature(Feature feature) {
        feature.properties().addProperty(PROPERTY_SELECTED, true);
    }

    private Feature getSelectedFeature() {
        if (featureCollection != null) {
            for (Feature feature : featureCollection.features()) {
                if (feature.getBooleanProperty(PROPERTY_SELECTED)) {
                    return feature;
                }
            }
        }

        return null;
    }

    /**
     * Animate camera to a feature.
     *
     * @param feature the feature to animate to
     */
    private void animateCameraToSelection(Feature feature, double newZoom) {
        CameraPosition cameraPosition = mapboxMap.getCameraPosition();

        if (animatorSet != null) {
            animatorSet.cancel();
        }

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                createLatLngAnimator(cameraPosition.target, convertToLatLng(feature)),
                createZoomAnimator(cameraPosition.zoom, newZoom),
                createBearingAnimator(cameraPosition.bearing, feature.getNumberProperty("bearing").doubleValue()),
                createTiltAnimator(cameraPosition.tilt, feature.getNumberProperty("tilt").doubleValue())
        );
        animatorSet.start();
    }

    private void animateCameraToSelection(Feature feature) {
        double zoom = feature.getNumberProperty("zoom").doubleValue();
        animateCameraToSelection(feature, zoom);
    }

    /**
     * Set the favourite state of a feature based on the index.
     *
     * @param index the index of the feature to favourite/de-favourite
     */
    public void toggleFavourite(int index) {
        Feature feature = featureCollection.features().get(index);
        String title = feature.getStringProperty(PROPERTY_TITLE);
        boolean currentState = feature.getBooleanProperty(PROPERTY_FAVOURITE);
        feature.properties().addProperty(PROPERTY_FAVOURITE, !currentState);
        View view = viewMap.get(title);

        ImageView imageView = view.findViewById(R.id.logoView);
        //imageView.setImageResource(currentState ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        Bitmap bitmap = SymbolGenerator.generate(view);
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.addImage(title, bitmap);
                refreshSource();
            }
        });
    }

    /**
     * Invoked when the bitmaps have been generated from a view.
     */
    public void setImageGenResults(HashMap<String, View> viewMap, HashMap<String, Bitmap> imageMap) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// calling addImages is faster as separate addImage calls for each bitmap.
                style.addImages(imageMap);
            }
        });
// need to store reference to views to be able to use them as hitboxes for click events.
        MapActivity.this.viewMap = viewMap;
    }

    private void setActivityStep(@ActivityStep int activityStep) {
        Feature selectedFeature = getSelectedFeature();
        double zoom = stepZoomMap.get(activityStep);
        animateCameraToSelection(selectedFeature, zoom);

        currentStep = activityStep;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapboxMap != null) {
            mapboxMap.removeOnMapClickListener(this);
        }
        mapView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (currentStep == STEP_LOADING || currentStep == STEP_READY) {
            setActivityStep(STEP_INITIAL);
            deselectAll(true);
            refreshSource();
        } else {
            super.onBackPressed();
        }
    }

    private LatLng convertToLatLng(Feature feature) {
        Point symbolPoint = (Point) feature.geometry();
        return new LatLng(symbolPoint.latitude(), symbolPoint.longitude());
    }

    private Animator createLatLngAnimator(LatLng currentPosition, LatLng targetPosition) {
        ValueAnimator latLngAnimator = ValueAnimator.ofObject(new LatLngEvaluator(), currentPosition, targetPosition);
        latLngAnimator.setDuration(CAMERA_ANIMATION_TIME);
        latLngAnimator.setInterpolator(new FastOutSlowInInterpolator());
        latLngAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mapboxMap.moveCamera(CameraUpdateFactory.newLatLng((LatLng) animation.getAnimatedValue()));
            }
        });
        return latLngAnimator;
    }

    private Animator createZoomAnimator(double currentZoom, double targetZoom) {
        ValueAnimator zoomAnimator = ValueAnimator.ofFloat((float) currentZoom, (float) targetZoom);
        zoomAnimator.setDuration(CAMERA_ANIMATION_TIME);
        zoomAnimator.setInterpolator(new FastOutSlowInInterpolator());
        zoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mapboxMap.moveCamera(CameraUpdateFactory.zoomTo((Float) animation.getAnimatedValue()));
            }
        });
        return zoomAnimator;
    }

    private Animator createBearingAnimator(double currentBearing, double targetBearing) {
        ValueAnimator bearingAnimator = ValueAnimator.ofFloat((float) currentBearing, (float) targetBearing);
        bearingAnimator.setDuration(CAMERA_ANIMATION_TIME);
        bearingAnimator.setInterpolator(new FastOutSlowInInterpolator());
        bearingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mapboxMap.moveCamera(CameraUpdateFactory.bearingTo((Float) animation.getAnimatedValue()));
            }
        });
        return bearingAnimator;
    }

    private Animator createTiltAnimator(double currentTilt, double targetTilt) {
        ValueAnimator tiltAnimator = ValueAnimator.ofFloat((float) currentTilt, (float) targetTilt);
        tiltAnimator.setDuration(CAMERA_ANIMATION_TIME);
        tiltAnimator.setInterpolator(new FastOutSlowInInterpolator());
        tiltAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mapboxMap.moveCamera(CameraUpdateFactory.tiltTo((Float) animation.getAnimatedValue()));
            }
        });
        return tiltAnimator;
    }

    /**
     * Helper class to evaluate LatLng objects with a ValueAnimator
     */
    private static class LatLngEvaluator implements TypeEvaluator<LatLng> {

        private final LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }

    /**
     * AsyncTask to load data from the assets folder.
     */
    private static class LoadPoiDataTask extends AsyncTask<Void, Void, FeatureCollection> {

        private final WeakReference<MapActivity> activityRef;

        LoadPoiDataTask(MapActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        protected FeatureCollection doInBackground(Void... params) {
            MapActivity activity = activityRef.get();

            if (activity == null) {
                return null;
            }

            Feature a = Feature.fromGeometry(
                    Point.fromLngLat(-57.225365, -33.213144));
            a.addStringProperty("title", "Panda Express");
            a.addStringProperty("poi", "marker");
            a.addStringProperty("style", "Chinese Restaurant");
            a.addStringProperty("description", "Fast-food chain for Chinese standards, including some health-conscious options.");
            a.addStringProperty("call-out", "Some fast Chinese goodies!");
            a.addBooleanProperty("selected", false);
            a.addBooleanProperty("loading", false);
            a.addBooleanProperty("favourite", false);
            a.addNumberProperty("zoom", 13.5);
            a.addNumberProperty("bearing", 35);
            a.addNumberProperty("tilt", 0);

            List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
            symbolLayerIconFeatureList.add(a);
            //String geoJson = loadGeoJsonFromAsset(activity, "sf_poi.geojson");
            return FeatureCollection.fromFeatures(symbolLayerIconFeatureList);
        }

        @Override
        protected void onPostExecute(FeatureCollection featureCollection) {
            super.onPostExecute(featureCollection);
            MapActivity activity = activityRef.get();
            if (featureCollection == null || activity == null) {
                return;
            }
            activity.setupData(featureCollection);
            new GenerateViewIconTask(activity).execute(featureCollection);
        }

        static String loadGeoJsonFromAsset(Context context, String filename) {
            try {
// Load GeoJSON file from local asset folder
                InputStream is = context.getAssets().open(filename);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return new String(buffer, Charset.forName("UTF-8"));
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * AsyncTask to generate Bitmap from Views to be used as iconImage in a SymbolLayer.
     * <p>
     * Call be optionally be called to update the underlying data source after execution.
     * </p>
     * <p>
     * Generating Views on background thread since we are not going to be adding them to the view hierarchy.
     * </p>
     */
    private static class GenerateViewIconTask extends AsyncTask<FeatureCollection, Void, HashMap<String, Bitmap>> {

        private final HashMap<String, View> viewMap = new HashMap<>();
        private final WeakReference<MapActivity> activityRef;
        private final boolean refreshSource;

        GenerateViewIconTask(MapActivity activity, boolean refreshSource) {
            this.activityRef = new WeakReference<>(activity);
            this.refreshSource = refreshSource;
        }

        GenerateViewIconTask(MapActivity activity) {
            this(activity, false);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected HashMap<String, Bitmap> doInBackground(FeatureCollection... params) {
            MapActivity activity = activityRef.get();
            if (activity != null) {
                HashMap<String, Bitmap> imagesMap = new HashMap<>();
                LayoutInflater inflater = LayoutInflater.from(activity);
                FeatureCollection featureCollection = params[0];

                for (Feature feature : featureCollection.features()) {
                   /* View view = inflater.inflate(R.layout.mapillary_layout_callout, null);

                    String name = feature.getStringProperty(PROPERTY_TITLE);
                    TextView titleTv = view.findViewById(R.id.title);
                    titleTv.setText(name);

                    String style = feature.getStringProperty(PROPERTY_STYLE);
                    TextView styleTv = view.findViewById(R.id.style);
                    styleTv.setText(style);

                    boolean favourite = feature.getBooleanProperty(PROPERTY_FAVOURITE);
                    ImageView imageView = view.findViewById(R.id.logoView);
                    imageView.setImageResource(favourite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

                    Bitmap bitmap = SymbolGenerator.generate(view);
                    imagesMap.put(name, bitmap);
                    viewMap.put(name, view);*/
                }

                return imagesMap;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String, Bitmap> bitmapHashMap) {
            super.onPostExecute(bitmapHashMap);
            MapActivity activity = activityRef.get();
            if (activity != null && bitmapHashMap != null) {

                activity.setImageGenResults(viewMap, bitmapHashMap);
                if (refreshSource) {
                    activity.refreshSource();
                }
            }
        }
    }


    /**
     * Utility class to generate Bitmaps for Symbol.
     */
    private static class SymbolGenerator {

        /**
         * Generate a Bitmap from an Android SDK View.
         *
         * @param view the View to be drawn to a Bitmap
         * @return the generated bitmap
         */
        static Bitmap generate(@NonNull View view) {
            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(measureSpec, measureSpec);

            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            view.layout(0, 0, measuredWidth, measuredHeight);
            Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }
    }
    /**
     * RecyclerViewAdapter adapting features to cards.
     */
    static class LocationRecyclerViewAdapter extends
            RecyclerView.Adapter<MapActivity.LocationRecyclerViewAdapter.MyViewHolder> {

        private List<Feature> featureCollection;
        private MapActivity activity;

        LocationRecyclerViewAdapter(MapActivity activity, FeatureCollection featureCollection) {
            this.activity = activity;
            this.featureCollection = featureCollection.features();
        }

        @Override
        public LocationRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_symbol_layer, parent, false);
            return new LocationRecyclerViewAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LocationRecyclerViewAdapter.MyViewHolder holder, int position) {
            Feature feature = featureCollection.get(position);
            holder.title.setText(feature.getStringProperty(PROPERTY_TITLE));
            holder.description.setText(feature.getStringProperty(PROPERTY_DESCRIPTION));
            holder.poi.setText(feature.getStringProperty(PROPERTY_POI));
            holder.style.setText(feature.getStringProperty(PROPERTY_STYLE));
            holder.setClickListener(new ItemClickListener() {
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
            ItemClickListener clickListener;

            MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.textview_title);
                poi = view.findViewById(R.id.textview_poi);
                style = view.findViewById(R.id.textview_style);
                description = view.findViewById(R.id.textview_description);
                singleCard = view.findViewById(R.id.single_location_cardview);
                singleCard.setOnClickListener(this);
            }

            void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getLayoutPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
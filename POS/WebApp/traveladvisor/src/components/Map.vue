<template>
  <MglMap
    ref="testmap"
    :style="`width: ${this.width}; height: ${this.height}; z-index=auto`"
    :accessToken="accessToken"
    :mapStyle.sync="mapStyle"
    @load="onMapLoad"
    @click="onClickMap"
  >
    <MglNavigationControl position="top-right" />
    <MglGeolocateControl position="top-right" v-if="mode == 'showAll'" @geolocate="geoLocate" />
    <MglScaleControl position="bottom-right" />

    <MglMarker
      :v-if="mode != 'create'"
      v-for="location in locations"
      :key="location.id"
      :coordinates="[location.koordinaten.Y, location.koordinaten.X]"
      :draggable="mode === 'update'"
      color="blue"
      @dragend="dragend"
    >
      <MglPopup v-if="mode === 'showAll'">
        <v-card flat elevation="0">
          <v-img
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            width="800px"
            height="200px"
            :src="'https://x.kinja-static.com/assets/images/logos/placeholders/default.png'"
            aspect-ratio="2"
          >
            <v-card-title>{{ location.bezeichnung }}</v-card-title>
          </v-img>
          <v-btn :to="{ name: 'Location anzeigen', params: { id: location.id }}" text>Mehr Details</v-btn>
        </v-card>
      </MglPopup>
    </MglMarker>
  </MglMap>
</template>

<script>
import Mapbox from "mapbox-gl";
import {
  MglMap,
  MglNavigationControl,
  MglGeolocateControl,
  MglScaleControl,
  MglMarker,
  MglPopup
} from "vue-mapbox";

export default {
  components: {
    MglMap,
    MglNavigationControl,
    MglGeolocateControl,
    MglScaleControl,
    MglMarker,
    MglPopup
  },
  data() {
    return {
      accessToken:
        "pk.eyJ1IjoibWtsZWluZWdnZXIiLCJhIjoiY2syeDB1bXNuMDc3ZzNndGFvMnhhNDB0eSJ9.g36eaDLBy327_G9xTFVWKQ",
      mapStyle: "mapbox://styles/mapbox/streets-v11",
      map: null,
      marker: null,
      valid: false
    };
  },
  props: {
    width: String,
    height: String,
    locations: Array,
    mode: String,
    center: Array
  },
  created() {
    this.mapbox = Mapbox;
  },
  methods: {
    async onMapLoad(event) {
      const asyncActions = event.component.actions;

      const newParams = await asyncActions.flyTo({
        center: this.center,
        zoom: 12,
        speed: 1
      });

      console.log(this.$refs.testmap);
    },
    validate() {
      if (
        this.locations[0].koordinaten.X != 0 &&
        this.locations[0].koordinaten.Y != 0
      )
        this.valid = true;
    },
    onClickMap(event) {
      if (this.mode === "create") {
        this.$emit("update:mode", "update");
        this.locations[0].koordinaten.x = event.mapboxEvent.lngLat.lat;
        this.locations[0].koordinaten.y = event.mapboxEvent.lngLat.lng;
        let marker = new Mapbox.Marker({
          color: "blue",
          draggable: true
        })
          .setLngLat([
            event.mapboxEvent.lngLat.lng,
            event.mapboxEvent.lngLat.lat
          ])
          .addTo(event.map);
      }
    },
    dragend(event) {
      if (this.mode === "update") {
        this.locations[0].koordinaten.X = event.marker._lngLat.lat;
        this.locations[0].koordinaten.Y = event.marker._lngLat.lng;
      }
    },
    geoLocate(event) {
      if (event.map.getSource("polygon")) {
        event.map.removeLayer("polygon");
        event.map.removeSource("polygon");
      }

      event.map.addSource(
        "polygon",
        this.createGeoJSONCircle(event.mapboxEvent.coords, 3)
      );

      event.map.addLayer({
        id: "polygon",
        type: "fill",
        source: "polygon",
        layout: {},
        paint: {
          "fill-color": "blue",
          "fill-opacity": 0.5
        }
      });
    },
    createGeoJSONCircle(center, radiusInKm, points) {
      if (!points) points = 64;

      var coords = {
        latitude: center.latitude,
        longitude: center.longitude
      };

      var km = radiusInKm;

      var ret = [];
      var distanceX =
        km / (111.32 * Math.cos((coords.latitude * Math.PI) / 180));
      var distanceY = km / 110.574;

      var theta, x, y;
      for (var i = 0; i < points; i++) {
        theta = (i / points) * (2 * Math.PI);
        x = distanceX * Math.cos(theta);
        y = distanceY * Math.sin(theta);

        ret.push([coords.longitude + x, coords.latitude + y]);
      }
      ret.push(ret[0]);

      return {
        type: "geojson",
        data: {
          type: "FeatureCollection",
          features: [
            {
              type: "Feature",
              geometry: {
                type: "Polygon",
                coordinates: [ret]
              }
            }
          ]
        }
      };
    }
  }
};
</script>
<style scoped>
.mapboxgl-popup {
  min-width: 400px;
  min-height: 600px;
  font: 12px/20px "Helvetica Neue", Arial, Helvetica, sans-serif;
}
</style>
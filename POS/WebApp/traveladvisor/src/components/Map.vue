<template>
<v-card class="mx-auto" :flat="true" :elevation="6"> 
  <div class="map">
    <div style="width: 100%; height: 650px; z-index=auto" id="mapContainer" />
  </div>
</v-card>
</template>

<script>
var H = window.H;

export default {
  name: "Map",
  data: function() {
    return {
      platform: null,
      map: null,
      behavior: null,
      ui: null,
      defaultLayers: null,
      lat: 46.608449,
      lng: 13.850268,
    };
  },
  created: function() {
    this.$root.$on(
      "switchLayerSatelliteTraffic",
      this.switchLayerSatelliteTraffic
    );
  },
  mounted: function() {
    this.platform = new H.service.Platform({
      app_id: "mVTP9XeAIqzMFgOJT6Ro",
      app_code: "RjPyj7psHGEa5agh7kFW-g",
      useHTTPS: true
    });
    var zoom_default = 14;
    this.defaultLayers = this.platform.createDefaultLayers();
    this.map = new H.Map(
      document.getElementById("mapContainer"),
      this.defaultLayers.normal.map,
      {
        zoom: zoom_default,
        center: { lat: this.lat, lng: this.lng }
      }
    );
    this.behavior = new H.mapevents.Behavior(
      new H.mapevents.MapEvents(this.map)
    );
    this.ui = H.ui.UI.createDefault(this.map, this.defaultLayers);
    this.useMetricMeasurements(this.map, this.defaultLayers);
  },
  methods: {
    reverseGeocodingSuccess: function(result) {
      var location = result.Response.View[0].Result[0];
      this.msg = location.Location.Address.Label;
    },
    reverseGeocoding: function() {
      var reverseGeocodingParameters = {
        prox: "" + this.lat + "," + this.lng + ",150",
        mode: "retrieveAddresses",
        maxresults: 1
      };
      var geocoder = this.platform.getGeocodingService();
      geocoder.reverseGeocode(
        reverseGeocodingParameters,
        this.reverseGeocodingSuccess,
        function(e) {
          this.msg = e;
        }
      );
    },
    switchLayerNormalTraffic: function() {
      this.map.setBaseLayer(this.defaultLayers.normal.traffic);
    },
    switchLayerSatelliteTraffic: function() {
      this.map.setBaseLayer(this.defaultLayers.satellite.traffic);
    },
    useMetricMeasurements: function(map, defaultLayers) {
      var ui = H.ui.UI.createDefault(map, defaultLayers);
      ui.setUnitSystem(H.ui.UnitSystem.METRIC);
    }
  }
};
</script>

<style scoped>
</style>
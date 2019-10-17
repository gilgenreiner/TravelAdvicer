<template>
  <v-card class="mx-auto" :elevation="6">
    <div class="map">
      <div :style="`width: ${this.width}; height: ${this.height}; z-index=auto`" id="mapContainer" />
    </div>
  </v-card>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

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
      mapEvents: null,
      lat: 46.608449,
      lng: 13.850268
    };
  },
  props: {
    width: String,
    height: String
  },
  created: function() {
    this.loadLocations();
  },
  mounted: function() {
    this.platform = new H.service.Platform({
      apikey: "GNUmK8T41k6qXZe3gA2cOcdTThPfTznTYP_UfemUzp8",
      useHTTPS: true
    });
    this.defaultLayers = this.platform.createDefaultLayers();
    this.map = new H.Map(
      document.getElementById("mapContainer"),
      this.defaultLayers.vector.normal.map,
      {
        zoom: 14,
        center: { lat: this.lat, lng: this.lng }
      }
    );
    this.mapEvents = new H.mapevents.MapEvents(this.map);
    this.behavior = new H.mapevents.Behavior(this.mapEvents);
    this.ui = H.ui.UI.createDefault(this.map, this.defaultLayers);
    this.drawPoints();
  },
  methods: {
    drawPoints: function() {
      for (let i = 0; i < this.allLocations.length; i++) {
        let coords = {
          lat: this.allLocations[i].koordinaten.X,
          lng: this.allLocations[i].koordinaten.Y
        };

        let svgMarkup =
          '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M12 0c-4.198 0-8 3.403-8 7.602 0 4.198 3.469 9.21 8 16.398 4.531-7.188 8-12.2 8-16.398 0-4.199-3.801-7.602-8-7.602zm0 11c-1.657 0-3-1.343-3-3s1.343-3 3-3 3 1.343 3 3-1.343 3-3 3z" fill="blue" /></svg>';
        let icon = new H.map.Icon(svgMarkup);
        let marker = new H.map.Marker(coords, { icon: icon });
        marker.setData(
          `<div width="300px"><p>Das ist die Location ${ this.allLocations[i].bezeichnung }</p>` +
            `<a style="-webkit-appearance: button; -moz-appearance: button; appearance: button; text-decoration: none;color: initial;" 
                      href="http://localhost:8081/locations/${ this.allLocations[i].id }"> Mehr details anzeigen </a ></div>`
        );
        marker.addEventListener(
          "tap",
          event => {
            var bubble = new H.ui.InfoBubble(event.target.getGeometry(), {
              content: event.target.getData()
            });
            this.ui.addBubble(bubble);
          },
          false
        );

        this.map.addObject(marker);
      }
    },
    ...mapActions(["loadLocations"])
  },
  computed: mapGetters(["allLocations"])
};
</script>

<style scoped>
</style>
<template>
  <v-card :elevation="4">
    <div class="map">
      <div :style="`width: ${this.width}; height: ${this.height}; z-index=auto`" id="mapContainer" />
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
      mapEvents: null,
      lat: 46.608449,
      lng: 13.850268
    };
  },
  props: {
    width: String,
    height: String,
    locations: Array,
    mode: {
      type: String,
      default: "showDetails"
    }
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

    window.addEventListener("resize", () => this.map.getViewPort().resize());

    this.mapEvents = new H.mapevents.MapEvents(this.map);
    this.behavior = new H.mapevents.Behavior(this.mapEvents);
    this.ui = H.ui.UI.createDefault(this.map, this.defaultLayers);
    if (this.mode === "showDetails") {
      this.drawPoints();
    }
    if (this.mode === "createNew") {
      this.setUpClickListener(this.map, this.locations);
    }
    if (this.mode === "update") {
      this.addDraggableMarker(this.map, this.behavior, this.locations);
    }
  },
  methods: {
    drawPoints: function() {
      for (let i = 0; i < this.locations.length; i++) {
        if (this.locations[i].aktiv === true) {
          let coords = {
            lat: this.locations[i].koordinaten.X,
            lng: this.locations[i].koordinaten.Y
          };

          let svgMarkup =
            '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M12 0c-4.198 0-8 3.403-8 7.602 0 4.198 3.469 9.21 8 16.398 4.531-7.188 8-12.2 8-16.398 0-4.199-3.801-7.602-8-7.602zm0 11c-1.657 0-3-1.343-3-3s1.343-3 3-3 3 1.343 3 3-1.343 3-3 3z" fill="blue" /></svg>';
          let icon = new H.map.Icon(svgMarkup);
          let marker = new H.map.Marker(coords, { icon: icon });
          marker.setData(
            `<div width="300px" style="background-color: transparent;">
            <img src="${this.locations[i].img}" width="350" height="200">
            <h2>${this.locations[i].bezeichnung}</h2>
            <a style="border: 0; background: none; box-shadow: none; border-radius: 0px;" 
            href="http://localhost:8081/locations/${this.locations[i].id}">Mehr details anzeigen</a></div>`
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
      }
    },
    setUpClickListener(map, locations) {
      map.addEventListener("tap", function(evt) {
        map.removeObjects(map.getObjects());
        var coord = map.screenToGeo(
          evt.currentPointer.viewportX,
          evt.currentPointer.viewportY
        );

        locations[0].koordinaten.X = coord.lat;
        locations[0].koordinaten.Y = coord.lng;

        let svgMarkup =
          '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M12 0c-4.198 0-8 3.403-8 7.602 0 4.198 3.469 9.21 8 16.398 4.531-7.188 8-12.2 8-16.398 0-4.199-3.801-7.602-8-7.602zm0 11c-1.657 0-3-1.343-3-3s1.343-3 3-3 3 1.343 3 3-1.343 3-3 3z" fill="blue" /></svg>';
        let icon = new H.map.Icon(svgMarkup);
        let marker = new H.map.Marker(coord, { icon: icon });
        map.addObject(marker);
      });
    },
    addDraggableMarker(map, behavior, locations) {
      let svgMarkup =
        '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M12 0c-4.198 0-8 3.403-8 7.602 0 4.198 3.469 9.21 8 16.398 4.531-7.188 8-12.2 8-16.398 0-4.199-3.801-7.602-8-7.602zm0 11c-1.657 0-3-1.343-3-3s1.343-3 3-3 3 1.343 3 3-1.343 3-3 3z" fill="blue" /></svg>';
      let icon = new H.map.Icon(svgMarkup);
      let marker = new H.map.Marker(
        {
          lat: locations[0].koordinaten.X,
          lng: locations[0].koordinaten.Y
        },
        {
          volatility: true,
          icon: icon
        }
      );

      marker.draggable = true;
      map.addObject(marker);

      map.addEventListener(
        "dragstart",
        function(ev) {
          var target = ev.target,
            pointer = ev.currentPointer;
          if (target instanceof H.map.Marker) {
            var targetPosition = map.geoToScreen(target.getGeometry());
            target["offset"] = new H.math.Point(
              pointer.viewportX - targetPosition.x,
              pointer.viewportY - targetPosition.y
            );
            behavior.disable();
          }
        },
        false
      );

      map.addEventListener(
        "dragend",
        function(ev) {
          var target = ev.target;
          if (target instanceof H.map.Marker) {
            behavior.enable();
          }
        },
        false
      );

      map.addEventListener(
        "drag",
        function(ev) {
          var target = ev.target,
            pointer = ev.currentPointer;
          if (target instanceof H.map.Marker) {
            var coord = map.screenToGeo(
              pointer.viewportX - target["offset"].x,
              pointer.viewportY - target["offset"].y
            );
            target.setGeometry(coord);

            locations[0].koordinaten.X = coord.lat;
            locations[0].koordinaten.Y = coord.lng;
          }
        },
        false
      );
    }
  }
};
</script>

<style scoped>
</style>
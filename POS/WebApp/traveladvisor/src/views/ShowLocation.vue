<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn class="ml-0" :to="{path: `/` }">
          <v-icon left>arrow_back</v-icon>Zurück
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="4">
        <LocationDetailReadonly
          :selectedLocation.sync="(getSelectedLocation === undefined) ? defaultLocation : getSelectedLocation"
        />
      </v-col>
      <v-col cols="8">
        <Map
          :width="'100%'"
          :height="'606px'"
          :locations="new Array(getSelectedLocation)"
          :mode="mode"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import Map from "@/components/Map";
import LocationDetailReadonly from "@/components/LocationDetailReadonly";

export default {
  name: "LocationDetails",
  components: {
    Map,
    LocationDetailReadonly
  },
  data() {
    return {
      backup: {},
      mode: "showDetails",
      component: "Map",
      defaultLocation: {
        bezeichnung: "",
        beschreibung: "",
        aktiv: false,
        punkte: 0,
        branchen: [],
        koordinaten: { x: 0, y: 0 }
      }
    };
  },
  computed: {
    ...mapGetters(["allLocations"]),
    getSelectedLocation() {
      return this.allLocations.filter(
        location => location.id == this.$route.params.id
      )[0];
    }
  },
  created() {
    if (this.allLocations.length === 0) {
      this.$store.dispatch("loadLocationById", this.$route.params.id);
    }
  }
};
</script>

<style>
.buttons {
  float: right;
}
.v-expansion-panel {
  box-shadow: none;
}
</style>

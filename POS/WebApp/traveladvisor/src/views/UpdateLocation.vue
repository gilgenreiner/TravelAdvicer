<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn class="ml-0" @click="$router.push({name: component})">
          <v-icon left>arrow_back</v-icon>Zurück
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="4">
        <LocationDetail
          ref="details"
          :selectedLocation.sync="(getSelectedLocation === undefined) ? defaultLocation : getSelectedLocation"
        />
      </v-col>
      <v-col cols="8">
        <v-hover v-slot:default="{ hover }">
          <v-card :elevation="hover ? 12 : 4">
            <Map
              :width="'100%'"
              :height="'676px'"
              :locations="new Array(getSelectedLocation)"
              :center.sync="getCoordsFromSelected"
              :mode="mode"
            />
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
    <v-row class="buttons">
      <v-col cols="12">
        <v-btn class="mr-2" @click="doCancel">Cancel</v-btn>
        <v-btn @click="doUpdateLocation()" :loading="isLoadingLocations">Location aktualisieren</v-btn>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import Map from "@/components/Map";
import LocationDetail from "@/components/LocationDetail";

export default {
  name: "LocationDetails",
  components: {
    Map,
    LocationDetail
  },
  data() {
    return {
      mode: "update",
      isDoUpdateButtonPressed: false,
      component: "Locations",
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
  watch: {
    isLoadingLocations() {
      if (
        this.isLoadingLocations === false &&
        this.isDoUpdateButtonPressed === true
      ) {
        this.isDoUpdateButtonPressed = false;
        this.$router.push({ name: this.component });
      }
    }
  },
  methods: {
    doUpdateLocation() {
      this.$refs.details.validate();
      if (this.$refs.details.valid === true) {
        this.$store.dispatch("updateLocationById", this.getSelectedLocation);
        this.isDoUpdateButtonPressed = true;
      }
    },
    doCancel() {
      this.$router.push({ name: this.component });
    }
  },
  computed: {
    ...mapGetters(["allLocations", "isLoadingLocations"]),
    getSelectedLocation() {
      return this.allLocations.filter(
        location => location.id == this.$route.params.id
      )[0];
    },
    getCoordsFromSelected() {
      return this.allLocations.length !== 0
        ? [
            this.getSelectedLocation.koordinaten.Y,
            this.getSelectedLocation.koordinaten.X
          ]
        : [0, 0];
    }
  },
  created() {
    if (this.allLocations.length == 0) {
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

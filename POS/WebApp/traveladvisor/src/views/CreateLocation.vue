<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn class="ml-0" @click="doCancel()">
          <v-icon left>arrow_back</v-icon>Zurück
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="4">
        <LocationDetail ref="details" :selectedLocation.sync="defaultLocation" />
      </v-col>
      <v-col cols="8">
        <Map
          ref="map"
          :width="'100%'"
          :height="'676px'"
          :locations="new Array(defaultLocation)"
          :mode="mode"
        />
      </v-col>
    </v-row>
    <v-row class="buttons">
      <v-col cols="12">
        <v-btn class="mr-2" @click="doCancel">Cancel</v-btn>
        <v-btn @click="doAddLocation()" :loading="isLoadingLocations">Location hinzufügen</v-btn>
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
      defaultLocation: {
        bezeichnung: "",
        beschreibung: "",
        aktiv: false,
        punkte: 0,
        branchen: [],
        besitzer: { id: "b717f71a-a902-4c1a-9fa9-659fc8c" },
        koordinaten: { x: 0, y: 0 }
      },
      mode: "create",
      component: "Locations",
      isDoCreateButtonPressed: false
    };
  },
  watch: {
    isLoadingLocations() {
      if (
        this.isLoadingLocations === false &&
        this.isDoCreateButtonPressed === true
      ) {
        this.isDoCreateButtonPressed = false;
        this.$router.push({ name: this.component });
      }
    }
  },
  methods: {
    doAddLocation() {
      this.$refs.details.validate();
      if (this.$refs.details.valid === true) {
        this.$store.dispatch("addLocation", this.defaultLocation);
        this.isDoCreateButtonPressed = true;
      }
    },
    doCancel() {
      this.$router.push({ name: this.component });
    }
  },
  computed: {
    ...mapGetters(["isLoadingLocations"])
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

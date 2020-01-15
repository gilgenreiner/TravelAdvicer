<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn class="ml-0" @click="$router.go(-1)">
          <v-icon left>arrow_back</v-icon>Zurück
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="4">
        <LocationDetail ref="details" :selectedLocation.sync="defaultLocation" />
      </v-col>
      <v-col cols="8">
        <v-hover v-slot:default="{ hover }">
          <v-card :elevation="hover ? 12 : 4">
            <Map
              ref="map"
              :width="'100%'"
              :height="'676px'"
              :locations="new Array(defaultLocation)"
              :center="[13.844549, 46.614073]"
              :mode.sync="mode"
            />
          </v-card>
        </v-hover>
        <p v-if="valid == false" class="red--text mt-1 mb-0">Es muss eine Location ausgewählt sein!</p>
      </v-col>
    </v-row>
    <v-row class="buttons">
      <v-col cols="12">
        <v-btn class="mr-2" @click="$router.go(-1)">Cancel</v-btn>
        <v-btn @click="doAddLocation()" :loading="isLoadingLocations">Location hinzufügen</v-btn>
      </v-col>
    </v-row>
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
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
        besitzer: { id: "" },
        koordinaten: { X: 0, Y: 0 },
      },
      mode: "create",
      isDoCreateButtonPressed: false,
      valid: true,
      snackbar: false,
      text: ""
    };
  },
  watch: {
    isLoadingLocations() {
      if (
        !this.isLoadingLocations &&
        this.isDoCreateButtonPressed &&
        !this.error
      ) {
        this.isDoCreateButtonPressed = false;
        this.$router.push({ name: "Locations" });
      }
    },
    errorLocations() {
      if (this.errorLocations) {
        this.text = "Konnte nicht gespeichert werden - " + this.errorLocations;
        this.snackbar = true;
      }
    },
    errorBranchen() {
      if (this.errorBranchen) {
        this.text =
          "Branchen konnten nicht geladen werden - " + this.errorBranchen;
        this.snackbar = true;
      }
    }
  },
  methods: {
    doAddLocation() {
      this.$refs.details.validate();
      this.$refs.map.validateForCreate();
      this.valid = this.$refs.map.valid;
      if (this.$refs.details.valid && this.$refs.map.valid) {
        this.defaultLocation.besitzer.id = this.user.data.id;
        this.$store.dispatch("addLocation", this.defaultLocation);
        this.isDoCreateButtonPressed = true;
      }
    }
  },
  computed: {
    ...mapGetters(["isLoadingLocations", "errorLocations", "errorBranchen", "user"])
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

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
        <LocationDetail ref="details" :selectedLocation.sync="getSelectedLocation" />
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
        <v-btn class="mr-2" @click="$router.go(-1)">Cancel</v-btn>
        <v-btn @click="doUpdateLocation()" :loading="isLoadingLocations">Location aktualisieren</v-btn>
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
      mode: "update",
      isDoUpdateButtonPressed: false,
      snackbar: false,
      text: "",
      defaultLocation: {
        bezeichnung: "",
        beschreibung: "",
        aktiv: false,
        punkte: 0,
        branchen: [],
        besitzer: { id: ""},
        koordinaten: { x: 0, y: 0 }
      }
    };
  },
  watch: {
    isLoadingLocations() {
      if (
        !this.isLoadingLocations &&
        this.isDoUpdateButtonPressed &&
        !this.error
      ) {
        this.isDoUpdateButtonPressed = false;
        this.$router.push({ name: "Locations" });
      }
    },
    errorLocations() {
      if (this.errorLocations) {
        this.text = "Konnte nicht aktualisiert werden - " + this.errorLocations;
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
    doUpdateLocation() {
      this.$refs.details.validate();
      if (this.$refs.details.valid === true) {
        this.$store.dispatch("updateLocationById", this.getSelectedLocation);
        this.isDoUpdateButtonPressed = true;
      }
    }
  },
  computed: {
    ...mapGetters([
      "allLocations",
      "isLoadingLocations",
      "errorLocations",
      "errorBranchen"
    ]),
    getSelectedLocation() {
      return this.allLocations.filter(
        location => location.id == this.$route.params.id
      )[0];
    },
    getCoordsFromSelected() {
      return [
        this.getSelectedLocation.koordinaten.Y,
        this.getSelectedLocation.koordinaten.X
      ];
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

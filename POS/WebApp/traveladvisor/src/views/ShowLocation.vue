<template>
  <div>
    <v-row>
      <v-col cols="12">
        <v-btn class="ml-0" @click="$router.go(-1)">
          <v-icon left>arrow_back</v-icon>Zurück
        </v-btn>
        <v-btn class="ml-2" @click="dialog = !dialog">Rezensionen</v-btn>
        <RezensionenPopup :dialog.sync="dialog" :location="getSelectedLocation" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="4">
        <LocationDetailReadonly
          :selectedLocation.sync="(getSelectedLocation === undefined) ? defaultLocation : getSelectedLocation"
        />
      </v-col>
      <v-col cols="8">
        <v-hover v-slot:default="{ hover }">
          <v-card :elevation="hover ? 12 : 4">
            <Map
              :width="'100%'"
              :height="'606px'"
              :locations="(getSelectedLocation === undefined) ?  new Array(defaultLocation) : new Array(getSelectedLocation)"
              :center.sync="getCoordsFromSelected"
              :mode="mode"
            />
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
    <v-row class="mt-4 ml-0" v-if="allBonuses.length > 0">
      <v-label>Prämien:</v-label>
    </v-row>
    <v-row>
      <v-col v-for="bonus in allBonuses" :key="bonus.id" lg="3" md="4" sm="6">
        <BonusListItem :bonus="bonus" :edit="false" />
      </v-col>
    </v-row>
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import Map from "@/components/Map";
import LocationDetailReadonly from "@/components/LocationDetailReadonly";
import RezensionenPopup from "@/components/popups/ShowRezensionenPopup";
import BonusListItem from "@/components/BonusListItem";

export default {
  name: "LocationDetails",
  components: {
    Map,
    LocationDetailReadonly,
    BonusListItem,
    RezensionenPopup
  },
  data() {
    return {
      backup: {},
      dialog: false,
      mode: "showDetails",
      snackbar: false,
      text: "",
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
    ...mapGetters(["allLocations", "errorLocations", "allBonuses"]),
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
  watch: {
    errorLocations() {
      if (this.errorLocations) {
        this.text = "Konnte nicht geladen werden - " + this.errorLocations;
        this.snackbar = true;
      }
    }
  },
  created() {
    if (this.allLocations.length === 0) {
      this.$store.dispatch("loadLocationById", this.$route.params.id);
    }
    this.$store.dispatch("loadBonuses", this.$route.params.id);
  }
};
</script>

<style >
.v-label {
  font-size: 20px;
}
</style>

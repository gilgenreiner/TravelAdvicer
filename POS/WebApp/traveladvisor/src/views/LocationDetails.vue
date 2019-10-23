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
          :selectedLocation.sync="(mode==='create') ? defaultLocation: selectedLocation"
          :mode="mode"
        />
      </v-col>
      <v-col cols="8">
        <Map
          :width="'100%'"
          :height="(mode === 'show') ? '606px' : '676px'"
          :locations="(mode==='create') ? new Array(defaultLocation): new Array(selectedLocation)"
          :mode="(mode == 'create') ? 'createNew': 'update'"
        />
      </v-col>
    </v-row>
    <v-row v-show="mode !== 'show'" class="buttons">
      <v-col cols="12">
        <v-btn class="mr-2" @click="doCancel">Cancel</v-btn>
        <v-btn v-show="mode === 'update'" @click="doUpdateLocation">Location aktualisieren</v-btn>
        <v-btn v-show="mode === 'create'" @click="doAddLocation()">Location hinzufügen</v-btn>
      </v-col>
    </v-row>
    <v-row v-show="mode === 'show'">
      <v-col cols="12">
        <Rezensionen />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

import Map from "@/components/Map";
import LocationDetail from "@/components/LocationDetail";
import Rezensionen from "@/components/Rezensionen";

export default {
  name: "LocationDetails",
  components: {
    Map,
    Rezensionen,
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
        koordinaten: { X: 0, Y: 0 }
      },
      backup: {}
    };
  },
  methods: {
    ...mapActions(["loadLocationById", "updateLocationById", "addLocation"]),
    doUpdateLocation() {
      this.$refs.details.validate();
      if (this.$refs.details.valid === true) {
        this.updateLocationById(this.selectedLocation);
        this.$router.push({ name: this.component });
      }
    },
    doAddLocation() {
      this.$refs.details.validate();
      if (this.$refs.details.valid === true) {
        this.addLocation(this.defaultLocation);
        this.$router.push({ name: this.component });
      }
    },
    doCancel() {
      this.$router.push({ name: this.component });
    }
  },
  props: {
    mode: {
      default: "show",
      type: String
    },
    component: {
      default: "Map",
      type: String
    }
  },
  computed: mapGetters(["selectedLocation"]),
  created() {
    if (this.mode !== "create") {
      this.loadLocationById(this.$route.params.id);
    }
    this.backup = this.selectedLocation;
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

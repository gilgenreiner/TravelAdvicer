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
        <LocationDetailReadonly :selectedLocation.sync="getSelectedLocation" />
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
import { mapGetters, mapActions } from "vuex";

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
      component: "Map"
    };
  },
  methods: {
    ...mapActions(["loadLocationById"])
  },
  computed: {
    ...mapGetters(["allLocations"]),
    getSelectedLocation() {
      return this.allLocations.filter(
        location => location.id == this.$route.params.id
      )[0];
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

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
        <LocationDetail ref="details" :selectedLocation.sync="getSelectedLocation" :mode="mode" />
      </v-col>
      <v-col cols="8">
        <Map
          :width="'100%'"
          :height="'676px'"
          :locations="new Array(getSelectedLocation)"
          :mode="mode"
        />
      </v-col>
    </v-row>
    <v-row class="buttons">
      <v-col cols="12">
        <v-btn class="mr-2" @click="doCancel">Cancel</v-btn>
        <v-btn @click="doUpdateLocation()" :loading="isLoading">Location aktualisieren</v-btn>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

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
      backup: {},
      mode: "update",
      isDoUpdateButtonPressed: false
    };
  },
  watch: {
    isLoading() {
      if (this.isLoading == false && this.isDoUpdateButtonPressed == true) {
        this.isDoUpdateButtonPressed = false;
        this.$router.push({ name: this.component });
      }
    }
  },
  methods: {
    ...mapActions(["loadLocationById", "updateLocationById"]),
    doUpdateLocation() {
      this.$refs.details.validate();
      if (this.$refs.details.valid === true) {
        this.updateLocationById(this.selectedLocation);
        this.isDoUpdateButtonPressed = true;
      }
    },
    doCancel() {
      this.$router.push({ name: this.component });
    }
  },
  props: {
    component: {
      default: "Map",
      type: String
    }
  },
  computed: {
    ...mapGetters(["allLocations", "isLoading"]),
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

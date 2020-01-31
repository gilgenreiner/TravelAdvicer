<template>
  <div class="locations">
    <v-container fluid>
      <v-row>
        <v-col cols="12">
          <v-btn :to="{ name: 'Location erstellen' }">Location hinzufügen</v-btn>
        </v-col>
      </v-row>
      <v-row v-if="allLocations.length > 0">
        <v-col v-for="location in allLocations" :key="location.id" lg="3" md="4" sm="6">
          <LocationListItem :location="location" />
        </v-col>
      </v-row>
      <v-row v-else justify="center">
        <v-label>Keine locations vorhanden</v-label>
      </v-row>
    </v-container>
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import LocationListItem from "@/components/LocationListItem";

export default {
  components: {
    LocationListItem
  },
  data() {
    return {
      snackbar: false,
      text: ""
    };
  },
  computed: mapGetters(["allLocations", "errorLocations", "user"]),
  watch: {
    error() {
      if (this.errorLocations) {
        this.text = "Konnte nicht geladen werden - " + this.errorLocations;
        this.snackbar = true;
      }
    }
  },
  created() {
    this.$store.dispatch("loadLocations", {
      besitzer: this.user.data.id,
      loadBranchen: true
    });
  }
};
</script>

<style scoped>
.locations {
  margin-left: 20px;
  padding-right: 20px;
}

.v-label {
  font-size: 30px;
}
</style>
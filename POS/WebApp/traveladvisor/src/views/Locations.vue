<template>
  <div class="locations">
    <v-container fluid>
      <v-row>
        <v-col cols="12">
          <v-btn :to="{ name: 'Location erstellen' }">Location hinzufügen</v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="location in allLocations" :key="location.id" lg="3" md="4" sm="6">
          <LocationListItem :location="location" />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

import LocationListItem from "@/components/LocationListItem";

export default {
  components: {
    LocationListItem
  },
  methods: {
    ...mapActions(["loadLocations"])
  },
  computed: mapGetters(["allLocations"]),
  created() {
    this.loadLocations();
  }
};
</script>

<style scoped>
.locations {
  margin-left: 20px;
  padding-right: 20px;
}
</style>
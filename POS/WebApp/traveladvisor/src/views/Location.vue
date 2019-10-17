<template>
  <div class="locations" style="margin-left: 20px;">
    <v-container fluid>
      <v-row>
        <v-col v-for="location in allLocations" :key="location.id" lg="3" md="4" sm="6">
          <LocationListItem :location="location" />
        </v-col>
      </v-row>
       <v-btn
        :to="{ name: 'Location-details', params: { id: 'newLocation', mode: 'create' }}"
      >Add Location</v-btn>
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
</style>
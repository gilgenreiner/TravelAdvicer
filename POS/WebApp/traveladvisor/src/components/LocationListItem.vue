<template>
  <v-card class="mx-auto" :elevation="5" flat height="300px">
    <v-img
      class="white--text align-end"
      height="200px"
      src="https://picsum.photos/510/300?random"
      aspect-ratio="2"
    >
      <v-card-title>{{ location.bezeichnung }}</v-card-title>
    </v-img>

    <v-card-text class="black--text">{{ location.beschreibung }}</v-card-text>
    <v-card-actions class="btn">
      <v-btn
        icon
        :to="{ name: 'Location-details', params: { id: location.id, mode: 'show' }}"
      >
        <v-icon>expand_more</v-icon>
      </v-btn>
      <v-btn
        icon
        :to="{ name: 'Location-details', params: { id: location.id, mode: 'update' }}"
      >
        <v-icon>edit</v-icon>
      </v-btn>
      <LocationDeletePopup :location="location" />
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions } from "vuex";

import LocationDeletePopup from "@/components/popups/DeleteLocationPopup.vue";

export default {
  components: {
    LocationDeletePopup
  },
  props: {
    location: Object
  }
};
</script>

<style scoped>
.btn {
  float: right;
}
</style>

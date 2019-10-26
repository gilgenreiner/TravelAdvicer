<template>
  <v-hover v-slot:default="{ hover }">
    <v-card :class="{ 'on-hover': hover }" :elevation="hover ? 12 : 4" flat height="295px">
      <v-img
        class="white--text align-end"
        height="200px"
        :src="location.img || 'https://x.kinja-static.com/assets/images/logos/placeholders/default.png'"
        aspect-ratio="2"
      >
        <v-card-title>{{ location.bezeichnung }}</v-card-title>
      </v-img>
      <v-card-text class="black--text">
        <p>{{ location.beschreibung }}</p>
        <div class="btn">
          <v-btn icon :to="{ name: 'Location aktualisieren', params: { id: location.id }}">
            <v-icon>expand_more</v-icon>
          </v-btn>
          <v-btn icon :to="{ name: 'Location aktualisieren', params: { id: location.id }}">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn icon @click="dialog = true">
            <v-icon>delete</v-icon>
          </v-btn>
          <LocationDeletePopup :dialog.sync="dialog" :location="location" />
        </div>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
import LocationDeletePopup from "@/components/popups/DeleteLocationPopup.vue";

export default {
  components: {
    LocationDeletePopup
  },
  data() {
    return {
      dialog: false
    };
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
.v-card {
  transition: opacity 0.4s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.8;
}
</style>

<template>
  <div>
    <v-btn icon @click="dialog = true">
      <v-icon>delete</v-icon>
    </v-btn>
    <v-dialog v-model="dialog" max-width="300">
      <v-card>
        <v-card-title class="headline">Location löschen</v-card-title>
        <v-card-text>Wollen Sie die Location "{{ location.bezeichnung }}" wirklich löschen</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green" text @click="dialog = false">Cancel</v-btn>
          <v-btn color="green" text @click="deleteLocation(location.id)">OK</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "LocationDeletePopup",
  data() {
    return {
      dialog: false
    };
  },
  props: {
    location: Object
  },
  methods: {
    ...mapActions(["deleteLocation"])
  }
};
</script>

<style>
</style>
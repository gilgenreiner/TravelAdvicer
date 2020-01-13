<template>
  <v-dialog v-model="dialog" max-width="300" persistent>
    <v-card>
      <v-card-title class="headline">Location löschen</v-card-title>
      <v-card-text>Wollen Sie die Location "{{ location.bezeichnung }}" wirklich löschen</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green" text @click="$emit('update:dialog', false)">Cancel</v-btn>
        <v-btn
          color="green"
          text
          @click="deleteLocation(location.id)"
          :loading="isLoadingLocations"
        >OK</v-btn>
      </v-card-actions>
    </v-card>
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
  </v-dialog>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "DeleteRezensionPopup",
  data() {
    return {
      snackbar: false,
      text: ""
    };
  },
  props: {
    location: Object,
    dialog: Boolean
  },
  methods: {
    deleteLocation(id) {
      this.$store.dispatch("deleteLocation", id);
    }
  },
  watch: {
    errorLocations() {
      if (this.errorLocations) {
        this.text = "Konnte nicht gelöscht werden - " + this.errorLocations;
        this.snackbar = true;
      }
    }
  },
  computed: mapGetters(["isLoadingLocations", "errorLocations"])
};
</script>

<style>
</style>
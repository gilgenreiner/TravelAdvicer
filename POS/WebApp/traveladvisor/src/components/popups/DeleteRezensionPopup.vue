<template>
  <v-dialog v-model="dialogDeleteRezension" max-width="300" persistent>
    <v-card>
      <v-card-title class="headline">Rezension löschen</v-card-title>
      <v-card-text>Wollen Sie die Rezension wirklich löschen</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green" text @click="$emit('update:dialogDeleteRezension', false)">Cancel</v-btn>
        <v-btn color="green" text @click="deleteRezension(rezension.id)">OK</v-btn>
      </v-card-actions>
    </v-card>
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
  </v-dialog>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "LocationDeletePopup",
  data() {
    return {
      snackbar: false,
      text: ""
    };
  },
  props: {
    rezension: Object,
    dialogDeleteRezension: Boolean
  },
  methods: {
    deleteRezension(id) {
      this.$store.dispatch("deleteRezension", id);
      this.$emit('update:dialogDeleteRezension', false);
    }
  }
};
</script>

<style>
</style>
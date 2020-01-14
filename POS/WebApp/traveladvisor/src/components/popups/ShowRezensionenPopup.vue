<template>
  <v-dialog v-model="dialog" max-width="1200" persistent>
    <v-card>
      <v-card-title>
        Rezensionen
        <v-spacer></v-spacer>
        <v-btn text class="mr-2" @click="dialogAddPopup = !dialogAddPopup">Erstellen</v-btn>
        <v-btn icon @click="$emit('update:dialog', false)">
          <v-icon>close</v-icon>
        </v-btn>
      </v-card-title>
      <Rezensionen :rezensionen="allRezensionen" />
    </v-card>
    <AddRezensionPopup :location="location" :dialogAddPopup.sync="dialogAddPopup" />
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
  </v-dialog>
</template>

<script>
import { mapGetters } from "vuex";
import Rezensionen from "@/components/Rezensionen";
import AddRezensionPopup from "@/components/popups/AddRezensionPopup";

export default {
  name: "ShowRezensionPopup",
  components: {
    Rezensionen,
    AddRezensionPopup
  },
  data() {
    return {
      snackbar: false,
      text: "",
      dialogAddPopup: false
    };
  },
  props: {
    location: Object,
    dialog: Boolean
  },
  computed: mapGetters(["allRezensionen"]),
  created() {
    this.$store.dispatch("loadRezensionen");
  }
};
</script>
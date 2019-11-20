<template>
  <div class="home">
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4">
        <Map
          width="100%"
          height="87vh"
          :locations.sync="allActiveLocations"
          :center="[13.844549, 46.614073]"
          :mode="mode"
          @event="searchLocations"
        />
      </v-card>
    </v-hover>
    <v-snackbar v-model="snackbar" color="red" :timeout="4000">{{ text }}</v-snackbar>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

import Map from "@/components/Map";

export default {
  name: "home",
  components: {
    Map
  },
  data() {
    return {
      mode: "showAll",
      snackbar: false,
      text: ""
    };
  },
  watch: {
    errorLocations() {
      if (this.errorLocations) {
        this.text = "Konnte nicht geladen werden - " + this.errorLocations;
        this.snackbar = true;
      }
    }
  },
  created() {
    this.loadLocations();
  },
  methods: {
    ...mapActions(["loadLocations"]),
    searchLocations(data) {
      this.loadLocations(data);
    }
  },
  computed: {
    ...mapGetters(["allActiveLocations", "errorLocations"])
  }
};
</script>
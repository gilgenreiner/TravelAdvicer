<template>
  <div class="home">
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4">
        <Map
          width="100%"
          height="87vh"
          :locations.sync="allLocations"
          :center="[13.844549, 46.614073]"
          :mode="'showAll'"
        />
      </v-card>
    </v-hover>
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
  created() {
    this.loadLocations();
  },
  methods: mapActions(["loadLocations"]),
  computed: mapGetters(["allLocations"])
};
</script>
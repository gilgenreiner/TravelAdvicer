<template>
  <div class="home">
    <Map :width="'100%'" :height="'800px'" :locations="allLocations" :mode="'showDetails'" />
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
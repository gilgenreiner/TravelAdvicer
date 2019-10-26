<template>
  <div class="home">
    <Map :width="'100%'" :height="'87vh'" :locations.sync="allLocations" :mode="'show'" />
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
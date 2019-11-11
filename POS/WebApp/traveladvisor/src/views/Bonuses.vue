<template>
  <div class="bonuses">
    <v-container fluid>
      <v-row>
        <v-col cols="12">
          <!--v-btn
            :to="{ name: 'Location-details', params: { id: 'newLocation', mode: 'create', component: 'Locations'}}"
          >Location hinzufügen</v-btn-->
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="bonus in allBonuses" :key="bonus.id" lg="3" md="4" sm="6">
          <BonusListItem :bonus="bonus" />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

import BonusListItem from "@/components/BonusListItem";

export default {
  components: {
    BonusListItem
  },
  methods: {
    ...mapActions(["loadBonuses"])
  },
  computed: mapGetters(["allBonuses"]),
  created() {
    this.loadBonuses();
  }
};
</script>

<style scoped>
.locations {
  margin-left: 20px;
  padding-right: 20px;
}
</style>
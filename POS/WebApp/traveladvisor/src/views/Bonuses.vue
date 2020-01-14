<template>
  <div class="bonuses">
    <v-container fluid>
      <v-row>
        <v-col cols="3">
        <v-combobox
          v-model="selectedLocation"
          :items="allLocations"
          item-text="bezeichnung"
          return-object
          label="Select a favorite activity"
        ></v-combobox>
        </v-col>
        <v-col>
        <v-btn @click="openDialog">Bonus hinzufügen</v-btn>
        <PopupAddBoni :dialog.sync="dialog" :bonus="bonus" />
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
import PopupAddBoni from "@/components/popups/AddBonusPopup";

export default {
  components: {
    BonusListItem,
    PopupAddBoni
  },
  data() {
    return {
      dialog: false,
      bonus: {
        bezeichnung: null,
        punkte: null,
        aktiv: false,
        locationId: null
      },
      selectedLocation: {}
    };
  },
  methods: {
    ...mapActions(["loadBonuses", "loadLocations"]),
    openDialog() {     
      this.bonus = {
        bezeichnung: null,
        punkte: null,
        aktiv: false,
        locationId: this.selectedLocation.id
      };
      this.dialog = !this.dialog;
    }
  },
  computed: mapGetters(["allBonuses", "allLocations"]),
  created() {
    this.loadBonuses();
    //todo get id from logged in user
    this.loadLocations({
      loadBranchen: false,
      besitzer: "b717f71a-a902-4c1a-9fa9-00000659fc8ck"
    });
  }
};
</script>

<style scoped>
.locations {
  margin-left: 20px;
  padding-right: 20px;
}
</style>
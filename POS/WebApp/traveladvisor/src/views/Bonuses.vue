<template>
  <div class="bonuses">
    <v-container fluid>
      <v-row>
        <v-col cols="12">
          <v-btn @click="openDialog">Bonus hinzufügen</v-btn>
          <PopupAddBoni :dialog.sync="dialog" :bonus="bonus"/>
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
        punkte:null,
        aktiv:false,
        locationId:null
      }
    }
  },
  methods: {
    ...mapActions(["loadBonuses"]),
    openDialog() {
      this.dialog = !this.dialog;
      this.bonus = {
        bezeichnung: null,
        punkte:null,
        aktiv:false,
        locationId:null
      }
    }
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
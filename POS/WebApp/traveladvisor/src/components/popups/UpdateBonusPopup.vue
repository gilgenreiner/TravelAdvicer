<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">Update Bonus</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="bonus.bezeichnung"
                  label="Bezeichnung*"
                  hint="10 % auf x"
                  persistent-hint
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="bonus.punkte" label="Punkte*" type="number" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-switch v-model="bonus.aktiv" :label="`Bonus aktiv`"></v-switch>
              </v-col>
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="$emit('update:dialog', false)">Close</v-btn>
          <v-btn color="blue darken-1" text @click="doUpdateBonus(bonus)">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
import { mapGetters } from "vuex";

export default {
  name: "UpdateBonusPopup",
  props: {
    bonus: Object,
    dialog: Boolean
  },
  methods: {
    doUpdateBonus() {
      this.$emit("update:dialog", false);
      //this.bonus.locationId = "5e010350-72a3-4435-8455-17f4e9f3ff66"; //TODO: noch holen /mitgeben
      this.$store.dispatch("updateBonus", this.bonus);
      this.isDoCreateButtonPressed = true;
    }
  }
};
</script>

<style>
</style>
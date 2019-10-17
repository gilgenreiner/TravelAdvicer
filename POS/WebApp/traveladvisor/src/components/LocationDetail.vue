<template>
  <v-dialog v-model="dialog" max-width="600">
    <template v-slot:activator="{ on }">
      <v-btn class="buttons" v-if="!isEdit" v-on="on">Add Project</v-btn>
      <v-btn class="buttons" v-else icon @click="dialog = !dialog">
        <v-icon>edit</v-icon>
      </v-btn>
    </template>
    <v-row justify="center">
      <v-col>
        <v-card>
          <v-card-text>
            <v-text-field v-model="location.bezeichnung" label="Bezeichnung" disabled required />
            <v-textarea v-model="location.beschreibung" label="Beschreibung" disabled required />
            <v-combobox
              v-model="location.branchen"
              :items="items"
              label="Wählen Sie ihre Branch(en) aus"
              multiple
            />
            <v-text-field
              v-model="location.punkte"
              label="Punkte die man für einen Besuch bekommt"
              required
              type="number"
            />
            <v-checkbox
              v-model="location.aktiv"
              label="Soll diese Location für alle angezeigt werden"
              type="checkbox"
              required
            ></v-checkbox>
            <v-btn>Location auf der Karte auswählen</v-btn>
          </v-card-text>
          <v-card-actions>
            <div class="flex-grow-1"></div>
            <v-btn color="green" text @click="dialog = !dialog">Cancel</v-btn>
            <v-btn color="green" text @click="dialog = !dialog">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      checkbox1: true,
      items: ["Gastronomie", "Nutten", "Filme"]
    };
  },
  props: {
    isEdit: Boolean,
    location: Object
  },
  methods: {}
};
</script>

<style>
.buttons {
  float: right;
}
</style>
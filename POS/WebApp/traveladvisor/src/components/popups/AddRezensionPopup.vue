<template>
  <v-dialog v-model="dialogAddPopup" max-width="800" persistent>
    <v-card>
      <v-card-title>Rezensionen erstellen</v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid">
          Bewertung:
          <v-rating v-model="defaultRezension.bewertung" color="amber" dense half-increments></v-rating>
          <v-textarea
            v-model="defaultRezension.text"
            :rules="[rules.required, rules.length(400)]"
            label="Beschreibung"
            counter="400"
          />
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green" text @click="$emit('update:dialogAddPopup', false)">Abbrechen</v-btn>
        <v-btn color="green" text @click="saveRezension">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "AddRezensionPopup",
  data() {
    return {
      defaultRezension: {
        id: "asdf",
        bewertung: 2.5,
        text: ""
      },
      valid: false,
      rules: {
        length: len => v =>
          (v || "").length <= len ||
          `Zu viele Zeichen, es dürfen höchstens ${len} sein`,
        required: v => !!v || "Dieses Feld ist verpflichtend"
      }
    };
  },
  props: {
    location: Object,
    dialogAddPopup: Boolean
  },
  methods: {
    saveRezension() {
      this.$refs.form.validate();
      if (this.valid) {
        this.$store.dispatch("saveRezension", this.defaultRezension);
        this.$emit("update:dialogAddPopup", false);
      }
    }
  },
  watch: {
    dialogAddPopup() {
      if (this.dialogAddPopup) {
        this.defaultRezension = {
          id: "asdf",
          bewertung: 2.5,
          text: ""
        };
        this.$refs.form.resetValidation();
      }
    }
  }
};
</script>

<style>
</style>
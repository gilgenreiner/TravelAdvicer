<template>
  <v-dialog v-model="dialogUpdatePopup" max-width="800" persistent>
    <v-card>
      <v-card-title>Rezensionen updaten</v-card-title>
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
        <v-btn color="green" text @click="$emit('update:dialogUpdatePopup', false)">Abbrechen</v-btn>
        <v-btn color="green" text @click="updateRezension">Akutalisieren</v-btn>
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
        text: "",
        bewertung: 2.5,
        id: ""
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
    rezension: Object,
    dialogUpdatePopup: Boolean
  },
  methods: {
    updateRezension() {
      this.$refs.form.validate();
      if (this.valid) {
        this.$store.dispatch("updateRezension", this.defaultRezension);
        this.$emit("update:dialogUpdatePopup", false);
      }
    }
  },
  watch: {
    //update problem lösen, dass er nur untere öffnen kann keine oberen von liste her
    dialogUpdatePopup() {
      if (this.dialogUpdatePopup) {
        this.defaultRezension = {
          text: "",
          bewertung: 2.5,
          id: ""
        };
        console.log(this.defaultRezension);
        this.defaultRezension.text = this.rezension.text;
        this.defaultRezension.bewertung = this.rezension.bewertung;
        this.defaultRezension.id = this.rezension.id;
         console.log(this.defaultRezension);
        this.$refs.form.resetValidation();
      }
    }
  }
};
</script>

<style>
</style>
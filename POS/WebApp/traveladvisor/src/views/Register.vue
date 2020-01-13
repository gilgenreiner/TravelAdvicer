<template>
  <div>
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5" width="800">
        <v-card-text>
          <v-form ref="form" v-model="valid">
            <v-text-field v-model="user.firstname" label="Vorname" :rules="[rules.required]" />
            <v-text-field v-model="user.lastname" label="Nachname" :rules="[rules.required]" />
            <v-text-field v-model="user.email" label="Email" :rules="[rules.required]" />
            <v-text-field
              v-model="user.password"
              type="password"
              label="Password"
              :rules="[rules.required]"
            />
            <RadioToggleButtons
              v-model="user.type"
              :values="values"
              color="green"
              textColor="#000"
              selectedTextColor="#fff"
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="$router.go(-1)" text>Abbrechen</v-btn>
          <v-btn @click="submit()" text>Registrieren</v-btn>
        </v-card-actions>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
import firebase from "firebase";

export default {
  data() {
    return {
      error: null,
      valid: false,
      user: {
        firstname: "",
        lastname: "",
        email: "",
        password: "",
        id: "",
        type: "Besitzer"
      },
      values: [
        { label: "Besucher", value: "besucher" },
        { label: "Besitzer", value: "besitzer" }
      ],
      rules: {
        length: len => v =>
          (v || "").length <= len ||
          `Zu viele Zeichen, es dürfen höchstens ${len} sein`,
        required: v => !!v || "Dieses Feld ist verpflichtend"
      }
    };
  },
  methods: {
    submit() {
      this.$refs.form.validate();

      firebase
        .auth()
        .createUserWithEmailAndPassword(this.user.email, this.user.password)
        .then(data => {
          data.user.updateProfile({
            displayName: this.user.firstname
          });
          
          this.user.id = data.user.uid;
          this.registerUserToOracle();
          this.writeUserDataToFirestore();
        })
        .catch(err => {
          this.error = err.message;
        });
    },
    registerUserToOracle() {
      this.$store.dispatch("registerUser", this.user);
    },
    writeUserDataToFirestore() {
      var db = firebase.firestore();
      db.collection("users")
        .doc(this.user.id)
        .set({
          vorname: this.user.firstname,
          email: this.user.email,
          nachname: this.user.lastname,
          typ: this.user.type,
          uid: this.user.id
        })
        .then(function() {
          console.log("Document successfully written!");
        })
        .catch(function(error) {
          console.error("Error writing document: ", error);
        });
    }
  }
};
</script>

<style>
</style>
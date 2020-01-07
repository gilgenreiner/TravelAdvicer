<template>
  <div>
    <div>
      <v-row>
        <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5">
          <v-card-text>
            <v-form ref="form" v-model="valid">
              <v-text-field v-model="user.firstname" label="Vorname" />
              <v-text-field v-model="user.lastname" label="Nachname" />
              <v-text-field v-model="user.email" label="Email" />
              <v-text-field v-model="user.password" type="password" label="Password" />
              <RadioToggleButtons
                v-model="currentValue"
                :values="values"
                color="purple"
                textColor="#000"
                selectedTextColor="#fff"
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn @click="$router.go(-1)" text>Cancel</v-btn>
            <v-btn @click="submit()" text>Registrieren</v-btn>
          </v-card-actions>
        </v-card>
      </v-row>
    </div>
    <div>
      <v-row>
        <v-col cols="12"></v-col>
      </v-row>
    </div>
  </div>
</template>


<script>
import firebase from "firebase";

export default {
  data() {
    return {
      user: {
        firstname: "Hans",
        lastname: "Klein",
        email: "st.sonnek@gmail.com",
        password: "test123",
        id: "",
        type: "Besitzer"
      },
      error: null,
      values: [
        { label: "Besucher", value: "1" },
        { label: "Besitzer", value: "2" }
      ],
      currentValue: ""
    };
  },
  methods: {
    submit() {
      firebase
        .auth()
        .createUserWithEmailAndPassword(this.user.email, this.user.password)
        .then(data => {
          data.user
            .updateProfile({
              displayName: this.user.firstname
            })

          this.user.id = data.user.uid;
          //this.registerUser();
          this.writeUserData();
        })
        .catch(err => {
          this.error = err.message;
        });
    },
    registerUser() {
      console.log("register user");
      this.$store.dispatch("registerUser", this.user);
    },
    writeUserData() {
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
.buttons {
  display: flex;
}
.v-expansion-panel {
  box-shadow: none;
}
</style>
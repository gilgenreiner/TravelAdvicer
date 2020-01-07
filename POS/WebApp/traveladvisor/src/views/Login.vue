<template>
  <div>
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5" width="600">
        <v-card-text>
          <v-img
            src="https://static.vecteezy.com/system/resources/previews/000/575/331/non_2x/login-sign-icon-vector.jpg"
            height="350"
            contain
          />
          <v-form ref="form" v-model="valid">
            <v-text-field v-model="form.email" label="Email" />
            <v-text-field v-model="form.password" label="Password" type="password" />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn :to="{ name: 'Register' }" text>Registrieren</v-btn>
          <v-btn @click="submit()" text>Anmelden</v-btn>
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
      valid: false,
      form: {
        email: "",
        password: "",
        id: ""
      },
      user: {
        id: ""
      },
      error: null
    };
  },
  methods: {
    submit() {
      firebase
        .auth()
        .signInWithEmailAndPassword(this.form.email, this.form.password)
        .then(data => {
          this.user.id = data.user.uid;
          localStorage.setItem("userid", this.user.id);
          this.$router.replace({ name: "Account" });
        })
        .catch(err => {
          this.error = err.message;
        });
    }
  }
};
</script>
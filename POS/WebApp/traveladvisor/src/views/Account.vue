<template>
  <div>
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5" width="800">
        <v-card-text>
          <v-form ref="form" v-model="valid">
            <v-text-field v-model="user.data.vorname" label="Vorname" readonly />
            <v-text-field v-model="user.data.nachname" label="Nachname" readonly />
            <v-text-field v-model="user.data.displayName" label="Displayname" readonly />
            <v-text-field v-model="user.data.email" label="Email" readonly />
            <v-text-field v-model="user.data.typ" label="Typ" readonly />
            <v-text-field v-model="user.data.id" label="Id" readonly />
            <v-text-field v-model="user.loggedIn" label="Status" readonly />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="signOut()" text>Abmelden</v-btn>
        </v-card-actions>
      </v-card>
    </v-hover>
  </div>
</template>
<script>
import { mapGetters, mapMutations } from "vuex";
import firebase from "firebase";
import index from "../store/index";
import userStore from "../store/modules/users"

export default {
  data() {
    return {
      userdata: {
        vorname: "",
        nachname: "",
        email: "",
        typ: "",
        uid: ""
      }
    };
  },
  computed: {
    ...mapGetters({
      user: "user"
    })
  },
  methods: {
    signOut() {
      firebase
        .auth()
        .signOut()
        .then(() => {
          this.$router.replace({
            name: "Login"
          })
           this.$store.commit("SET_LOGGED_IN", false);
          this.$store.commit("SET_USER", null);
        });
    },
    setData(data) {
      this.userdata = data;
      console.log(this.userdata);
    }
  },
  created() {
    if (!this.user.loggedIn) {
      this.$router.push({ name: "Login" });
    }
  }
};
</script>
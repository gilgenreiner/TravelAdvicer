<template>
  <nav class="navbar navbar-expand-md navbar-light navbar-laravel">
    
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto"></ul>
        <ul class="navbar-nav ml-auto">
          <template v-if="user.loggedIn">
            <div >Vorname: {{user.data.vorname}}</div>
            <div >Displayname: {{user.data.displayName}}</div>
            <div >Nachname: {{user.data.nachname}}</div>
            <div >Email: {{user.data.email}}</div>
            <div >Typ: {{user.data.typ}}</div>
            <div >Userid: {{user.data.id}}</div>
            <li class="nav-item">
              <a class="nav-link" @click.prevent="signOut">Sign out</a>
            </li>
          </template>
          <template v-else>
            <li class="nav-item">
              <router-link to="login" class="nav-link">Login</router-link>
            </li>
            <li class="nav-item">
              <router-link to="register" class="nav-link">Register</router-link>
            </li>
          </template>
        </ul>
      </div>
  </nav>
</template>
<script>
import { mapGetters } from "vuex";
import firebase from "firebase";
import index from "../store/index"

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
      // map `this.user` to `this.$store.getters.user`
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
            name: "home"
          });
        });
    },
    setData(data) {
      this.userdata = data;
      console.log(this.userdata);
    }
  }
};
</script>
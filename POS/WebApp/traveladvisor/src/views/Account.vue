<template>
  <div>
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5" width="800">
        <v-card-title>Information</v-card-title>
        <v-card-text>
          <v-form ref="form" v-if="user != null">
            <v-text-field v-model="user.vorname" label="Vorname" readonly />
            <v-text-field v-model="user.nachname" label="Nachname" readonly />
            <v-text-field v-model="user.displayName" label="Displayname" readonly />
            <v-text-field v-model="user.email" label="Email" readonly />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green" @click="signOut()" text>Abmelden</v-btn>
        </v-card-actions>
      </v-card>
    </v-hover>
  </div>
</template>
<script>
import { mapGetters } from "vuex";

export default {
  name: "Account",
  computed: {
    ...mapGetters({
      user: "users/user"
    })
  },
  methods: {
    signOut() {
      this.$store.dispatch("users/signOut");
      this.$router.replace({ name: "Login" });
    }
  }
};
</script>
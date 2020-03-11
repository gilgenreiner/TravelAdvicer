<template>
  <div>
    <v-row>
      <v-col cols="6" lg="6" sm="12">
        <v-hover v-slot:default="{ hover }">
          <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5">
            <v-card-title>
              Profil {{ ( update ) ? 'bearbeiten' : '' }}
              <v-spacer></v-spacer>
              <v-btn icon @click="update = !update">
                <v-icon>edit</v-icon>
              </v-btn>
            </v-card-title>
            <v-card-text>
              <v-form ref="form" v-if="user != null">
                <v-text-field
                  v-model="user.displayName"
                  label="Displayname"
                  color="green"
                  readonly
                />
                <v-text-field
                  v-model="user.vorname"
                  label="Vorname"
                  :readonly="!update"
                  color="green"
                />
                <v-text-field
                  v-model="user.nachname"
                  label="Nachname"
                  :readonly="!update"
                  color="green"
                />
                <v-text-field v-model="user.email" label="Email" readonly color="green" />
                <v-text-field
                  v-if="update"
                  v-model="password"
                  type="password"
                  label="Password"
                  readonly
                  color="green"
                  append-outer-icon="mail"
                  @click:append-outer="resetPassword"
                />
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn v-if="update" color="green" @click="update = !update" text>Cancel</v-btn>
              <v-btn
                v-if="update"
                color="green"
                @click="updateUser"
                text
                :loading="isLoading"
              >Aktualisieren</v-btn>
              <v-btn v-if="!update" color="green" @click="signOut" text>Abmelden</v-btn>
            </v-card-actions>
          </v-card>
        </v-hover>
      </v-col>
      <v-col cols="6" lg="6" sm="12" v-show="true">
        <v-hover v-slot:default="{ hover }">
          <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5">
            <v-card-title>Punkte</v-card-title>
            <v-card-text>
              <v-timeline>
                <v-timeline-item v-for="(year, i) in years" :key="i" :color="year.color" small>
                  <template v-slot:opposite>
                    <span
                      :class="`headline font-weight-bold ${year.color}--text`"
                      v-text="year.date"
                    ></span>
                  </template>
                  <div class="py-4">
                    <h2
                      :class="`headline font-weight-light mb-4 ${year.color}--text`"
                    >{{ year.location }}</h2>
                    <div>Sie haben am {{ year.date }} bei der Location {{ year.location }} {{ year.points }} gesammelt.</div>
                  </div>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import firebase from "firebase";

export default {
  name: "Account",
  data() {
    return {
      update: false,
      password: "123456",
      years: [
        {
          color: "cyan",
          date: "12.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          color: "green",
          date: "13.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          color: "pink",
          date: "14.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          color: "amber",
          date: "16.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          color: "orange",
          date: "17.12.2020",
          points: 230,
          location: "HTL-Villach"
        }
      ]
    };
  },
  computed: {
    ...mapGetters({
      user: "users/user",
      isLoading: "users/isLoading"
    })
  },
  watch: {
    isLoading() {
      if (!this.isLoading) {
        this.update = !this.update;
      }
    },
    update() {
      if (!this.update) {
        this.$store.dispatch("users/fetchUser", firebase.auth().currentUser);
      }
    }
  },
  methods: {
    signOut() {
      this.$store.dispatch("users/signOut");
      this.$router.replace({ name: "Login" });
    },
    resetPassword() {
      this.$store.dispatch("users/resetPasswortViaEmail", this.user.email);
    },
    updateUser() {
      this.$store.dispatch("users/updateUser", this.user);
    }
  }
};
</script>
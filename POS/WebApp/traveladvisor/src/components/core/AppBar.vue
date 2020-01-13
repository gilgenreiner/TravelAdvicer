<template>
  <v-app-bar id="core-app-bar" absolute app color="transparent" flat height="88">
    <v-toolbar-title class="tertiary--text align-self-center ml-6 mr-2">{{ title }}</v-toolbar-title>
    <v-spacer />

    <!--v-toolbar-items>
      <v-row align="center" class="mx-0">
      {{user.data.displayName}}
      </v-row>
    </v-toolbar-items-->

    <v-toolbar-items>
      <v-row align="center" class="mx-0">
          <p v-if="user.data !== null && user.data.typ === 'besucher'">Eingeloggt als Besucher</p>

        <v-btn to="/account" icon>
          
          <v-icon color="tertiary">mdi-account</v-icon>
        </v-btn>
      </v-row>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data: () => ({
    title: "TravelAdvisor"
  }),
  watch: {
    $route(val) {
      this.title = val.name;
    }
  },
  computed: {
    ...mapGetters({
      user: "user"
    })
  }
};
</script>

<style>
#core-app-bar {
  width: auto;
}

#core-app-bar a {
  text-decoration: none;
}
</style>

<template>
  <v-app-bar id="core-app-bar" absolute app color="transparent" flat height="88">
    <v-toolbar-title class="tertiary--text align-self-center ml-6 mr-2">{{ title }}</v-toolbar-title>

    <v-spacer />

    <v-toolbar-items>
      <v-row align="center" class="mx-0">
        <v-btn to="/account" icon>
          <v-icon color="tertiary">mdi-account</v-icon>
        </v-btn>
      </v-row>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
export default {
  data: () => ({
    title: "TravelAdvisor"
  }),
  watch: {
    $route(val) {
      this.title = val.name;
    }
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

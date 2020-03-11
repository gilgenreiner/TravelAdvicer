<template>
  <v-app-bar id="core-app-bar" absolute app color="transparent" flat height="88">
    <v-toolbar-title class="tertiary--text align-self-center ml-6 mr-2">
      <v-btn v-if="responsive" class="v-btn--simple" icon @click.stop="onClickBtn">
        <v-icon>mdi-view-list</v-icon>
      </v-btn>
      {{ title }}
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items>
      <v-row align="center" class="mx-0">
        <v-btn to="/account" icon>
          <v-icon color="tertiary">mdi-account</v-icon>
        </v-btn>
        <v-switch
          class="mt-6"
          color="green"
          v-model="darkModeOn"
          @change="onSwitchChange"
          :prepend-icon="icon"
        />
      </v-row>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      title: "TravelAdvisor",
      responsive: true,
      icon: "brightness_5",
      darkModeOn: false
    };
  },
  watch: {
    $route(val) {
      this.title = val.name;
    },
    darkModeOn() {
      this.icon = this.darkModeOn ? "brightness_3" : "brightness_5";
    }
  },
  methods: {
    onClickBtn() {
      this.$store.dispatch("application/setDrawer", true);
    },
    onResponsiveInverted() {
      if (window.innerWidth < 991) {
        this.responsive = true;
      } else {
        this.responsive = false;
      }
    },
    onSwitchChange() {
      localStorage.setItem("dark", this.darkModeOn);
      this.icon = this.darkModeOn ? "brightness_3" : "brightness_5";
      this.$vuetify.theme.dark = this.darkModeOn;
    }
  },
  mounted() {
    this.onResponsiveInverted();
    window.addEventListener("resize", this.onResponsiveInverted);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.onResponsiveInverted);
  },
  created() {
    if (localStorage.getItem("dark")) {
      this.darkModeOn = localStorage.getItem("dark");
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

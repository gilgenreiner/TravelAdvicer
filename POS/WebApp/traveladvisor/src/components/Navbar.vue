<template>
  <nav>
    <v-toolbar>
      <v-app-bar-nav-icon class="grey--text" @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="headline text-uppercase">
        <span class="font-weight-light">Traveladvisor</span>
      </v-toolbar-title>
    </v-toolbar>
    <v-navigation-drawer app clipped temporary v-model="drawer" class="green customNavDrawer">
      <v-list>
        <v-list-item v-for="link in links" :key="link.text" :to="link.link">
          <v-list-item-action>
            <v-icon class="white--text">{{link.icon}}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title class="white--text">{{link.text}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      drawer: false,
      links: [
        { icon: "home", text: "Home", link: '/' },
        { icon: "person", text: "Account", link: '/account' },
        { icon: "card_giftcard", text: "Prämien", link: '/gifts' },
        { icon: "room", text: "Locations", link: '/locations' }
      ]
    };
  }
};
</script>

<style scoped>
.customNavDrawer {
  padding-top: 1%;
}
</style>
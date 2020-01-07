<template>
  <v-app>
    <core-app-bar />

    <core-drawer />

    <core-view />
  </v-app>
</template>

<script>
import acc from "./views/Account"
export default {
  
  components: {
    CoreDrawer: () => import("@/components/core/Drawer"),
    CoreAppBar: () => import("@/components/core/AppBar"),
    CoreView: () => import("@/components/core/View"),
    acc
  }
};
</script>

<template>
  <v-card class="mx-auto" :elevation="6" :width="'25%'" :height="'300px'">
    <v-img
      class="white--text"
      height="200px"
      src="https://picsum.photos/510/300?random"
      aspect-ratio="2"
    >
      <v-card-title class="align-end fill-height">{{ location.name }}</v-card-title>
    </v-img>
    <v-card-text class="black--text">{{ location.beschreibung }}</v-card-text>
    <v-card-actions class="buttons">
      <v-btn icon>
        <v-icon>edit</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: {
    location: Object
  }
};
</script>

<style scoped>
.buttons {
  float: right;
}
</style>
<template>
  <div>
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4">
        <v-img
          class="white--text align-end"
          height="200px"
          :src="'https://picsum.photos/510/300?random' || `https://x.kinja-static.com/assets/images/logos/placeholders/default.png`"
          :key="selectedLocation.img"
          aspect-ratio="2"
        ></v-img>
      </v-card>
    </v-hover>
    <v-hover v-slot:default="{ hover }">
      <v-card :elevation="hover ? 12 : 4" class="mx-auto mt-5">
        <v-card-text>
          <v-text-field v-model="selectedLocation.bezeichnung" label="Bezeichnung" readonly />
          <v-textarea v-model="selectedLocation.beschreibung" label="Beschreibung" readonly />
          <v-select
            v-model="selectedLocation.branchen"
            item-text="bezeichnung"
            label="Branch(en)"
            multiple
            return-object
            readonly
          />
          <v-text-field
            v-model="selectedLocation.punkte"
            label="Punkte pro Besuch"
            readonly
          />
        </v-card-text>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
export default {
  name: "LocationDetailReadonly",
  props: {
    selectedLocation: Object
  }
};
</script>

<style>
</style>
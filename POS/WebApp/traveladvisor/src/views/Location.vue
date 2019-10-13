<template>
  <div class="locations">
    <h1 class="heading">Locations:</h1>
    <v-list class="list">
      <v-list-item v-for="location in locations" :key="location.name">
        <v-list-item-content>
            <LocationListItem :location=location />
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </div>
</template>

<script>
import LocationListItem from "@/components/LocationListItem";

export default {
  components: {
    LocationListItem
  },
  data() {
    return {
      locations: [
        {
          name: "Museum",
          beschreibung: "das ist ein sehr schönes Musuem"
        },
        {
          name: "Theater",
          beschreibung: "das ist ein sehr schönes Theater"
        },
        {
          name: "Restaurant",
          beschreibung: "das ist ein sehr schönes Restaurant"
        },
        {
          name: "Kino",
          beschreibung: "das ist ein sehr schönes Kino"
        }
      ]
    };
  }
};
</script>

<style scoped>
.list {
  display: flex;
}

.heading {
  padding-left: 1%;
  padding-top: 1%;
  color: grey;
}
</style>
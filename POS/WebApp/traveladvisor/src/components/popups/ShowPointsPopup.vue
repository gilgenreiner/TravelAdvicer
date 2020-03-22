<template>
  <v-dialog v-model="dialog" max-width="1200" persistent>
    <v-card>
      <v-card-title>
        Punkte
        <v-spacer></v-spacer>
        <v-btn icon @click="$emit('update:dialog', false)">
          <v-icon>close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <v-timeline>
          <v-timeline-item v-for="(year, i) in years" :key="i" color="green" small>
            <template v-slot:opposite>
              <span :class="`headline font-weight-bold green--text`" v-text="year.date"></span>
            </template>
            <div class="py-4">
              <h2 :class="`headline font-weight-light mb-4`">{{ year.location }}</h2>
              <div>Sie haben am {{ year.date }} bei der Location {{ year.location }} {{ year.points }} gesammelt.</div>
            </div>
          </v-timeline-item>
        </v-timeline>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
    name: 'ShowPointsPopup',
    data() {
    return {
      years: [
        {
          date: "12.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          date: "13.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          date: "14.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          date: "16.12.2020",
          points: 230,
          location: "HTL-Villach"
        },
        {
          date: "17.12.2020",
          points: 230,
          location: "HTL-Villach"
        }
      ],
    }
    },
    props: {
        dialog: Boolean
    }
};
</script>

<style>
</style>
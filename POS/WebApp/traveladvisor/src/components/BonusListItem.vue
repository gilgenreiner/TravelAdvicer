<template>
  <v-hover v-slot:default="{ hover }">
    <v-card
      class="mx-auto"
      color="grey lighten-4"
      max-width="600"
    >
      <v-img
        :aspect-ratio="16/9"
        :src="'https://picsum.photos/510/300?random'"
      >
        <v-expand-transition>
          <div
            v-if="hover"
            class="d-flex transition-fast-in-fast-out green darken-2 v-card--reveal display-3 white--text"
            style="height: 100%;"
          >
            {{ bonus.punkte }}
          </div>
        </v-expand-transition>
      </v-img>
      <v-card-text
        class="pt-6"
        style="position: relative;"
      >
        <h3 class="display-1 font-weight-light green--text mb-2">{{bonus.bezeichnung}}</h3>
         
      </v-card-text>
      <v-card-actions>
        <v-spacer> </v-spacer>
          <v-btn icon>
            <v-icon>expand_more</v-icon>
          </v-btn>
          <v-btn icon  @click="updateDialog=true">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn icon @click="deleteDialog = true">
            <v-icon>delete</v-icon>
          </v-btn>
          <!--LocationDeletePopup :dialog.sync="dialog" :bonus="bonus" /-->
          <PopupUpdateBoni :dialog.sync="updateDialog" :bonus="bonus" />
      </v-card-actions>
    </v-card>
  </v-hover>
</template>


<script>
import LocationDeletePopup from "@/components/popups/DeleteLocationPopup.vue";
import PopupUpdateBoni from "@/components/popups/UpdateBonusPopup.vue";
export default {
  components: {
    LocationDeletePopup,
    PopupUpdateBoni
  },
  data() {
    return {
      dialog: false,
      updateDialog: false
    };
  },
  props: {
    bonus: Object
  }
};
</script>

<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}
</style>

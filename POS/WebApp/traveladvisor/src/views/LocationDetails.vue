<template>
  <div>
    <v-row>
      <v-col cols="4">
        <v-card>
          <v-img
            class="white--text align-end"
            height="200px"
            src="https://picsum.photos/510/300?random"
            aspect-ratio="2"
          ></v-img>
        </v-card>

        <v-card class="mt-5">
          <v-card-text>
            <v-text-field
              v-model="selectedLocation.bezeichnung"
              label="Bezeichnung"
              required
              :readonly="mode === 'show'"
            />
            <v-textarea
              v-model="selectedLocation.beschreibung"
              label="Beschreibung"
              required
              :readonly="mode === 'show'"
            />
            <v-combobox
              v-model="selectedLocation.branchen"
              :items="items"
              item-text="bezeichnung"
              label="Branch(en)"
              multiple
               return-object
              :readonly="mode === 'show'"
            />
            <v-text-field
              v-model="selectedLocation.punkte"
              label="Punkte pro Besuch"
              required
              type="number"
              :readonly="mode === 'show'"
            />
            <v-checkbox
              v-model="selectedLocation.aktiv"
              label="Soll diese Location für alle angezeigt werden"
              type="checkbox"
              required
              v-show="mode !== 'show'"
            ></v-checkbox>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="8">
        <Map :width="'100%'" :height="'670px'" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-expansion-panels accordion v-show="mode === 'show'">
          <v-expansion-panel v-for="(item,i) in 5" :key="i">
            <v-expansion-panel-header>
              <v-row no-gutters align="center" align-content="start">
                <v-col cols="2">
                  <v-list-item-avatar color="white">
                    <v-img
                      src="https://i.pinimg.com/originals/89/46/db/8946dbf52cc180dd12b084300dfa04f5.jpg"
                      height="30"
                      contain
                      align-start
                    />
                  </v-list-item-avatar>
                </v-col>
                <v-col cols="10" style="margin-left: -200px;">
                  <v-rating :value="4.5" color="amber" dense half-increments readonly></v-rating>
                </v-col>
              </v-row>
            </v-expansion-panel-header>
            <v-expansion-panel-content>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

import Map from "@/components/Map";

export default {
  name: "LocationDetail",
  components: {
    Map
  },
  methods: {
    ...mapActions(["loadLocationById"])
  },
  props: {
    mode: {
      default: 'show',
      type: String
    }
  },
  computed: mapGetters(["selectedLocation"]),
  created() {
    this.loadLocationById(this.$route.params.id);
  }
};
</script>

<style>
.buttons {
  float: right;
}
.v-expansion-panel {
  box-shadow: none;
}
</style>

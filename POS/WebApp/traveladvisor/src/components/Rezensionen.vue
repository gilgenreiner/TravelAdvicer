<template>
  <v-expansion-panels accordion>
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
      <v-expansion-panel-content>
        <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>edit</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>delete</v-icon>
        </v-btn>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {};
</script>

<style>
</style>
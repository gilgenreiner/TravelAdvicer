<template>
  <v-navigation-drawer
    id="app-drawer"
    app
    src="https://images.unsplash.com/photo-1546299138-7574176aad1a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"
    dark
    floating
    mobile-break-point="991"
    persistent
    width="260"
  >
    <template v-slot:img="attrs">
      <v-img v-bind="attrs" gradient="to top, rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)" />
    </template>

    <v-list-item two-line>
      <v-list-item-avatar color="white">
        <v-img
          src="https://i.pinimg.com/originals/89/46/db/8946dbf52cc180dd12b084300dfa04f5.jpg"
          height="34"
          contain
        />
      </v-list-item-avatar>

      <v-list-item-title class="title">TravelAdvisor</v-list-item-title>
    </v-list-item>

    <v-divider class="mx-3 mb-3" />

    <v-list nav>
      <div />

      <v-list-item
        v-for="(link, i) in links"
        :key="i"
        :to="link.to"
        active-class="green white--text"
      >
        <v-list-item-action>
          <v-icon>{{ link.icon }}</v-icon>
        </v-list-item-action>

        <v-list-item-title v-text="link.text" />
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  data: () => ({
    links: [
      { icon: "home", text: "Home", to: "/" },
      { icon: "person", text: "Account", to: "/account" },
      { icon: "room", text: "Locations", to: "/locations" },
      { icon: "card_giftcard", text: "Prämien", to: "/gifts" }
    ]
  })
};
</script>

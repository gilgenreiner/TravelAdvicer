import Vue from 'vue'
import Router from 'vue-router'
import MapView from '@/views/MapView.vue'
import Account from '@/views/Account.vue'
import Location from '@/views/Location.vue'
import LocationDetails from '@/views/LocationDetails.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Map',
      component: MapView
    },
    {
      path: '/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/locations',
      name: 'Locations',
      component: Location
    },
    {
      path: '/locations/:id',
      name: 'Location-details',
      component: LocationDetails,
      props: true
    }
  ]
})

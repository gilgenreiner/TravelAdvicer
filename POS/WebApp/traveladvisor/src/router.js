import Vue from 'vue'
import Router from 'vue-router'
import MapView from '@/views/MapView.vue'
import Account from '@/views/Account.vue'
import Locations from '@/views/Locations.vue'
import UpdateLocation from '@/views/UpdateLocation.vue'
import CreateLocation from '@/views/CreateLocation.vue'
import PageNotFound from '@/components/error/PageNotFound.vue'

Vue.use(Router)

const router = new Router({
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
      component: Locations
    },
    {
      path: '/locations/update/:id',
      name: 'Location aktualisieren',
      component: UpdateLocation,
      props: true
    },
    {
      path: '/locations/create',
      name: 'Location erstellen',
      component: CreateLocation
    },
    {
      path: "*",
      component: PageNotFound
    }
  ]
})

router.beforeResolve((to, from, next) => {
  if (to.name) {
    NProgress.start()
  }
  next()
})

router.afterEach((to, from) => {
  NProgress.done()
})

export default router
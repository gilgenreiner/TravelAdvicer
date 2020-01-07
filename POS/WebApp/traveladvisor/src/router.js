import Vue from 'vue'
import Router from 'vue-router'
import MapView from '@/views/MapView.vue'
import Account from '@/views/Account.vue'
import Locations from '@/views/Locations.vue'
import UpdateLocation from '@/views/UpdateLocation.vue'
import CreateLocation from '@/views/CreateLocation.vue'
import ShowLocation from '@/views/ShowLocation.vue'
import PageNotFound from '@/components/error/PageNotFound.vue'
import Bonuses from '@/views/Bonuses.vue'
import Register from '@/views/Register'
import Login from '@/views/Login'

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
      path: '/locations/view/:id',
      name: 'Location anzeigen',
      component: ShowLocation,
      props: true
    },
    {
      path: '/locations/update/:id',
      name: 'Location aktualisieren',
      component: UpdateLocation
    },
    {
      path: '/locations/create',
      name: 'Location erstellen',
      component: CreateLocation
    },
    {
      path: '/bonuses',
      name: 'Bonuses',
      component: Bonuses
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
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
import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home.vue'
import Account from '@/views/Account.vue'
import Location from '@/views/Location.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/account',
      name: 'account',
      component: Account
    },
    {
      path: '/locations',
      name: 'locations',
      component: Location
    }
  ]
})

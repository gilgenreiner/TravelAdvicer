import Vue from 'vue'
import './firebase'
import App from './App.vue'
import store from './store'
import vuetify from './plugins/vuetify';
import router from './router'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import VueRadioToggleButtons from 'vue-radio-toggle-buttons';
import firebase from 'firebase'
import 'vue-radio-toggle-buttons/dist/vue-radio-toggle-buttons.css';
import store_idx from "./store/index"

Vue.config.productionTip = false
Vue.use(VueRadioToggleButtons)

firebase.auth().onAuthStateChanged(user => {
  store_idx.dispatch("fetchUser", user);
});

//filter
Vue.filter('shorterText', (value) => {
  if (value.length <= 51) return value;
  return value.slice(0, 48) + "...";
});

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')

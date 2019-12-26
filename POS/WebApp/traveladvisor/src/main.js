import Vue from 'vue'
import App from './App.vue'
import store from './store'
import vuetify from './plugins/vuetify';
import router from './router'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.config.productionTip = false

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

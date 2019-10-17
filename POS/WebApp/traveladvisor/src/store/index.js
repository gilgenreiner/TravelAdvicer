import Vuex from 'vuex';
import Vue from 'vue';
import locations from './modules/locations';

//Load Vuex
Vue.use(Vuex);

//Create store
export default new Vuex.Store({
    modules: {
        locations
    }
})
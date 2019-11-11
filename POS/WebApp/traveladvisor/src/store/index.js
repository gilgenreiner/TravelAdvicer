import Vuex from 'vuex';
import Vue from 'vue';
import locations from '@/store/modules/locations';
import branchen from '@/store/modules/branchen';
import bonuses from '@/store/modules/bonuses';

//Load Vuex
Vue.use(Vuex);

//Create store
export default new Vuex.Store({
    modules: {
        locations,
        branchen,
        bonuses
    }
})
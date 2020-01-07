import Vuex from 'vuex';
import Vue from 'vue';
import locations from '@/store/modules/locations';
import branchen from '@/store/modules/branchen';
import bonuses from '@/store/modules/bonuses';
import users from '@/store/modules/users';
import firebase from "firebase";


//Load Vuex
Vue.use(Vuex);

//Create store
export default new Vuex.Store({
    modules: {
        locations,
        branchen,
        bonuses, 
        users
    }
})
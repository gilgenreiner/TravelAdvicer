import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;

const state = {
    rezensionen: [
        {
            besucherid: "350e7145-5f8d-4203-94e0-8aa6e208c73f",
            bewertung: 4,
            text:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            id: "9e71af73-2e47-412d-9e89-6b90d7bf7955",
            locationid: "5e010350-72a3-4435-8455-17f4e9f3ff66",
            timestamp: "2019-11-14T14:33:21.917Z[UTC]"
        },
        {
            besucherid: "350e7145-5f8d-4203-94e0-8aa6e208c738",
            bewertung: 1,
            text:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            id: "9e71af73-2e47-412d-9e89-6b90d7bf7956",
            locationid: "5e010350-72a3-4435-8455-17f4e9f3ff66",
            timestamp: "2019-11-14T14:33:21.917Z[UTC]"
        },
        {
            besucherid: "350e7145-5f8d-4203-94e0-8aa6e208c734",
            bewertung: 2.5,
            text:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            id: "9e71af73-2e47-412d-9e89-6b90d7bf7952",
            locationid: "5e010350-72a3-4435-8455-17f4e9f3ff66",
            timestamp: "2019-11-14T14:33:21.917Z[UTC]"
        }
    ],
    isLoadingRezensionen: false
};

const getters = {
    allRezensionen: state => state.rezensionen
};

const actions = {
    loadRezensionen({ commit }) {
        //webservicecall
        commit('setRezensionen', state.rezensionen);
    },
    saveRezension({ commit }, rezension) {
        //webservicecall
        commit('addRezension', rezension);
    },
    updateRezension({ commit }, rezension) {
        //webservicecall
        commit('updateRezension', rezension);
    },
    deleteRezension({ commit }, id) {
        //webservicecall
        commit('deleteRezension', id);
    }
};

const mutations = {
    setRezensionen: (state, rezensionen) => (state.rezensionen = rezensionen),
    addRezension: (state, rezension) => (state.rezensionen.push(rezension)),
    updateRezension: (state, rezension) => {
        const index = state.rezensionen.findIndex(r => r.id === rezension.id);
        if (index !== -1) state.rezensionen.splice(index, 1, rezension);
    },
    deleteRezension: (state, id) => (state.rezensionen = state.rezensionen.filter(rezension => rezension.id !== id))
};

export default {
    state,
    getters,
    actions,
    mutations
};
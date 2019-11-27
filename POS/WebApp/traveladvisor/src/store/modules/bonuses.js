import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;  

const state = {
    bonuses: [
        {id:1,
         bezeichnung:"10% auf mich",
         punkte:"punkte",
         aktiv:false,
         locationId:"teset"}
        ],
    selectedBonus: {}
};

const getters = {
    allBonuses: state => state.bonuses,
    selectedBonus: state => state.selectedBonus
};

const actions = {
    loadBonuses({ commit }) {
        commit('setBonuses', []);
        axiosWithLoader.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/praemienList")
            .then(response => {
                commit('setBonuses', response.data)
            })
            .catch(err => console.err(err));
    },   
    addBonus({ commit }, bonus) {
        commit('updateStateLoadingLocations', true);
        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/praemienDetail`, bonus)
            .then(response => commit('addBonus', response.data))
            .catch(err => console.log(err));
    },
    updateBonus({ commit }, bonus) {
        commit('updateStateLoadingLocations', true);
        axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/praemienDetail/${bonus.id}`, bonus)
            .then(response => commit('updateBonus', response.data))
            .catch(err => console.log(err));
    },
    /*async loadLocationById({ commit }, id) {
        //const response = await axios.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        //commit('setSingleLocation', response.data);
        commit('setSingleLocation', state.locations[0]);
    },
    async addLocation({ commit }, location) {
        //const response = await axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${location.id}`, location);

        //commit('addLocation', location)
        commit('addLocation', location);
    },
    async updateLocationById({ commit }, location) {
        //const response = await axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${location.id}`, location);

        //commit('updateLocation', location)
        commit('updateLocation', location);
    },
    async deleteLocation({ commit }, id) {
        //const response = await axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        // commit('deleteLocation', id)
        commit('deleteLocation', id);
    }*/
};

const mutations = {
    setBonuses: (state, bonuses) => (state.bonuses = bonuses),
    addBonus: (state, bonus) => (state.bonuses.push(bonus)),
    updateBonus: (state, bonus) => {
        const index = state.bonuses.findIndex(b => b.id === bonus.id);
        if (index !== -1) state.bonuses.splice(index, 1, bonus);
    },
    /*setSingleLocation: (state, location) => (state.selectedLocation = location),
    ,
    updateLocation: (state, location) => {
        const index = state.locations.findIndex(l => l.id === location.id);
        if (index !== -1) state.locations.splice(index, 1, location);
    },
    deleteLocation: (state, id) => (state.locations = state.locations.filter(location => location.id !== id))*/
};

export default {
    state,
    getters,
    actions,
    mutations
};
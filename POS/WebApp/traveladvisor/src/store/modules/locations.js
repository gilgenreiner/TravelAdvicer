import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = 'http://10.0.0.45:8080';

const state = {
    locations: [],
    isLoadingLocations: false
};

const getters = {
    allLocations: state => state.locations,
    isLoadingLocations: state => state.isLoadingLocations
};

const actions = {
    loadLocations({ commit }) {
        commit('setLocations', []);
        axiosWithLoader.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/locationList")
            .then(response => {
                commit('setLocations', response.data)
            })
            .catch(err => console.log(err));
    },
    loadLocationById({ commit }, id) {
        axiosWithLoader.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`)
            .then(response => commit('setLocations', new Array(response.data)))
            .catch(err => console.log(err));
    },
    addLocation({ commit }, location) {
        commit('updateStateLoadingLocations', true);
        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail`, location)
            .then(response => commit('addLocation', response.data))
            .catch(err => console.log(err))
            .finally(() => commit('updateStateLoadingLocations', false));
    },
    updateLocationById({ commit }, location) {
        commit('updateStateLoadingLocations', true);
        axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${location.id}`, location)
            .then(response => commit('updateLocation', response.data))
            .catch(err => console.log(err))
            .finally(() => commit('updateStateLoadingLocations', false));
    },
    deleteLocation({ commit }, id) {
        commit('updateStateLoadingLocations', true);
        axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`)
            .then(response => commit('deleteLocation', id))
            .catch(err => console.log(err))
            .finally(() => commit('updateStateLoadingLocations', false));
    }
};

const mutations = {
    setLocations: (state, locations) => (state.locations = locations),
    addLocation: (state, location) => (state.locations.push(location)),
    updateLocation: (state, location) => {
        const index = state.locations.findIndex(l => l.id === location.id);
        if (index !== -1) state.locations.splice(index, 1, location);
    },
    deleteLocation: (state, id) => (state.locations = state.locations.filter(location => location.id !== id)),
    updateStateLoadingLocations: (state, updateLoading) => (state.isLoadingLocations = updateLoading)
};

export default {
    state,
    getters,
    actions,
    mutations
};
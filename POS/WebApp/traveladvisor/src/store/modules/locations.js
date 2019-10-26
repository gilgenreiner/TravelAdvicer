import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = 'http://192.168.179.132:8080';

const state = {
    locations: [],
    selectedLocation: {},
    isLoading: false
};

const getters = {
    allLocations: state => state.locations,
    selectedLocation: state => state.selectedLocation,
    isLoading: state => state.isLoading
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
            .then(response => commit('setSingleLocation', response.data))
            .catch(err => console.log(err));
    },
    addLocation({ commit }, location) {
        commit('updateStateLoading', true);
        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail`, location)
            .then(response => {
                commit('updateStateLoading', false);
                commit('addLocation', response.data);
            })
            .catch(err => console.log(err));
    },
    updateLocationById({ commit }, location) {
        commit('updateStateLoading', true);
        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail`, location)
            .then(response => {
                commit('updateStateLoading', false);
                commit('updateLocation', response.data);
            })
            .catch(err => console.log(err));
    },
    async deleteLocation({ commit }, id) {
        const response = await axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        commit('deleteLocation', id)
        //commit('deleteLocation', id);
    }
};

const mutations = {
    setLocations: (state, locations) => (state.locations = locations),
    setSingleLocation: (state, location) => (state.selectedLocation = location),
    addLocation: (state, location) => (state.locations.push(location)),
    updateLocation: (state, location) => {
        const index = state.locations.findIndex(l => l.id === location.id);
        if (index !== -1) state.locations.splice(index, 1, location);
    },
    deleteLocation: (state, id) => (state.locations = state.locations.filter(location => location.id !== id)),
    updateStateLoading: (state, updateLoading) => (state.isLoading = updateLoading)
};

export default {
    state,
    getters,
    actions,
    mutations
};
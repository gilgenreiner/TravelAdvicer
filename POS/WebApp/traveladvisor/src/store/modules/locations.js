import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;

const state = {
    locations: [],
    isLoadingLocations: false,
    errorLocations: null
};

const getters = {
    allLocations: state => state.locations,
    allActiveLocations: state => state.locations.filter(location => location.aktiv === true),
    isLoadingLocations: state => state.isLoadingLocations,
    errorLocations: state => state.errorLocations
};

const actions = {
    loadLocations({ commit }, searchParams) {
        commit('setLocations', []);
        axiosWithLoader.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/locationList", { params: searchParams })
            .then(response => {
                commit('setLocations', response.data);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err));
    },
    loadLocationById({ commit }, id) {
        axiosWithLoader.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`)
            .then(response => {
                commit('setLocations', new Array(response.data));
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err));
    },
    addLocation({ commit }, location) {
        commit('updateStateLoadingLocations', true);

        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail`, location)
            .then(response => {
                commit('addLocation', response.data);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
            .finally(() => commit('updateStateLoadingLocations', false));
    },
    updateLocationById({ commit }, location) {
        commit('updateStateLoadingLocations', true);

        axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${location.id}`, location)
            .then(response => {
                commit('updateLocation', response.data);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
            .finally(() => commit('updateStateLoadingLocations', false));
    },
    deleteLocation({ commit }, id) {
        commit('updateStateLoadingLocations', true);

        axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`)
            .then(response => {
                commit('deleteLocation', id);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
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
    updateStateLoadingLocations: (state, updateLoading) => (state.isLoadingLocations = updateLoading),
    errorOccurred: (state, error) => (state.errorLocations = error)
};

export default {
    state,
    getters,
    actions,
    mutations
};
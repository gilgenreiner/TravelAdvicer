import axios from 'axios';

const baseURL = 'http://192.168.179.132:8080';

const state = {
    locations: [],
    selectedLocation: {}
};

const getters = {
    allLocations: state => state.locations,
    selectedLocation: state => state.selectedLocation
};

const actions = {
    loadLocations({ commit }) {
        axios.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/locationList")
            .then(response => commit('setLocations', response.data))
            .catch(err => console.log(err));
    },
    loadLocationById({ commit }, id) {
        axios.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`)
            .then(response => commit('setSingleLocation', response.data))
            .catch(err => console.log(err));
    },
    async addLocation({ commit }, location) {
        const response = await axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/`, location);

        commit('addLocation', response.data)
        //commit('addLocation', location);
    },
    async updateLocationById({ commit }, location) {
        const response = await axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/`, location);

        commit('updateLocation', response.data)
        //commit('updateLocation', location);
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
    deleteLocation: (state, id) => (state.locations = state.locations.filter(location => location.id !== id))
};

export default {
    state,
    getters,
    actions,
    mutations
};
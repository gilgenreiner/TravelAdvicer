import axios from 'axios';

const baseURL = 'http://172.16.204.131:8080';

const state = {
    locations: [],
    selectedLocation: {}
};

const getters = {
    allLocations: state => state.locations,
    selectedLocation: state => state.selectedLocation
};

const actions = {
    async loadLocations({ commit }) {
        //const response = await axios.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/locationList");
        fetch(baseURL + "/TravelAdvisor_WebServices/TravelGuide/locationList", {
            method: 'GET', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors' // no-cors, *cors, same-origin
        }).then(data => { return data.json() }).then(data => commit('setLocations', data)).catch(err => console.log(err));



        //commit('setLocations', await response.json());
        //commit('setLocations', []);
    },
    async loadLocationById({ commit }, id) {
        const response = await axios.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        commit('setSingleLocation', response.data);
        //commit('setSingleLocation', state.locations[0]);
    },
    async addLocation({ commit }, location) {
        const response = await axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/`, location);

        commit('addLocation', response.data)
        //commit('addLocation', location);
    },
    async updateLocationById({ commit }, location) {
        const response = await axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${location.id}`, location);

        commit('updateLocation', response.data)
        //commit('updateLocation', location);
    },
    async deleteLocation({ commit }, id) {
        const response = await axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        // commit('deleteLocation', id)
        commit('deleteLocation', id);
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
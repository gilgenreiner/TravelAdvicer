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
    async loadLocations({ commit }) {
        //const response = await axios.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/locationList");

        //commit('setLocations', response.data);
        commit('setLocations', [{
            id: "fae11412-0dfe-4d5e-9d29-5108eecf7fbb",
            bezeichnung: "Raceres",
            beschreibung: "Sehr gutes Restaurant",
            aktiv: true,
            punkte: 9500,
            branchen: [
                {
                    id: "b10bb3f7-be05-4a0b-8e42-9fafc1a9dccd",
                    bezeichnung: "Gastronomie"
                }
            ],
            besitzer: { id: "42a56ffc-98cf-402a-83e5-b54b9127b9ac" },
            koordinaten: { X: 46.604887, Y: 13.869746 }
        }, {
            id: "fae11412-0dfe-4d5e-9d29-5108eecf7fb2",
            bezeichnung: "McDonalds",
            beschreibung: "Sehr gutes Restaurant",
            aktiv: true,
            punkte: 9500,
            branchen: [
                {
                    id: "b10bb3f7-be05-4a0b-8e42-9fafc1a9dccd",
                    bezeichnung: "Gastronomie"
                }
            ],
            besitzer: { id: "42a56ffc-98cf-402a-83e5-b54b9127b9ac" },
            koordinaten: { X: 46.605100, Y: 13.841134 }
        }]);
    },
    async loadLocationById({ commit }, id) {
        //const response = await axios.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        //commit('setSingleLocation', response.data);
        commit('setSingleLocation', {
            id: "fae11412-0dfe-4d5e-9d29-5108eecf7fbb",
            bezeichnung: "Raceres",
            beschreibung: "Sehr gutes Restaurant",
            aktiv: true,
            punkte: 9500,
            branchen: [
                {
                    id: "b10bb3f7-be05-4a0b-8e42-9fafc1a9dccd",
                    bezeichnung: "Gastronomie"
                }
            ],
            besitzer: { id: "42a56ffc-98cf-402a-83e5-b54b9127b9ac" },
            koordinaten: { X: 46.604887, Y: 13.869746 }
        });
    },
    async deleteLocation({ commit }, id) {
        //const response = await axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}`);

        // commit('deleteLocation', id)
        commit('deleteLocation', id);
    }
};

const mutations = {
    setLocations: (state, locations) => (state.locations = locations),
    setSingleLocation: (state, location) => (state.selectedLocation = location),
    deleteLocation: (state, id) => (state.locations = state.locations.filter(location => location.id !== id))
};

export default {
    state,
    getters,
    actions,
    mutations
};
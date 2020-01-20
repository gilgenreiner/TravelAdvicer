import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;

const state = {
    rezensionen: [],
    isLoadingRezensionen: false
};

const getters = {
    allRezensionen: state => state.rezensionen
};

const actions = {
    loadRezensionen({ commit }, id) {
        commit('setRezensionen', []);
        axiosWithLoader.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}/rezensionen`)
            .then(response => {
                commit('setRezensionen', response.data);
                //commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err));
    },
    saveRezension({ commit }, rezension) {
        //commit('updateStateLoadingLocations', true);

        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/rezensionenDetail`, rezension)
            .then(response => {
                commit('addRezension', response.data);
                //commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
            .finally(() => commit('updateStateLoadingLocations', false));
    },
    updateRezension({ commit }, rezension) {
        //commit('updateStateLoadingLocations', true);

        axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/rezensionenDetail/${rezension.id}`, location)
            .then(response => {
                commit('updateRezension', response.data);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
            .finally(() => commit('updateStateLoadingLocations', false));
    },
    deleteRezension({ commit }, id) {
        // commit('updateStateLoadingLocations', true);

        axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/rezensionenDetail/${id}`)
            .then(response => {
                commit('deleteRezension', id);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
            .finally(() => commit('updateStateLoadingLocations', false));
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
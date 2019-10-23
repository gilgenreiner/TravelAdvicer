import axios from 'axios';

const baseURL = 'http://192.168.179.132:8080';

const state = {
    branchen: []
};

const getters = {
    allBranchen: state => state.branchen
};

const actions = {
    async loadBranchen({ commit }) {
        const response = await axios.get(baseURL + "/TravelAdvisor_WebServices/TravelGuide/brancheList");

        commit('setBranchen', response.data);
    }
};

const mutations = {
    setBranchen: (state, branchen) => (state.branchen = branchen)
};

export default {
    state,
    getters,
    actions,
    mutations
};
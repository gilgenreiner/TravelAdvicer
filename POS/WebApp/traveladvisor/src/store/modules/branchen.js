import axios from 'axios';

const baseURL = 'http://172.16.204.131:8080';

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
        /*
        commit('setBranchen', [{
            id: "b10bb3f7-be05-4a0b-8e42-9fafc1a9dccd",
            bezeichnung: "Gastronomie"
        },
        {
            id: "b10bb3f7-be05-4a0b-8e42-9fafc1a9dcc2",
            bezeichnung: "Vergnügung"
        },
        {
            id: "b10bb3f7-be05-4a0b-8e42-9fafc1a9dcc3",
            bezeichnung: "Freizeit und Sport"
        }]);
        */
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
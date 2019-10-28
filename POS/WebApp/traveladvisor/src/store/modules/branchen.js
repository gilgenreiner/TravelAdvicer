import axios from 'axios';

const baseURL = 'http://10.0.0.45:8080';

const state = {
    branchen: [],
    isLoadingBranchen: false
};

const getters = {
    allBranchen: state => state.branchen,
    isLoadingBranchen: state => state.isLoadingBranchen
};

const actions = {
    loadBranchen({ commit }) {
        commit('updateStateLoadingBranchen', true);
        axios.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/brancheList`)
            .then(response => {
                commit('updateStateLoadingBranchen', false);
                commit('setBranchen', response.data);
            })
            .catch(err => {
                console.log(err);
                commit('updateStateLoadingBranchen', false);
            });
    }
};

const mutations = {
    setBranchen: (state, branchen) => (state.branchen = branchen),
    updateStateLoadingBranchen: (state, updateLoading) => (state.isLoadingBranchen = updateLoading)
};

export default {
    state,
    getters,
    actions,
    mutations
};
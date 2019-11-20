import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;

const state = {
    branchen: [],
    isLoadingBranchen: false,
    errorBranchen: null
};

const getters = {
    allBranchen: state => state.branchen,
    isLoadingBranchen: state => state.isLoadingBranchen,
    errorBranchen: state => state.errorBranchen
};

const actions = {
    loadBranchen({ commit }) {
        commit('updateStateLoadingBranchen', true);
        axios.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/brancheList`)
            .then(response => {
                commit('setBranchen', response.data);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
            .finally(() => commit('updateStateLoadingBranchen', false));
    }
};

const mutations = {
    setBranchen: (state, branchen) => (state.branchen = branchen),
    updateStateLoadingBranchen: (state, updateLoading) => (state.isLoadingBranchen = updateLoading),
    errorOccurred: (state, error) => (state.errorBranchen = error)
};

export default {
    state,
    getters,
    actions,
    mutations
};
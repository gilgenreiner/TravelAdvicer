const state = {
    drawer: null
};

const getters = {
    drawer: state => state.drawer
};

const actions = {
    setDrawer({ commit }, value) {
        commit("setDrawer", value);
    }
};

const mutations = {
    setDrawer: (state, value) => state.drawer = value
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
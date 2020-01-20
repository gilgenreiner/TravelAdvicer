import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;

const state = {
    bonuses: [
        {
            id: 1,
            bezeichnung: "10% auf mich",
            punkte: "punkte",
            aktiv: false,
            locationId: "teset"
        }
    ],
    selectedBonus: {}
};

const getters = {
    allBonuses: state => state.bonuses,
    selectedBonus: state => state.selectedBonus
};

const actions = {
    loadBonuses({ commit }, id) {
        commit('setBonuses', []);
        axiosWithLoader.get(baseURL + `/TravelAdvisor_WebServices/TravelGuide/locationDetail/${id}/praemien`)
            .then(response => {
                commit('setBonuses', response.data)
            })
            .catch(err => console.log(err));
    },
    addBonus({ commit }, bonus) {
        axios.post(baseURL + `/TravelAdvisor_WebServices/TravelGuide/praemienDetail`, bonus)
            .then(response => commit('addBonus', response.data))
            .catch(err => console.log(err));
    },
    updateBonus({ commit }, bonus) {
        axios.put(baseURL + `/TravelAdvisor_WebServices/TravelGuide/praemienDetail/${bonus.id}`, bonus)
            .then(response => commit('updateBonus', response.data))
            .catch(err => console.log(err));
    },
    deleteBonus({ commit }, id) {
        axios.delete(baseURL + `/TravelAdvisor_WebServices/TravelGuide/praemienDetail/${id}`)
            .then(response => {
                commit('deleteBonus', id);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err))
    }
};

const mutations = {
    setBonuses: (state, bonuses) => (state.bonuses = bonuses),
    addBonus: (state, bonus) => (state.bonuses.push(bonus)),
    updateBonus: (state, bonus) => {
        const index = state.bonuses.findIndex(b => b.id === bonus.id);
        if (index !== -1) state.bonuses.splice(index, 1, bonus);
    },
    deleteBonus: (state, id) => (state.bonuses = state.bonuses.filter(bonus => bonus.id !== id)),
};

export default {
    state,
    getters,
    actions,
    mutations
};
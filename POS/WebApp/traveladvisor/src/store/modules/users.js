import axiosWithLoader from '../../http';
import axios from 'axios';
import firebase from 'firebase'

const baseURL = process.env.VUE_APP_API_URL;

const state = {
  user: {
    loggedIn: false,
    data: null
  }
}

const getters = {
  user: state => state.user
}

const actions = {
  fetchUser({ commit }, user) {
    console.log("fetching user");
    commit("SET_LOGGED_IN", user !== null);
    if (user) {
      commit("SET_USER", {
        displayName: user.displayName,
        email: user.email,
        id: user.uid
      });

      let db = firebase.firestore();
      let docRef = db.collection("users").doc(user.uid);
      console.log("loading userdata from firestore . . . ");
      docRef
        .get()
        .then(function (doc) {
          if (doc.exists) {
            console.log("Document data:", doc.data());
            commit("SET_USER", {
              displayName: doc.data().vorname,
              email: doc.data().email,
              id: doc.data().uid,
              vorname: doc.data().vorname,
              nachname: doc.data().nachname,
              typ: doc.data().typ
            })
          } else {
            console.log("No such document!");
          }
        })
        .catch(function (error) {
          console.log("Error getting document:", error);
        });
    } else {
      commit("SET_USER", null);
    }
  },
  registerUser({ commit }, user) {
    console.log(state.user.data);
    console.log(user.type);
    var route;
    if(user.type == 'besitzer')
      route = `/TravelAdvisor_WebServices/TravelGuide/besitzerDetail`;
    else
      route = `/TravelAdvisor_WebServices/TravelGuide/besucherDetail`;
    
    axios.post(baseURL + route, user)
        .then(response => {
            commit('setUser', response.data);
            commit('errorOccurred', null);
        })
        .catch(err => commit('errorOccurred', err));
        
}
}


const mutations = {
  SET_LOGGED_IN(state, value) {
    state.user.loggedIn = value;
  },
  SET_USER(state, data) {
    state.user.data = data;
  }
}

export default {
  state,
  getters,
  actions,
  mutations
};
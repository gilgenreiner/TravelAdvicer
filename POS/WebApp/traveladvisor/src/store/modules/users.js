import axiosWithLoader from '../../http';
import axios from 'axios';

const baseURL = process.env.VUE_APP_API_URL;

//export default new Vuex.Store({

   const state = {
        user: {
          loggedIn: false,
          data: null
        }
      }
     const getters = {
        user: state => state.user
      }
     const mutations = {
        SET_LOGGED_IN(state, value) {
          state.user.loggedIn = value;
        },
        SET_USER(state, data) {
          state.user.data = data;
        }
      }
      const actions = {
        fetchUser({ commit }, user) {
          commit("SET_LOGGED_IN", user !== null);
          if (user) {
            commit("SET_USER", {
              displayName: user.displayName,
              email: user.email,
              id: user.uid
            });


            var db = firebase.firestore();

          var docRef = db.collection("users").doc(user.uid);
    
          docRef
            .get()
            .then(function(doc) {
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
            .catch(function(error) {
              console.log("Error getting document:", error);
            }); 


          } else {
            commit("SET_USER", null);
          }
        }
      }


      /*
const state = {
    user: {}
};

const getters = {
    activeUser: state => state.user
};


const actions = {
    registerUser({ commit }, user) {
        console.log(user.id);
        axios.post(`http://172.16.204.131:8080` + `/TravelAdvisor_WebServices/TravelGuide/besitzerDetail`, user)
            .then(response => {
                commit('setUser', response.data);
                commit('errorOccurred', null);
            })
            .catch(err => commit('errorOccurred', err));
    }
};


const mutations = {
    setUser: (state, user) => (state.user = user),
    errorOccurred: (state, error) => (state.errorLocations = error)
};
*/
export default {
    state,
    getters,
    actions,
    mutations
};
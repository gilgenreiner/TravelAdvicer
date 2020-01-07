<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">Login</div>
          <div class="card-body">
            <div v-if="error" class="alert alert-danger">{{error}}</div>
            <form action="#" @submit.prevent="submit">
              <div class="form-group row">
                <label for="email" class="col-md-4 col-form-label text-md-right">Email</label>

                <div class="col-md-6">
                  <input
                    id="email"
                    type="email"
                    class="form-control"
                    name="email"
                    value
                    required
                    autofocus
                    v-model="form.email"
                  />
                </div>
              </div>

              <div class="form-group row">
                <label for="password" class="col-md-4 col-form-label text-md-right">Password</label>

                <div class="col-md-6">
                  <input
                    id="password"
                    type="password"
                    class="form-control"
                    name="password"
                    required
                    v-model="form.password"
                  />
                </div>
              </div>

              <div class="form-group row">
                <div class="col-md-6">
                  <div class="form-group row">
                    <div class="col-md-6">
                      <v-btn @click="submit()" rounded color="primary" dark>Login</v-btn>
                    </div>
                    <v-btn :to="{ name: 'Register' }" class="ma-2" outlined color="indigo">Register</v-btn>
                  </div>
                </div>
              </div>

              <div class="form-group row">
                <div class="col-md-6"></div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import firebase from "firebase";

export default {
  data() {
    return {
      form: {
        email: "",
        password: "",
        id: ""
      },
      user: {
        id: ""
      },
      error: null
    };
  },
  methods: {
    submit() {
      firebase
        .auth()
        .signInWithEmailAndPassword(this.form.email, this.form.password)
        .then(data => {
          this.user.id = data.user.uid;
          localStorage.setItem('userid', this.user.id )
          this.$router.replace({ name: "Account" });
        })
        .catch(err => {
          this.error = err.message;
        });
    }
  }
};
</script>
// 기본---------------------------
import "./assets/main.css";

import { createApp } from "vue";
// import { createPinia } from 'pinia'

import App from "./App.vue";
import router from "./router";

// const app = createApp(App)

// app.use(createPinia())
// app.use(router)

// app.mount('#app')
// ------------------------------

// Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignIcons.css";

const vuetify = createVuetify({
  components,
  directives,
});

createApp(App).use(router).use(vuetify).mount("#app");

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDT_1D0OV-Xhob_hbn_SFZayaGOXVsnTpA",
  authDomain: "crypto-front.firebaseapp.com",
  databaseURL: "https://crypto-front-38afb-default-rtdb.europe-west1.firebasedatabase.app/ ",
  projectId: "crypto-front",
  storageBucket: "crypto-front.appspot.com",
  messagingSenderId: "189923371135",
  appId: "1:189923371135:web:c2eb217504de971eb3f74a",
  measurementId: "G-4385J6SV4X"
};

export const BASE_DB_URL = firebaseConfig.databaseURL
export const SIGN_UP_URL = `https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${firebaseConfig.apiKey}`
export const SIGN_IN_URL = `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${firebaseConfig.apiKey}`

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

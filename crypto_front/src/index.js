import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import 'bootstrap/dist/css/bootstrap.css';
import "bootstrap-icons/font/bootstrap-icons.css";
// import CanvasJSReact from '@canvasjs/react-stockcharts';
import { Provider } from 'react-redux'
import { RouterProvider } from 'react-router-dom'
import store from './store.js'
import router from './app-routing.jsx'
import './index.css'


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <RouterProvider router={router}/>
    </Provider>
  </React.StrictMode>
);


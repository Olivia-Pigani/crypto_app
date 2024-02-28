import { configureStore } from '@reduxjs/toolkit'
import authSlice from './components/auth/authSlice'
import cryptoSlice from './components/crypto/cryptoSlice'


const store = configureStore({
  reducer: {
    auth: authSlice,
    cryptos: cryptoSlice
  }
})

export default store
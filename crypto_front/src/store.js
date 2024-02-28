import { configureStore } from '@reduxjs/toolkit'
import authSlice from './components/trader/traderSlice'
import cryptoSlice from './components/crypto/cryptoSlice'


const store = configureStore({
  reducer: {
    traders: traderSlice,
    cryptos: cryptoSlice
  }
})

export default store
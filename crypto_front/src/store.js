import { configureStore } from '@reduxjs/toolkit'
import userSlice from './components/user/userSlice'
import cryptoSlice from './components/crypto/cryptoSlice'
import walletSlice from './components/wallet/walletSlice'


const store = configureStore({
  reducer: {
    auth: userSlice,
    cryptos: cryptoSlice,
    wallets: walletSlice
  }
})

export default store
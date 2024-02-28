import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";


export const fetchCryptosAction = createAsyncThunk(
    "cryptos/fetchCryptosAction",
    async () => {
      const response = await fetch(`${BASE_DB_URL}cryptos.json`)
  
      if (!response.ok) {
        throw new Error("Something went wrong when getting cryptos...")
      }
      
      const data = await response.json()
      
      const tmpArray = []
      
      for (const key in data) {
        tmpArray.push({id: key, ...data[key]})
      }
      
      return tmpArray
    }
    )

const cryptoSlice = createSlice({
    name: "cryptos",
    initialState: {
        cryptos:[],
        isLoading: false,
        formMode:"",
        error: null,
        selectedCrypto: null
    },
    reducers: {
        setSelectedCrypto : (state, action) => {
            state.selectedCrypto = action.payload
        },
        setFormMode : (state, action) => {
            state.formMode = action.payload
            console.log(state.formMode);
        }
    },
    extraReducers: (builder) => {
        builder.addCase(fetchCryptosAction.pending, (state) => {
            state.isLoading = true
            state.error = null
            state.cryptos = []
        })
    }
})
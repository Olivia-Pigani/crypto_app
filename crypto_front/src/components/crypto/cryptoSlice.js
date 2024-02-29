import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const API_URL = "http://localhost:8080/crypto/all"; 

export const getAllCryptos = createAsyncThunk(
  "cryptos/getAllCryptos",
  async () => {
      return (await axios.get(`${API_URL}`)).data;
  }
);

export const getOnecrypto = createAsyncThunk(
  "cryptos/getOnecrypto",
  async (id) => {
      return (await axios.get(`${API_URL}/crypto/${id}`)).data;
  }
);


const cryptoSlice = createSlice({
  name: "cryptos",
  initialState: {
    cryptos: [],
    selectedCrypto: null
  },
  reducers: {
    setSelectedCrypto: (state, action) => {
      state.selectedCrypto = action.payload;
    }
  },
  extraReducers: (builder) => {
    builder.addCase(getAllCryptos.fulfilled, (state, action) => {
      console.log("Fulfilled with data:", action.payload);
      state.cryptos = action.payload;
    });
  
    builder.addCase(getAllCryptos.pending, (state) => {
      console.log("Pending...");
      state.cryptos = [];
    });
  
    builder.addCase(getAllCryptos.rejected, (state, action) => {
      console.error("Rejected with error:", action.error.message);
      state.cryptos = [];
    });
    
  }
  
});

export const { setSelectedCrypto } = cryptoSlice.actions;

export default cryptoSlice.reducer;

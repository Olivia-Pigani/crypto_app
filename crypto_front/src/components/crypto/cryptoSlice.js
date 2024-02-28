import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

// Replace this with your actual API URL
const API_URL = "your_api_url";

export const signIn = createAsyncThunk(
  "trader/signIn",
  async (trader) => {
    const response = await axios.post(`${API_URL}/traders/signin`, {
      email: trader.email,
      password: trader.password
    });
    return response.data;
  }
);

export const signUp = createAsyncThunk(
  "trader/signUp",
  async (trader) => {
    const response = await axios.post(`${API_URL}/traders/signup`, {
      email: trader.email,
      password: trader.password
    });
    return response.data;
  }
);

const traderSlice = createSlice({
  name: "trader",
  initialState: {
    traderMode: "Sign in",
    trader: null
  },
  reducers: {
    logOutAction(state, action) {
      state.trader = null;
      localStorage.removeItem('token');
    },
    setTraderMode: (state, action) => {
      state.traderMode = action.payload;
    }
  },
  extraReducers: (builder) => {
    builder.addCase(signIn.fulfilled, (state, action) => {
      state.trader = action.payload;
      console.log(state.trader);
      localStorage.setItem('token', action.payload.idToken);
    });

    builder.addCase(signUp.fulfilled, (state, action) => {
      state.trader = action.payload;
      console.log(state.trader);
      localStorage.setItem('token', action.payload.idToken);
    });
  }
});

export const { logOutAction, setTraderMode } = traderSlice.actions;

export default traderSlice.reducer;

import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

export const signIn = createAsyncThunk(
  "user/signIn",
  async (user) => {
      const response = await axios.post(`${API_URL}/users/signin`, {
          email: user.email,
          password: user.password
      });
      return response.data;
  }
);

export const signUp = createAsyncThunk(
  "user/signUp",
  async (user) => {
      const response = await axios.post(`${API_URL}/users/signup`, {
          email: user.email,
          password: user.password
      });
      return response.data;
  }
);

const traderSlice = createSlice({
  name: "traders",
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

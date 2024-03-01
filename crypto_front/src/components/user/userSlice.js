import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const API_URL = "";

export const signIn = createAsyncThunk(
  "auth/signIn",
  async (user) => {
    const response = await axios.post(`${API_URL}/users/signin`, {
      email: user.email,
      password: user.password
    });
    return response.data;
  }
);

export const signUp = createAsyncThunk(
  "auth/signUp",
  async (user) => {
    const response = await axios.post(`${API_URL}/users/signup`, {
      email: user.email,
      password: user.password
    });
    return response.data;
  }
);


const userSlice = createSlice({
  name: "auth",
  initialState: {
    userMode: "Register",
    user: null
  },
  reducers: {
    logOutAction(state, action) {
      state.user = null;
      localStorage.removeItem('token');
    },
    setUserMode: (state, action) => {
        state.userMode = action.payload;
    }
  },
  extraReducers: (builder) => {
    builder.addCase(signIn.fulfilled, (state, action) => {
      state.user = action.payload;
      console.log(state.user);
      localStorage.setItem('token', action.payload.idToken);
    });

    builder.addCase(signUp.fulfilled, (state, action) => {
      state.user = action.payload;
      console.log(state.user);
      localStorage.setItem('token', action.payload.idToken);
    });
  }
});

export const { logOutAction, setUserMode } = userSlice.actions;

export default userSlice.reducer;

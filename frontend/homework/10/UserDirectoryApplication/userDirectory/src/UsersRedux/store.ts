import { configureStore } from "@reduxjs/toolkit";
import userReducer, { userapi } from "./Users"

export const store = configureStore({
    reducer : {
        users : userReducer,
        [userapi.reducerPath] : userapi.reducer
    },
    middleware: (getDefaultMiddleware) => 
        getDefaultMiddleware().concat(userapi.middleware)  
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
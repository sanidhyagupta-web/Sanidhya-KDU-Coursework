
import { createSlice } from "@reduxjs/toolkit"
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react"

const BASE_URL = "https://dummyjson.com/users"


export interface UserState{
    id : number
    firstName : string,
    LastName : string,
    email : string,
    age : number
}

const initialState : UserState = {
    id : Math.random(),
    firstName : "",
    LastName : "",
    email : "",
    age : 0
}

const userapi = createApi({
   reducerPath : "userApi",

   baseQuery : fetchBaseQuery({
        baseUrl : BASE_URL
   }),

   tagTypes : ["User"],

   endpoints: (builder) => ({
        getUsers : builder.query<any,void>({
            query : () => "/", 
            providesTags : ["User"]
        })
        ,
        addUsers : builder.mutation({
            query : (UserState) => ({
                url : "/add",
                method : "POST",
                body : UserState,
            }),
            invalidatesTags : ["User"]
        })
        ,
        getUserById : builder.query({
            query : (id :string) => `/${id}`
        })
   })
})

const userSlice = createSlice({
    name: "user",
    initialState,
    reducers : {
        setFirstName(state , action : {payload : string}){
            state.firstName = action.payload
        },
        setLastName(state, action : {payload : string}){
            state.LastName = action.payload
        },
        setEmail(state,action : {payload : string}){
            state.email = action.payload
        },
        setAge(state, action : {payload : number}){
            state.age = action.payload
        }
    }
})

export const currentUserFirstName = (state : any) => state.users.firstName
export const currentUserLastName = (state : any) => state.users.LastName
export const currentUserEmail = (state : any) => state.users.email
export const currentUserAge = (state : any) => state.users.age
export const currentUserId = (state : any) => state.users.id

export const { useGetUsersQuery, useAddUsersMutation, useGetUserByIdQuery } = userapi;
export default userSlice.reducer 
export {userapi}
export const {setFirstName , setLastName , setAge , setEmail} = userSlice.actions
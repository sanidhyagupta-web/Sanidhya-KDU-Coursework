import React from 'react'
import '../styles/Header.scss'
import { useDispatch } from 'react-redux'
import { currentUserAge, currentUserEmail, currentUserFirstName, currentUserLastName, setAge, setEmail, setFirstName, setLastName, useAddUsersMutation, useGetUsersQuery } from '../UsersRedux/Users'
import { useAppSelector } from '../hooks'

const Header = () => {
  const dispatch = useDispatch()

  const firstName = useAppSelector(currentUserFirstName);
  const lastName = useAppSelector(currentUserLastName);
  const email = useAppSelector(currentUserEmail);
  const age = useAppSelector(currentUserAge);

  const [addUser, { isLoading, isError, error, isSuccess }] = useAddUsersMutation();

  const handleEmail = (e : React.ChangeEvent<HTMLInputElement>) => {
      dispatch(setEmail(e.target.value))
  }

  const handleFirstName = (e : React.ChangeEvent<HTMLInputElement>) => {
      dispatch(setFirstName(e.target.value))
  }

  const handleLastName = (e : React.ChangeEvent<HTMLInputElement>) => {
      dispatch(setLastName(e.target.value))
  }

  const handleAge = (e : React.ChangeEvent<HTMLInputElement>) => {
      dispatch(setAge(Number(e.target.value)))
  }

  const handleAddUser = ()=>{
      try{
        addUser({
            firstName : firstName,
            LastName : lastName,
            email : email,
            age : age
        }).unwrap()
      } catch(err){
        console.log("Add user failed ")
      }
  }

  return (
    <div>
        <h1 className='header' >User Directory</h1>

        <h3>Add new User</h3>
        <div className='formContainer'>
            <form >
            <div className='container1'>
            <div className='subcontainer'>
            <p>First Name</p>
        
            <input 
                id = 'firstname'
                name='firstname'
                type='text'
                placeholder = "Enter your name"
                onChange={handleFirstName}
                required
            />
            </div>

            <div className='subcontainer'>
            <p>Last Name</p>
            <input 
                id = 'lastname'
                name = 'lastname'
                type='text'
                placeholder='Enter Last name'
                onChange={handleLastName}
                required
            />
            </div>

            <div className='subcontainer'>
            <p>Email</p>
            <input
                id = 'email'
                name = 'email'
                type='email'
                placeholder='Enter email'
                onChange={handleEmail}
            />
            </div>

            <div className='subcontainer'>
            <p>Age</p>
            <input
                id = 'age'
                name='age'
                type='number'
                placeholder='Enter age'
                min="1"
                max="120"
                onChange={handleAge}
            />
            </div>
            </div>
            </form>
        </div>

        <button className='btn' onClick={handleAddUser} disabled={isLoading}>
            {isLoading ? "Adding..." : "Add user"} 
        </button>
        {isSuccess && <p> User added!</p>}
        {isError && <p> Failed to add user</p>}
    </div>
  )
}

export default Header

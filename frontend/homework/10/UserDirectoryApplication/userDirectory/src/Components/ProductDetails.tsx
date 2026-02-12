import React from 'react'
import { useGetUserByIdQuery, type UserState } from '../UsersRedux/Users';
import { data, Link, useParams } from 'react-router-dom';

const ProductDetails = () => {
    const { id } = useParams<{ id: string }>();
    const { data: data1, isLoading: isLoading1, isError: isError1 } = useGetUserByIdQuery(String(id));  

    if(isLoading1){
        return <p>Loading ...</p>
    }
  
    if(isError1){
       return  <p>Error ...</p>
    }
  
  return (
    <div>

        <Link to="/">Back</Link>
        <div>
            <img src={data1.image} alt="Error" />
            <p>Name : {data1.firstName} {data1.lastName}</p>
            <p>Id : {data1.id}</p>

            <div>
                <h4>Contact Information</h4>
                <p>Email : {data1.email}</p>
                <p>Phone : {data1.phone}</p>
            </div>

            <div>
                <p>Personal Information : {data1.age}</p>
            </div>
        </div>
        
    </div>
  )
}

export default ProductDetails

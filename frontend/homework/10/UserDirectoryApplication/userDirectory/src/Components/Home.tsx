import React from "react";
import { useGetUserByIdQuery, useGetUsersQuery, type UserState } from "../UsersRedux/Users";
import ProductDetails from "./ProductDetails";
import { useNavigate } from "react-router-dom";
import Header from "./Header";
import "../styles/Home.scss"

const Home = () => {
  const { data, isLoading, isError } = useGetUsersQuery();
  const navigate = useNavigate()

  const handleDetailsPage = (user : UserState) => {
    navigate(`/id/${user.id}`)
  }

  if (isLoading) return <p>Loading...</p>;
  if (isError) return <p>Something went wrong</p>;

  return (
    <div className="container">
        <Header/>
      <h2>Users ({data?.users.length})</h2>

      <div className="grid">
        {data?.users.map((user: any) => (
          <div key={user.id} className="card" role="button" onClick={() => handleDetailsPage(user)}>
            <img src={user.image} alt={user.firstName} />

            <div className="info">
              <h3>
                {user.firstName} {user.lastName}
              </h3>
              <p>{user.email}</p>
              <p>{user.phone}</p>
              <p className="age">Age: {user.age}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;

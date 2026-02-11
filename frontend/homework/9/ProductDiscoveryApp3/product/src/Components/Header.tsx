import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/main.scss";
import { useDebounce } from "../hooks/useDebounce";
import { useAppDispatch, useAppSelector } from "../hooks";
import { setSearchQuery, clearSearch } from "../thunks/product";
import { selectTotalItems } from "../thunks/cartSlice";

const Header = () => {
  const [searchText, setSearchText] = useState("");
  const debouncedSearchText = useDebounce(searchText, 100);
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const totalItems = useAppSelector(selectTotalItems);

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(e.target.value);
  };

useEffect(() => {
  const q = debouncedSearchText.trim();

  if (!q) {
    dispatch(setSearchQuery(""));
    return;
  }

  dispatch(setSearchQuery(q));
  navigate(`/product/search?q=${encodeURIComponent(q)}`);
}, [debouncedSearchText]);

  return (
    <nav className="Header-top">
      <h1 className="header">Product Discovery</h1>

      <ul>
        <input
          type="text"
          placeholder="Search for products"
          className="search-bar"
          name="search-bar"
          value={searchText}
          onChange={handleSearch}
        />

        <button
          onClick={() => {
            setSearchText("");
            dispatch(clearSearch());
            navigate("/");
            return
          }}
          className="clear-btn"
        >
          X
        </button>

        <Link
          to="/"
          className="home-btn"
          onClick={() => {
            setSearchText("");
            dispatch(clearSearch());
          }}
        >
          Home
        </Link>

        {/* ✅ Cart Button */}
        <Link to="/cart" className="cart-btn">
          Cart ({totalItems})
        </Link>
      </ul>
    </nav>
  );
};

export default Header;

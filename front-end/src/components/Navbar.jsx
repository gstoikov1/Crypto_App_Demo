import React from 'react'
import { NavLink } from 'react-router-dom'

const Navbar = () => {
    const linkClass = ({isActive}) => 
      isActive 
        ? 'flex-1 text-center bg-blue-700 tab-button' 
        : 'flex-1 text-center hover:bg-blue-700 tab-button';


  return (
    <nav className="bg-blue-200 border-b">
      <div className="flex ">
        <NavLink to="/" className={linkClass}>View Live Prices</NavLink>
        <NavLink to="/buy" className={linkClass}>Buy</NavLink>
        <NavLink to="/sell" className={linkClass}>Sell</NavLink>
        <NavLink to="/wallet" className={linkClass}>Wallet</NavLink>
        <NavLink to="/transactions" className={linkClass}>Transactions</NavLink>
        <NavLink to="/register" className={linkClass}>Register</NavLink>
        <NavLink to="/reset" className={linkClass}>Reset Data</NavLink>
      </div>
    </nav>
  );
};

export default Navbar;

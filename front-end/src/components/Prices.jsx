import React, { useState, useEffect } from 'react';

const Prices = () => {
  const [prices, setPrices] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPrices = async () => {
      try {
        const res = await fetch('http://localhost:8080/api/prices');
        const data = await res.json();
        setPrices(Object.entries(data.prices));
      } catch (error) {
        setPrices([]);
      } finally {
        setLoading(false);
      }
    };

    fetchPrices();

    const intervalId = setInterval(fetchPrices, 1000);

    return () => clearInterval(intervalId);
  }, []);

  return (
    <div className="text-blue-600 flex-1 border p-4">
      <div className="flex font-bold border-b pb-2 mb-2">
        <div className="w-1/2 text-right">Crypto Currency</div>
        <div className="w-1/2 text-right">Live Value (in USD)</div>
      </div>
      {loading ? (
        <div className="text-center py-4">Loading...</div>
      ) : (
        prices.map(([symbol, value]) => (
          <div className="flex border-b py-1" key={symbol}>
            <div className="w-1/3 text-left">
            <img
              src={`/images/${symbol.split('/')[0]}.png`}
              className="w-15 h-15 object-contain"
            />
            </div>
            <div className="w-1/2 text-center">{symbol.split('/')[0]}</div>
            <div className="w-1/2 text-right">{value}</div>
          </div>
          
        ))
      )}
    {prices.length === 0 && !loading && (
    <div className="text-center py-4 text-red-500">
        <p>No connection to server</p>
        <p>Attempting to reconnect...</p>
    </div>
    )}    </div>
  );
};

export default Prices;

import { useState } from "react";
import CustomDropdown from "./CustomDropdown";
import { handleTradeResponse } from '../utils/handleTradeResponse';


const Buy = () => {

  const [form, setForm] = useState({
    username: '',
    password: '',
    symbol: '',
    quantity: '',
  });

  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleBuy = async () => {

    if (!form.username || form.username.length < 3 || 
      !form.password || form.password.length < 3
    ) {
      setError('Username and password must be at least 3 characters long');
      return;
    } else if (!form.quantity || form.quantity <= 0) {
      setError('Quantity must be a positive value');

    }

    try {
      const res = await fetch('http://localhost:8080/api/buy', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form)
      });

      const data = await res.json();
      const { message, error } = handleTradeResponse(data, form);

      setMessage(message);
      setError(error);
      
    } catch (err) {
      setError('Error buying asset.');
      setMessage('');
    }
  };

  return (
    <div className="max-w-xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Buy Cryptocurrency</h1>

      <div className="space-y-3">
        <input name="username" placeholder="Username" className="w-full p-2 border rounded" value={form.username} onChange={handleChange} />
        <input type="password" name="password" placeholder="Password" className="w-full p-2 border rounded" value={form.password} onChange={handleChange} />
        <CustomDropdown value={form.symbol} onChange={handleChange} />
        <input name="quantity" type="number" placeholder="Quantity" className="w-full p-2 border rounded" value={form.quantity} onChange={handleChange} />
        <button onClick={handleBuy} className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Buy</button>
      </div>

      {message && (
      <p
      className="text-green-600 mt-4"
      dangerouslySetInnerHTML={{ __html: message }}
      />
    )}

      {error && <p className="text-red-500 mt-4">{error}</p>}
    </div>
  );
};

export default Buy;

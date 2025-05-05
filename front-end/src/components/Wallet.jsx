import { useState } from "react";

const Wallet = () => {
  const [form, setForm] = useState({
    username: '',
    password: ''
  });

  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const fetchWallet = async () => {
    try {

        if (!form.username || form.username.length < 3 || !form.password || form.password.length < 3) {
            setError('Username and password must be at least 3 characters long');
            return;
        }

      const res = await fetch('http://localhost:8080/api/wallet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: form.username,
          password: form.password
        }),
      });
      
      const data = await res.json();
      console.log(data);
      if (data.responseResult === 'SUCCESS') {
        setResponse(data);
        setError('');
      } else {
          setError('Invalid username or password.');
          setResponse(null);
      }
    } catch (err) {
      setError('Error fetching wallet data.');
      setResponse(null);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Wallet</h1>

      <div className="space-y-3">
        <input
          type="text"
          name="username"
          placeholder="Username"
          className="w-full p-2 border rounded"
          value={form.username}
          onChange={handleChange}
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          className="w-full p-2 border rounded"
          value={form.password}
          onChange={handleChange}
        />
        <button
          onClick={fetchWallet}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Fetch Wallet
        </button>
      </div>

      {error && <p className="text-red-500 mt-4">{error}</p>}

      {response && (
  <div className="mt-6">
    <h2 className="text-xl font-semibold mb-2">Wallet Balances:</h2>
    <h1>Available Funds: {response.totalFunds}</h1>
    <ul className="space-y-3">
      {Object.entries(response.quantityPerSymbol).map(([symbol, quantity]) => {
        const imageName = symbol.split('/')[0];
        return (
          <li
            key={symbol}
            className="flex items-center gap-4 bg-gray-100 p-3 rounded shadow"
          >
            <img
              src={`/images/${imageName}.png`}
              alt={imageName}
              className="w-8 h-8 object-contain"
              onError={(e) => (e.target.style.display = 'none')}
            />
            <div>
              <p className="font-medium">{symbol}</p>
              <p>{quantity}</p>
            </div>
          </li>
        );
      })}
    </ul>
  </div>
)}
    </div>
  );
};
export default Wallet;
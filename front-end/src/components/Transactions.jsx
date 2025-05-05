import { useState } from "react";

const Transactions = () => {
  const [form, setForm] = useState({
    username: '',
    password: ''
  });

  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');
  const [showAllTransactions, setShowAllTransactions] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const fetchTransactions = async () => {
    try {
        if (!form.username || form.username.length < 3 || !form.password || form.password.length < 3) {
            setError('Username and password must be at least 3 characters long');
            return;
        }

        const res = await fetch('http://localhost:8080/api/transactions', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: form.username,
          password: form.password
        }),
      });

      const data = await res.json();

      if (data.responseResult === 'SUCCESS') {
        setError('');
        data.transactions.sort((a, b) => new Date(b.tradeTime) - new Date(a.tradeTime));
        setResponse(data.transactions);
      } else {
        setResponse(null);
        setError('Invalid username or password.');
      } 
    } catch (err) {
      setError('Error fetching transaction data.');
      setResponse(null);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Transactions</h1>

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
          onClick={fetchTransactions}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Fetch Transactions
        </button>
      </div>

      {error && <p className="text-red-500 mt-4">{error}</p>}

      {response && (
  <div className="mt-6">
    <h2 className="text-xl font-semibold mb-2">Transactions History:</h2>
    <ul className="space-y-3">
      {(showAllTransactions ? response : response.slice(0, 10)).map(({ symbol, price, quantity, tradeTime, transactionType, transactionId }) => {
        const imageName = symbol.split('/')[0];
        return (
          <li
            key={transactionId}
            className="flex items-center gap-4 bg-gray-100 p-3 rounded shadow"
          >
            <img
              src={`/images/${imageName}.png`}
              alt={imageName}
              className="w-8 h-8 object-contain"
              onError={(e) => (e.target.style.display = 'none')}
            />
            <div>
              <p className="font-medium">{symbol.split('/')[0]}</p>
              <p>{quantity}</p>
              <p>{tradeTime}</p>
              <p>{transactionType}</p>
            </div>
          </li>
        );
      })}
    </ul>
    {response.length > 10 && (
      <div className="flex justify-center mt-4">
      <button
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-center"
        onClick={() => setShowAllTransactions(!showAllTransactions)}
      >
        {showAllTransactions ? 'Show Less' : 'Show All'}
      </button>
    </div>
    )}
  </div>
)}

    </div>
  );
};
export default Transactions;
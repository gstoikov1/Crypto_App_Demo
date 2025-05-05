import React, { useState, useEffect } from 'react';
import { handleTradeResponse } from '../utils/handleTradeResponse';


const UserReset = () => {
  const [form, setForm] = useState({
    username: '',
    password: ''
  });

  const [message, setMessage] = useState(null);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const fetchReset = async () => {
    try {

        if (!form.username || form.username.length < 3 || 
            !form.password || form.password.length < 3 
          ) {
            setError('Username, email and password must be at least 3 characters long');
            return;
        }

      const res = await fetch('http://localhost:8080/api/user/reset', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: form.username,
          password: form.password
        }),
      });
      
      const data = await res.json();

      const { message, error } = handleTradeResponse(data, form);
      
      setMessage(message);
      setError(error);
    } catch (err) {
      setError('Error resetting user.');
      setMessage(null);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Registration Form</h1>

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
          onClick={fetchReset}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Reset 
        </button>
      </div>

      {error && <p className="text-red-500 mt-4">{error}</p>}

      {message && <p className="text-green-600 mt-4">{message}</p>}
    </div>
  );
};

export default UserReset;
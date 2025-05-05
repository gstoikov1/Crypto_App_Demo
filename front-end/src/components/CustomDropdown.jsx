import { useState } from 'react';
import currencies from '../currencies.json';

const CustomDropdown = ({ value, onChange }) => {
  const [open, setOpen] = useState(false);

  const handleSelect = (currency) => {
    onChange({ target: { name: 'symbol', value: currency } });
    setOpen(false);
  };

  return (
    <div className="relative">
      <button
        onClick={() => setOpen(!open)}
        className="w-full p-2 border rounded flex justify-between items-center"
      >
        <span className="flex items-center gap-2">
          {value ? (
            <>
              <img
                src={`/images/${value.split('/')[0]}.png`}
                alt={value}
                className="w-6 h-6 object-contain"
              />
              {value.split('/')[0]}
            </>
          ) : (
            'Select currency'
          )}
        </span>
        <span className="ml-2">▼</span>
      </button>
  
      {open && (
        <ul className="absolute z-10 w-full bg-white border mt-1 max-h-60 overflow-auto rounded shadow">
          {currencies.currencies.map((currency) => (
            <li
              key={currency}
              onClick={() => handleSelect(currency)}
              className="flex items-center gap-2 p-2 hover:bg-gray-100 cursor-pointer"
            >
              <img
                src={`/images/${currency.split('/')[0]}.png`}
                alt={currency}
                className="w-6 h-6 object-contain"
              />
              {currency.split('/')[0]}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
  
}
export default CustomDropdown;

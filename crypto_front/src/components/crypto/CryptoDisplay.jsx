import React from "react";

const CryptoDisplay = ({ crypto }) => {
  if (!crypto) {
    return <div>Loading...</div>;
  }

  return (
    <div className="flex items-center justify-between rounded-lg bg-white shadow-[0_2px_15px_-3px_rgba(0,0,0,0.07),0_10px_20px_-2px_rgba(0,0,0,0.04)] dark:bg-neutral-700 w-full">
      <div className="px-6 py-3 border-b-2 border-neutral-100 dark:border-neutral-600 dark:text-neutral-50 font-bold">
        {crypto.symbol}
      </div>
      <div className="p-6">
        {crypto.name}
      </div>
      <div className="p-6">
        $7.6
      </div>
    </div>
  );
}

export default CryptoDisplay;

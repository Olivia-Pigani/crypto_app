const CryptoDisplay = (props) => {
  const crypto = props.crypto;

  if (!crypto) {
    return <div>Loading...</div>; 
  }

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <div className="mb-4">
        <div class="max-w-4xl px-10 py-6 bg-white rounded-lg shadow-md h-full">
        <div class="flex flex-col justify-between items-left h-full">
          <div class="flex justify-between items-center">
            <div class="mt-2">
          <span>{crypto.name || "No Name Available"}</span>
          </div>
        <div className="card-body">
          <ul className="list-group list-group-flush">
            <li className="list-group-item bg-light text-dark d-flex">
              <b className="me-auto"> </b>
              {crypto.symbol || "No Symbol Available"}
            </li>
            <li className="list-group-item bg-light text-dark d-flex">
              <b className="me-auto">Name: </b>
              {crypto.name || "No Name Available"}
            </li>
            {/* <li className="list-group-item bg-light text-dark d-flex">
              <b className="me-auto">Value: </b>
              {crypto.cryptoValue || "No Value Available"}
            </li> */}
          </ul>
          </div>
        </div>
      </div>
    </div>
    </div>
    </div>
  );
};

export default CryptoDisplay;

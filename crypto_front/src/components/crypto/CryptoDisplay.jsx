

const CryptoDisplay = (props) => {
  const crypto = props.crypto


  return (
    <div className="col">
      <div className="card bg-light text-dark border border-light rounded">
        <div className="card-header d-flex align-items-center">
          <span>{crypto.name}</span>
         
        </div>
        <div className="card-body">
          <ul className="list-group list-group-flush">
            <li className="list-group-item bg-light text-dark d-flex"><b className="me-auto">Name: </b>{crypto.name}</li>
            <li className="list-group-item bg-light text-dark d-flex"><b className="me-auto">Value: </b>{crypto.cryptoValue}</li>
          </ul>
        </div>
      </div>
    </div>
  )
}

export default CryptoDisplay

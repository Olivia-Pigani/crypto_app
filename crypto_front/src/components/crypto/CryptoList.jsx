import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAllCryptos } from "./cryptoSlice";
import CryptoDisplay from "./CryptoDisplay";

const CryptoList = () => {
  const dispatch = useDispatch();
  const cryptos = useSelector((state) => state.cryptos.cryptos);

  useEffect(() => {
    dispatch(getAllCryptos());
  }, []);

  return (
    <div className="row my-3">
      <div className="rounded bg-light text-dark p-3">
        <div className="d-flex align-items-center">
          <h3>Cryptos</h3>
        </div>
        <hr />
        {cryptos.length === 0 ? (
          <p>There is no crypto in the database!</p>
        ) : (
          <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4 gy-4">
            {cryptos.map((crypto) => (
              <CryptoDisplay key={crypto.id} crypto={crypto} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default CryptoList;

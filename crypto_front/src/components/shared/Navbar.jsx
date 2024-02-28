import { logOutAction } from "../auth/authSlice";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";

const NavBar = () => {
    const dispatch = useDispatch()
    const user = useSelector(state => state.auth.trader)
  
    return ( 
        <nav className="bg-gray-800 px-6 py-4 shadow">
          <div className="flex flex-col container mx-auto md:flex-row md:items-center md:justify-between">
            <div className="flex justify-between items-center">
                <Link className="navbar-brand" to={"/"}><i className="bi bi-globe"></i> LightWallet</Link>
            </div>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              {user ? (
                  <button className="btn btn-secondary ms-auto" onClick={() => dispatch(logOutAction())}>Sign out</button>
                ) : (
                  <Link className="btn btn-primary ms-auto" to="/sign">Sign In</Link>
              )}
            </div>
          </div>
        </nav>
     );
}
 
export default NavBar;
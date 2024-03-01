import { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setUserMode, signIn, signUp } from "./userSlice";
import { useNavigate } from "react-router-dom";


const SignForm = () => {
  const dispatch = useDispatch();
  const userMode = useSelector((state) => state.auth.userMode);
  const navigate = useNavigate();
  const emailRef = useRef();
  const passwordRef = useRef();
  const firstNameRef = useRef();
  const lastNameRef = useRef();
  const userNameRef = useRef();

  const submitHandler = (event) => {
    event.preventDefault();

    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    const firstName = firstNameRef.current.value;
    const lastName = lastNameRef.current.value;
    const userName = userNameRef.current.value;

    const credentials = {
      email,
      password,
      returnSecureToken: true,
      firstName,
      lastName,
      userName,
    };

    userMode === "Sign in" ? dispatch(signIn(credentials)) : dispatch(signUp(credentials));
    navigate('/');
  };

  return (
    <div className="h-screen d-flex justify-content-center">
      <div className="flex md:w-1/2 justify-center py-10 items-center bg-white">
        <form className="bg-white p-8 rounded-md shadow-md w-full md:w-2/3 lg:w-3/4" onSubmit={submitHandler}>
          <h1 className="text-gray-800 font-bold text-2xl mb-2">Hello!</h1>
          <p className="text-sm text-gray-600 mb-4">{userMode}</p>

          {userMode === "Register" && (
          <div className="mb-4">
            <input
              className="w-full py-2 px-3 rounded-md border-2 focus:outline-none focus:border-indigo-600"
              type="text"
              placeholder="First name"
              ref={firstNameRef}
            />
          </div>
          )}

          {userMode === "Register" && (
            <div className="mb-4">
              <input
                className="w-full py-2 px-3 rounded-md border-2 focus:outline-none focus:border-indigo-600"
                type="text"
                placeholder="Last name"
                ref={lastNameRef}
              />
            </div>
          )}

          <div className="mb-4">
            <input
              className="w-full py-2 px-3 rounded-md border-2 focus:outline-none focus:border-indigo-600"
              type="text"
              placeholder="Username"
              ref={userNameRef}
            />
          </div>

          <div className="mb-4">
            <input
              className="w-full py-2 px-3 rounded-md border-2 focus:outline-none focus:border-indigo-600"
              type="text"
              placeholder="Email Address"
              ref={emailRef}
            />
          </div>

          <div className="mb-6">
            <input
              className="w-full py-2 px-3 rounded-md border-2 focus:outline-none focus:border-indigo-600"
              type="password"
              placeholder="Password"
              ref={passwordRef}
            />
          </div>

          <div className="text-center">
            {userMode === "Sign in" && (
              <button
                type="button"
                className="text-white bg-gradient-to-r from-teal-400 via-teal-500 to-teal-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-teal-300 dark:focus:ring-teal-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
              >
                {userMode}
              </button>
            )}

            {userMode === "Register" && (
              <button
              type="button"
              className="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
              onClick={() => dispatch(setUserMode(userMode === "Sign in" ? "Register" : "Sign in"))}
            >
              {userMode === "Sign in" ? "Sign in" : "Register"}
            </button>
            
            )}
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignForm;

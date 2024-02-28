import { useRef } from "react"
import { useDispatch, useSelector } from "react-redux"
import { setAuthMode, signInAction, signUpAction } from "./authSlice"
import { useNavigate } from "react-router-dom"

const SignForm = () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const authMode = useSelector(state => state.auth.authMode)
    const emailRef = useRef()
    const passwordRef = useRef()

    const submitHandler = (event) => {
        event.preventDefault()

        const email = emailRef.current.value
        const password = passwordRef.current.value

        const credentials = {
        email,
        password, 
        returnSecureToken: true
        }

        authMode === "Se connecter" ? dispatch(signInAction(credentials)) : dispatch(signUpAction(credentials))
        navigate('/')
    }

  return (
    <>
        <h3>{authMode}</h3>
        <hr />
        <form class="max-w-sm mx-auto" onSubmit={submitHandler}>
            <div className="mb-5">
                <label for="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your email</label>
                <input type="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                    focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 
                    dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@flowbite.com" required ref={emailRef}/>
            </div>
            <div className="mb-5">
                <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your password</label>
                <input type="password" id="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 
                    focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white 
                    dark:focus:ring-blue-500 dark:focus:border-blue-500" required ref={passwordRef} />
            </div>
            <div className="text-end">
                <button className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium 
                    rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">{authMode}</button>
                <button 
                    className="btn btn-primary ms-auto"
                    onClick={() => dispatch(setAuthMode(authMode === "Sign in" ? "Register" : "Sign in"))}>
                        {authMode === "Sign in" ? "Register" : "Sign in"}
                </button>
            </div>
        </form>
    </>
  )
}

export default SignForm
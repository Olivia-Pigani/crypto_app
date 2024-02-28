import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import SignForm from "./components/auth/SignForm"
import ProtectedRoute from "./components/shared/ProtectedRoute";


const router = createBrowserRouter([
    {
        path :"/",
        element : <App />,
        children: [
            {
                path: "/",
                element: <CryptoList />
            },
            {
                path :"/wallet",
                element : <ProtectedRoute><Wallet /></ProtectedRoute>
            },
            {
                path: "/sign",
                element: <SignForm />
            }
        ]
    },
])

export default router
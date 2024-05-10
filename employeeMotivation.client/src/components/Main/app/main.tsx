import ReactDOM from 'react-dom/client'
import App from '../../App/App'
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import ErrorPage from '../../ErrorPage/ErrorPage';


const router = createBrowserRouter([
  {
    path: "*",
    element: <App />,
    errorElement: <ErrorPage/>
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <RouterProvider router={router} />
);

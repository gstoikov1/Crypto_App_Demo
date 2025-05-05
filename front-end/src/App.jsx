import {Route, createBrowserRouter, createRoutesFromElements, RouterProvider} from 'react-router-dom';
import './styles.css';
import HomePage from './pages/HomePage';
import MainLayout from './layouts/MainLayout';
import WalletPage from './pages/WalletPage';
import BuyPage from './pages/BuyPage';
import SellPage from './pages/SellPage';
import TransactionsPage from './pages/TransactionsPage';
import RegisterPage from './pages/RegisterPage';
import UserResetDataPage from './pages/UserResetDataPage';

const router = createBrowserRouter(
  createRoutesFromElements(
  <Route path='/' element={<MainLayout />}>
    <Route index element={<HomePage />}/>
    <Route path='/wallet' element={<WalletPage />}/>
    <Route path='/buy' element={<BuyPage />}/>
    <Route path='/sell' element={<SellPage />}/>
    <Route path='/transactions' element={<TransactionsPage />}/>
    <Route path='/register' element={<RegisterPage />}/>
    <Route path='/reset' element={<UserResetDataPage />}/>
  </Route>
  )
);


function App() {
  return <RouterProvider router={router}/>
}

export default App;

import HomePage from '../pages/HomePage/HomePage'
import Login from '../pages/Login/Login'
import Person from '../pages/Person/Person'
import OrderManager from "../pages/Order/OrderManager";
import OrderBuy from "../pages/Order/OrderBuy";

export const mainRoutes = [{
  pathname: '/login',
  component: Login
},{
  pathname: '/home',
  component: HomePage,
  title: '主页',
},{
  pathname: '/order',
  component: OrderManager,
  title: '订单管理',
},{
  pathname: '/buy',
  component: OrderBuy,
  title: '购买页面',
},]


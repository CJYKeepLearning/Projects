import React from 'react';
import {render} from 'react-dom';
import 'antd/dist/antd.css';
import './index.css';
import {HashRouter as Router, Route, Switch, Redirect} from 'react-router-dom'
import {mainRoutes} from './routes'
import {Provider} from "react-redux";
import store from "./redux/Store";
import zhCN from 'antd/es/locale/zh_CN';
import {ConfigProvider} from "antd";

render(
  <Provider store={store} >
    <ConfigProvider locale={zhCN}>
      <Router>
        <Switch>
          {
            mainRoutes.map(route => {
              return <Route key={route.pathname} path={route.pathname} component={route.component}/>
            })
          }
          <Redirect to='/home' from='/' exact/>
        </Switch>
      </Router>
    </ConfigProvider>
  </Provider>,
  document.getElementById('root')
);

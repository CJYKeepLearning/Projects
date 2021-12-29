import { combineReducers } from 'redux';

import isLogin from './LoginReducer';

const rootReducer = combineReducers({
  isLogin
})

export default rootReducer;

import { createStore } from 'redux';
import RootReducer from './CombineReducer';

const store = createStore(RootReducer)

export default store;

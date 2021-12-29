import { message } from "antd";
import Axios from "axios";
import qs from 'qs';


import { TRAIN_LIST, TRAIN_ADD, TRAIN_DELETE } from "../../constants/requestURL";

export const GET_TRAINLIST_REQUEST = 'GET_TRAINLIST_REQUEST';
export const GET_TRAINLIST_SUCCESS = 'GET_TRAINLIST_SUCCESS';
export const GET_TRAINLIST_FAILURE = 'GET_TRAINLIST_FAILURE';

export const ADD_TRAINLIST_REQUEST = 'ADD_TRAINLIST_REQUEST';
export const ADD_TRAINLIST_SUCCESS = 'ADD_TRAINLIST_SUCCESS';
export const ADD_TRAINLIST_FAILURE = 'ADD_TRAINLIST_FAILURE';

export const DELETE_TRAINLIST_REQUEST = 'DELETE_TRAINLIST_REQUEST';
export const DELETE_TRAINLIST_SUCCESS = 'DELETE_TRAINLIST_SUCCESS';
export const DELETE_TRAINLIST_FAILURE = 'DELETE_TRAINLIST_FAILURE';

// const stationInfo = [{"acrossDays":0,"station":"深圳","time":"07:00"},{"acrossDays":1,"station":"哈尔滨","time":"22:30"}], 
// const seatInfo = [{"num":10,"type":"硬座"},{"num":5,"type":"硬卧"}]

const stationInfo ="[{\"acrossDays\":0,\"station\":\"深圳\",\"time\":\"07:00\"},{\"acrossDays\":1,\"station\":\"哈尔滨\",\"time\":\"22:30\"}]"
const seatInfo = "[{\"num\":10,\"type\":\"硬座\"},{\"num\":5,\"type\":\"硬卧\"}]"
export const addtrainlist = ({ trainId, startStation, endStation, startTime, endTime }) => (dispatch) => {
    dispatch({ type: ADD_TRAINLIST_REQUEST });
    return Axios.post(TRAIN_ADD, ({ trainId, startStation, endStation, startTime, endTime, acrossDays: 1, stationInfo,seatInfo }), {
        headers: {
            'token': window.sessionStorage.getItem('Token'),
            'Content-Type': 'application/json'
        }
    }).then(
        res => res.data
    ).then(res => {
        if (res.code === 0) {
            message.info("添加成功")
            dispatch({ type: ADD_TRAINLIST_SUCCESS, data: res.data });
        } else {
            dispatch({ type: ADD_TRAINLIST_FAILURE });
            message.error('添加失败：' + res.message)
        }
    }).catch(err => {
        dispatch({ type: ADD_TRAINLIST_FAILURE });
        console.error(err);
        message.error('添加失败，请求异常');
    })
}

export const deletetrainlist = ({ trainId }) => (dispatch) => {
    dispatch({ type: DELETE_TRAINLIST_REQUEST });
    return Axios.post(TRAIN_DELETE, qs.stringify({ trainId }), {
        headers: {
            'token': window.sessionStorage.getItem('Token')
        }
    }).then(
        res => res.data
    ).then(res => {
        if (res.code === 0) {
            message.info("修改成功")
            dispatch({ type: DELETE_TRAINLIST_SUCCESS });
            dispatch(gettrainlist())
        } else {
            dispatch({ type: DELETE_TRAINLIST_FAILURE });
            message.error('修改失败：' + res.message)
        }
    }).catch(err => {
        dispatch({ type: DELETE_TRAINLIST_FAILURE });
        console.error(err);
        message.error('修改失败，请求异常');
    })
}

export const gettrainlist = () => (dispatch) => {
    dispatch({ type: GET_TRAINLIST_REQUEST });
    return Axios.get(TRAIN_LIST,{
        headers: {
            'token': window.sessionStorage.getItem('Token')
        }
    }).then(
        res => res.data
    ).then(res => {
        if (res.code === 0) {
            let newlist = res.data.map((item, index) => {
                return {
                    ...item,
                    key: index
                }
            })
            // message.info("获取成功")
            dispatch({ type: GET_TRAINLIST_SUCCESS, data: newlist });
        } else {
            dispatch({ type: GET_TRAINLIST_FAILURE });
            message.error('获取失败：' + res.message)
        }
    }).catch(err => {
        dispatch({ type: GET_TRAINLIST_FAILURE });
        console.error(err);
        message.error('获取失败，请求异常');
    })
}
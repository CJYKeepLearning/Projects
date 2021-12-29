import '../../pages/HomePage/HomePage.css';
import {DatePicker, message} from "antd";
import React from "react";

import moment from 'moment';
import 'moment/locale/zh-cn';
import zhCN from 'antd/es/date-picker/locale/zh_CN';

class DateBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      day: this.props.value,
    }
  }

  handleChange = value => {
    this.props.onValueChange(value);
  }

  render() {
    return (
      <DatePicker
        className={this.props.className}
        locale={zhCN}
        placeholder={'日期'}
        defaultValue={moment()}
        onChange={this.handleChange}/>
    );
  }
}

export default DateBox;

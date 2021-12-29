import {Select} from 'antd';
import React from "react";
import {req_get} from "../../request";

const {Option} = Select;


class AddressBox extends React.Component {
  state = {
    data: []
  };

  handleSearch = value => {
    if (value) {
      req_get('/ticket/city', {city: value}, data => this.setState({data}));
    } else {
      this.setState({data: []});
    }
  };

  handleChange = value => {
    this.props.onValueChange(value);
  };

  render() {
    return (
      <Select
        showSearch
        value={this.props.value}
        className={this.props.className}
        placeholder={this.props.placeholder}
        allowClear={true}
        defaultActiveFirstOption={false}
        showArrow={false}
        filterOption={false}
        onSearch={this.handleSearch}
        onChange={this.handleChange}>
        {this.state.data.map(d => <Option value={d}>{d}</Option>)}
      </Select>
    );
  }
}

export default AddressBox;

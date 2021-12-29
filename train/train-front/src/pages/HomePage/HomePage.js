import React from "react";
import AddressBox from "../../components/input/AddressBox";
import DateBox from "../../components/input/DateBox";
import {Button, Image, Input, message, PageHeader, Slider, Space} from "antd";
import {ReactComponent as Arrow} from '../../images/icon/arrow.svg'
import Layout, {Content, Header} from "antd/lib/layout/layout";
import moment from 'moment';
import {req_get} from "../../request";
import TicketList from "../../components/list/TicketList";
import "./HomePage.css"
import {Link} from "react-router-dom";
import {connect} from "react-redux";
import {login_fail, login_success, mapStateToProps} from "../../redux/LoginReducer";

class HomePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      startCity: undefined,
      endCity: undefined,
      day: moment(),
      tickets: [],
      isLogin: window.sessionStorage.getItem('TOKEN') !== null
    }
  }

  logout = () => {
    message.info("注销成功");
    window.sessionStorage.removeItem('TOKEN');
    this.setState({isLogin: false})
    this.props.login_fail()
  }

  setStartCity = (startCity) => {
    this.setState({startCity});
  }

  setEndCity = (endCity) => {
    this.setState({endCity});
  }

  setDay = (day) => {
    this.setState({day});
  }

  searchTicket = (transfer) => {
    req_get('/ticket/search', {
      startCity: this.state.startCity,
      endCity: this.state.endCity,
      day: this.state.day.valueOf(),
      transfer: transfer,
    }, (tickets) => {
      console.log(tickets)
      this.setState({tickets})
    })
  }

  skipOrder = () => {
    if (this.state.isLogin) {
      this.props.history.push('/order');
    } else {
      message.warn('请先登录')
    }
  }

  log = () => {
    console.log(this.props.isLogin)
  }

  render() {
    return (
      <Layout>
        <Header className="ant-header">
          <Space className="header-child" size={'large'}>
            <AddressBox className="input"
                        placeholder={'出发'}
                        value={this.state.startCity}
                        onValueChange={this.setStartCity}/>
            <Button type="text" icon={<Arrow style={{width: 20, height: 20}} onClick={this.log}/>}/>
            <AddressBox className="input"
                        placeholder={'到达'}
                        value={this.state.endCity}
                        onValueChange={this.setEndCity}/>
            <DateBox className="input"
                     value={this.state.day}
                     onValueChange={this.setDay}/>
            <Button onClick={() => this.searchTicket(0)} style={{background: '#E2F1FF'}}>搜索</Button>
            <Button onClick={() => this.searchTicket(1)} style={{background: '#E2F1FF'}}>换乘搜索</Button>
            <Button onClick={this.skipOrder} style={{background: '#E2F1FF'}}>我的订单</Button>
            {
              this.state.isLogin ?
                <Button onClick={this.logout} style={{background: '#FFF1F0'}}>注销</Button> :
                <Button>
                  <Link to="/login">登录</Link>
                </Button>
            }
          </Space>
        </Header>
        <Content>
          <TicketList tickets={this.state.tickets} isLogin={this.state.isLogin} history={this.props.history}/>
        </Content>
      </Layout>
    );
  }
}

export default connect(mapStateToProps, {login_success, login_fail})(HomePage);

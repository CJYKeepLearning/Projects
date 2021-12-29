import React from "react";
import {Button, Card, Divider, Input, Layout, message, PageHeader, Select, Space, Tag} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import './index.css'
import Text from "antd/es/typography/Text";
import {req_get, req_post_form} from "../../request";
import {Option} from "antd/es/mentions";

class OrderBuy extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      ticketId: undefined,
      ticket: undefined,
      name: undefined,
      userno: undefined,
    }
  }

  componentDidMount() {
    req_get('/ticket/info', {ticketId: this.props.location.query.id},
      (data) => {
        var ticket = data.ticket;
        var train = data.train;
        var startTime = new Date(Date.parse(ticket.startTime.replace(/-/g, "/")));
        var endTime = new Date(Date.parse(ticket.endTime.replace(/-/g, "/")));
        var last = (endTime.getTime() - startTime.getTime()) / 1000 / 60
        var time_str;
        if (last % 60 === 0)
          time_str = `${Math.floor(last / 60)}小时`
        else
          time_str = `${Math.floor(last / 60)}小时${last % 60}分`
        this.setState({
          ticketId: ticket.ticketId,
          trainId: ticket.trainId,
          startStation: ticket.startStation,
          endStation: ticket.endStation,
          startTime: ticket.startTime.substr(5, 11),
          endTime: ticket.endTime.substr(5, 11),
          ticketType: ticket.ticketType,
          ticketPrice: ticket.ticketPrice,
          time_str: time_str,
        })
      })
  }

  submitOrder = () => {
    console.log(this.state.userno)
    if (this.state.username === '崔金玉' && this.state.userno === '370832200203300922') {
      req_post_form('/ticket/buy', {ticketId: this.state.ticketId},
        (data) => {
          message.info('创建订单成功，请及时支付')
          const div = document.createElement('divform');
          div.innerHTML = data.form;
          document.body.appendChild(div);
          document.forms[0].submit();
        })
    } else {
      message.warn('证件号验证错误')
    }

  }

  render() {
    return (
      <Layout>
        <Header className="ant-header">
          <PageHeader
            className="site-page-header"
            onBack={() => window.history.back()}
            title="提交订单"
            subTitle="Order Buy"
          />
        </Header>
        <Content className="order-buy-content">
          <Space direction="vertical" size={30}>
            <Card title="车次信息" headStyle={{fontSize: 17}} hoverable>
              <Space size={100}>
                <Text style={{fontWeight: 'bold', fontSize: 20}}>{this.state.trainId}</Text>
                <Space direction="vertical">
                  <Space>
                    <Tag color="#FF5500">始</Tag>
                    <Tag color="red">{this.state.startStation}</Tag>
                  </Space>
                  <Space>
                    <Tag color="#6D81D1">终</Tag>
                    <Tag color="blue">{this.state.endStation}</Tag>
                  </Space>
                </Space>
                <Space direction="vertical">
                  <Space>
                    <Tag color="#FF5500">始</Tag>
                    <Text style={{fontWeight: 'bold', fontSize: 16}}>{this.state.startTime}</Text>
                  </Space>
                  <Space>
                    <Tag color="#6D81D1">终</Tag>
                    <Text style={{fontWeight: 'bold', fontSize: 16}}>{this.state.endTime}</Text>
                  </Space>
                </Space>
                <Text style={{fontWeight: 'bold', fontSize: 16}}>{this.state.time_str}</Text>
                <Space size={5}>
                  <Text style={{fontWeight: 'bold', fontSize: 16}}>{this.state.ticketType}</Text>
                  <Text style={{fontWeight: 'bold', color: '#FF5500', fontSize: 18}}>￥{this.state.ticketPrice}</Text>
                </Space>
              </Space>
            </Card>
            <Card title="乘客信息" headStyle={{fontSize: 17}} hoverable>
              <Space size={50}>
                <Input size="large" placeholder="姓名(与证件一致)" style={{width: 250}}
                       onChange={(e) => this.setState({username: e.target.value})}/>
                <Input.Group size="large" compact>
                  <Select size="large" style={{width: 130}} defaultValue="身份证">
                    <Option value="身份证">身份证</Option>
                    <Option value="护照">护照</Option>
                    <Option value="台湾通行证">台湾通行证</Option>
                    <Option value="港澳通行证">港澳通行证</Option>
                  </Select>
                  <Input style={{width: 250}} placeholder="证件号" onChange={(e) => this.setState({userno: e.target.value})}/>
                </Input.Group>
              </Space>
            </Card>
            <Card hoverable>
              <Text style={{fontWeight: 'bold', fontSize: 17}}>订单总额: </Text>
              <Text style={{fontWeight: 'bold', fontSize: 19, color: "red"}}>￥{this.state.ticketPrice}</Text>
            </Card>
            <Space style={{'display': 'flex', 'justify-content': 'center'}}>
              <Button className="order-buy-btn" onClick={this.submitOrder}>
                立即支付
              </Button>
            </Space>
          </Space>
        </Content>
      </Layout>
    );
  }
}

export default OrderBuy;

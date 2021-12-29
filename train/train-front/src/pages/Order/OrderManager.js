import React from "react";
import {Button, Card, Col, Layout, Menu, message, Modal, PageHeader, Row, Space, Tag} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import './index.css'
import {req_get, req_post_form} from "../../request";
import Text from "antd/es/typography/Text";
import {ExclamationCircleOutlined} from "@ant-design/icons";

class OrderManager extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      key: "全部",
      orders: [],
    }
  }

  componentDidMount() {
    this.refreshTickets()
  }

  refreshTickets = () => {
    req_get('/order/list', {},
      (orders) => {
        this.setState({orders})
      })
  }

  handleClick = e => {
    this.setState({key: e.key});
  };

  filterOrders = () => {
    let k = this.state.key;
    if (k === '全部') {
      return this.state.orders;
    }
    if (k === '已改签') {
      k = '未出行'
    }
    let res = []
    for (let i = 0; i < this.state.orders.length; i++) {
      let order = this.state.orders[i];
      if (k === order.orderStatus)
        res.push(order);
    }
    return res;
  }

  submitOrder = (orderId) => {
    req_post_form('/order/buy', {orderId: orderId},
      (data) => {
        const div = document.createElement('divform');
        div.innerHTML = data.form;
        document.body.appendChild(div);
        document.forms[0].submit();
      })
  }

  getTagColor = (status) => {
    switch (status) {
      case '未出行':
        return 'orange';
      case '待支付':
        return 'red';
      case '已完成':
        return 'green';
      case '已退票':
        return 'purple';
      case '已改签':
        return 'geekblue';
      case '已取消':
        return 'cyan';
      default:
        return 'cyan';
    }
  }

  refundTicket = (orderId) => {
    Modal.confirm({
      title: '确认退票?',
      icon: <ExclamationCircleOutlined/>,
      onOk: () => {
        req_post_form('/order/refund', {orderId: orderId},
          () => {
            message.info('退票成功')
            this.refreshTickets()
          })
      }
    })
  }

  render() {
    return (
      <Layout>
        <Header className="ant-header">
          <PageHeader
            className="site-page-header"
            onBack={() => this.props.history.push('/home')}
            title="订单管理"
            subTitle="Order Manager"
          />
        </Header>
        <Content style={{background: '#E2F1FF'}}>
          <Menu className="order-menu" onClick={this.handleClick} selectedKeys={this.state.key}
                mode="horizontal">
            <Menu.Item key="全部">
              全部
            </Menu.Item>
            <Menu.Item key="未出行">
              未出行
            </Menu.Item>
            <Menu.Item key="待支付">
              待支付
            </Menu.Item>
            <Menu.Item key="已完成">
              已完成
            </Menu.Item>
            <Menu.Item key="已退票">
              已退票
            </Menu.Item>
            <Menu.Item key="已取消">
              已取消
            </Menu.Item>
          </Menu>
          <Row gutter={[25, 25]} style={{margin: 'auto 200px'}}>
            {
              this.filterOrders().map(
                (t) => (
                  <Col span={6}>
                    <Card className="order-manager-card" hoverable>
                      <Space direction="vertical">
                        <Space size={5}>
                          <Text style={{fontWeight: 'bold', fontSize: 16}}>{t.ticket.startStation}</Text>
                          <Text>-></Text>
                          <Text style={{fontWeight: 'bold', fontSize: 16,}}>{t.ticket.endStation}</Text>
                          <Tag color={this.getTagColor(t.orderStatus)} style={{marginLeft: 15}}>{t.orderStatus}</Tag>
                        </Space>
                        <Space size={0}>
                          <Tag>始</Tag>
                          <Text style={{fontSize: 14}}>{t.ticket.startTime.substr(5, 11)}</Text>
                        </Space>
                        <Space size={0}>
                          <Tag>终</Tag>
                          <Text style={{fontSize: 14}}>{t.ticket.endTime.substr(5, 11)}</Text>
                        </Space>
                        <Space>
                          <Tag>座位</Tag>
                          <Text style={{fontWeight: 'bold', fontSize: 16}}>{t.seat.trainId}</Text>
                          <Text style={{fontWeight: 'bold'}}>{t.seat.carriage}厢</Text>
                          <Text style={{fontWeight: 'bold'}}>{t.seat.seatNum}号</Text>
                        </Space>
                        <Space>
                          <Tag>座位类型</Tag>
                          <Text style={{fontWeight: 'bold'}}>{t.ticket.ticketType}</Text>
                        </Space>
                        <Space>
                          <Tag>订单时间</Tag>
                          <Text style={{fontSize: 14}}>{t.buyTime.substr(5, 14)}</Text>
                        </Space>
                        <Space style={{margin: '0 0 0 100px'}}>
                          <Text style={{fontWeight: 'bold', fontSize: 15}}>合计: </Text>
                          <Text style={{fontWeight: 'bold', fontSize: 18, color: "red"}}>￥{t.ticket.ticketPrice}</Text>
                        </Space>
                        <Space size={25} style={{margin: 'auto 20px'}}>
                          <Button style={{background: '#F0F5FF'}}
                                  onClick={() => this.submitOrder(t.orderId)}>支付</Button>
                          <Button onClick={() => this.refundTicket(t.orderId)}
                                  style={{background: '#F0F5FF'}}>退票</Button>
                        </Space>
                      </Space>
                    </Card>
                  </Col>
                )
              )
            }
          </Row>
        </Content>
      </Layout>
    )
  }
}

export default OrderManager;

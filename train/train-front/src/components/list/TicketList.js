import React from "react";
import {List, Typography, Divider, Space, Tag, Table, Button, message, Row, Col} from 'antd';
import Text from "antd/es/typography/Text";

const parserDate = (date) => {
  return new Date(Date.parse(date.replace(/-/g, "/")));
}

class TicketList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      transfer: 0,

    }
  }

  columns = [
    {
      title: '车次',
      key: 'trainId',
      render: item => (
        <Row gutter={[0, 20]}>
          <Col span={24}>
            <Text style={{fontWeight: 'bold', fontSize: 20}}>{item.tickets[0].items[0].trainId}</Text>
          </Col>
          {
            item.tickets.length !== 2 ? <></> :
              <>
                <Col span={24}>
                  <Text>换乘</Text>
                </Col>
                <Col span={24}>
                  <Text style={{fontWeight: 'bold', fontSize: 20}}>{item.tickets[1].items[0].trainId}</Text>
                </Col>
              </>
          }
        </Row>
      )
    },
    {
      title: '始/终站',
      key: 'station',
      render: item => (
        <Row gutter={[0, 20]}>
          {
            item.tickets.map(
              (t) => (
                <Col span={24}>
                  <Space direction="vertical">
                    <Space size="15px">
                      <Tag color="#FF5500">始</Tag>
                      <Tag color="red">{t.items[0].startStation}</Tag>
                    </Space>
                    <Space size="15px">
                      <Tag color="#6D81D1">终</Tag>
                      <Tag color="blue">{t.items[0].endStation}</Tag>
                    </Space>
                  </Space>
                </Col>
              )
            )
          }
        </Row>
      )
    },
    {
      title: '始/终时间',
      key: 'time',
      sorter: (a, b) => parserDate(a.tickets[0].items[0].startTime) - parserDate(b.tickets[0].items[0].startTime),
      render: item => (
        <Row gutter={[0, 20]}>
          {
            item.tickets.map(
              (t) => (
                <Col span={24}>
                  <Space direction="vertical">
                    <Space>
                      <Tag color="#FF5500">始</Tag>
                      <Text style={{
                        fontWeight: 'bold',
                        fontSize: 16
                      }}>{t.items[0].startTime.substr(5, 11)}</Text>
                    </Space>
                    <Space>
                      <Tag color="#6D81D1">终</Tag>
                      <Text style={{
                        fontWeight: 'bold',
                        fontSize: 16
                      }}>{t.items[0].endTime.substr(5, 11)}</Text>
                    </Space>
                  </Space>
                </Col>
              )
            )
          }
        </Row>
      ),
    },
    {
      title: '运行时间',
      key: 'lastTime',
      render: item => {
        var startTime = new Date(Date.parse(item.tickets[0].items[0].startTime.replace(/-/g, "/")));
        var endTime = new Date(Date.parse(item.tickets[0].items[0].endTime.replace(/-/g, "/")));
        var last = (endTime.getTime() - startTime.getTime()) / 1000 / 60;
        if (item.tickets[1]) {
          startTime = new Date(Date.parse(item.tickets[0].items[0].startTime.replace(/-/g, "/")));
          endTime = new Date(Date.parse(item.tickets[0].items[0].endTime.replace(/-/g, "/")));
          last += (endTime.getTime() - startTime.getTime()) / 1000 / 60;
        }
        item.lastTime = last;
        var time_str;
        if (last % 60 === 0)
          time_str = `${Math.floor(last / 60)}小时`
        else
          time_str = `${Math.floor(last / 60)}小时${last % 60}分`
        return (
          <Text style={{fontWeight: 'bold', fontSize: 16}}>{time_str}</Text>
        )
      },
      sorter: (a, b) => {
        return a.lastTime - b.lastTime
      }
    },
    {
      title: '票价',
      key: 'price',
      render: item => (
        <Row gutter={[0, 20]}>
          {
            item.tickets.map(
              (t) => (
                <Col span={24}>
                  <Space direction="vertical">
                    {
                      item.tickets[0].items.map(
                        (t) => (
                          <Space>
                            <Text style={{fontWeight: 'bold'}}>{t.ticketType}</Text>
                            <Text style={{fontWeight: 'bold', color: '#FF5500', fontSize: 18}}>￥{t.ticketPrice}</Text>
                          </Space>
                        )
                      )
                    }
                  </Space>
                </Col>
              )
            )
          }
        </Row>
      ),
      sorter: (a, b) => {
        var mina = 2147413647, minb = 2147413647;
        for (let i = 0; i < a.tickets[0].items.length; i++) {
          mina = Math.min(mina, a.tickets[0].items[i].ticketPrice);
        }
        for (let i = 0; i < b.tickets[0].items.length; i++) {
          minb = Math.min(minb, b.tickets[0].items[i].ticketPrice);
        }
        return mina - minb;
      }
    },
    {
      title: '剩余票数',
      key: 'lastTicketNum',
      render: item => (
        <Row gutter={[0, 20]}>
          {
            item.tickets.map(
              (t) => (
                <Col span={24}>
                  <Space direction="vertical">
                    {
                      item.tickets[0].items.map(
                        (t) => (
                          <Tag color={t.ticketNum === 0 ? '#FF5500' : '#3cce1d'}
                               style={{fontWeight: 'bold'}}>{t.ticketNum}</Tag>
                        )
                      )
                    }
                  </Space>
                </Col>
              )
            )
          }
        </Row>
      ),
      sorter: (a, b) => {
        var maxa = 2147413647, maxb = 2147413647;
        for (let i = 0; i < a.tickets[0].items.length; i++) {
          maxa = Math.max(maxa, a.tickets[0].items[i].ticketNum);
        }
        for (let i = 0; i < b.tickets[0].items.length; i++) {
          maxb = Math.min(maxb, b.tickets[0].items[i].ticketNum);
        }
        return maxa - maxb;
      }
    },
    {
      title: "",
      key: "action",
      render: item => (
        <Row gutter={[0, 20]}>
          {
            item.tickets.map(
              (t) => (
                <Col span={24}>
                  <Space direction="vertical">
                    {
                      item.tickets[0].items.map(
                        (t) => (
                          t.ticketNum === 0 ?
                            <Button style={{background: '#E2F1FF'}} disabled>无票</Button> :
                            <Button onClick={() => this.buyTicket(t.ticketId)}
                                    style={{background: '#E2F1FF'}}>购票</Button>
                        )
                      )
                    }
                  </Space>
                </Col>
              )
            )
          }
        </Row>
      )
    }
  ];

  buyTicket = (ticketId) => {
    if (window.sessionStorage.getItem('TOKEN')) {
      this.props.history.push({pathname: '/buy', query: {id: ticketId}});
    } else {
      message.warn('请先登录')
    }
  }

  render() {
    return (
      <Table columns={this.columns} dataSource={this.props.tickets}/>
    )
  }
}

export default TicketList;

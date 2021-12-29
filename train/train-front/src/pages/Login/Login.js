import React from "react";
import {Button, Card, Form, Input, message} from "antd";
import {req_post_form} from "../../request";
import {UnlockOutlined, UserOutlined} from "@ant-design/icons";
import './index.css'
import {mapStateToProps, login_success, login_fail} from '../../redux/LoginReducer'
import {connect} from "react-redux";
import {Redirect} from "react-router-dom";

class Login extends React.Component {
  onFinish = (userInfo) => {
    req_post_form('/auth/login', userInfo, data => {
      message.info("登录成功")
      window.sessionStorage.setItem('TOKEN', data.token)
      this.props.login_success()
    })
  };

  render() {
    return (
      window.sessionStorage.getItem('TOKEN') ? <Redirect to='/home'/> :
        <Card title="火车票售票系统" className="login-style" hoverable={true}>
          <Form className="login-form"
                onFinish={this.onFinish}>
            <Form.Item
              name="username"
              rules={[{required: true, message: '请输入用户名'}]}>
              <Input size="large" prefix={<UserOutlined/>} placeholder="用户名"/>
            </Form.Item>

            <Form.Item
              name="password"
              rules={[{required: true, message: '请输入密码'}]}>
              <Input.Password size="large" prefix={<UnlockOutlined/>} placeholder="密码"/>
            </Form.Item>
            <Form.Item>
              <Button htmlType="submit">
                登录
              </Button>
            </Form.Item>
          </Form>
        </Card>
    );
  }
}

export default connect(mapStateToProps, {login_success, login_fail})(Login);

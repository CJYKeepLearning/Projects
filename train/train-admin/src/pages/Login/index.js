import React, { useEffect } from 'react'
import { Button, Card, Form, Input, message } from "antd";
import { onLogin } from './login_redux';
import { useDispatch,  useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom'
import { UnlockOutlined, UserOutlined } from "@ant-design/icons";
import './index.css'

export default function Index() {
    let history = useHistory()
    let dispatch = useDispatch();

    let isLogin = useSelector(state => state.Login.isLogin)
    let onFinish = (e) => {
        console.log(e);
        dispatch(onLogin(e));
    };

    useEffect(() => {
        if (isLogin === true) history.push('/admin');
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [isLogin]);

    return (
        <div>
            <Card title="WELCOME LOGIN" className="login-style" hoverable={true}>
                <Form className="login-form"
                    onFinish={onFinish}>
                    <Form.Item
                        name="username"
                        rules={[{ required: true, message: '请输入用户名' }]}>
                        <Input size="large" prefix={<UserOutlined />} placeholder="用户名" />
                    </Form.Item>

                    <Form.Item
                        name="password"
                        rules={[{ required: true, message: '请输入密码' }]}>
                        <Input.Password size="large" prefix={<UnlockOutlined />} placeholder="密码" />
                    </Form.Item>
                    <Form.Item>
                        <Button htmlType="submit">
                            登录
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    )
}

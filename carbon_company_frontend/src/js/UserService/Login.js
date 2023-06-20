import React from "react";
import '../../css/UserService.css'
import {back_url} from "../../Function";
import {Form, Input} from 'antd';

class Login extends React.Component {
    checkUser = () => {
        let usr = document.getElementById("usr").value;
        let pwd = document.getElementById("pwd").value;
        if (usr === "" || pwd === "") {
            alert("用户名或密码为空，请重新输入。");
            return;
        }
        let info = {
            'company_name': usr,
            'password': pwd,
        };
        fetch(back_url + "/CheckCompany", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(info),
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if (data.toString() === "-1") {
                    alert("用户名或密码错误，请重新输入。");
                    window.location.href = "/Login";
                } else {
                    if (data[2].toString() !== "1") {
                        alert("出行工具公司'" + data[1].toString() + "'登录成功，点击进入主界面！");
                        window.location.href = "/Page?" + data[0].toString();
                    } else {
                        alert("认证机构'" + data[1].toString() + "'登录成功，点击进入主界面！");
                        window.location.href = "/Manager?" + data[0].toString();
                    }
                }
            })
            .catch(function (ex) {
                console.log('parsing failed', ex);
            })
    }

    render() {
        return (
            <div className="usr">
                <div>
                    <div className="login_part">
                        <Form name="login_form" initialValues={{remember: true}}>
                            <h1 className="usr_title">登录您的系统</h1>
                            <Form.Item name="username" rules={[{required: true, message: "用户名"}]}>
                                <Input placeholder="请输入用户名" id="usr"/>
                            </Form.Item>
                            <Form.Item name="password" rules={[{required: true, message: "密码"}]}>
                                <Input.Password placeholder="请输入密码" id="pwd"/>
                            </Form.Item>
                            <Form.Item>
                                <button type="button" className="usr_button" onClick={this.checkUser}>
                                    登录
                                </button>
                            </Form.Item>
                            <br/>
                            <a href={"/Register"} className="to_another">为您的公司注册账号！</a>
                        </Form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;

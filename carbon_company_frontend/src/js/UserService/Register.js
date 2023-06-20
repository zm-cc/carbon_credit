import React from "react";
import '../../css/UserService.css'
import {back_url} from "../../Function";
import {Form, Input} from "antd";

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.getTravelTypes().then();
    }

    getTravelTypes = async () => {
        fetch(back_url + "/GetAllTypes", {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                let select_type = document.getElementById("typename");
                for (let i = 0; i < data.length; i++) {
                    select_type.options.add(new Option(data[i].toString(), data[i].toString()));
                }
                select_type.options.selectedIndex = 0;
            })
            .catch(function (ex) {
                console.log('parsing failed', ex);
            })
    }

    checkNew = () => {
        let usr = document.getElementById("usr_").value;
        let pwd1 = document.getElementById("pwd1").value;
        let pwd2 = document.getElementById("pwd2").value;
        let phone = document.getElementById("phone").value;
        let email = document.getElementById("email").value;
        let s = document.getElementById("typename");
        let type = s.options[s.options.selectedIndex].value;
        if (usr.length === 0 || pwd1.length === 0 || pwd2.length === 0 || phone.length === 0 || email.length === 0) {
            alert("某些项为空，请重新输入");
            return;
        }
        if (pwd1 !== pwd2) {
            alert("两次密码不一致，请重新输入");
            return;
        } else if (/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(email) === false) {
            alert("邮箱格式错误，请重新输入");
            return;
        } else if (isNaN(phone)) {
            alert("电话格式错误，请重新输入");
            return;
        } else {
            let num = parseFloat(phone);
            if (num % 1 !== 0) {
                alert("电话格式错误，请重新输入");
                return;
            }
        }
        let info = {
            'company_name': usr,
            'password': pwd1,
            'contact_phone': phone,
            'contact_email': email,
            'type_name': type,
        };
        fetch(back_url + "/CreateCompany", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(info),
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if (data.toString() !== "0") {
                    alert("出行公司'" + usr + "'注册成功！");
                    window.location.href = "/Register";
                } else {
                    alert("出行公司'" + usr + "'已存在。");
                }
            })
            .catch(function (ex) {
                console.log('parsing failed', ex);
            })
    }

    render() {
        return (
            <div className="register">
                <div>
                    <div className="login_part">
                        <Form name="login_form" initialValues={{remember: true}}>
                            <h1 className="usr_title">注册一个新账号</h1>
                            <Form.Item name="username" rules={[{required: true, message: "用户名"}]}>
                                <ul>
                                    用户：
                                    <Input style={{width: 270}} placeholder="请输入用户名" id="usr_"/>
                                </ul>
                            </Form.Item>
                            <Form.Item name="password" rules={[{required: true, message: "密码"}]}>
                                <ul>
                                    密码：
                                    <Input.Password style={{width: 270}} placeholder="请输入密码" id="pwd1"/>
                                </ul>
                            </Form.Item>
                            <Form.Item name="password" rules={[{required: true, message: "密码"}]}>
                                <ul>
                                    重复：
                                    <Input.Password style={{width: 270}} placeholder="请重复密码" id="pwd2"/>
                                </ul>
                            </Form.Item>
                            <Form.Item name="phone" rules={[{required: true, message: "电话"}]}>
                                <ul>
                                    电话：
                                    <Input style={{width: 270}} placeholder="请输入联系电话" id="phone"/>
                                </ul>
                            </Form.Item>
                            <Form.Item name="email" rules={[{required: true, message: "邮箱"}]}>
                                <ul>
                                    邮箱：
                                    <Input style={{width: 270}} placeholder="请输入联系邮箱" id="email"/>
                                </ul>
                            </Form.Item>
                            <Form.Item name="type" rules={[{required: true, message: "出行工具类型"}]}>
                                <ul className="selector">
                                    请选择出行工具类型：
                                    <select id="typename"/>
                                </ul>
                            </Form.Item>
                            <Form.Item>
                                <button type="button" className="usr_button" onClick={this.checkNew}>
                                    注册
                                </button>
                            </Form.Item>
                            <br/>
                            <a href={"/"} className="to_another">回到登录界面</a>
                        </Form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Register;
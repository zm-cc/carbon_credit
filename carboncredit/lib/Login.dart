import 'package:flutter/material.dart';
import 'constant.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import 'dart:convert';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  // 创建全局的FormState来保存表单的状态
  final _formKey = GlobalKey<FormState>();

  // 创建两个变量来保存用户输入的用户名和密码
  String _username="";
  String _password="";
  
  void login() async{
    var body = {
      'username': _username,
      'password': _password,
    };
    String url = '${Global.host}/CheckUser';
    var client = http.Client();
    var response = await client.post(Uri.parse(url),
    body: json.encode(body), headers: {"content-type": "application/json"});
    int res = 0; //成功登录
    if (response.body == "1") {
      res = 1; //用户不存在
    } else if (response.body == "2") {
      res = 2; //密码错误
    }
    if (res==0) 
    {
      var body = {
        'username': _username,
      };
      String url = '${Global.host}/GetUserId';
      var client = http.Client();
      var response = await client.post(Uri.parse(url),
      body: json.encode(body), headers: {"content-type": "application/json"});
      SharedPreferences prefs = await SharedPreferences.getInstance();
      prefs.setString('username',_username);
      prefs.setInt("userId",int.parse(response.body));
      Navigator.pushNamed(context,'/index');
    }
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text('提示'),
        content: SingleChildScrollView(
          child: ListBody(
            children: [
              Text(res == 0 ? "用户'${_username}'登录成功" : "登录失败"),
                res == 0
                ? const Text("")
                : Text(res == 1 ? "原因：用户'${_username}'不存在" : "原因：密码错误"),
            ],
          ),
        ),
        actions: [
          ElevatedButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
          child: const Text("确定")),
        ],
    ));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Text('Login'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey, // 将全局的_formKey传递给Form组件
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              TextFormField(
                // 创建一个用户名输入框
                decoration: InputDecoration(
                  labelText: 'Username',
                ),
                validator: (value) {
                  // 验证输入的用户名是否为空
                  if (value==null) {
                    return 'Please enter your username';
                  }
                  return null;
                },
                onSaved: (value) {
                  // 当用户名输入框的值发生改变时，保存它的值到变量_username中
                  _username = value!;
                },
              ),
              TextFormField(
                // 创建一个密码输入框
                obscureText: true, // 隐藏密码
                decoration: InputDecoration(
                  labelText: 'Password',
                ),
                validator: (value) {
                  // 验证输入的密码是否为空
                  if (value==null) {
                    return 'Please enter your password';
                  }
                  return null;
                },
                onSaved: (value) {
                  // 当密码输入框的值发生改变时，保存它的值到变量_password中
                  _password = value!;
                },
              ),
              SizedBox(
                height: 16.0,
              ),
              ElevatedButton(
                onPressed: () async {
                  // 当用户点击登录按钮时，验证表单是否有效
                  if (_formKey.currentState!.validate()) {
                    // 如果表单有效，则保存表单状态
                    _formKey.currentState!.save();
                    // 在控制台中输出用户名和密码
                    print('Username: $_username');
                    print('Password: $_password');
                    // 可以在这里添加登录逻辑
                    login();
                  }
                },
                child: Text('Login'),
              ),
              ElevatedButton(
                onPressed: () async {
                  Navigator.pushNamed(context,'/register');
                },
                child: Text('Register'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'constant.dart';

class RegisterPage extends StatefulWidget {
  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterPage> {
  TextEditingController _usernameController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _register() async{
    String username = _usernameController.text;
    String password = _passwordController.text;

    // 在这里执行注册逻辑
    print('Username: $username');
    print('Password: $password');

    var body = {
      'username': username,
      'password': password,
      'age':'0',
      'email': '0@qq.com',
      'image':
          'https://img2.baidu.com/it/u=1367512152,228915312&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',
    };
    String url = '${Global.host}/CreateUser';
    var client = http.Client();
    var response = await client.post(Uri.parse(url),
        body: json.encode(body), headers: {"content-type": "application/json"});
    bool res = true;
    if (response.body == "0") {
      res = false;
    }
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text("提示"),
        content: SingleChildScrollView(
          child: Text(res == true
          ? "用户'${username}'注册成功"
          : "用户'${username}'已存在，注册失败"),
        ),
        actions: [
          ElevatedButton(
            onPressed: () {
              Navigator.of(context).pop();
              if (res == true) {
                Navigator.of(context).pop();
              }
            },
            child: const Text("确定")),
        ],
      ));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Register'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: _usernameController,
              decoration: InputDecoration(
                labelText: 'Username',
              ),
            ),
            SizedBox(height: 16.0),
            TextField(
              controller: _passwordController,
              obscureText: true,
              decoration: InputDecoration(
                labelText: 'Password',
              ),
            ),
            SizedBox(height: 16.0),
            ElevatedButton(
              onPressed: _register,
              child: Text('Register'),
            ),
          ],
        ),
      ),
    );
  }
}

import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;

import 'constant.dart';

class RewardExchangePage extends StatefulWidget {
  @override
  _RewardExchangeState createState() => _RewardExchangeState();
}

class _RewardExchangeState extends State<RewardExchangePage> {

  int points=0;
  
  void _load() async{
  SharedPreferences prefs = await SharedPreferences.getInstance();
  var userId=prefs.getInt('userId');
  var body = {
    'user_id': userId,
  };
  String url = '${Global.host}/GetUserInfo';
  var client = http.Client();
  var response = await client.post(Uri.parse(url),
  body: json.encode(body), headers: {"content-type": "application/json"});
  var data = jsonDecode(response.body);
  //print(data);
  points=int.parse(data[5]);
  setState((){});
}

  @override
  void initState(){
    Future.delayed(Duration.zero, () => setState(() {
       _load();
    }));
  }
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Reward Exchange'),
        automaticallyImplyLeading: false,
      ),
      body: Scrollbar(
        child: SingleChildScrollView(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Remaining Points:${points}',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 16),
            Text(
              'Available Rewards:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 16),
            RewardItem(
              name: '太阳能手表',
              points: 30000,
              image:'asset/太阳能手表.jpg',
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text('确认兑换'),
                    content: SingleChildScrollView(
                      child: ListBody(
                        children: [
                          Text("是否确认购买"),
                        ],
                      ),
                    ),
                    actions: [
                      ElevatedButton(
                        onPressed: () async {
                          if (points>30000)
                          {
                            points-=30000;
                            SharedPreferences prefs = await SharedPreferences.getInstance();
                            var userId=prefs.getInt('userId');
                            var body = {
                              'user_id': userId,
                              'points': 30000,
                            };
                            String url = '${Global.host}/DecPoints';
                            var client = http.Client();
                            var response = await client.post(Uri.parse(url),
                            body: json.encode(body), headers: {"content-type": "application/json"});
                            Fluttertoast.showToast(msg: '兑换成功！');
                            setState((){});
                          }
                          else 
                          {
                            Fluttertoast.showToast(msg: '积分余额不足,兑换失败');
                          }
                          Navigator.of(context).pop();
                        },
                        child: const Text("确定")),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text("取消")),
                    ],
                ));
              },
            ),
            SizedBox(height: 8),
            RewardItem(
              name: '太阳能充电宝',
              points: 10000,
              image:'asset/太阳能充电宝.jpg',
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text('确认兑换'),
                    content: SingleChildScrollView(
                      child: ListBody(
                        children: [
                          Text("是否确认购买"),
                        ],
                      ),
                    ),
                    actions: [
                      ElevatedButton(
                        onPressed: () async {
                          if (points>10000)
                          {
                            points-=10000;
                            SharedPreferences prefs = await SharedPreferences.getInstance();
                            var userId=prefs.getInt('userId');
                            var body = {
                              'user_id': userId,
                              'points': 10000,
                            };
                            String url = '${Global.host}/DecPoints';
                            var client = http.Client();
                            var response = await client.post(Uri.parse(url),
                            body: json.encode(body), headers: {"content-type": "application/json"});
                            Fluttertoast.showToast(msg: '兑换成功！');
                            setState((){});
                          }
                          else 
                          {
                            Fluttertoast.showToast(msg: '积分余额不足,兑换失败');
                          }
                          Navigator.of(context).pop();
                        },
                        child: const Text("确定")),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text("取消")),
                    ],
                ));
              },
            ),
            SizedBox(height: 8),
            RewardItem(
              name: 'LED节能台灯',
              points: 5000,
              image:'asset/LED节能台灯.jpg',
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text('确认兑换'),
                    content: SingleChildScrollView(
                      child: ListBody(
                        children: [
                          Text("是否确认购买"),
                        ],
                      ),
                    ),
                    actions: [
                      ElevatedButton(
                        onPressed: () async {
                          if (points>5000)
                          {
                            points-=5000;
                            SharedPreferences prefs = await SharedPreferences.getInstance();
                            var userId=prefs.getInt('userId');
                            var body = {
                              'user_id': userId,
                              'points': 5000,
                            };
                            String url = '${Global.host}/DecPoints';
                            var client = http.Client();
                            var response = await client.post(Uri.parse(url),
                            body: json.encode(body), headers: {"content-type": "application/json"});
                            Fluttertoast.showToast(msg: '兑换成功！');
                            setState((){});
                          }
                          else 
                          {
                            Fluttertoast.showToast(msg: '积分余额不足,兑换失败');
                          }
                          Navigator.of(context).pop();
                        },
                        child: const Text("确定")),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text("取消")),
                    ],
                ));
              },
            ),
            SizedBox(height: 8),
            RewardItem(
              name: '可降解咖啡杯',
              points: 1000,
              image:'asset/可降解咖啡杯.jpg',
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text('确认兑换'),
                    content: SingleChildScrollView(
                      child: ListBody(
                        children: [
                          Text("是否确认购买"),
                        ],
                      ),
                    ),
                    actions: [
                      ElevatedButton(
                        onPressed: () async {
                          if (points>1000)
                          {
                            points-=1000;
                            SharedPreferences prefs = await SharedPreferences.getInstance();
                            var userId=prefs.getInt('userId');
                            var body = {
                              'user_id': userId,
                              'points': 1000,
                            };
                            String url = '${Global.host}/DecPoints';
                            var client = http.Client();
                            var response = await client.post(Uri.parse(url),
                            body: json.encode(body), headers: {"content-type": "application/json"});
                            Fluttertoast.showToast(msg: '兑换成功！');
                            setState((){});
                          }
                          else 
                          {
                            Fluttertoast.showToast(msg: '积分余额不足,兑换失败');
                          }
                          Navigator.of(context).pop();
                        },
                        child: const Text("确定")),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text("取消")),
                    ],
                ));
              },
            ),
            SizedBox(height: 8),
            RewardItem(
              name: '环境科普书籍',
              points: 2000,
              image:'asset/环保科普书籍.jpg',
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text('确认兑换'),
                    content: SingleChildScrollView(
                      child: ListBody(
                        children: [
                          Text("是否确认购买"),
                        ],
                      ),
                    ),
                    actions: [
                      ElevatedButton(
                        onPressed: () async {
                          if (points>2000)
                          {
                            points-=2000;
                            SharedPreferences prefs = await SharedPreferences.getInstance();
                            var userId=prefs.getInt('userId');
                            var body = {
                              'user_id': userId,
                              'points': 2000,
                            };
                            String url = '${Global.host}/DecPoints';
                            var client = http.Client();
                            var response = await client.post(Uri.parse(url),
                            body: json.encode(body), headers: {"content-type": "application/json"});
                            Fluttertoast.showToast(msg: '兑换成功！');
                            setState((){});
                          }
                          else 
                          {
                            Fluttertoast.showToast(msg: '积分余额不足,兑换失败');
                          }
                          Navigator.of(context).pop();
                        },
                        child: const Text("确定")),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text("取消")),
                    ],
                ));
              },
            ),
            SizedBox(height: 8),
            RewardItem(
              name: '健康亲肤抽纸',
              points: 200,
              image:'asset/健康亲肤抽纸.jpg',
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (_) => AlertDialog(
                    title: const Text('确认兑换'),
                    content: SingleChildScrollView(
                      child: ListBody(
                        children: [
                          Text("是否确认购买"),
                        ],
                      ),
                    ),
                    actions: [
                      ElevatedButton(
                        onPressed: () async {
                          if (points>200)
                          {
                            points-=200;
                            SharedPreferences prefs = await SharedPreferences.getInstance();
                            var userId=prefs.getInt('userId');
                            var body = {
                              'user_id': userId,
                              'points': 200,
                            };
                            String url = '${Global.host}/DecPoints';
                            var client = http.Client();
                            var response = await client.post(Uri.parse(url),
                            body: json.encode(body), headers: {"content-type": "application/json"});
                            Fluttertoast.showToast(msg: '兑换成功！');
                            setState((){});
                          }
                          else 
                          {
                            Fluttertoast.showToast(msg: '积分余额不足,兑换失败');
                          }
                          Navigator.of(context).pop();
                        },
                        child: const Text("确定")),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text("取消")),
                    ],
                ));
              },
            ),
          ],
        ),
        ),
      ),
    );
  }
}

class RewardItem extends StatelessWidget {
  final String name;
  final int points;
  final String image;
  final VoidCallback onPressed;

  const RewardItem({
    required this.name,
    required this.points,
    required this.image,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onPressed,
      child: Container(
        padding: EdgeInsets.all(16.0),
        decoration: BoxDecoration(
          border: Border.all(color: Colors.grey),
          borderRadius: BorderRadius.circular(8.0),
        ),
        child: Row(
          children: [
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    name,
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  ),
                  SizedBox(height: 4),
                  Image.asset(
                    image,
                    height:100,
                    width:100,
                  ),
                  SizedBox(height: 4),
                  Text(
                    'Points: $points',
                    style: TextStyle(fontSize: 14),
                  ),
                ],
              ),
            ),
            Icon(Icons.arrow_forward_ios),
          ],
        ),
      ),
    );
  }
}
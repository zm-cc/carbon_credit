import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'constant.dart';

class PersonInfoPage extends StatefulWidget {
  @override
  _PersonInfoState createState() => _PersonInfoState();
}

class _PersonInfoState extends State<PersonInfoPage> {
  var data=<dynamic>["","","","","","",""];
  int exp=0;
  int level=0;

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
  data = jsonDecode(response.body);
  print(data);
  exp=int.parse(data[6]);
  while (exp>0){
    level+=1;
    exp=exp~/10;
  }
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
        title: Text('Personal Info'),
        automaticallyImplyLeading: false,
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            CircleAvatar(
              radius: 60,
              backgroundImage: AssetImage('asset/profile.jpg'),
            ),
            SizedBox(height: 16),
            Text(
              'Name:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text(
              data[1],
              style: TextStyle(fontSize: 16),
            ),
            SizedBox(height: 16),
            Text(
              'Level:${level}',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text(
              'exp:${data[6]}',
              style: TextStyle(fontSize: 16),
            ),
            SizedBox(height: 16),
            Text(
              'Points:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text(
              data[5],
              style: TextStyle(fontSize: 16),
            ),
            SizedBox(height: 16),
            Text(
              'Email:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text(
              data[4],
              style: TextStyle(fontSize: 16),
            ),
          ],
        ),
      ),
    );
  }
}

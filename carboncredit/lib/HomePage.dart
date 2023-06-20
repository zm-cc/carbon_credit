import 'dart:async';
import 'dart:convert';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:amap_flutter_map/amap_flutter_map.dart';
import 'package:amap_flutter_base/amap_flutter_base.dart';
import 'package:amap_flutter_location/amap_flutter_location.dart';
import 'package:amap_flutter_location/amap_location_option.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'aMapPage.dart';
import 'PersonInfoPage.dart';
import 'RewardExchangePage.dart';
import 'package:image_gallery_saver/image_gallery_saver.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'constant.dart';
import 'package:http/http.dart' as http;

bool isstart=false;
int count=0;

class HomePage extends StatefulWidget {

  @override
  // ignore: library_private_types_in_public_api, no_logic_in_create_state
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> with AMapLocationStateMixin{

  String _currentAddress="";

  @override
  String get iosKey => '42dbeddf9adbc780e7b09da13ddd5755';

  //@override
  //String get androidKey => '1dbf56e2e8a4d0e4cdc2df9efd36bc71';

  @override
  String get androidKey => '764fd13ddaa5d36ccedd5e2577ccde99';

  String? get mapContentApprovalNumber => AMapApprovalNumber.mapContentApprovalNumber;
  String? get satelliteImageApprovalNumber => AMapApprovalNumber.satelliteImageApprovalNumber;

  Timer? _timer;
  Timer? _timer1;

  //final List<LatLng> _trackPath = [];
  //Map mapPolylineListMap = <dynamic, Polyline>{};

  @override
  void initState() {
    super.initState();
    startLocation();
  }

  AMapController? aMapController;

  var options=<String>["地铁","公交","共享单车"];
  String theoption="";
  int optionIndex = 0;

  @override
  Widget build(BuildContext context) {
    startLocation();
    final AMapPage map = AMapPage(iosKey, androidKey, onMapCreated: (AMapController controller) {
      aMapController = controller;
    },);

    List<Widget> approvalNumberWidget = [      if (null != mapContentApprovalNumber) Text(mapContentApprovalNumber!),      if (null != satelliteImageApprovalNumber) Text(satelliteImageApprovalNumber!),    ];

    _timer=Timer.periodic(const Duration(seconds:5),(timer){
      LatLng latlng = LatLng(locationInfo.latitude ?? 31.22, locationInfo.longitude ?? 121.48);
      CameraUpdate cameraUpdate = CameraUpdate.newLatLng(latlng);
      aMapController?.moveCamera(cameraUpdate);
      //_trackPath.add(latlng);
    });

    return Scaffold(
      appBar: AppBar(
        title: const Text("出行"),
        actions: [
          TextButton(onPressed: () async {
            /*LatLng latlng = LatLng(locationInfo.latitude ?? 31.22, locationInfo.longitude ?? 121.48);
            CameraUpdate cameraUpdate = CameraUpdate.newLatLng(latlng);
            aMapController?.moveCamera(cameraUpdate);*/
            await _showBasicModelBottomSheet();
            //optionIndex=optionIndex!+2;
          }, child: const Icon(Icons.location_on_rounded, color: Colors.red,))
        ],
      ),
      body: map,
      floatingActionButton: FloatingActionButton(
        child: Text(isstart?"结束":"开始"),
        ///点击响应事
        onPressed: () async {
          if (theoption=="") await _showBasicModelBottomSheet();
          isstart=!isstart; 
          if (isstart==true){
          _timer1=Timer.periodic(const Duration(seconds:1),(timer){
            count+=1;
          });
          }
          if (isstart==false){
          var tmp=distanceinmeter;
          var tmp1=count;
          Uint8List? image = await (aMapController?.takeSnapshot());
          if (_timer1 !=null && _timer1!.isActive){
            _timer1!.cancel();
          }
          var balance=3;
          showDialog(
            context: context,
            builder: (_) => AlertDialog(
              title: const Text('出行结束'),
              content: SingleChildScrollView(
                child: ListBody(
                  children: [
                    Text("本次出行里程${tmp}米，出行时间${tmp1}秒,出行方式为${theoption}"),
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
          SharedPreferences prefs = await SharedPreferences.getInstance();
          var user_id=prefs.getInt('userId');
          var body = {
            'user_id': user_id,
            'type_id': optionIndex,
            'distance':tmp.toInt(),
            'duration':tmp1,
          };
          String url = '${Global.host}/AddBalance';
          var client = http.Client();
          var response = await client.post(Uri.parse(url),
          body: json.encode(body), headers: {"content-type": "application/json"});
          var result=null;
          if (image!=null){
          result = await ImageGallerySaver.saveImage(
            image
          );
          }
          count=0;
          distanceinmeter=0;
          polylines.clear();
          if(result != null){
                Fluttertoast.showToast(msg: '成功保存到相册中');
            }else{
                Fluttertoast.showToast(msg: '保存失败');
            }
          }
        },
        tooltip: "出行",
        ///设置悬浮按钮的背景
        backgroundColor: isstart? Colors.red : Colors.blue,
        ///获取焦点时显示的颜色
        //focusColor: Colors.green,
        ///鼠标悬浮在按钮上时显示的颜色
        //hoverColor: Colors.yellow,
        ///水波纹颜色
        //splashColor: Colors.deepPurple,
        ///定义前景色 主要影响文字的颜色
        foregroundColor: Colors.white,
        ///配制阴影高度 未点击时
        elevation: 0.0,
        ///配制阴影高度 点击时
        highlightElevation: 20.0,
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      drawer: Container(
        color: Colors.white,
        child: SafeArea(
          child: Column(children: [Text("里程为${distanceinmeter}米" ),
          Text("速度为${locationResult["speed"]}米/秒" ),
          Text("时间为${count}秒" ),
          //createButtonContainer(),
            //Expanded(child: resultList()),
            ...approvalNumberWidget,
          ],),
        ),
        width: MediaQuery.of(context).size.width * 0.8,
      ),
    );
  }

  @override
  void dispose(){
    if (_timer !=null && _timer!.isActive){
      _timer!.cancel();
    }
  }

  Future<int?> _showBasicModelBottomSheet() async {
    return showModalBottomSheet<int>(
      isScrollControlled: false,
      context: context,
      builder: (BuildContext context) {
        return ListView.builder(
          itemBuilder: (BuildContext context, int index) {
            return ListTile(
                title: Text(options[index]),
                onTap: () {
                  theoption=options[index];
                  optionIndex=index+2;
                  print(optionIndex);
                  Navigator.of(context).pop();
                });
          },
          itemCount: options.length,
        );
      },
    );
  }

  Widget createButtonContainer() {
    return Row(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        ElevatedButton(
          onPressed: startLocation,
          child: const Text('开始定位'),
          style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(Colors.blue),
            foregroundColor: MaterialStateProperty.all(Colors.white),
          ),
        ),
        Container(width: 20.0),
        ElevatedButton(
          onPressed: stopLocation,
          child: const Text('停止定位'),
          style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(Colors.blue),
            foregroundColor: MaterialStateProperty.all(Colors.white),
          ),
        )
      ],
    );
  }

  Widget resultList() {
    List<Widget> widgets = <Widget>[];

    locationResult.forEach((key, value) {
      widgets.add(Text('$key: $value', softWrap: true, style: const TextStyle(color: Colors.black),),);
    });

    return ListView(children: widgets, padding: const EdgeInsets.all(8),);
  }
}

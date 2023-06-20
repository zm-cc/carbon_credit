import React from "react";
import '../../css/TravelCompany/CompanyDetail.css';
import {back_url} from "../../Function";

let cid = "";

class CompanyDetail extends React.Component {
    constructor(props) {
        super(props);
        const {company_id} = props;
        cid = company_id;
        this.getCompanyInfo(cid).then();
    }

    getCompanyInfo = async (cid) => {
        let info = {
            'id': cid,
        };
        fetch(back_url + "/GetCompanyInfo", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(info),
            }
        )
            .then(response => response.json())
            .then(data => {
                const show = document.createElement("img");
                show.src = data[6];
                show.height = 500;
                show.width = 600;
                let bs = document.getElementById("imgShow");
                bs.append(show);
                let na = document.getElementById("name_");
                na.innerHTML += data[1];
                let ph = document.getElementById("phone_");
                ph.innerHTML += data[2];
                let em = document.getElementById("email_");
                em.innerHTML += data[3];
                let ty = document.getElementById("type_");
                ty.innerHTML += data[4];
                let emi = document.getElementById("emission_");
                emi.innerHTML += data[5];
                let ra = document.getElementById("rating_");
                ra.innerHTML += "碳排放量=" + data[7] + "×距离，积分=" + data[8] + "×碳排放量";
                let pr = document.getElementById("pricing_");
                pr.innerHTML += data[9] + "元";
            })
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    goBack() {
        window.history.go(-1);
    }

    seeLimit = () => {
        fetch(back_url + "/GetAuthenticationEmission", {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
            }
        )
            .then(response => response.json())
            .then(data => {
                alert("认证机构'" + data[0] + "'持有碳排放量：" + data[1]);
            })
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    buyEmission = () => {
        let emi = document.getElementById("emi").value;
        if (emi === "" || isNaN(emi)) {
            alert("请输入正确格式的碳排放量。");
            return;
        } else {
            let emi_num = parseFloat(emi);
            if (emi_num % 1 !== 0) {
                alert("请输入正确格式的碳排放量。");
                return;
            }
        }
        let info = {
            'company_id': cid,
            'emission': emi,
        };
        fetch(back_url + "/BuyEmission", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(info),
            }
        )
            .then(response => response.json())
            .then(data => {
                if (data.toString() === "-1") {
                    alert("认证机构的碳排放量不足，交易失败");
                } else {
                    alert("购买成功，花费了" + data.toString() + "元。");
                    window.history.go(0);
                }
            })
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    render() {
        return (
            <div className="my_detail">
                <div id="imgShow" className="imgShow"/>
                <ul className="detail">
                    <li id="name_">公司名：</li>
                    <><br/></>
                    <li id="phone_">联系电话：</li>
                    <><br/></>
                    <li id="email_">联系邮箱：</li>
                    <><br/></>
                    <li id="type_">出行工具类别：</li>
                    <><br/></>
                    <li id="rating_">当前比例：</li>
                    <><br/></>
                    <li id="pricing_">碳排放量购买单价：</li>
                    <><br/></>
                    <ul className="emission fl">
                        <li id="emission_">持有的碳排放总量：</li>
                        <button className="seeLimit" onClick={this.seeLimit}>查询可购买的碳排放量</button>
                    </ul>
                    <><br/></>
                    <div className="buy_emi">
                        <input id="emi" type="text" placeholder="想购入的碳排放量（正整数）" className="input"/>
                        <button type="button" onClick={this.buyEmission}>购买碳排放量</button>
                    </div>
                </ul>
            </div>
        );
    }
}

export default CompanyDetail;
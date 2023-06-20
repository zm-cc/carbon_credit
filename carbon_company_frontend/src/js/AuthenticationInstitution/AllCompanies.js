import React from "react";
import '../../css/AuthenticationInstitution/AllCompanies.css';
import {back_url} from "../../Function";

let show = "";

class AllCompanies extends React.Component {
    constructor(props) {
        super(props);
        this.getAllCompany().then();
    }

    getAllCompany = async () => {
        fetch(back_url + "/AllTravelCompany", {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                let table = document.getElementById("user_tab");
                let arr = [];
                let i = 0;
                for (const j in data[0]) {
                    arr[i] = j;
                    i++;
                }
                for (let k = 0; k < data.length; k++) {
                    let row = table.insertRow(table.rows.length);
                    row.style.backgroundColor = "#CED1FF";
                    row.style.color = "#D10054";
                    row.style.fontSize = "20px";
                    for (let l = 0; l < arr.length; l++) {
                        const a = arr[l];
                        const b = row.insertCell(l);
                        b.innerHTML = data[k][a];
                    }
                }
            })
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    updateAll() {
        show = "";
        fetch(back_url + "/UpdatePrices", {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                    show = "价格更新完毕，变化如下\n";
                    for (let k = 0; k < data.length; k++) {
                        show += "出行公司'" + data[k][0] + "'：" + data[k][1] + " → " + data[k][2] + "\n";
                    }
                    alert(show);
                    window.history.go(0);
                }
            )
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    render() {
        return (
            <div className="user_list">
                <div className="update_price">
                    <button type="button" onClick={this.updateAll}>更新价格</button>
                </div>
                <table className="user_tab" id="user_tab">
                    <thead>
                    <tr>
                        <th>出行公司id</th>
                        <th>公司名称</th>
                        <th>联系电话</th>
                        <th>联系邮箱</th>
                        <th>出行工具类型</th>
                        <th>持有碳排放量</th>
                        <th>碳排放量购买单价</th>
                    </tr>
                    </thead>
                    <tbody/>
                </table>
            </div>
        );
    }
}

export default AllCompanies;
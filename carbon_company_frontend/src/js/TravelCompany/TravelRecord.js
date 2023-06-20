import React from "react";
import '../../css/TravelCompany/TravelRecord.css';
import {back_url} from "../../Function";

let cid = "";

class TravelRecord extends React.Component {
    constructor(props) {
        super(props);
        const {company_id} = props;
        cid = company_id;
        this.getTravelRecord(cid).then();
    }

    getTravelRecord = async (cid) => {
        let info = {
            'company_id': cid,
        }
        fetch(back_url + "/GetCompanyRecord", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(info),
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                let table = document.getElementById("record_tab");
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

    goBack() {
        let num = window.location.href.split('?');
        window.location.href = "/Page?" + num[1];
    }

    render() {
        return (
            <div className="travel">
                <table className="record_tab" id="record_tab">
                    <thead>
                    <tr>
                        <th>记录编号</th>
                        <th>用户</th>
                        <th>出行公司</th>
                        <th>工具类别</th>
                        <th>出行时长</th>
                        <th>出行距离</th>
                        <th>获得积分</th>
                    </tr>
                    </thead>
                    <tbody/>
                </table>
            </div>
        );
    }
}

export default TravelRecord;
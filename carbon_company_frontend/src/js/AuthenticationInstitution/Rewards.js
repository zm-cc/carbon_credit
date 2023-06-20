import React from "react";
import '../../css/AuthenticationInstitution/Rewards.css';
import {back_url} from "../../Function";

class Rewards extends React.Component {
    constructor(props) {
        super(props);
        this.getAllRewards().then();
    }

    getAllRewards = async () => {
        fetch(back_url + "/AllRewards", {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            }
        )
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                let table = document.getElementById("reward_tab");
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
                        const b = row.insertCell(l);
                        if (l === 1) {
                            const pic = document.createElement("img");
                            pic.src = data[k][l];
                            pic.width = 250;
                            pic.height = 250;
                            b.append(pic);
                        } else {
                            const a = arr[l];
                            b.innerHTML = data[k][a];
                        }
                    }
                }
            })
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    render() {
        return (
            <div className="reward_list">
                <table className="reward_tab" id="reward_tab">
                    <thead>
                    <tr>
                        <th>奖励id</th>
                        <th>图片</th>
                        <th>名称</th>
                        <th>所需积分</th>
                        <th>所需等级</th>
                        <th>库存</th>
                    </tr>
                    </thead>
                    <tbody/>
                </table>
            </div>
        );
    }
}

export default Rewards;
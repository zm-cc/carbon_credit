import React from "react";
import '../../css/AuthenticationInstitution/ManagerDetail.css';
import {back_url} from "../../Function";

let cid = "";

class ManagerDetail extends React.Component {
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
                let na = document.getElementById("name");
                na.innerHTML += data[1];
                let ph = document.getElementById("phone");
                ph.innerHTML += data[2];
                let em = document.getElementById("email");
                em.innerHTML += data[3];
                let emi = document.getElementById("emission");
                emi.innerHTML += data[5];
            })
            .catch(function (ex) {
                console.log('parsing failed', ex)
            })
    }

    goBack() {
        window.history.go(-1);
    }

    render() {
        return (
            <div className="manager_detail">
                <ul className="root_detail">
                    <li id="name">机构名称：</li>
                    <><br/></>
                    <li id="phone">联系电话：</li>
                    <><br/></>
                    <li id="email">联系邮箱：</li>
                    <><br/></>
                    <ul className="emission fl">
                        <li id="emission">持有的碳排放总量：</li>
                    </ul>
                </ul>
            </div>
        );
    }
}

export default ManagerDetail;
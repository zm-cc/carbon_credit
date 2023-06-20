import React from "react";
import '../../css/AuthenticationInstitution/Manager.css';
import CompanyDetail from "./CompanyDetail";
import TravelRecord from "./TravelRecord";

class Page extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentPage: 'page1',
        };
    }

    switchPage = (page) => {
        this.setState({currentPage: page});
    };

    handleLogout = () => {
        const confirmed = window.confirm('确定要退出登录吗？');
        if (confirmed) {
            window.location.href = "/";
        }
    };

    render() {
        let num = window.location.search.split('?');
        const {currentPage} = this.state;

        return (
            <div>
                <div id="sidebar">
                    <ul>
                        <li onClick={() => this.switchPage('page1')}
                            className={currentPage === 'page1' ? 'active' : ''}>
                            公司信息
                        </li>
                        <li onClick={() => this.switchPage('page2')}
                            className={currentPage === 'page2' ? 'active' : ''}>
                            用户出行记录
                        </li>
                    </ul>
                    <button className="logout_button" type="button" onClick={this.handleLogout}>退出登录</button>
                </div>
                <div className="content">
                    {currentPage === 'page1' && (
                        <div id="page1">
                            <CompanyDetail company_id={num[1]}/>
                        </div>
                    )}
                    {currentPage === 'page2' && (
                        <div id="page2">
                            <TravelRecord company_id={num[1]}/>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

export default Page;
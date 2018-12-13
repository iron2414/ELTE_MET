import * as React from "react";
import { ElteMetApi } from "../api/manual/api";
import Response from "../api/manual/interfaces/Response";
import * as css from "./LoginScreen.css";

interface Props {
    api: ElteMetApi;
    requestHandler: <T extends any>(
        generator: () => Promise<Response<T>>,
    ) => Promise<T | undefined>;
}

class State {
    username: string = "";
    password: string = "";
}

export default class LoginScreen extends React.Component<Props, State> {
    state = new State();

    render() {
        return (
            <div className={css.root} style={{backgroundImage: 'url(img/old_west_building_architecture_outside_scotland_university_european-144731.jpg!d.jpeg)'}}>
                <div className={css.loginBox}>
                    User name:{" "}
                    <input
                        value={this.state.username}
                        onChange={e =>
                            this.setState({ username: e.target.value })
                        }
                    />
                    <br />
                    Password:&nbsp;&nbsp;{" "}
                    <input
                        type="password"
                        value={this.state.password}
                        onChange={e =>
                            this.setState({ password: e.target.value })
                        }
                    /><br />
                    <button
                    style={{
                        width: 235,
                        marginTop: 5,
                    }}
                        onClick={() =>
                            this.props.requestHandler(() =>
                                this.props.api.logIn(
                                    this.state.username,
                                    this.state.password,
                                ),
                            )
                        }
                    >
                        Login ▸
                    </button>
                </div>
                <div className={css.logo}>ELTE-MET<img src="img/86364.svg" />
                </div>
            </div>
        );
    }
}

import * as React from "react";
import * as css from "./View.css";

interface Props {
    data: any;
}

export default ({ data }: Props) => (
    <div className={css.root}>
        {(() => {
            switch (typeof data) {
                case "number":
                    return data;
                case "string":
                    return data;
                case "boolean":
                    <input type="checkbox" disabled checked={data} />;
                case "object":
                    if (data == null) {
                        return "-";
                    }
                    if (data instanceof Array) {
                        return data.map(e => (
                            <>
                                <span className={css.atomic}>{e.name}</span>
                                <span> </span>
                            </>
                        ));
                    } else {
                        return data.name;
                    }
                default:
                    return JSON.stringify(data);
            }
        })()}
    </div>
);

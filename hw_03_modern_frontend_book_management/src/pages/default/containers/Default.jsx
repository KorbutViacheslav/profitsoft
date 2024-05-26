import React from 'react';
import Typography from 'components/Typography';
import Header from "../header/Header";
import Dashboard from "../dashboard/Dashboard";

function Default() {

    return (
        <Typography>
                <Header/>
                <Dashboard/>
        </Typography>
    );
}

export default Default;

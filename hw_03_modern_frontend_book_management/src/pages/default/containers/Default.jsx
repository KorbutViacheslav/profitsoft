import React from 'react';
import Typography from 'components/Typography';
import Header from "../header/Header";
import {Route, Routes} from "react-router-dom";
import Dashboard from "../dashboard/Dashboard";
import Postauthor from "../author/Postauthor";
import Updateauthor from "../author/Updateauthor";

function Default() {

    return (
        <Typography>
            <>
                <Header/>
                <Routes>
                    <Route path="/" element={<Dashboard/>}></Route>
                    <Route path="/author" element={<Postauthor/>}></Route>
                    <Route path="/author/:id" element={<Updateauthor/>}></Route>
                </Routes>
            </>
        </Typography>
    );
}

export default Default;

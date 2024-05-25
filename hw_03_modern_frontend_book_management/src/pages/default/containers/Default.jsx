import React from 'react';
import Typography from 'components/Typography';
import Header from "../header/Header";
import {Route, Routes} from "react-router-dom";
import Dashboard from "../dashboard/Dashboard";
import Postauthor from "../author/Postauthor";
import Updateauthor from "../author/Updateauthor";
import Nomatch from "../noMatch/Nomatch";

function Default() {

    return (
        <Typography>
            <>
                <Header/>
                <Routes>
                    <Route path="/" element={<Dashboard/>}></Route>
                    <Route path="/author" element={<Postauthor/>}></Route>
                    <Route path="/author/:id" element={<Updateauthor/>}></Route>
                    <Route path="*" element={<Nomatch/>}></Route>
                </Routes>
            </>
        </Typography>
    );
}

export default Default;

import React, { useState, useEffect } from 'react';
import { Line} from '@ant-design/charts';
import { Divider } from 'antd';

import { dataset }  from './data.js'

export default function Index() {
    const [data, setData] = useState(dataset);
    // useEffect(() => {
    //     asyncFetch();
    // }, []);
    // const asyncFetch = () => {
    //     fetch('https://gw.alipayobjects.com/os/bmw-prod/1d565782-dde4-4bb6-8946-ea6a38ccf184.json')
    //         .then((response) => response.json())
    //         .then((json) => {setData(json)} )
    //         .catch((error) => {
    //             console.log('fetch data failed', error);
    //         });
    // };
    var config = {
        data: data,
        padding: 'auto',
        xField: 'Date',
        yField: 'scales',
        annotations: [
            {
                type: 'regionFilter',
                start: ['min', 'median'],
                end: ['max', '0'],
                color: '#F4664A',
            },
            {
                type: 'text',
                position: ['min', 'median'],
                content: '中位数',
                offsetY: -4,
                style: { textBaseline: 'bottom' },
            },
            {
                type: 'line',
                start: ['min', 'median'],
                end: ['max', 'median'],
                style: {
                    stroke: '#F4664A',
                    lineDash: [2, 2],
                },
            },
        ],
    };
    return (
    <div style={{ margin: "10px 0px 40px 20px" }} >
        <Divider orientation="left" style={{ margin: '25px 0px' }} >每月营业额(单位: 元)</Divider>
        <Line  {...config} />;
    </div>)

}

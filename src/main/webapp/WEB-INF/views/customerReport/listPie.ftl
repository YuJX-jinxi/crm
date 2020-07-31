<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="/js/js/plugins/echarts/echarts.common.min.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '潜在客户报表',
            subtext: '分组类型 : ${groupTypeName!}',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data:${legend} /*['虚坤', '骚坤', '三碗坤', '丧坤']*/
        },
        series: [
            {
                name: '潜在客户新增数',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: ${series},
                    /*{value: 335, name: '虚坤'},
                    {value: 310, name: '骚坤'},
                    {value: 234, name: '三碗坤'},
                    {value: 135, name: '丧坤'},*/

                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
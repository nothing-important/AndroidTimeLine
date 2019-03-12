package com.example.administrator.androidtimeline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dataList = ArrayList<TimeLineBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData(){
        for (i in 1..20){
            var timeLineBean = TimeLineBean()
            timeLineBean.desc = "这是第$i 个状态"
            when {
                i % 2 == 0 -> timeLineBean.img = R.mipmap.add_consignee
                i % 3 == 0 -> timeLineBean.img = R.mipmap.commit_order_nocheck
                else -> timeLineBean.img = R.mipmap.commit_order_check
            }
            timeLineBean.time = "2019年$i 月$i 日"
            dataList.add(timeLineBean)
        }
    }

    private fun initView(){
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerTimeLine.layoutManager = linearLayoutManager
        recyclerTimeLine.addItemDecoration(TimeLineItemDecoration(this , dataList))
        var timeLineAdapter = TimeLineAdapter(this , dataList)
        recyclerTimeLine.adapter = timeLineAdapter
    }
}

package com.example.admin.swipablerecyclerview

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val names = ArrayList<String>()
    private var adapter: DataAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var alertDialog: AlertDialog.Builder? = null
    private var et_name: EditText? = null
    private var edit_position: Int = 0
    private var view: View? = null
    private var add = false
    private val p = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initDialog()
    }

    private fun initViews() {
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(this)
        recyclerView = findViewById(R.id.card_recycler_view) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        adapter = DataAdapter(names)
        recyclerView!!.adapter = adapter
        names.add("Kaushal")
        names.add("Alex")
        names.add("Ram")
        names.add("Abhishek")
        names.add("Narendra Modi")
        adapter!!.notifyDataSetChanged()
        initSwipe()

    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    adapter!!.removeItem(position)
                } else {
                    removeView()
                    edit_position = position
                    alertDialog!!.setTitle("Edit Name")
                    et_name!!.setText(names[position])
                    alertDialog!!.show()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        p.color = Color.parseColor("#388E3C")
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_edit_white)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.color = Color.parseColor("#D32F2F")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                        val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun removeView() {
        if (view!!.parent != null) {
            (view!!.parent as ViewGroup).removeView(view)
        }
    }

    private fun initDialog() {
        alertDialog = AlertDialog.Builder(this)
        view = layoutInflater.inflate(R.layout.dialog_layout, null)
        alertDialog!!.setView(view)
        alertDialog!!.setPositiveButton("Save") { dialog, which ->
            if (add) {
                add = false
                adapter!!.addItem(et_name!!.text.toString())
                dialog.dismiss()
            } else {
                names[edit_position] = et_name!!.text.toString()
                adapter!!.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        et_name = view!!.findViewById(R.id.et_name) as EditText
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.fab -> {
                removeView()
                add = true
                alertDialog!!.setTitle("Add Name")
                //   et_country.setText("");
                alertDialog!!.show()
            }
        }


    }

}

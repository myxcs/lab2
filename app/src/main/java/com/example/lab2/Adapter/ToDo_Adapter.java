package com.example.lab2.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.MainActivity;
import com.example.lab2.R;
import com.example.lab2.dao.ToDoDao;
import com.example.lab2.model.ToDoModel;

import java.util.ArrayList;

public class ToDo_Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private final Context context;
    private ArrayList<ToDoModel> list;

    public ToDoDao toDoDao;

    EditText edt_title, edt_content, edt_date, edt_type;

    Button btn_update, btn_cancel;

    public ToDo_Adapter(Context context, ArrayList<ToDoModel> list, ToDoDao toDoDao) {
        this.context = context;
        this.list = list;
        this.toDoDao = toDoDao;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((MainActivity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_rcv_todo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvContent.setText(list.get(position).getTitle());
        holder.tvDate.setText(list.get(position).getDate());
        if (list.get(position).getStatus() == 1) {
            holder.chk.setChecked(true);
            holder.tvContent.setPaintFlags(holder.tvContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            holder.chk.setChecked(false);
            holder.tvContent.setPaintFlags(holder.tvContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }



        holder.iv_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("canh bao");
                builder.setIcon(R.drawable.baseline_warning_amber_24);
                builder.setMessage("ban co chac chan xoa cong viec nay khong?");

                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(holder.getAdapterPosition()).getId();
                        boolean check = toDoDao.delete(id);
                        if (check){
                            Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = toDoDao.getAll();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }else{
                            Toast.makeText(context, "xoa that bai !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();




            }
        });


        holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = list.get(holder.getAdapterPosition()).getId();
                boolean check = toDoDao.updateStatus(id, holder.chk.isChecked());
                if (check){
                    Toast.makeText(context, "da update status", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = toDoDao.getAll();
                    notifyDataSetChanged();


                }else {
                    Toast.makeText(context, "update status that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.iv_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoModel todoo = list.get(holder.getAdapterPosition());
                DialogUpdate(todoo);
            }
        });



    }

    public  void DialogUpdate(ToDoModel td){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_todo, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        edt_title = view.findViewById(R.id.edt_title);
        edt_content = view.findViewById(R.id.edt_Content);
        edt_date = view.findViewById(R.id.edt_date);
        edt_type = view.findViewById(R.id.edt_type);
        btn_update = view.findViewById(R.id.btn_update);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        edt_title.setText(td.getTitle());
        edt_content.setText(td.getContent());
        edt_date.setText(td.getDate());
        edt_type.setText(td.getType());

        edt_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mucDoCv = {"kho", "trung binh", "de"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("chon muc do cong viec");
                builder.setItems(mucDoCv, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_type.setText(mucDoCv[which]);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString();
                String content = edt_content.getText().toString();
                String date = edt_date.getText().toString();
                String type = edt_type.getText().toString();


                ToDoModel todo = new ToDoModel(td.getId(), title, content, date, type, td.getStatus());
                boolean check = toDoDao.update(todo);
                if (check){
                    list.clear();
                    list = toDoDao.getAll();
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                    Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Update that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

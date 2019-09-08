package com.developer.arsltech.tasbeehcounter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class zikar_adapter extends RecyclerView.Adapter<zikar_adapter.ProductViewHolder> {


    HelperClass myDB;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<zikar> zikarList;


    //getting the context and product list with constructor
    public zikar_adapter(Context mCtx, List<zikar> zikarList) {
        this.mCtx = mCtx;
        this.zikarList = zikarList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.zikar_cardview, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        zikar product = zikarList.get(position);

        //binding the data with the viewholder views
        holder.textViewID1.setText((String.valueOf(product.getId())));
        holder.textViewTitle.setText(product.getZikarName());
        holder.textViewPrice.setText(String.valueOf(product.getCounter()));
        holder.textViewRemainingCount.setText(String.valueOf(product.getRemainingCounter()));



    }


    @Override
    public int getItemCount() {
        return zikarList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder  {

        TextView textViewTitle, textViewPrice,textViewID1,textViewRemainingCount;



        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewID1 = itemView.findViewById(R.id.textViewID);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRemainingCount = itemView.findViewById(R.id.textViewRemaining);



            final CardView card_view = (CardView) itemView.findViewById(R.id.card_view);

            card_view.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                    builder.setMessage("What do you Want?");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            myDB = new HelperClass(mCtx);
                            myDB.deleteData(textViewTitle.getText().toString());
                            ((Activity)mCtx).finish();
                            Toast.makeText(mCtx, "Zikar Deleted", Toast.LENGTH_SHORT).show();
                            mCtx.startActivity(new Intent(mCtx,zikar_list.class));
                        }
                    });

                    builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent pass = new Intent(mCtx,zika_counter.class);
                            pass.putExtra("counterPrevious",textViewRemainingCount.getText().toString());
                            pass.putExtra("zikrName",textViewTitle.getText().toString());
                            pass.putExtra("counterTotal",textViewPrice.getText().toString());

                            mCtx.startActivity(pass);
                            ((Activity)mCtx).finish();
                        }
                    });

                    //creating alert dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mCtx.getResources().getColor(R.color.colorPrimaryDark));
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mCtx.getResources().getColor(R.color.colorPrimaryDark));
                }
            });


        }

        }


    }

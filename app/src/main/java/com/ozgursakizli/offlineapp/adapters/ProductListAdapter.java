package com.ozgursakizli.offlineapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ozgursakizli.offlineapp.R;
import com.ozgursakizli.offlineapp.models.ProductModel;
import com.ozgursakizli.offlineapp.utils.Util;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemViewHolder> {

    private List<ProductModel> productList;
    private Context context;
    private OnProductListItemClicked onProductListItemClicked;

    public ProductListAdapter(Context context, List<ProductModel> productList, OnProductListItemClicked onProductListItemClicked) {
        this.context = context;
        this.productList = productList;
        this.onProductListItemClicked = onProductListItemClicked;
    }

    @Override
    public ProductItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist_item, parent, false);
        return new ProductListAdapter.ProductItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductItemViewHolder holder, int position) {
        ProductModel product = productList.get(position);

        if (product.getImage() != null && !product.getImage().equals("")) {
            if (!Util.isNetworkActive()) {
                Picasso.with(context).load(product.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imgProduct);
            } else {
                Picasso.with(context).load(product.getImage()).into(holder.imgProduct);
            }

            // Cache image
            Picasso.with(context).load(product.getImage()).fetch();
        }

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtName;
        TextView txtPrice;

        ProductItemViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            txtName = view.findViewById(R.id.txt_productname);
            txtPrice = view.findViewById(R.id.txt_productprice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProductListItemClicked.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnProductListItemClicked {
        void onItemClicked(int position);
    }

}


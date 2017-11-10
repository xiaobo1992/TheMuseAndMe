package com.bobo.normalman.themuseandme.view.profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/2/17.
 */

public class ProductAdapter extends RecyclerView.Adapter {
    List<Product> data;

    public ProductAdapter(Coach coach) {
        this.data = coach.products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        Product product = data.get(position);
        viewHolder.description.setText(product.getOfferDescription());
        viewHolder.price.setText(product.getProductPrice());
        viewHolder.productName.setText(product.getOfferName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.product_description)
        TextView description;
        @BindView(R.id.product_price)
        TextView price;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
